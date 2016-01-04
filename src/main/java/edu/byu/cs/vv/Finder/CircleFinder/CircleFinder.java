package edu.byu.cs.vv.Finder.CircleFinder;

import Finder.AbstractFinder;
import Finder.AbstractMatchGenerator;
import Finder.AbstractPattern;
import Finder.ProgramStepper;
import Syntax.Operations.Operation;
import Syntax.Operations.Receive;
import Syntax.Operations.Wait;
import Syntax.Program;
import Syntax.Process;

import java.util.*;

public class CircleFinder extends AbstractFinder {

    public CircleFinder(Program p) {
        super(p);
    }

    @Override
    public boolean verify() {
        AbstractMatchGenerator matches = new CircleMatchGenerator(program);
        Digraph graph = new Digraph(program, matches.getMatchTable());
        Johnson tc = new Johnson(graph);

        //TODO: how does a circular pattern look like?
        //if only considering receive and send,
        //how to push the operations between the receive and its nw to the shape
        List<CirclePattern> patterns = tc.getPatterns();
        if(patterns.size() == 0) {
            //report no deadlock for unmatched ep pattern
            System.out.printf("No deadlock is found for circular patterns!\n");
            return true;
        }

        try {
            for (CirclePattern pattern : patterns) {
                Map<Integer, Map<Integer, Integer>> sendNums = new HashMap<>();
                Map<Integer, Map<Integer, Integer>> recvNums = new HashMap<>();
                Map<Wait, List<Receive>> witnessedRecv = new HashMap<>();

                ProgramStepper stepper = new ProgramStepper(program);

                int[] lastrInShape = new int[stepper.size()];
                Arrays.fill(lastrInShape, -1);
                int[][] lastsInShape = new int[stepper.size()][stepper.size()];
                for (int[] array : lastsInShape) {
                    Arrays.fill(array, -1);
                }

                //initialize the prefix
                for (Process proc : program) {
                    if (!pattern.hasProcess(proc)) {
                        stepper.advanceToNextBlockPoint(proc);
                    } else {
                        stepper.advanceToPoint(proc, pattern.get(proc));
                    }
                }

                //judge prefix's feasibility
                breakpoint:
                while (schedulable(stepper, pattern, sendNums, recvNums, witnessedRecv)) {
                    scheduling(stepper, pattern, sendNums, recvNums, witnessedRecv, lastrInShape, lastsInShape);
                    if (stepper.atBlockPoint()) {
                        Set<Process> reachableRanks = feasible(stepper, pattern, sendNums, recvNums);
                        if (reachableRanks.isEmpty()) {
                            //may deadlock, a prefix is found, no need to test in SMT encoding
                            //(1) if the prefix is satisfiable, deadlock exists for this pattern
                            //(2) if the prefix is unsatisfiable, deadlock exists in the prefix
                            //TODO: need to verify if (2) is true
                            return false;
                            //continue to check other instances even when a deadlock is found
                        }
                        for (Process proc : reachableRanks) {
                            //report no deadlock for this pattern
                            //could be deadlock in the prefix, see A above
                            if (pattern.hasProcess(proc)) {
                                //System.out.print(count + " No deadlock is found for pattern: "
                                //	+ patternProcesses + "\n");
                                break breakpoint;
                            }
                        }
                        stepper.moveBlockPoints(reachableRanks, pattern);
                    }
                }
            }
        } catch (DeadlockException ex) {
            return false;
        }
        return true;
    }

    protected Set<Process> feasible (ProgramStepper stepper,
                                     AbstractPattern pattern,
                                     Map<Integer, Map<Integer, Integer>> sendNums,
                                     Map<Integer, Map<Integer, Integer>> recvNums) {
        Set<Process> reachableRanks = new HashSet<>();
        for (Process process : program) {
            if (!stepper.atBlockPoint(process)) {
                Operation blockOp = stepper.currentOp(process);
                if (pattern.hasProcess(process)) {
                    Receive deadlockPoint = (Receive) blockOp;
                    if (!mayDeadlock(deadlockPoint, sendNums, recvNums)) {
                        reachableRanks.add(process);
                        return reachableRanks;
                    }
                    continue;
                }
                if (blockOp instanceof Receive) {
                    Receive rv = (Receive) blockOp;
                    if (checkAvailable(rv, sendNums, recvNums)) {
                        reachableRanks.add(process);
                    }
                }
            }
        }
        return reachableRanks;
    }

    protected static boolean schedulable(ProgramStepper stepper,
                                         AbstractPattern pattern,
                                         Map<Integer, Map<Integer, Integer>> sendNums,
                                         Map<Integer, Map<Integer, Integer>> recvNums,
                                         Map<Wait, List<Receive>> witnessedRecv) throws DeadlockException {
        boolean reachPatternPoint = true;
        for (Process process : stepper) {
            if (!stepper.atBlockPoint(process)
                    || ((stepper.blockPoint(process) == 0) && (stepper.currentLocation(process) == 0))) {
                Operation op = stepper.currentOp(process);
                if (pattern.hasProcess(process) && !stepper.atBlockPoint(process)) {
                    reachPatternPoint = false;
                }
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
                } else {
                    return true;
                }
            }
        }
        if (reachPatternPoint) {
            throw new DeadlockException("Deadlock is found for pattern " + pattern.toString());
        } else {
            return false;
        }
    }

}
