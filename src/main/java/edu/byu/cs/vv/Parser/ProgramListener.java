package edu.byu.cs.vv.Parser;

import edu.byu.cs.vv.Syntax.Operations.Operation;
import edu.byu.cs.vv.Syntax.Operations.Receive;
import edu.byu.cs.vv.Syntax.Operations.Send;
import edu.byu.cs.vv.Syntax.Program;
import org.antlr.v4.runtime.tree.ErrorNode;

import java.lang.*;
import java.util.HashMap;
import java.util.Map;

public class ProgramListener extends edu.byu.cs.vv.Parser.MercuryParserBaseListener {

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
    public void enterProgram(edu.byu.cs.vv.Parser.MercuryParser.ProgramContext ctx) {
        programBuilder = new ProgramBuilder();
        super.enterProgram(ctx);
    }

    @Override
    public void enterThread(edu.byu.cs.vv.Parser.MercuryParser.ThreadContext ctx) {
        processBuilder = new ProcessBuilder();
        processBuilder.setRank(programBuilder.size());
        sends = new HashMap<>();
        recv_rank = 0;
        super.enterThread(ctx);
    }

    @Override
    public void enterThreadHeader(edu.byu.cs.vv.Parser.MercuryParser.ThreadHeaderContext ctx) {
        processBuilder.setName(ctx.children.get(1).getText());
        super.enterThreadHeader(ctx);
    }

    @Override
    public void exitThread(edu.byu.cs.vv.Parser.MercuryParser.ThreadContext ctx) {
        programBuilder.addProcess(processBuilder.finish());
        super.exitThread(ctx);
    }

    // TODO: Nearest enclosing wait is ignored
    // TODO: Blocking receive is ignored
    @Override
    public void enterReceive(edu.byu.cs.vv.Parser.MercuryParser.ReceiveContext ctx) {
        int source = Integer.parseInt(ctx.children.get(1).getText());
        Operation op = new Receive(
                processBuilder.rank() + "_" + processBuilder.size(),   // Name
                processBuilder.rank(),                                // Process Rank
                recv_rank,                                            // Operation Rank
                source,                                               // Source
                processBuilder.rank(),                                // Destination
                null,                                                 // Nearest wait
                true,                                                 // Blocking?
                (source == -1));                                      // Wildcard?
        processBuilder.addOperation(op);
        recv_rank++;
        super.enterReceive(ctx);
    }

    // TODO: Nearest enclosing wait is ignored
    // TODO: Blocking send is ignored
    @Override
    public void enterSend(edu.byu.cs.vv.Parser.MercuryParser.SendContext ctx) {
        int rank, destination = Integer.parseInt(ctx.children.get(1).getText());
        if (sends.containsKey(destination)) {
            rank = sends.get(destination);
        } else {
            rank = 0;
        }
        Operation op = new Send(
                processBuilder.rank() + "_" + processBuilder.size(),   // Name
                processBuilder.rank(),                                // Process Rank
                rank,                                                 // Operation rank
                processBuilder.rank(),                                // Source
                destination,                                          // Destination
                true,                                                 // Blocking?
                null);                                                // Nearest wait
        processBuilder.addOperation(op);
        sends.put(destination, rank + 1);
        super.enterSend(ctx);
    }

}
