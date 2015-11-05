package Parser;

import Syntax.Process;
import Syntax.Program;

import java.util.ArrayList;
import java.util.List;

class ProgramBuilder {

    private String name = "";
    private List<Process> procs = new ArrayList<>();

    public Program finish() {
        return new Program(name, procs);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProcess(Syntax.Process process) {
        procs.add(process);
    }

    public int size() {
        return procs.size();
    }
}
