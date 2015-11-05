package JTAFinder.UnmatchedEndpoint;

import JTAFinder.AbstractMatchGenerator;
import JTASyntax.Match;
import JTASyntax.Operations.Receive;
import JTASyntax.Operations.Send;
import JTASyntax.Program;

import java.util.*;

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
