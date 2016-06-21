package edu.byu.cs.vv.match_generation;

import edu.byu.cs.vv.ast.Match;
import edu.byu.cs.vv.ast.Program;

import java.util.Set;

public interface IMatchPairGenerator {

    Set<Match> getMatches(Program program);

}
