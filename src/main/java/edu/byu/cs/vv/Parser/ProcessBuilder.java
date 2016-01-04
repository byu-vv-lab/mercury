package edu.byu.cs.vv.Parser;

import Syntax.Operations.Operation;
import Syntax.Process;

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
