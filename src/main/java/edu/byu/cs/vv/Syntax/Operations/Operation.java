package edu.byu.cs.vv.Syntax.Operations;

public abstract class Operation implements Comparable<Operation> {

    public final String name;
    public final int rank;
    public final boolean isBlock;
    public final int process_rank;
    public int order;

    public Operation(String name, int rank, boolean isBlock, int process_rank) {
        this.name = name;
        this.rank = rank;
        this.isBlock = isBlock;
        this.process_rank = process_rank;
    }

    @Override
    public int compareTo(Operation o) {
        return Integer.compare(this.order, o.order);
    }

}
