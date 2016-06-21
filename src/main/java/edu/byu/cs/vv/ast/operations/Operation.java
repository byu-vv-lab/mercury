package edu.byu.cs.vv.ast.operations;

import edu.byu.cs.vv.encoding.Encodeable;
import edu.byu.cs.vv.encoding.SMTContext;

public abstract class Operation implements Comparable<Operation>, Encodeable {

    public final String name;
    public final int process_rank;
    public final int order;
    public final int communicator;
    public final boolean isBlock;

    /**
     * Constructor
     * @param name The name of the operation
     * @param communicator
     * @param process_rank The index of this process in the program
     * @param order The index of this operation in its process
     * @param isBlock Is the operation blocking?
     */
    public Operation(String name, int communicator, int process_rank, int order, boolean isBlock) {
        this.name = name;
        this.communicator = communicator;
        this.order = order;
        this.isBlock = isBlock;
        this.process_rank = process_rank;
    }

    @Override
    public int compareTo(Operation o) {
        return Integer.compare(this.order, o.order);
    }

    @Override
    public void encode(SMTContext ctx){
        throw new RuntimeException("Not implemented");
    }

    public abstract String toSexp();
}
