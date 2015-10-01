package JTAFinder;

import JTAFinder.Patterns.UnmatchedEndpoint;
import JTASyntax.*;
import JTASyntax.Process;
import com.microsoft.z3.Model;

import java.util.*;

public class UnmatchedEndpointFinder extends AbstractFinder {

    public UnmatchedEndpointFinder(Program program) {
        super(program);
    }

    @Override
    public String printResults() {
        return log;
    }

    @Override
    public boolean verify() {
        Set<UnmatchedEndpoint> patterns = generatePatterns(program);
        if (patterns.isEmpty()) {
            log = "No unmatched endpoint patterns found";
            return (result = true);
        }
        for (UnmatchedEndpoint pattern : patterns) {
            ProgramStepper stepper = new ProgramStepper(program);

            Map<Integer, Map<Integer, Integer>> sendNums = new HashMap<>();
            Map<Integer, Map<Integer, Integer>> recvNums = new HashMap<>();
            Map<Wait, List<Receive>> witnessedRecv = new HashMap<>();

            int[] lastrInShape = new int[stepper.size()];
            Arrays.fill(lastrInShape, -1);
            int[][] lastsInShape = new int[stepper.size()][stepper.size()];
            for (int[] array : lastsInShape) {
                Arrays.fill(array, -1);
            }

            for (Process process : stepper) {
                stepper.advanceToNextBlockPoint(process);
                stepper.reset(process);
            }

            while (schedulable(stepper, pattern, sendNums, recvNums, witnessedRecv)) {
                scheduling(stepper, pattern, sendNums, recvNums, witnessedRecv, lastrInShape, lastsInShape);
                if (stepper.atBlockPoint()) {
                    Set<Process> reachableRanks = reachableProcesses(stepper, pattern, sendNums, recvNums);
                    if (reachableRanks.isEmpty()) {
                        // may deadlock
                        // apply SMT here to check the feasibility of prefix
                        // if it is feasible, deadlock for this pattern
                        // if not, no deadlock for this pattern(could be A, deadlock in the prefix; or B, there is no deadlock)

                        // A
                        //  0      1       2
                        // S(2)   R(*)    R(*)
                        //        S(2)    S(1)
                        //                R(0)
                        // --------------------
                        // <R(2)>          ...

                        // B
                        //  0      1       2
                        // R(*)   R(*)    S(0)
                        // S(1)   S(0)
                        // --------------------
                        // <R(1)>          ...

                        Encoder encoder = new UnmatchedEndpointEncoder(stepper, pattern, lastrInShape, lastsInShape, pattern_match, match_table);

                        encoder.encodeProgram();
                        //encoder.solver.displayFormulas();
                        Model model = encoder.isSatisfiable();
                        if(model != null) {
                            //System.out.println("[SAT] Witness Example:\n" + model);
//                            System.out.println("Verification ends for this program!");
//                            System.out.println(count);
//                            if(endtime == 0)
//                                endtime = System.currentTimeMillis();
                            //continue to check even when a deadlock is found
                            log = "Found deadlock";
                            return (result = false);
                        }
                        else {
//                            System.out.println("[UNSAT]:No deadlock is found for pattern: ["
//                                    + pattern.deterministic.toString() + "]");
                        }
                    } else if (reachableRanks.contains(pattern.process)) {
                        Operation rv = stepper.blockOp(pattern.process);
                        if (rv.equals(pattern.deterministic)) {
                            //report no deadlock for this pattern
                            //could be deadlock in the prefix,
                            // 0      1       2
                            //S(2)   R(*)    R(*)
                            //       S(2)    S(1)
                            //               R(0)
                            //				 S(0)
                            //--------------------
                            //<R(2)>          ...

                            continue;
                        }
                    }
                    stepper.moveBlockPoints(reachableRanks, pattern);
                }
            }
        }
        log = "Fell through to the end";
        return (result = true);
    }

