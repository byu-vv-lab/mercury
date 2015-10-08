package Parser;

import Parser.JTAParserBaseListener;
import Parser.JTAParser;
import JTASyntax.Operation;
import JTASyntax.Program;
import JTASyntax.Receive;
import JTASyntax.Send;
import org.antlr.v4.runtime.tree.ErrorNode;

import java.util.HashMap;
import java.util.Map;

public class ProgramListener extends JTAParserBaseListener {
    @Override
    public void visitErrorNode(ErrorNode node) {
        System.out.println("Error in syntax: " + node.toString());
        throw new RuntimeException("Error in syntax: " + node.toString());
    }

    private ProgramBuilder programBuilder;
    private ProcessBuilder processBuilder;
    private Map<Integer, Integer> sends;
    private int recv_rank;

    public Program getProgram() {
        return programBuilder.finish();
    }

    @Override
    public void enterProgram(JTAParser.ProgramContext ctx) {
        programBuilder = new ProgramBuilder();
    }

    @Override
    public void enterThread(JTAParser.ThreadContext ctx) {
        processBuilder = new ProcessBuilder();
        processBuilder.setRank(programBuilder.size());
        sends = new HashMap<>();
        recv_rank = 0;
    }

    @Override
    public void exitThread(JTAParser.ThreadContext ctx) {
        programBuilder.addProcess(processBuilder.finish());
    }

    // NOTE: This ignores the destination specified in the dsl for the receive, and assumes that the receive
    //       endpoint is the thread it is declared within.
    @Override
    public void enterReceive(JTAParser.ReceiveContext ctx) {
        int source = Integer.parseInt(ctx.children.get(2).getText());
        Operation op = new Receive(
                processBuilder.rank()+ "_" + processBuilder.size(),   // Name
                processBuilder.rank(),                                // Process Rank
                recv_rank,                                            // Operation Rank
                source,                                               // Source
                processBuilder.rank(),                                // Destination
                null,                                                 // Matching Send
                null,                                                 // Nearest wait
                true,                                                 // Blocking?
                (source == -1));                                      // Wildcard?
        processBuilder.addOperation(op);
        recv_rank++;
    }

    @Override
    public void enterSend(JTAParser.SendContext ctx) {
        int rank, destination = Integer.parseInt(ctx.children.get(2).getText());
        if (sends.containsKey(destination)) {
            rank = sends.get(destination);
        } else {
            rank = 0;
        }
        Operation op = new Send(
                processBuilder.rank()+ "_" + processBuilder.size(),   // Name
                processBuilder.rank(),                                // Process Rank
                rank,                                                 // Operation rank
                processBuilder.rank(),                                // Source
                destination,                                          // Destination
                null,                                                 // Matching receive
                Integer.parseInt(ctx.children.get(4).getText()),      // Value
                true,                                                 // Blocking?
                null);                                                // Nearest wait
        processBuilder.addOperation(op);
        sends.put(destination, rank + 1);
    }
}
