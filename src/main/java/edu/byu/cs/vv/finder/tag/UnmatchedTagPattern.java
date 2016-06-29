package edu.byu.cs.vv.finder.unmatchedendpoint;

import edu.byu.cs.vv.finder.AbstractPattern;
import edu.byu.cs.vv.ast.operations.Operation;
import edu.byu.cs.vv.ast.operations.Receive;
import edu.byu.cs.vv.ast.Process;

public class UnmatchedTagPattern extends AbstractPattern {
    public final Process process;
    public final Receive deterministic;
    public final int rank;
    
    public UnmatchedTagPattern(Receive d, Process p) {
        deterministic = d;
        process = p;
        rank = p.rank;
    }
    
    @Override
    public boolean isMatch(Process process, Receive receive) {
        return ((process.rank == this.rank) && receive.equals(this.deterministic));
    }
    
    @Override
    public boolean hasProcess(Process proc) {
        return this.process.equals(proc);
    }
    
    @Override
    public boolean hasReceive(Receive recv) {
        return recv.equals(deterministic);
    }
    
    @Override
    public boolean hasOp(Operation op) {
        return false;
    }
    
}
