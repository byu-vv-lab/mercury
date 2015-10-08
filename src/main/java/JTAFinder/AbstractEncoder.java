package JTAFinder;

import JTASyntax.*;
import com.microsoft.z3.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEncoder {

    protected ProgramStepper program;
    protected Schedule schedule;
    protected SMTSolver solver;
    protected Map<Operation, Pair<Expr, IntExpr>> operation_expr_map = new HashMap<>();

    public AbstractEncoder(ProgramStepper program) {
        this.program = program;
        this.solver = new SMTSolver();
    }

    public Model isSatisfiable () {
        try {
            return solver.Check(Status.SATISFIABLE);
        } catch (Exception e) {
            return null;
        }
    }

    public Schedule getSchedule () {
        return schedule;
    }

    public abstract boolean encodeProgram();
    protected abstract void encodeSend (Send op) throws Z3Exception;
    protected abstract void encodeReceive (Receive op) throws Z3Exception;
    protected abstract void encodeWait (Wait op) throws Z3Exception;
    protected abstract void encodeBarrier (Barrier op) throws Z3Exception;
    protected abstract void encodeMatches () throws Z3Exception;
    protected abstract Collection<Collection<Match>> filterMatches ();
    protected abstract void encodeMatchSet (Collection<Match> matches) throws Z3Exception;

}
