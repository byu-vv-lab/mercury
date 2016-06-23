package edu.byu.cs.vv.finder.unmatchedendpoint;

import edu.byu.cs.vv.encoding.AbstractEncoder;
import edu.byu.cs.vv.finder.AbstractFinder;
import edu.byu.cs.vv.finder.AbstractPattern;
import edu.byu.cs.vv.finder.ProgramStepper;
import edu.byu.cs.vv.ast.operations.Barrier;
import edu.byu.cs.vv.ast.operations.Operation;
import edu.byu.cs.vv.ast.operations.Receive;
import edu.byu.cs.vv.ast.operations.Wait;
import edu.byu.cs.vv.ast.Process;
import edu.byu.cs.vv.ast.Program;
import com.microsoft.z3.Model;

import java.util.*;

public class UmEPFinder extends AbstractFinder {

    private UmEPMatchGenerator matchGenerator;

    public UmEPFinder(Program program) {
        super(program);
        matchGenerator = new UmEPMatchGenerator(program);
    }

    @Override
    public boolean verify() {
        Set<UnmatchedEndpointPattern> patterns = generatePatterns(program);
        if (patterns.isEmpty()) {
            log = "No unmatched endpoint patterns found";
            return (result = true);
        }
        for (UnmatchedEndpointPattern pattern : patterns) {
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

                        AbstractEncoder encoder = new UmEPEncoder(stepper,
                                pattern,
                                lastrInShape,
                                lastsInShape,
                                matchGenerator.getPatternMatch(),
                                matchGenerator.getMatchTable());

                        encoder.encodeProgram();
//                        encoder.solver.displayFormulas();
                        Model model = encoder.isSatisfiable();
                        if (model != null) {
                            log = "Found deadlock";
                            return (result = false);
                        } else {
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
     * @param stepper       A ProgramStepper marking the current position in the program
     * @param pattern       The UnmatchedEndpointPattern we are evaluating against
     * @param sendNums
     * @param recvNums
     * @param witnessedRecv
     * @return True if schedulable, false otherwise
     */
    protected static boolean schedulable(ProgramStepper stepper,
                                       AbstractPattern pattern,
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
                            if ((!pattern.hasReceive(firstR)) && (checkAvailable(firstR, sendNums, recvNums))) {
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
     * @param stepper
     * @param pattern
     * @param sendNums
     * @param recvNums
     * @return
     */
    private static Set<Process> reachableProcesses(ProgramStepper stepper,
                                                   AbstractPattern pattern,
                                                   Map<Integer, Map<Integer, Integer>> sendNums,
                                                   Map<Integer, Map<Integer, Integer>> recvNums) {
        Set<Process> reachable = new HashSet<>();
        for (Process process : stepper) {
            if (!stepper.atEnd(process)) {
                Operation blockOp = stepper.currentOp(process);
                if (blockOp instanceof Receive) {
                    Receive recv = (Receive) blockOp;
                    if (pattern.hasProcess(process)) {
                        if (!(pattern.hasReceive(recv))) {
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
     * Returns a set of all process' UnmatchedEndpoint patterns
     *
     * @param prog
     * @return
     */
    private static Set<UnmatchedEndpointPattern> generatePatterns(Program prog) {
        Set<UnmatchedEndpointPattern> set = new HashSet<>();
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
    private static Set<UnmatchedEndpointPattern> generatePatterns(Process proc) {
        Set<UnmatchedEndpointPattern> set = new HashSet<>();
        boolean hasPred = false;
        for (Receive recv : proc.getReceiveList()) {
            if (recv.isSourceWildcard()) {
                hasPred = true;
            } else if (hasPred) {
                set.add(new UnmatchedEndpointPattern(recv, proc));
            }
        }
        return set;
    }

    @Override
    protected boolean isPattern() {
        return true;
    }

}
