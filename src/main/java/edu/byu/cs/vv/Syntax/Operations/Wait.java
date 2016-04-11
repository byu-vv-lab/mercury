package edu.byu.cs.vv.Syntax.Operations;

public class Wait extends Operation {

    public final boolean forR;

    public Wait(String name, int order, boolean forR) {
        super(name, order, false, 0);
        this.forR = forR;
    }

}
