package Syntax;

import Syntax.Operations.Operation;
import Syntax.Operations.Receive;
import Syntax.Operations.Send;

import java.util.*;

public class Process implements Iterable<Operation> {
    public final int rank;
    public final List<Operation> operations;
    public final List<Receive> rlist;
    public final Map<Integer, List<Send>> smap;

    public Process(int rank, List<Operation> operations) {
        this.rank = rank;
        this.operations = Collections.unmodifiableList(operations);
        this.rlist = getReceiveList();
        this.smap = getSendMap();
    }

    public Operation getOp(int rank) {
        return operations.get(rank);
    }

    private List<Receive> getReceiveList() {
        List<Receive> rlist = new ArrayList<>();
        for (Operation op : this) {
            if (op instanceof Receive) {
                rlist.add((Receive) op);
            }
        }
        return Collections.unmodifiableList(rlist);
    }

    private Map<Integer, List<Send>> getSendMap() {
        Map<Integer, List<Send>> tempmap = new HashMap<>();
        for (Operation op : this) {
            if (op instanceof Send) {
                Send send = (Send) op;
                if (!tempmap.containsKey(send.dest)) {
                    List<Send> sends = new ArrayList<>();
                    sends.add(send);
                    tempmap.put(send.dest, sends);
                } else {
                    tempmap.get(send.dest).add(send);
                }
            }
        }
        Map<Integer, List<Send>> smap = new HashMap<>();
        for (Integer key : tempmap.keySet()) {
            smap.put(key, Collections.unmodifiableList(tempmap.get(key)));
        }
        return Collections.unmodifiableMap(smap);
    }

    @Override
    public Iterator<Operation> iterator() {
        return operations.iterator();
    }

    public boolean hasDeterminsticRecv() {
        for (Receive op : rlist) {
            if (op.src != -1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Process that = (Process) o;

        if (rank != that.rank) return false;
        return operations.equals(that.operations);
    }

    @Override
    public int hashCode() {
        int result = rank;
        result = 31 * result + operations.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "\nProcess{" +
                "rank=" + rank +
                ", operations=" + operations +
                '}';
    }

    public int size() {
        return operations.size();
    }
}
