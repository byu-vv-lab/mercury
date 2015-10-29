package JTAFinder;

import JTASyntax.Match;
import JTASyntax.Program;

import java.util.Collection;

public abstract class AbstractMatchGenerator {

    protected final Program program;

    protected AbstractMatchGenerator(Program program) {
        this.program = program;
    }

    /**
     * TODO: Specify Ors/Ands for matches
     * @return
     */
    public abstract Collection<Collection<Match>> generateMatches();

}
