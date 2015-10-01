package JTAFinder;

import com.microsoft.z3.Model;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;

public abstract class Encoder {

    ProgramStepper program;
    boolean satisfiable;
    Schedule schedule;
    SMTSolver solver;

    public Encoder (ProgramStepper program) {
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

    public abstract boolean encodeProgram ();

}
