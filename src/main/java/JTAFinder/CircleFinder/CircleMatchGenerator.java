package JTAFinder.CircleFinder;

import JTAFinder.AbstractMatchGenerator;
import JTASyntax.Match;
import JTASyntax.Program;

import java.util.Collection;

public class CircleMatchGenerator extends AbstractMatchGenerator {

    protected CircleMatchGenerator(Program program) {
        super(program);
    }

    @Override
    public Collection<Collection<Match>> generateMatches() {
        return null;
    }

}
