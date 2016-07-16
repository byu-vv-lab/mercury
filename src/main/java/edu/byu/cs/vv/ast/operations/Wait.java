package edu.byu.cs.vv.ast.operations;

public class Wait extends Operation {

    public final String opWitnessed;

    public Wait(String name, String opWitnessed, int process_rank, int order) {
        super(name, -1, process_rank, order);
        this.opWitnessed = opWitnessed;
    }

    @Override
    public String toSexp() {
        return "(Wait " + opWitnessed + ")";
    }

    @Override
    public Operation setOrder(int time) {
        return new Wait(name, opWitnessed, process_rank, time);
    }
}
