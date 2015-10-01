package Parser;

import JTASyntax.*;
import JTASyntax.Process;

import java.util.ArrayList;
import java.util.List;

class ProgramBuilder {

    private String name = "";
    private List<Process> procs = new ArrayList<>();

    public Program finish() {
        return new Program(name, procs);
    }

    public void setName (String name) {
        this.name = name;
    }

    public void addProcess(JTASyntax.Process process) {
        procs.add(process);
    }

    public int size () {
        return procs.size();
    }
}
