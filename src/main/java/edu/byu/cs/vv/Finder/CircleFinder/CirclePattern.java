package edu.byu.cs.vv.Finder.CircleFinder;

import edu.byu.cs.vv.Finder.AbstractPattern;
import edu.byu.cs.vv.Syntax.Operations.Receive;
import edu.byu.cs.vv.Syntax.Process;

import java.util.Map;

public class CirclePattern extends AbstractPattern {

    public final Map<Integer, Receive> patterns;

    public CirclePattern(Map<Integer, Receive> patterns) {
        this.patterns = patterns;
    }

    @Override
    public boolean isMatch(Process process, Receive receive) {
        return false;
    }

    @Override
    public boolean hasProcess(Process proc) {
        return patterns.containsKey(proc.rank);
    }

    @Override
    public boolean hasReceive(Receive recv) {
        return recv.equals(patterns.get(recv.dest));
    }

    public Receive get(Process proc) {
        if (hasProcess(proc))
            return patterns.get(proc.rank);
        else
            return null;
    }
}
