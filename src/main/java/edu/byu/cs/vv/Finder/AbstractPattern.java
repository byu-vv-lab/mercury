package edu.byu.cs.vv.Finder;

import edu.byu.cs.vv.Syntax.Operations.Operation;
import edu.byu.cs.vv.Syntax.Operations.Receive;
import edu.byu.cs.vv.Syntax.Process;

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
