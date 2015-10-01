package JTAFinder.Patterns;

import JTASyntax.*;
import JTASyntax.Process;

public class UnmatchedEndpoint {
    public final Process process;
    public final Receive deterministic;
    public final int rank;

    public UnmatchedEndpoint (Receive d, Process p) {
        deterministic = d;
        process = p;
        rank = p.rank;
    }
}
