package edu.byu.cs.vv.finder;

import edu.byu.cs.vv.ast.Match;
import edu.byu.cs.vv.ast.operations.*;
import edu.byu.cs.vv.util.Pair;
import edu.byu.cs.vv.ast.Schedule;
import com.microsoft.z3.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEncoder {

    protected ProgramStepper program;
    protected SMTSolver solver;
    protected Map<Operation, Pair<Expr, IntExpr>> operation_expr_map = new HashMap<>();
    protected Schedule schedule;

    public AbstractEncoder(ProgramStepper program) {
        this.program = program;
        this.solver = new SMTSolver();
    }

    public Model isSatisfiable() {
        try {
            Model model = solver.Check(Status.SATISFIABLE);
            if (model != null)
                schedule = this.getSchedule(model);
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    public abstract boolean encodeProgram();

    protected abstract Schedule getSchedule(Model model);

    protected abstract void encodeSend(Send op) throws Z3Exception;

    protected abstract void encodeReceive(Receive op) throws Z3Exception;

    protected abstract void encodeWait(Wait op) throws Z3Exception;

    protected abstract void encodeBarrier(Barrier op) throws Z3Exception;

    protected abstract void encodeMatches() throws Z3Exception;

    protected abstract Collection<Collection<Match>> filterMatches();

    protected abstract void encodeMatchSet(Collection<Match> matches) throws Z3Exception;

}