    /**
     * Determines whether the given program is schedulable, i.e. whether any of the processes can be advanced
     *
     * @param stepper
     * @param pattern
     * @param sendNums
     * @param recvNums
     * @param witnessedRecv
     * @return True if schedulable, false otherwise
     */
    private static boolean schedulable(ProgramStepper stepper,
                                       UnmatchedEndpoint pattern,
                                       Map<Integer, Map<Integer, Integer>> sendNums,
                                       Map<Integer, Map<Integer, Integer>> recvNums,
                                       Map<Wait, List<Receive>> witnessedRecv) {
        for (Process process : stepper) {
            if (!stepper.atBlockPoint(process)
                    || ((stepper.blockPoint(process) == 0) && (stepper.currentLocation(process) == 0))) {
                Operation op = stepper.currentOp(process);
                if (op instanceof Receive) {
                    Receive recv = (Receive) op;
                    if (!recv.isBlock || checkAvailable(recv, sendNums, recvNums)) {
                        return true;
                    }
                } else if (op instanceof Wait) {
                    Wait wait = (Wait) op;
                    if (witnessedRecv.containsKey(wait)) {
                        List<Receive> witnessedR = witnessedRecv.get(wait);
                        if (!witnessedR.isEmpty()) {
                            Receive firstR = witnessedR.get(0);
                            if ((!firstR.equals(pattern.deterministic)) && (checkAvailable(firstR, sendNums, recvNums))) {
                                return true;
                            }
                        }
                    }
                } else if (op instanceof Barrier) {
                    // NOTE: if barriers are matched then return true
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param recv
     * @param sendNums
     * @param recvNums
     * @return
     */
    private static boolean checkAvailable(Receive recv,
                                          Map<Integer, Map<Integer, Integer>> sendNums,
                                          Map<Integer, Map<Integer, Integer>> recvNums) {
        int src = recv.src;
        int dest = recv.dest;

        if (src == -1) {
            if (recvNums.containsKey(dest)) {
                int totalAvailableRecvs = 0;
                for (Integer rsrc : recvNums.get(dest).keySet())
                    totalAvailableRecvs += recvNums.get(dest).get(rsrc);
                return (totalNUM(sendNums, src, dest) > totalAvailableRecvs);
            } else {
                return false;
            }
        } else {
            // S(c->0) > R(c)
            boolean accumulator = totalNUM(sendNums, src, dest) > totalNUM(recvNums, src, dest);
            // S(c->0) > R(*) + R(c)
            accumulator &= (totalNUM(sendNums, -1, dest) > (totalNUM(recvNums, -1, dest) + totalNUM(recvNums, src, dest)));
            return accumulator;
        }
    }

    /**
     * @param stepper
     * @param pattern
     * @param sendNums
     * @param recvNums
     * @param witnessedRecv
     * @param lastrInShape
     * @param lastsInShape
     * @return
     */
    private static boolean scheduling(ProgramStepper stepper,
                                      UnmatchedEndpoint pattern,
                                      Map<Integer, Map<Integer, Integer>> sendNums,
                                      Map<Integer, Map<Integer, Integer>> recvNums,
                                      Map<Wait, List<Receive>> witnessedRecv,
                                      int[] lastrInShape,
                                      int[][] lastsInShape) {
        for (Process process : stepper) {
            if ((stepper.currentLocation(process) == 0) && (stepper.blockPoint(process) == 0)) {
                Operation op = stepper.currentOp(process);
                if (op instanceof Receive) {
                    Receive recv = (Receive) op;
                    if (recv.isBlock) {
                        int src = recv.src;
                        int dest = recv.dest;
                        lastrInShape[dest] = recv.rank;
                        if (!recvNums.containsKey(dest)) {
                            recvNums.put(dest, new HashMap<Integer, Integer>());
                        }
                        if (!recvNums.get(dest).containsKey(src)) {
                            recvNums.get(dest).put(src, 0);
                        }
                    }
                }
            } else if (stepper.atBlockPoint(process)) {
                // Do nothing
            } else {
                while (!stepper.atBlockPoint(process)) {
                    Operation op = stepper.currentOp(process);
                    if (op instanceof Send) {
                        Send send = (Send) op;
                        int src = send.src;
                        int dest = send.dest;
                        lastsInShape[dest][src] = send.rank;

                        if (!sendNums.containsKey(dest)) { sendNums.put(dest, new HashMap<Integer, Integer>()); }
                        if (!sendNums.get(dest).containsKey(src)) { sendNums.get(dest).put(src, 0); }
                        if (!sendNums.get(dest).containsKey(-1)) { sendNums.get(dest).put(-1, 0); }

                        sendNums.get(dest).put(src, sendNums.get(dest).get(src)+1);
                        // TODO: Why are we adding a wildcard?
                        sendNums.get(dest).put(-1, sendNums.get(dest).get(-1)+1);
                    } else if (op instanceof Receive) {
                        Receive receive = (Receive) op;
                        int src = receive.src;
                        int dest = receive.dest;
                        lastrInShape[dest] = receive.rank;

                        if (receive.isBlock) {
                            if (!recvNums.containsKey(dest)) { recvNums.put(dest, new HashMap<Integer, Integer>()); }
                            if (!recvNums.get(dest).containsKey(src)) { recvNums.get(dest).put(src, 0); }

                            if (checkAvailable(receive, sendNums, recvNums)) {
                                recvNums.get(dest).put(src, recvNums.get(dest).get(src)+1);
                            } else {
                                break;
                            }
                        } else {
                            Wait nw = receive.NearestWait;
                            if (!witnessedRecv.containsKey(nw)) { witnessedRecv.put(nw, new ArrayList<Receive>()); }
                            witnessedRecv.get(nw).add(receive);
                        }
                    } else if (op instanceof Wait) {
                        Wait wait = (Wait) op;
                        if (witnessedRecv.containsKey(wait)) {
                            List<Receive> witnessedR = witnessedRecv.get(wait);
                            if (witnessedR.isEmpty()) {
                                witnessedRecv.remove(wait);
                            } else {
                                for (Receive recv : witnessedR) {
                                    if (process.rank == pattern.rank && recv.equals(pattern.deterministic)) { break; }
                                    int src = recv.src;
                                    int dest = recv.dest;

                                    if (!recvNums.containsKey(dest)) { recvNums.put(dest, new HashMap<Integer, Integer>()); }
                                    if (!recvNums.get(dest).containsKey(src)) { recvNums.get(dest).put(src, 0); }

                                    if (checkAvailable(recv, sendNums, recvNums)) {
                                        recvNums.get(dest).put(src, recvNums.get(dest).get(src)+1);
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    stepper.incrementLocation(process);
                }
            }
        }
        return true;
    }

    /**
     *
     * @param stepper
     * @param pattern
     * @param sendNums
     * @param recvNums
     * @return
     */
    private static Set<Process> reachableProcesses(ProgramStepper stepper,
                                                   UnmatchedEndpoint pattern,
                                                   Map<Integer, Map<Integer, Integer>> sendNums,
                                                   Map<Integer, Map<Integer, Integer>> recvNums) {
        Set<Process> reachable = new HashSet<>();
        for (Process process : stepper) {
            if (!stepper.atEnd(process)) {
                Operation blockOp = stepper.currentOp(process);
                if (blockOp instanceof Receive) {
                    Receive recv = (Receive) blockOp;
                    if (process.equals(pattern.process)) {
                        if (!recv.equals(pattern.deterministic)) {
                            reachable.add(process);
                        }
                        if (!mayDeadlock(recv, sendNums, recvNums)) {
                            reachable.add(process);
                            return reachable;
                        }
                        continue;
                    } else {
                        if (checkAvailable(recv, sendNums, recvNums)) {
                            reachable.add(process);
                        }
                    }
                }
            }
        }
        return reachable;
    }

    /**
     *
     * @param deadlockPoint
     * @param sendNums
     * @param recvNums
     * @return
     */
    private static boolean mayDeadlock(Receive deadlockPoint,
                                       Map<Integer, Map<Integer, Integer>> sendNums,
                                       Map<Integer, Map<Integer, Integer>> recvNums) {
        int src = deadlockPoint.src;
        int dest = deadlockPoint.dest;
        int send = 0;
        int recv = 0;

        if ((sendNums.containsKey(dest)) && (sendNums.get(dest).containsKey(src))) {
            send = sendNums.get(dest).get(src);
        }

        if (recvNums.containsKey(dest)) {
            if (recvNums.get(dest).containsKey(-1)) {
                recv += recvNums.get(dest).get(-1);
            }
            if ((src != -1) && (recvNums.get(dest).containsKey(src))) {
                recv += recvNums.get(dest).get(src);
            }
        }
        return (send <= recv);
    }

    /**
     * Returns a set of all process' UnmatchedEndpoint patterns
     *
     * @param prog
     * @return
     */
    private static Set<UnmatchedEndpoint> generatePatterns(Program prog) {
        Set<UnmatchedEndpoint> set = new HashSet<>();
        for (Process process : prog) {
            if (process.hasDeterminsticRecv()) {
                set.addAll(generatePatterns(process));
            }
        }
        return set;
    }

    /**
     * Returns a set of all receives in a process that are preceded by a wildcard receive
     *
     * @param proc
     * @return
     */
    private static Set<UnmatchedEndpoint> generatePatterns(Process proc) {
        Set<UnmatchedEndpoint> set = new HashSet<>();
        boolean hasPred = false;
        for (Receive recv : proc.rlist) {
            if (recv.isWildcard) {
                hasPred = true;
            } else if (hasPred) {
                set.add(new UnmatchedEndpoint(recv, proc));
            }
        }
        return set;
    }

    @Override
    protected boolean isPattern() {
        return true;
    }

}
