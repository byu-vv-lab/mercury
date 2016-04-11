package edu.byu.cs.vv.Syntax.Operations;

public class Receive extends Operation {

    public final int src;
    public final int dest;
    public final boolean isWildcard;
    public final Wait NearestWait;

    public Receive(String name, int process_rank, int order, int src, int dest,
                   Wait nw, boolean isBlock, boolean isWildcard) {
        super(name, order, isBlock, process_rank);
        this.src = src;
        this.isWildcard = isWildcard;
        this.dest = dest;
        this.NearestWait = nw;
    }

    @Override
    public String toString() {
        return "Recv: {" +
                "src=" + src +
                ", dest=" + dest +
                ", rank=" + order +
                ", isBlock=" + isBlock +
                ", isWildcard=" + isWildcard +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receive recv = (Receive) o;

        if (src != recv.src) return false;
        if (dest != recv.dest) return false;
        if (order != recv.order) return false;
        return isBlock == recv.isBlock;
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
