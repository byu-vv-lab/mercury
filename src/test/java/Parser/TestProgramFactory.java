package Parser;


import JTASyntax.*;
import JTASyntax.Process;

import java.util.ArrayList;
import java.util.List;

public class TestProgramFactory {

    public static Program test1 () {
        List<Process> procs = new ArrayList<>();
        List<Operation> ops = new ArrayList<>();
        ops.add(new Receive("0_0", 0, 0, -1, 0, null, null, true, true));
        ops.add(new Receive("0_1", 0, 1, 1, 0, null, null, true, false));
        procs.add(new Process(0, ops));

        ops = new ArrayList<>();
        ops.add(new Send("1_0", 1, 0, 1, 0, null, 2, true, null));
        ops.add(new Send("1_1", 1, 1, 1, 0, null, 3, true, null));
        procs.add(new Process(1, ops));

        return new Program("", procs);
    }

    public static Program test2 () {
        List<Process> procs = new ArrayList<>();
        List<Operation> ops = new ArrayList<>();

        ops.add(new Receive("0_0", 0, 0, -1, 0, null, null, true, true));
        ops.add(new Receive("0_1", 0, 1, 1, 0, null, null, true, false));
        procs.add(new Process(0, ops));

        ops = new ArrayList<>();
        ops.add(new Send("1_0", 1, 0, 1, 0, null, 2, true, null));
        procs.add(new Process(1, ops));

        ops = new ArrayList<>();
        ops.add(new Send("2_1", 2, 0, 2, 0, null, 3, true, null));
        procs.add(new Process(2, ops));

        return new Program("", procs);
    }

}
