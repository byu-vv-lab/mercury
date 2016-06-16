package edu.byu.cs.vv.finder;

import edu.byu.cs.vv.ast.operations.Operation;
import edu.byu.cs.vv.ast.operations.Receive;
import edu.byu.cs.vv.ast.Process;

public abstract class AbstractPattern {

    public abstract boolean isMatch(Process process, Receive receive);

    public abstract boolean hasProcess(Process proc);

    public abstract boolean hasReceive(Receive recv);

    public boolean hasOp(Operation op) {
        if (op instanceof Receive) {
            return hasReceive((Receive) op);
        } else {
            return false;
        }
    }
}
