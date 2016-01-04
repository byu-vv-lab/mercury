package edu.byu.cs.vv.Syntax;

import java.lang.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Program implements Iterable<java.lang.Process> {

    public final String name;
    public final List<java.lang.Process> processes;

    public Program(String name, List<java.lang.Process> procs) {
        this.name = name;
        this.processes = Collections.unmodifiableList(procs);
    }

    public int size() {
        return processes.size();
    }

    public java.lang.Process get(int index) {
        return processes.get(index);
    }


    @Override
    public Iterator<java.lang.Process> iterator() {
        return processes.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Program processes1 = (Program) o;

        if (!name.equals(processes1.name)) return false;
        return processes.equals(processes1.processes);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + processes.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Program{" +
                "name='" + name + '\'' +
                ", processes=" + processes +
                '}';
    }
}
