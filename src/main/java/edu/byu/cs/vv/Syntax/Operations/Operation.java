package edu.byu.cs.vv.Syntax.Operations;

import edu.byu.cs.vv.Encoding.Encodeable;
import edu.byu.cs.vv.Encoding.SMTContext;

public abstract class Operation implements Comparable<Operation>, Encodeable {

    public final String name;
    public final int order;
    public final boolean isBlock;
    public final int process_rank;

    public Operation(String name, int order, boolean isBlock, int process_rank) {
        this.name = name;
        this.order = order;
        this.isBlock = isBlock;
        this.process_rank = process_rank;
    }

    @Override
    public int compareTo(Operation o) {
        return Integer.compare(this.order, o.order);
    }

    @Override
    public void encode(SMTContext ctx) {
        throw new UnsupportedOperationException();
    }
}
