package JTAFinder;

import JTAFinder.UnmatchedEndpoint.UmEPMatchGenerator;
import JTASyntax.*;

import java.util.*;

public abstract class AbstractFinder {
    protected boolean result;
    protected Schedule schedule;
    protected String log;

    protected Program program;
    protected UmEPMatchGenerator matchGenerator;

    protected AbstractFinder(Program p) {
        program = p;
        matchGenerator = new UmEPMatchGenerator(p);
    }

    /**
     * Checks whether the program supplied is correct under the given finder
     * @return True if the program is verifiably correct, false otherwise
     */
    public abstract boolean verify();
    public abstract String printResults();

    public boolean getResult() {
        return result;
    }

    protected Schedule getSchedule() {
        return schedule;
    }

    protected static int totalNUM (Map<Integer, Map<Integer, Integer>> map, int src, int dest) {
        if(map.containsKey(dest)) {
            if(map.get(dest).containsKey(src)) {
                return map.get(dest).get(src);
            }
        }
        return 0;
    }

    protected boolean isPattern () { return false; }

}
