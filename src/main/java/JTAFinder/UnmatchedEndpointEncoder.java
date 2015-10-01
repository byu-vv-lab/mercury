package JTAFinder;

import JTAFinder.Patterns.UnmatchedEndpoint;
import JTASyntax.*;
import JTASyntax.Process;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Z3Exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnmatchedEndpointEncoder extends Encoder {

    UnmatchedEndpoint pattern;
    int[] lastrInShape;
    int[][] lastsInShape;

    //for unmatched endpoint, make sure all sends in source endpoint should be matched
    Map<Send, List<Receive>> pattern_match;
    Map<Receive, List<Send>> match_table;
    Operation lastr = null;
    Operation lastsz = null;
    Map<Integer, Operation> lasts = new HashMap<>();

    //pair.first is op expr, pair.second is op time
    Map<Operation, Pair<Expr, IntExpr>> operation_expr_map = new HashMap<>();

    //data structure to record the last wait on each process
    Wait[] lastwait;

    public boolean isZero = false;

    public UnmatchedEndpointEncoder(ProgramStepper program,
                                    UnmatchedEndpoint pattern,
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
            encodeMatch();
            return true;
        } catch (Z3Exception e) {
            return false;
        }
    }

    public void Encoding(Operation operation) throws Z3Exception {
        if(operation instanceof Receive) {
            Receive op = (Receive) operation;

            IntExpr time = solver.MkTime("T" + op.name);
            Expr recv = solver.mkRecv("R" + op.name);
            Pair<Expr,IntExpr> recvinfo = new Pair<>(recv,time);
            operation_expr_map.put(op, recvinfo);
            IntExpr var = solver.MkTime("var" + op.name);
            //TODO: add nw when the receive is non-blocking
            IntExpr nw = null;
            if(!op.isBlock) {
                Wait wait = op.NearestWait;
                //add nearest inclosing wait for recv
                if(!operation_expr_map.containsKey(wait)) {
                    //TODO:generate the time for wait?
                    IntExpr waittime = solver.MkTime("T" + op.name);
                    Pair<Expr,IntExpr> waitinfo = new Pair<>(null,waittime);
                    operation_expr_map.put(wait, waitinfo);
                }
                //add HB over a receive and its nw
                solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(op).getSecond(),
                        (IntExpr)operation_expr_map.get(wait).getSecond()));
            }
            solver.addFormula(solver.initRecv(recv, op.src, op.dest, time, var, nw));
            if(lastr != null) {
                solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastr).getSecond(),
                        time));
            }

            if(lastsz!=null && isZero) {
                solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastsz).getSecond(),
                        time));
            }

            lastr = op;
            //lastr will be initilized to null at beginning of traversing each process
        }
        else if(operation instanceof Wait) //assume only receives have waits
        {
            //HB over this wait and its next send is added when op is a send
            lastwait[operation.process_rank] = (Wait) operation;
        }
        //TODO: add HB for barriers and other operations
        else if(operation instanceof Send)
        {
            Send op = (Send) operation;
            IntExpr time = solver.MkTime("T" + op.name);
            Expr send = solver.mkSend("S" + op.name);
            Pair<Expr,IntExpr> sendinfo = new Pair<>(send,time);
            operation_expr_map.put(op, sendinfo);
            solver.addFormula(solver.initSend(send, op.src, op.dest, time, op.value));

            if(lastr != null) {
//				System.out.println("send: " + send + "recv: " + operation_expr_map.get(lastr).getFirst());
//				System.out.println("lastr_time: "+ (IntExpr)operation_expr_map.get(lastr).getSecond()+ " send_time: "+time);
                //if non-blocking receive is applied, should be nw < send
                solver.addFormula(solver.HB(operation_expr_map.get(lastr).getSecond(),
                        time));
            }

            if(lastsz !=null)
            {
                solver.addFormula(solver.HB(operation_expr_map.get(lastsz).getSecond(),
                        time));
            }

            if(lasts.containsKey(op.dest))
            {
                //do not repeatedly add hb for last send and op under zero buffer
                if(!lasts.get(op.dest).equals(lastsz))
                    solver.addFormula(solver.HB(operation_expr_map.get(lasts.get(op.dest)).getSecond(),
                            time));
            }

            if(lastwait[op.process_rank] != null)
            {
                //does not constrain the HB for a wait and the next receive, defined in NFM paper
                solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastwait[op.process_rank]).getSecond(),
                        time));
            }
//			}

            //add nearest inclosing send for dest
            lasts.put(op.dest,op);
            if(isZero)
                lastsz = op;
        }
    }

    public void encodeMatch() throws Z3Exception
    {
        //two parts: first, for every receive r, there must be a match, (r,.)
        continuepoint:
        for(Receive r : match_table.keySet())
        {
            //only encode match when r is in shape
            if(r.rank > lastrInShape[r.dest])
                continue continuepoint;
            Expr rExpr = operation_expr_map.get(r).getFirst();
//			IntExpr rTime = operation_expr_map.get(r).getSecond();
            BoolExpr a = null;
            BoolExpr b = null;

            continuepoint1:
            for(Send s : match_table.get(r))
            {
                //only encode match when s is in shape
                if(s.rank > lastsInShape[s.dest][s.src])
                    continue continuepoint1;
                Expr sExpr = operation_expr_map.get(s).getFirst();
//				IntExpr sTime = operation_expr_map.get(s).getSecond();
                if(rExpr != null && sExpr != null)//should not be null
                {
                    if(!isZero)
                        a = solver.Match(rExpr, sExpr);
                    else a = solver.Match_Zero(rExpr, sExpr);
                    b = (b!=null)?solver.mkOr(a, b):a;//make or for all matches for receive r
                }
            }
            solver.addFormula(b);
        }

        //second, for every send s that can match pattern receive R(c), there must be a match (s,.)

        for(Send s : pattern_match.keySet())
        {
            //only encode send if dest and src is pattern requires and it is in shape
            if(s.dest != pattern.process.rank || s.src != pattern.deterministic.src
                    || s.rank > lastsInShape[s.dest][s.src])
                continue;
            Expr sExpr = operation_expr_map.get(s).getFirst();
//			IntExpr sTime = operation_expr_map.get(s).getSecond();
            BoolExpr a = null;
            BoolExpr b = null;
            for(Receive r : pattern_match.get(s))
            {
                //only encode when r is in shape
                if(r.rank > lastrInShape[r.dest])
                    continue;
                Expr rExpr = operation_expr_map.get(r).getFirst();
//				IntExpr rTime = operation_expr_map.get(r).getSecond();
                if(rExpr != null && sExpr != null)//should not be null
                {
                    a = solver.Match(rExpr, sExpr);
                    b = (b!=null)?solver.mkOr(a, b):a;//make or for all matches for send s
                }
            }
            if (b != null) {
                solver.addFormula(b);
            }
        }
    }
}
