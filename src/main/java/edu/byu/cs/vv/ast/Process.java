package edu.byu.cs.vv.ast;

import com.google.common.collect.*;
import edu.byu.cs.vv.ast.operations.Operation;
import edu.byu.cs.vv.ast.operations.Receive;
import edu.byu.cs.vv.ast.operations.Send;

import java.util.*;

public class Process implements Iterable<Operation> {
    public final int rank;
    public final List<Operation> operations;
    public final List<Receive> rlist;
    public final Multimap<Integer, Send> smap;

    public Process(int rank, List<Operation> operations) {
        this.rank = rank;
        this.operations = ImmutableList.copyOf(operations);
        this.rlist = getReceiveList();
        this.smap = getSendMap();
    }

    public Operation getOp(int rank) {
        return operations.get(rank);
    }

    private List<Receive> getReceiveList() {
        ImmutableList.Builder<Receive> rlist = ImmutableList.builder();
        for (Operation op : this) {
            if (op instanceof Receive) {
                rlist.add((Receive) op);
            }
        }
        return rlist.build();
    }

    private Multimap<Integer, Send> getSendMap() {
        ImmutableMultimap.Builder<Integer, Send> map = ImmutableListMultimap.builder();
        for (Operation op : this) {
            if (op instanceof Send) {
                Send send = (Send) op;
                map.put(send.dest, send);
            }
        }
        return map.build();
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
