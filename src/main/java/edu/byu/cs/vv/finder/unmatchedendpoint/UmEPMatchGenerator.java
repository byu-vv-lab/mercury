package edu.byu.cs.vv.finder.unmatchedendpoint;

import edu.byu.cs.vv.finder.AbstractMatchGenerator;
import edu.byu.cs.vv.ast.Match;
import edu.byu.cs.vv.ast.Program;

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
