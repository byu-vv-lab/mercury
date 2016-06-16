package edu.byu.cs.vv.parser;

import edu.byu.cs.vv.ast.operations.Operation;
import edu.byu.cs.vv.ast.Process;

import java.util.ArrayList;
import java.util.List;

class ProcessBuilder {
    private int rank = 0;
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
    }

    public int rank() {
        return rank;
    }

    public int size() {
        return ops.size();
    }
}
