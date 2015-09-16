package Parser;

import Syntax.*;
import Syntax.Process;

public class ProgramListener extends JTAParserBaseListener {

    private Program program;
    private Process thread;

    Program getProgram() {
        return program;
    }

    @Override
    public void enterProgram(JTAParser.ProgramContext ctx) {
        program = new Program(true);
    }

    @Override
    public void enterThread(JTAParser.ThreadContext ctx) {
        thread = new Process(program.size());
    }

    @Override
    public void exitThread(JTAParser.ThreadContext ctx) {
        program.add(thread);
    }

    // NOTE: This ignores the destination specified in the dsl for the receive, and assumes that the receive
    //       endpoint is the thread it is declared within.
    @Override
    public void enterReceive(JTAParser.ReceiveContext ctx) {
        Operation op = new Recv(
                thread.getRank()+ "_" + thread.size(),             // Name
                thread,                                            // Parent thread
                thread.size(),                                     // Rank
                Integer.parseInt(ctx.children.get(2).getText()),   // Source
                thread.getRank(),                                  // Destination
                null,                                              // Matching send
                true,                                              // Blocking?
                null                                               // Nearest wait
        );
        thread.add(op);
    }

    @Override
    public void enterSend(JTAParser.SendContext ctx) {
        Operation op = new Send(
                thread.getRank()+ "_" + thread.size(),             // Name
                thread,                                            // Parent thread
                thread.size(),                                     // Rank
                thread.getRank(),                                  // Source
                Integer.parseInt(ctx.children.get(2).getText()),   // Destination
                null,                                              // Matching receive
                Integer.parseInt(ctx.children.get(4).getText()),   // Value
                true,                                              // Blocking
                null                                               // Nearest wait
        );
        thread.add(op);
    }
}
