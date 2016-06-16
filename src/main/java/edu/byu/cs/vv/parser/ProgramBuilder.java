package edu.byu.cs.vv.parser;

import edu.byu.cs.vv.ast.Process;
import edu.byu.cs.vv.ast.Program;

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

    public void addProcess(Process process) {
        procs.add(process);
    }

    public int size() {
        return procs.size();
    }
}
