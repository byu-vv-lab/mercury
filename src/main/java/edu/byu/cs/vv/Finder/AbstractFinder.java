package edu.byu.cs.vv.Finder;

import edu.byu.cs.vv.Syntax.Operations.Operation;
import edu.byu.cs.vv.Syntax.Operations.Receive;
import edu.byu.cs.vv.Syntax.Operations.Send;
import edu.byu.cs.vv.Syntax.Operations.Wait;
import edu.byu.cs.vv.Syntax.Process;
import edu.byu.cs.vv.Syntax.Program;
import edu.byu.cs.vv.Syntax.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractFinder {
    protected boolean result;
    protected Schedule schedule;
    protected String log;

    protected Program program;

    protected AbstractFinder(Program p) {
        program = p;
    }

    public static class DeadlockException extends Exception {
        private String message;
        public DeadlockException (String message) { this.message = message; }
        public String getMessage () { return message; }
    }

    /**
     * Checks whether the program supplied is correct under the given finder
     *
     * @return True if the program is verifiably correct, false otherwise
     */
    public abstract boolean verify();

    /**
     * @return
     */
    public boolean getResult() {
        return result;
    }

    /**
     * @return
     */
    public Schedule getSchedule() {
        return schedule;
    }

    protected static boolean schedulable(ProgramStepper stepper,
                                         AbstractPattern pattern,
                                         Map<Integer, Map<Integer, Integer>> sendNums,
                                         Map<Integer, Map<Integer, Integer>> recvNums,
                                         Map<Wait, List<Receive>> witnessedRecv) throws DeadlockException {
        return false;
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
    protected static boolean scheduling(ProgramStepper stepper,
                                        AbstractPattern pattern,
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

                        if (!sendNums.containsKey(dest)) {
                            sendNums.put(dest, new HashMap<Integer, Integer>());
                        }
                        if (!sendNums.get(dest).containsKey(src)) {
                            sendNums.get(dest).put(src, 0);
                        }
                        if (!sendNums.get(dest).containsKey(-1)) {
                            sendNums.get(dest).put(-1, 0);
                        }

                        sendNums.get(dest).put(src, sendNums.get(dest).get(src) + 1);
                        // TODO: Why are we adding a wildcard?
                        sendNums.get(dest).put(-1, sendNums.get(dest).get(-1) + 1);
                    } else if (op instanceof Receive) {
                        Receive receive = (Receive) op;
                        int src = receive.src;
                        int dest = receive.dest;
                        lastrInShape[dest] = receive.rank;

                        if (receive.isBlock) {
                            if (!recvNums.containsKey(dest)) {
                                recvNums.put(dest, new HashMap<Integer, Integer>());
                            }
                            if (!recvNums.get(dest).containsKey(src)) {
                                recvNums.get(dest).put(src, 0);
                            }

                            if (checkAvailable(receive, sendNums, recvNums)) {
                                recvNums.get(dest).put(src, recvNums.get(dest).get(src) + 1);
                            } else {
                                break;
                            }
                        } else {
                            Wait nw = receive.NearestWait;
                            if (!witnessedRecv.containsKey(nw)) {
                                witnessedRecv.put(nw, new ArrayList<Receive>());
                            }
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
                                    if (pattern.isMatch(process, recv)) {
                                        break;
                                    }
                                    int src = recv.src;
                                    int dest = recv.dest;

                                    if (!recvNums.containsKey(dest)) {
                                        recvNums.put(dest, new HashMap<Integer, Integer>());
                                    }
                                    if (!recvNums.get(dest).containsKey(src)) {
                                        recvNums.get(dest).put(src, 0);
                                    }

                                    if (checkAvailable(recv, sendNums, recvNums)) {
                                        recvNums.get(dest).put(src, recvNums.get(dest).get(src) + 1);
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
     * @param recv
     * @param sendNums
     * @param recvNums
     * @return
     */
    protected static boolean checkAvailable(Receive recv,
                                            Map<Integer, Map<Integer, Integer>> sendNums,
                                            Map<Integer, Map<Integer, Integer>> recvNums) {
        int src = recv.src;
        int dest = recv.dest;

        if (src != -1) {
            // S(c->0) > R(c)
            boolean accumulator = totalNUM(sendNums, src, dest) > totalNUM(recvNums, src, dest);
            // S(c->0) > R(*) + R(c)
            accumulator &= (totalNUM(sendNums, -1, dest) > (totalNUM(recvNums, -1, dest) + totalNUM(recvNums, src, dest)));
            return accumulator;
        } else {
            if (recvNums.containsKey(dest)) {
                int totalAvailableRecvs = 0;
                for (Integer rsrc : recvNums.get(dest).keySet())
                    totalAvailableRecvs += recvNums.get(dest).get(rsrc);
                return (totalNUM(sendNums, src, dest) > totalAvailableRecvs);
            } else {
                return false;
            }
        }
    }

    /**
     * @param deadlockPoint
     * @param sendNums
     * @param recvNums
     * @return
     */
    protected static boolean mayDeadlock(Receive deadlockPoint,
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
     * @param map
     * @param src
     * @param dest
     * @return
     */
    protected static int totalNUM(Map<Integer, Map<Integer, Integer>> map, int src, int dest) {
        if (map.containsKey(dest)) {
            if (map.get(dest).containsKey(src)) {
                return map.get(dest).get(src);
            }
        }
        return 0;
    }

    /**
     * @return
     */
    protected boolean isPattern() {
        return false;
    }

}
