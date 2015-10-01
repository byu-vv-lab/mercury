package JTASyntax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Program implements Iterable<Process> {

    public final String name;
    public final List<Process> processes;
//    public List<Receive>[] recvlist;
//    public List<Send>[][] sendlist;

    public Program(String name, List<Process> procs) {
        this.name = name;
        this.processes = Collections.unmodifiableList(procs);
//        recvlist = new ArrayList[this.size()];
//        sendlist = new ArrayList[this.size()][this.size()];
//        for (Process process : this) {
//            recvlist[process.rank] = process.rlist;
//            for (Integer dest : process.smap.keySet()) {
//                sendlist[dest][process.rank] = process.smap.get(dest);
//            }
//        }
    }

    public int size () {
        return processes.size();
    }

    public Process get (int index) {
        return processes.get(index);
    }


    @Override
    public Iterator<Process> iterator() {
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
