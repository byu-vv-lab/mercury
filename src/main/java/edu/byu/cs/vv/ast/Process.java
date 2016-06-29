package edu.byu.cs.vv.ast;

import com.google.common.collect.*;
import edu.byu.cs.vv.ast.operations.Operation;
import edu.byu.cs.vv.ast.operations.Receive;
import edu.byu.cs.vv.ast.operations.Send;

import java.util.*;

public class Process implements Iterable<Operation> {

    public final int rank;
    public final List<Operation> operations;
    private List<Receive> rlist;
    private List<Send> slist;
    private Multimap<Integer, Send> smap;

    public Process(int rank, List<Operation> operations) {
        this.rank = rank;
        this.operations = ImmutableList.copyOf(operations);
        this.smap = getSendMap();
    }

    public Operation getOp(int rank) {
        return operations.get(rank);
    }

    public List<Receive> getReceiveList() {
        if (this.rlist == null) {
            setOpLists();
        }
        return this.rlist;
    }

    public List<Send> getSendList() {
        if (this.slist == null) {
            setOpLists();
        }
        return this.slist;
    }

    private void setOpLists() {
        ImmutableList.Builder<Send> slist = ImmutableList.builder();
        ImmutableList.Builder<Receive> rlist = ImmutableList.builder();
        for (Operation op : this) {
            if (op instanceof Send) {
                slist.add((Send) op);
            } else if (op instanceof Receive) {
                rlist.add((Receive) op);
            }
        }
        this.slist = slist.build();
        this.rlist = rlist.build();
    }

    public Multimap<Integer, Send> getSendMap() {
        if (this.smap ==  null) {
            buildSendMap();
        }
        return this.smap;
    }

    private void buildSendMap() {
        ImmutableMultimap.Builder<Integer, Send> map = ImmutableListMultimap.builder();
        for (Send send : this.getSendList()) {
            map.put(send.dest, send);
        }
        this.smap = map.build();
    }

    @Override
    public Iterator<Operation> iterator() {
        return operations.iterator();
    }

    public boolean hasDeterminsticRecv() {
        for (Receive op : getReceiveList()) {
            if (!op.isSourceWildcard()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasTagDeterminsticRecv(){
        for (Receive op : getReceiveList()) {
            if (!op.isTagWildcard()) {
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

    public String toSexp() {
        StringBuilder builder = new StringBuilder();
        builder.append("(Thread ");
        for (Operation op : this) {
            builder.append(op.toSexp());
        }
        builder.append(')');
        return builder.toString();
    }
}
