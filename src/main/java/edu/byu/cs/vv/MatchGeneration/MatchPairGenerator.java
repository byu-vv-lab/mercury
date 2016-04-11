package edu.byu.cs.vv.MatchGeneration;

import edu.byu.cs.vv.Syntax.Match;

import java.util.Collection;

public interface MatchPairGenerator {

    Collection<Collection<Match>> generateMatches();

}
