package edu.byu.cs.vv.ast.operations;

public class Receive extends Operation {

    public final int src;
    public final int dest;
    public final int tag;
    public final boolean isWildcard;
    public final Wait NearestWait;

    public Receive(String name, int communicator, int process_rank, int order, int src, int dest,
                   int tag, Wait nw, boolean isBlock, boolean isWildcard) {
        super(name, communicator, process_rank, order, isBlock);
        this.src = src;
        this.dest = dest;
        this.tag = tag;
        this.isWildcard = isWildcard;
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