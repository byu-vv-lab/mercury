package edu.byu.cs.vv.Finder.Unmatchedtag;

import edu.byu.cs.vv.Finder.AbstractEncoder;
import edu.byu.cs.vv.Finder.ProgramStepper;
import edu.byu.cs.vv.Syntax.Match;
import edu.byu.cs.vv.Syntax.Operations.*;
import edu.byu.cs.vv.Syntax.Pair;
import edu.byu.cs.vv.Syntax.Process;
import edu.byu.cs.vv.Syntax.Schedule;
import com.microsoft.z3.*;

import java.util.*;

public class tagEncoder extends AbstractEncoder {
    
    UnmatchedEndpointPattern pattern;
    int[] lastrInShape;
    int[][] lastsInShape;
    
    //for unmatched endpoint, make sure all sends in source endpoint should be matched
    Map<Send, List<Receive>> pattern_match;
    Map<Receive, List<Send>> match_table;
    Map<Integer,Map<Integer,Operation>> lastr = null;
    Operation lastsz = null;
    Map<Integer, Operation> lasts = new HashMap<>();
    
    Hashtable<String,IntExpr> var_table;
    Hashtable<Operation, Pair<Expr, IntExpr>> operation_expr_map = null;
    
    //pair.first is op expr, pair.second is op time
    
    //data structure to record the last wait on each process
    Wait[] lastwait;
    Wait[] lastwait_s;
    
    boolean[] hb_ws_r;
    boolean[][] hb_ws_s; //[src][dest]
    boolean[][] hb_wr_s; //[src][dest]
    
    public boolean zeroSemantics = false;
    
