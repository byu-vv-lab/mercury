package Syntax.Operations;

public class Send extends Operation {

    public final int src;
    public final int dest;
    public final Receive match;
    public final int value;// should be bitset
    public final Wait NearestWait;

    public Send(String name, int process_rank, int rank, int src, int dest, Receive match, int value,
                boolean isBlock, Wait nw) {
        super(name, rank, isBlock, process_rank);
        this.src = src;
        this.dest = dest;
        this.match = match;
        this.value = value;
        this.NearestWait = nw;
    }

    @Override
    public String toString() {
        return "Send: {" +
                "src=" + src +
                ", dest=" + dest +
                ", value=" + value +
                ", rank=" + rank +
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
        if (value != send.value) return false;
        if (rank != send.rank) return false;
        return isBlock == send.isBlock;
    }

    @Override
    public int hashCode() {
        int result = src;
        result = 31 * result + dest;
        result = 31 * result + value;
        result = 31 * result + rank;
        result = 31 * result + (isBlock ? 1 : 0);
        return result;
    }

}
