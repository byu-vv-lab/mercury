package edu.byu.cs.vv.finder.unmatchedendpoint;

import edu.byu.cs.vv.finder.AbstractEncoder;
import edu.byu.cs.vv.finder.ProgramStepper;
import edu.byu.cs.vv.ast.Match;
import edu.byu.cs.vv.ast.operations.*;
import edu.byu.cs.vv.util.Pair;
import edu.byu.cs.vv.ast.Process;
import edu.byu.cs.vv.ast.Schedule;
import com.microsoft.z3.*;

import java.util.*;

public class UmEPEncoder extends AbstractEncoder {

    UnmatchedEndpointPattern pattern;
    int[] lastrInShape;
    int[][] lastsInShape;

    //for unmatched endpoint, make sure all sends in source endpoint should be matched
    Map<Send, List<Receive>> pattern_match;
    Map<Receive, List<Send>> match_table;
    Operation lastr = null;
    Operation lastsz = null;
    Map<Integer, Operation> lasts = new HashMap<>();

    //pair.first is op expr, pair.second is op time

    //data structure to record the last wait on each process
    Wait[] lastwait;

    public boolean zeroSemantics = false;

    public UmEPEncoder(ProgramStepper program,
                       UnmatchedEndpointPattern pattern,
                       int[] lastrInShape,
                       int[][] lastsInShape,
                       Map<Send, List<Receive>> pattern_match,
                       Map<Receive, List<Send>> match_table) {
        super(program);
        this.pattern = pattern;
        this.lastrInShape = lastrInShape;
        this.lastsInShape = lastsInShape;
        this.pattern_match = pattern_match;
        this.match_table = match_table;
        lastwait = new Wait[program.size()];
        Arrays.fill(lastwait, null);
    }

    @Override
    protected Schedule getSchedule(Model model) {
        Schedule schedule = new Schedule();
        try {
            for (Operation key : operation_expr_map.keySet()) {
                Expr cont = model.ConstInterp(operation_expr_map.get(key).getSecond());
                int time = Integer.parseInt(cont.toString());
                key.order = time;
                schedule.add(key);
            }
        } catch (Z3Exception e) {
            e.printStackTrace();
        }
        Collections.sort(schedule);
        return schedule;
    }

    @Override
    public boolean encodeProgram() {
        try {
            solver.setup();
            for (Process process : program) {
                lastr = null;
                lasts.clear();
                for (int i = 0; i < program.blockPoint(process); i++) {
                    Encoding(process.getOp(i));
                }
            }
            encodeMatches();
            return true;
        } catch (Z3Exception e) {
            return false;
        }
    }

    public void Encoding(Operation operation) throws Z3Exception {
        if (operation instanceof Receive) {
            encodeReceive((Receive) operation);
        } else if (operation instanceof Wait) {
            //assume only receives have waits
            encodeWait((Wait) operation);
        } else if (operation instanceof Send) {
            encodeSend((Send) operation);
        }
        //TODO: add HB for barriers and other operations
    }

    protected void encodeReceive(Receive op) throws Z3Exception {
        IntExpr time = solver.MkTime("T" + op.name);
        Expr recv = solver.mkRecv("R" + op.name);
        Pair<Expr, IntExpr> recvinfo = new Pair<>(recv, time);
        operation_expr_map.put(op, recvinfo);
        IntExpr var = solver.MkTime("var" + op.name);
        //TODO: add nw when the receive is non-blocking
        IntExpr nw = null;
        if (!op.isBlock) {
            Wait wait = op.NearestWait;
            //add nearest inclosing wait for recv
            if (!operation_expr_map.containsKey(wait)) {
                //TODO:generate the time for wait?
                IntExpr waittime = solver.MkTime("T" + op.name);
                Pair<Expr, IntExpr> waitinfo = new Pair<>(null, waittime);
                operation_expr_map.put(wait, waitinfo);
            }
            //add HB over a receive and its nw
            solver.addFormula(solver.HB(operation_expr_map.get(op).getSecond(),
                    operation_expr_map.get(wait).getSecond()));
        }
        solver.addFormula(solver.initRecv(recv, time, var, nw, op));
        if (lastr != null) {
            solver.addFormula(solver.HB(operation_expr_map.get(lastr).getSecond(), time));
        }

        if (lastsz != null && zeroSemantics) {
            solver.addFormula(solver.HB(operation_expr_map.get(lastsz).getSecond(), time));
        }

        lastr = op;
        //lastr will be initilized to null at beginning of traversing each process
    }

