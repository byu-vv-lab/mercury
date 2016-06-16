package edu.byu.cs.vv.finder.circlefinder;

import edu.byu.cs.vv.finder.AbstractMatchGenerator;
import edu.byu.cs.vv.ast.Match;
import edu.byu.cs.vv.ast.Program;

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
