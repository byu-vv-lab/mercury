package edu.byu.cs.vv.ast.operations;

public class Send extends Operation {

    public final int src;
    public final int dest;
    public final int tag;
    public final int value = 0;

    public Send(String name, int communicator, int process_rank, int order, int src, int dest, int tag) {
        super(name, communicator, process_rank, order);
        this.src = src;
        this.dest = dest;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Send: {" +
                "src=" + src +
                ", dest=" + dest +
                ", rank=" + order +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Send send = (Send) o;

        if (src != send.src) return false;
        if (dest != send.dest) return false;
        return order == send.order;
    }

    @Override
    public int hashCode() {
        int result = src;
        result = 31 * result + dest;
        result = 31 * result + order;
        return result;
    }

    @Override
    public String toSexp() {
        return "(Send " + name + " " + communicator + " " + dest + " " + tag + ")";
    }

    @Override
    public Operation setOrder(int time) {
        return new Send(name, communicator, process_rank, time, src, dest, tag);
    }
}
