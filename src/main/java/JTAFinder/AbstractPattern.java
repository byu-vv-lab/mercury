package JTAFinder;

import JTASyntax.Operations.Operation;
import JTASyntax.Operations.Receive;
import JTASyntax.Process;

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
