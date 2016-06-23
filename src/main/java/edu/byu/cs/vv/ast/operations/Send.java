package edu.byu.cs.vv.ast.operations;

public class Send extends Operation {

    public final int src;
    public final int dest;
    public final int tag;
    public final int value = 0;
    public final Wait nearestWait;

    public Send(String name, int communicator, int process_rank, int order, int src, int dest,
                int tag, Wait nw, boolean isBlock) {
        super(name, communicator, process_rank, order, isBlock);
        this.src = src;
        this.dest = dest;
        this.tag = tag;
        this.nearestWait = nw;
    }

    @Override
    public String toString() {
        return "Send: {" +
                "src=" + src +
                ", dest=" + dest +
                ", rank=" + order +
                ", isBlock=" + isBlock +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Send send = (Send) o;

        if (src != send.src) return false;
        if (dest != send.dest) return false;
        if (order != send.order) return false;
        return isBlock == send.isBlock;
    }

    @Override
    public int hashCode() {
        int result = src;
        result = 31 * result + dest;
        result = 31 * result + order;
        result = 31 * result + (isBlock ? 1 : 0);
        return result;
    }

    @Override
    public String toSexp() {
        return "(Send " + communicator + " " + dest + " " + tag + ")";
    }

    @Override
    public Operation setOrder(int time) {
        return new Send(name, communicator, process_rank, time, src, dest, tag, nearestWait, isBlock);
    }
}
