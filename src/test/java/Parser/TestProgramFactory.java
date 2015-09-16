package Parser;

import Syntax.*;
import Syntax.Process;

public class TestProgramFactory {

    public static Program test1 () {
        Program program = new Program(true);
        Process process0 = new Syntax.Process(0);
        Process process1 = new Process(1);
        program.add(process0);
        program.add(process1);

        process0.add(new Recv(process0.getRank() + "_" + 0, process0, 0, -1,
                0, null, true, null));
        process0.add(new Recv(process0.getRank() + "_" + 1, process0, 1, 1,
                0, null, true, null));

        process1.add(new Send(process1.getRank() + "_" + 0, process1, 0, 1, 0, null, 2,
                true, null));
        process1.add(new Send(process1.getRank() + "_" + 1, process1, 1, 1, 0, null, 3,
                true, null));

        return program;
    }

    public static Program test2 () {
        Program program = new Program(true);
        Process process0 = new Process(0);
        Process process1 = new Process(1);
        Process process2 = new Process(2);
        program.add(process0);
        program.add(process1);
        program.add(process2);

        process0.add(new Recv(process0.getRank()+ "_" + 0, process0, 0, -1,
                0, null, true, null));
        process0.add(new Recv(process0.getRank()+ "_" + 1, process0, 1,1,
                0, null, true, null));

        process1.add(new Send(process1.getRank() + "_" + 0, process1,0, 1, 0, null, 2,
                true, null));
        process2.add(new Send(process2.getRank() + "_" + 0, process1,0, 2, 0, null, 3,
                true, null));

        return program;
    }

}