    public tagEncoder(ProgramStepper program,
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
        lasts = new Hashtable<Integer,Operation>();
        var_table = new Hashtable<String,IntExpr>();
        operation_expr_map = new Hashtable<Operation,Pair<Expr,IntExpr>>();
        lastwait = new Wait[program.size()];
        lastwait_s = new Wait[program.size()];
        Arrays.fill(lastwait, null);
        Arrays.fill(lastwait_s,null);
        hb_ws_r = new boolean[program.size()];
        Arrays.fill(hb_ws_r,false);
        hb_ws_s = new boolean[program.size()][program.size()];
        hb_wr_s = new boolean[program.size()][program.size()];
        for(int i = 0; i < program.size(); i++)
        {
            Arrays.fill(hb_ws_s[i],false);
            Arrays.fill(hb_ws_r[i],false);
        }
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
                lastr.clear();
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
        //add var to var table if it is not in the table
        String newName = "var" + op.event;
        
        Receive receive = (Receive)op;
        
        IntExpr var;
        if(var_table.containsKey(newName))
            var = var_table.get(newName);
        else
        {
            var =  solver.MkTime(newName);
            var_table.put(newName, var);
        }
        // add nw when the receive is non-blocking
        IntExpr nw = null;
        if (!op.isBlock) {
            nw = solver.MkTime("T"+((Recv)op).NearestWait.event);
            Expr wait = solver.mkWait("W" + op.event);
            Pair<Expr, IntExpr> waitinfo = new Pair<Expr, IntExpr>(wait,nw);
            operation_expr_map.put(((Recv)op).NearestWait,waitinfo);
            //add HB over a receive and its nw
            solver.addFormula(solver.HB(time,nw));
        }
        else
        {
            //extract the line number of op in its process
            //need to catch exception
            int loc=op.process.size();
            try
            {
                loc = Integer.parseInt(op.event.split("_")[1]);
            }
            catch(Exception e)
            {
                System.out.println(e);
                System.exit(1);
            }
            
            //there exists a following operation after op
            if(loc < op.process.size()-1)
            {
                ((Recv)op).nextOP = op.process.get(loc+1);
                //do not add hb over op and the next operation after op because the hb will be added later
            }
        }
        solver.addFormula(solver.initRecv(recv, ((Recv)op).src, ((Recv)op).dest, time, var, nw));
        
        //TODO: will need more data structures to store multiple last receives for tags and endpoints
        
        if(receive.isSourceWildcard() && receive.isTagWildcard())
        {
            if(lastr != null)
            {
                for(Integer dest : lastr.keySet())
                {
                    for(Integer tag: lastr.get(dest).keySet())
                    {
                        solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastr.get(dest).get(tag)).getSecond(),
                                                    time));
                    }
                }
            }
            else
            {
                lastr = new HashMap<Integer,Map<Integer,Operation>>();
                lastr.put(-1,new HashMap<Integer,Operation>());
                lastr.get(-1).put(-1,receive);
            }
        }
        else if (receive.isSourceWildcard() && !receive.isTagWildcard())
        {
            if(lastr!=null)
            {
                for(Integer dest : lastr.keySet())
                {
                    if(lastr.get(dest).containsKey(receive.tag))
                    {
                        solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastr.get(dest).get(receive.tag)).getSecond(),
                                                    time));
                    }
                    else if(lastr.get(dest).containsKey(-1))
                    {
                        solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastr.get(dest).get(-1)).getSecond(),
                                                    time));
                    }
                    lastr.get(dest).put(receive.tag,receive);
                    //TODO: remove the duplicated HB relations caused by source wildcard and tag wildcard
                }
                
                if(!lastr.containsKey(-1))
                {
                    lastr.put(-1,new HashMap<Integer,Operation>());
                }
            }
            else
            {
                lastr = new HashMap<Integer,Map<Integer,Operation>>();
                lastr.put(-1,new HashMap<Integer,Operation>());
            }
            lastr.get(-1).put(receive.tag,receive);
        }
        else if(!receive.isSourceWildcard() && !receive.isTagWildcard())
        {
            if(lastr != null)
            {
                if(lastr.containsKey(op.dest))
                {
                    if(lastr.get(op.dest).containsKey(receive.tag))
                    {
                        solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastr.get(op.dest).get(receive.tag)).getSecond(),
                                                    time));
                    }
                    else if(lastr.get(op.dest).containsKey(-1))
                    {
                        solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastr.get(op.dest).get(-1)).getSecond(),
                                  time));
                    }
                }
                else
                {
                    if(lastr.containsKey(-1))
                    {
                        if(lastr.get(-1).containsKey(receive.tag))
                        {
                            solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastr.get(-1).get(receive.tag)).getSecond(),
                                                        time));
                        }
                        else if(lastr.get(-1).containsKey(-1))
                        {
                            solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastr.get(-1).get(-1)).getSecond(),
                                                        time));
                        }
                    }
                    lastr.put(op.dest,new HashMap<Integer,Operation>());
                }
            }
            else
            {
                lastr = new HashMap<Integer,Map<Integer,Operation>>();
                lastr.put(op.dest,new HashMap<Integer,Operation>());
            }
            lastr.get(op.dest).put(receive.tag,receive);
        }
        else
        {
            if(lastr!=null)
            {
                if(lastr.containsKey(dest))
                {
                    for(Integer tag: lastr.get(dest).keySet())
                    {
                        solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastr.get(dest).get(tag)).getSecond(),
                                                    time));
                        lastr.get(dest).put(tag,receive);
                    }
                    
                    if(!lastr.get(dest).containsKey(-1))
                    {
                        lastr.get(dest).put(-1,receive);
                    }
                }
                else if(lastr.containsKey(-1))
                {
                    for(Integer tag: lastr.get(-1).keySet())
                    {
                        solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastr.get(-1).get(tag)).getSecond(),
                                                    time));
                        lastr.get(-1).put(tag,receive);
                    }
                }
            }
            else
            {
                lastr = new HashMap<Integer,Map<Integer,Operation>>();
                lastr.put(dest,new HashMap<Integer,Operation>());
                lastr.get(dest).put(-1,receive);
            }
        }
        
        
        
        if(lastwait_s[op.process.getRank()] != null && !hb_ws_r[op.process.getRank()])
        {
            solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastwait_s[op.process.getRank()]).getSecond(),
                                        time));
            
            //only add hb from last wait w to a receive once
            hb_ws_r[op.process.getRank()]=true;
        }
        
        if(lastsz!=null && (zeroSemantics||lastsz instanceof SSend))
        {
            //only add HB over the last send and op when the send and op are in a common process
            if(lastsz.process.equals(op.process))
            {
                BoolExpr newexpr =solver.HB((IntExpr)operation_expr_map.get(lastsz).getSecond(),
                                            time);
                solver.addFormula(newexpr);
                
                //System.out.println(newexpr);
            }
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
        IntExpr nw = null;
        if(!((Send)op).isBlock)
        {
            nw = solver.MkTime("T"+((Send)op).NearestWait.event);
            Expr wait = solver.mkWait("W" + op.event);
            Pair<Expr, IntExpr> waitinfo = new Pair<Expr, IntExpr>(wait,nw);
            operation_expr_map.put(((Send)op).NearestWait,waitinfo);
            //add HB over a send and its nw
            solver.addFormula(solver.HB(time,nw));
        }
        else
        {
            //extract the line number of op in its process
            //need to catch exception
            int loc=op.process.size();
            try
            {
                loc = Integer.parseInt(op.event.split("_")[1]);
            }
            catch(Exception e)
            {
                System.out.println(e);
                System.exit(1);
            }
            
            //there exists a following operation after op
            if(loc < op.process.size()-1)
            {
                ((Send)op).nextOP = op.process.get(loc+1);
                //do not add hb over op and the next operation after op because the hb will be added later
            }
            
        }
        solver.addFormula(solver.initSend(send, ((Send)op).src, ((Send)op).dest, time, ((Send)op).value,nw));
        
        if (lastr != null) {
            solver.addFormula(solver.HB(operation_expr_map.get(lastr).getSecond(), time));
        }
        
        if (lastsz != null) {
            //only add HB over the last send and op when the send and op are in a common process
            if(lastsz.process.equals(op.process))
            {
                solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastsz).getSecond(),
                                            time));
            }
        }
        
        if (lasts.containsKey(op.dest)) {
            //do not repeatedly add hb for last send and op under zero buffer
            if (!lasts.get(op.dest).equals(lastsz))
                solver.addFormula(solver.HB(operation_expr_map.get(lasts.get(op.dest)).getSecond(), time));
        }
        
        if(lastwait[op.process.getRank()] != null && !hb_wr_s[op.process.getRank()][((Send)op).dest])
        {
            //does not constrain the HB for a wait and the next receive, defined in NFM paper
            solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastwait[op.process.getRank()]).getSecond(), time));
            //should remove hb from last wait w to the send s if w<hb s' exists where s'<hb s
            hb_wr_s[op.process.getRank()][((Send)op).dest] = true;
        }
        
        if(lastwait_s[op.process.getRank()] != null && !hb_ws_s[op.process.getRank()][((Send)op).dest])
        {
            //does not constrain the HB for a wait and the next receive, defined in NFM paper
            solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastwait_s[op.process.getRank()]).getSecond(),
                                        time));
            //should remove hb from last wait w to the send s if w<hb s' exists where s'<hb s
            hb_ws_s[op.process.getRank()][((Send)op).dest]=true;
        }
        
        //add nearest inclosing send for dest
        lasts.put(op.dest, op);
        if (zeroSemantics || op instanceof SynchronousSend)
            lastsz = op;
    }
    
    @Override
    protected void encodeWait(Wait op) throws Z3Exception {
        Wait w = (Wait)op;
        //HB over this wait and its next send is added when op is a send
        if(w.forR)
        {
            if(lastwait[op.process.getRank()]!=null)
                solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastwait[op.process.getRank()]).getSecond(),
                                            (IntExpr)operation_expr_map.get(w).getSecond()));
            lastwait[op.process.getRank()] = w;
            for(int i = 0; i < program.size(); i++)
                hb_wr_s[op.process.getRank()][i]=false;
        }
        else
        {
            if(lastwait_s[op.process.getRank()]!=null)
                solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastwait_s[op.process.getRank()]).getSecond(),
                                            (IntExpr)operation_expr_map.get(w).getSecond()));
            lastwait_s[op.process.getRank()] = w;
            hb_ws_r[op.process.getRank()] = false;
            for(int i = 0; i < program.size(); i++)
                hb_ws_s[op.process.getRank()][i]=false;
        }
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
