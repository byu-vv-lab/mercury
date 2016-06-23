package edu.byu.cs.vv.ast.operations;

public class Barrier extends Operation {

    public Barrier(String name, int communicator, int order) {
        super(name, communicator, 0, order, true);
    }

    @Override
    public String toSexp() {
        return "(Barrier " + communicator + ")";
    }

    @Override
    public Operation setOrder(int time) {
        return new Barrier(name, communicator, time);
    }
}
