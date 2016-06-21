package edu.byu.cs.vv.ast.operations;

public class Wait extends Operation {

    public final boolean forR;

    public Wait(String name, int communicator, int order, boolean forR) {
        super(name, communicator, 0, order, false);
        this.forR = forR;
    }

    @Override
    public String toSexp() {
        return "(Wait " + communicator + ")";
    }
}
