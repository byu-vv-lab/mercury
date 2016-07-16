package edu.byu.cs.vv.parser;

import edu.byu.cs.vv.ast.operations.*;
import edu.byu.cs.vv.ast.Process;

import java.util.ArrayList;
import java.util.List;

class ProcessBuilder {
    private int rank = 0;
    private int sends = 0;
    private int receives = 0;
    private int barriers = 0;
    private int waits = 0;
    private String name;

    private List<Operation> ops = new ArrayList<>();

    public Process finish() {
        return new Process(rank, ops);
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addOperation(Operation op) {
        ops.add(op);
        if (op instanceof Send) {
            ++sends;
        } else if (op instanceof Receive) {
            ++receives;
        } else if (op instanceof Barrier) {
            ++barriers;
        } else if (op instanceof Wait) {
            ++waits;
        } else {
            throw new RuntimeException("Not a valid operation");
        }
    }

    public int rank() {
        return rank;
    }

    public int size() {
        return ops.size();
    }

    public int sends() { return sends; }

    public int recvs() { return receives; }

    public int barriers() { return barriers; }

    public int waits() { return waits; }
}
