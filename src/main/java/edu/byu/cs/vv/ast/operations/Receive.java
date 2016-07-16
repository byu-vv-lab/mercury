package edu.byu.cs.vv.ast.operations;

public class Receive extends Operation {

    public final int src;
    public final int dest;
    public final int tag;

    public Receive(String name, int communicator, int process_rank, int order, int src, int dest, int tag) {
        super(name, communicator, process_rank, order);
        this.src = src;
        this.dest = dest;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Recv: {" +
                "src=" + src +
                ", dest=" + dest +
                ", rank=" + order +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receive recv = (Receive) o;

        if (src != recv.src) return false;
        if (dest != recv.dest) return false;
        return order == recv.order;
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
        return "(Receive " + name + " " + communicator + " " + src + " " + tag + ")";
    }

    @Override
    public Operation setOrder(int time) {
        return new Receive(name, communicator, process_rank, time, src, dest, tag);
    }

    public boolean isDeterministic() {
        return !(isSourceWildcard() || isTagWildcard());
    }

    public boolean isSourceWildcard() {
        return this.src == -1;
    }

    public boolean isTagWildcard() {
        return this.tag == -1;
    }

    public boolean doesMatch (Send send) {
        if (send.communicator != this.communicator) {
            return false;
        } else if (!isSourceWildcard() && (send.src != this.src)) {
            return false;
        } else if (!isTagWildcard() && (send.tag != this.tag)) {
            return false;
        } else {
            return true;
        }
    }
}
