package JTAFinder;

import JTAFinder.UnmatchedEndpoint.UnmatchedEndpointPattern;
import JTASyntax.Operations.Operation;
import JTASyntax.Operations.Receive;
import JTASyntax.Operations.Wait;
import JTASyntax.Process;
import JTASyntax.Program;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ProgramStepper implements Iterable<Process> {
    protected final Program program;
    private final Map<Process, Integer> blockMap;
    private final Map<Process, Integer> currentMap;

    public ProgramStepper(Program program) {
        this.program = program;
        blockMap = new HashMap<>();
        currentMap = new HashMap<>();
        for (Process process : program) {
            blockMap.put(process, 0);
            currentMap.put(process, 0);
        }
    }

    public int incrementLocation(Process process) {
        return currentMap.put(process, (currentMap.get(process) + 1));
    }

    public int currentLocation(Process process) {
        return currentMap.get(process);
    }

    public Operation currentOp(Process process) {
        return process.getOp(currentMap.get(process));
    }

    public Operation blockOp(Process process) {
        return process.getOp(blockPoint(process));
    }

    public int blockPoint(Process process) {
        return blockMap.get(process);
    }

    public boolean atBlockPoint() {
        for (Process process : blockMap.keySet()) {
            if (currentMap.get(process) < blockMap.get(process)) {
                return false;
            }
        }
        return true;
    }

    public void reset(Process process) {
        currentMap.put(process, 0);
    }

    public boolean atBlockPoint(Process process) {
        return (currentMap.get(process) >= blockMap.get(process));
    }

    public boolean atEnd(Process process) {
        return currentMap.get(process) >= process.size();
    }

    public int size() {
        return program.size();
    }

    @Override
    public Iterator<Process> iterator() {
        return program.iterator();
    }

    public void moveBlockPoints(Set<Process> reachableProcesses, UnmatchedEndpointPattern pattern) {
        for (Process process : reachableProcesses) {
            // if it is a pattern process and it reaches the determinstic receive in pattern,
            // do not need to increment indicator
            if (!(process.equals(pattern.process) && blockOp(process).equals(pattern.deterministic))) {
                blockMap.put(process, blockMap.get(process) + 1);
                advanceToNextBlockPoint(process);
            }
        }
    }

    public void advanceToNextBlockPoint(Process process) {
        // Make sure I don't run off the end
//        if (blockPoint(process) < (process.size())) {
//            blockMap.put(process, blockMap.get(process) + 1);
        while (blockPoint(process) < process.size()) {
            Operation op = blockOp(process);
            if ((op instanceof Receive) || (op instanceof Wait)) {
                if (op.isBlock) {
                    break;
                }
            } else {
                blockMap.put(process, blockMap.get(process) + 1);
            }
        }
//        }
    }

}
