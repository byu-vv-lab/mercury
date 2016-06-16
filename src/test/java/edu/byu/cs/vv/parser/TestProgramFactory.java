package edu.byu.cs.vv.parser;


import edu.byu.cs.vv.ast.operations.Operation;
import edu.byu.cs.vv.ast.operations.Receive;
import edu.byu.cs.vv.ast.operations.Send;
import edu.byu.cs.vv.ast.Process;
import edu.byu.cs.vv.ast.Program;

import java.util.ArrayList;
import java.util.List;

public class TestProgramFactory {

    public static Program test1 () {
        List<Process> procs = new ArrayList<>();
        List<Operation> ops = new ArrayList<>();
        ops.add(new Receive("0_0", , 0, 0, -1, 0, null, true, true));
        ops.add(new Receive("0_1", , 0, 1, 1, 0, null, true, false));
        procs.add(new Process(0, ops));

        ops = new ArrayList<>();
        ops.add(new Send("1_0", 1, 0, 1, 0, tag, true, null));
        ops.add(new Send("1_1", 1, 1, 1, 0, tag, true, null));
        procs.add(new Process(1, ops));

        return new Program("", procs);
    }

    public static Program test2 () {
        List<Process> procs = new ArrayList<>();
        List<Operation> ops = new ArrayList<>();

        ops.add(new Receive("0_0", , 0, 0, -1, 0, null, true, true));
        ops.add(new Receive("0_1", , 0, 1, 1, 0, null, true, false));
        procs.add(new Process(0, ops));

        ops = new ArrayList<>();
        ops.add(new Send("1_0", 1, 0, 1, 0, tag, true, null));
        procs.add(new Process(1, ops));

        ops = new ArrayList<>();
        ops.add(new Send("2_1", 2, 0, 2, 0, tag, true, null));
        procs.add(new Process(2, ops));

        return new Program("", procs);
    }

}
