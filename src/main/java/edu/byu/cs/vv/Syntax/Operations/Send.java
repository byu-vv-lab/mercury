package edu.byu.cs.vv.Syntax.Operations;

public class Send extends Operation {

    public final int src;
    public final int dest;
    public final Wait NearestWait;

    public Send(String name, int process_rank, int order, int src, int dest,
                boolean isBlock, Wait nw) {
        super(name, order, isBlock, process_rank);
        this.src = src;
        this.dest = dest;
        this.NearestWait = nw;
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

}
