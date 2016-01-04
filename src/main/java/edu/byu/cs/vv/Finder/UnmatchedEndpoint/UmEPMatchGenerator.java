package edu.byu.cs.vv.Finder.UnmatchedEndpoint;

import edu.byu.cs.vv.Finder.AbstractMatchGenerator;
import edu.byu.cs.vv.Syntax.Match;
import edu.byu.cs.vv.Syntax.Program;

import java.util.Collection;

// TODO: Extend AbstractMatchGenerator
public class UmEPMatchGenerator extends AbstractMatchGenerator {

    public UmEPMatchGenerator(Program program) {
        super(program);
    }

    @Override
    public Collection<Collection<Match>> generateMatches() {
        return null;
    }

}
