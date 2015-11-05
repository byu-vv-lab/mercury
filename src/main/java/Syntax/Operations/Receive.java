package Syntax.Operations;

import java.util.BitSet;

public class Receive extends Operation {

    public final int src;
    public final int dest;
    public final Send match;
    public final BitSet var;
    public final boolean isWildcard;
    public final Wait NearestWait;

    public Receive(String name, int process_rank, int rank, int src, int dest,
                   Send match, Wait nw, boolean isBlock, boolean isWildcard) {
        super(name, rank, isBlock, process_rank);
        this.src = src;
        this.isWildcard = isWildcard;
        this.dest = dest;
        this.match = match;
        this.var = new BitSet();
        this.NearestWait = nw;
    }

    @Override
    public String toString() {
        return "Recv: {" +
                "src=" + src +
                ", dest=" + dest +
                ", rank=" + rank +
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
        if (rank != recv.rank) return false;
        return isBlock == recv.isBlock;
    }

    @Override
    public int hashCode() {
        int result = src;
        result = 31 * result + dest;
        result = 31 * result + rank;
        result = 31 * result + (isBlock ? 1 : 0);
        return result;
    }

}