    @Override
    protected void encodeSend(Send op) throws Z3Exception {
        IntExpr time = solver.MkTime("T" + op.name);
        Expr send = solver.mkSend("S" + op.name);
        Pair<Expr, IntExpr> sendinfo = new Pair<>(send, time);
        operation_expr_map.put(op, sendinfo);
        solver.addFormula(solver.initSend(send, time, op));

        if (lastr != null) {
            solver.addFormula(solver.HB(operation_expr_map.get(lastr).getSecond(), time));
        }

        if (lastsz != null) {
            solver.addFormula(solver.HB(operation_expr_map.get(lastsz).getSecond(), time));
        }

        if (lasts.containsKey(op.dest)) {
            //do not repeatedly add hb for last send and op under zero buffer
            if (!lasts.get(op.dest).equals(lastsz))
                solver.addFormula(solver.HB(operation_expr_map.get(lasts.get(op.dest)).getSecond(), time));
        }

        if (lastwait[op.process_rank] != null) {
            //does not constrain the HB for a wait and the next receive, defined in NFM paper
            solver.addFormula(solver.HB(operation_expr_map.get(lastwait[op.process_rank]).getSecond(), time));
        }

        //add nearest inclosing send for dest
        lasts.put(op.dest, op);
        if (zeroSemantics)
            lastsz = op;
    }

    @Override
    protected void encodeWait(Wait op) throws Z3Exception {
        //HB over this wait and its next send is added when op is a send
        lastwait[op.process_rank] = (Wait) op;
    }

    @Override
    protected void encodeBarrier(Barrier op) throws Z3Exception {
        // Do nothing
    }

    @Override
    protected void encodeMatches() throws Z3Exception {
        Collection<Collection<Match>> matches = this.filterMatches();
        for (Collection<Match> match_set : matches) {
            encodeMatchSet(match_set);
        }
    }

    @Override
    protected Collection<Collection<Match>> filterMatches() {
        Collection<Collection<Match>> results = new HashSet<>();
        for (Receive receive : match_table.keySet()) {
            if (receive.rank > lastrInShape[receive.dest])
                continue;
            Collection<Match> inner = new HashSet<>();
            for (Send send : match_table.get(receive)) {
                if (send.rank > lastsInShape[send.dest][send.src])
                    continue;
                inner.add(new Match(send, receive));
            }
            if (inner.size() > 0) {
                results.add(inner);
            } else {
                throw new RuntimeException("No suitable match candidates");
            }
        }
        for (Send send : pattern_match.keySet()) {
            if (send.dest != pattern.process.rank || send.src != pattern.deterministic.src
                    || send.rank > lastsInShape[send.dest][send.src])
                continue;
            Collection<Match> inner = new HashSet<>();
            for (Receive receive : pattern_match.get(send)) {
                if (receive.rank > lastrInShape[receive.dest])
                    continue;
                inner.add(new Match(send, receive));
            }
            if (inner.size() > 0) {
                results.add(inner);
            } else {
                throw new RuntimeException("No suitable match candidates");
            }
        }
        return results;
    }

    @Override
    protected void encodeMatchSet(Collection<Match> matches) throws Z3Exception {
        List<BoolExpr> exprs = new ArrayList<>();
        for (Match match : matches) {
            Expr rexpr = operation_expr_map.get(match.receive).getFirst();
            Expr sexpr = operation_expr_map.get(match.send).getFirst();
            if (rexpr != null && sexpr != null) {
                if (zeroSemantics) {
                    exprs.add(solver.Match(rexpr, sexpr));
                } else {
                    exprs.add(solver.Match_Zero(rexpr, sexpr));
                }
            }
        }
        if (exprs.size() > 1) {
            solver.addFormula(solver.mkOr(exprs));
        } else if (exprs.size() == 1) {
            solver.addFormula(exprs.get(0));
        }
    }

}
