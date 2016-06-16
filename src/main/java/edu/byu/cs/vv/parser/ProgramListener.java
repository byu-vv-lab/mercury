package edu.byu.cs.vv.parser;

import edu.byu.cs.vv.ast.operations.Barrier;
import edu.byu.cs.vv.ast.operations.Operation;
import edu.byu.cs.vv.ast.operations.Receive;
import edu.byu.cs.vv.ast.operations.Send;
import edu.byu.cs.vv.ast.Program;
import org.antlr.v4.runtime.tree.ErrorNode;

import java.lang.*;
import java.util.HashMap;
import java.util.Map;

class ProgramListener extends CTPParserBaseListener {


    private ProgramBuilder programBuilder;
    private ProcessBuilder processBuilder;
    private Map<Integer, Integer> sends;
    private int recv_rank;

    public Program getProgram() {
        return programBuilder.finish();
    }

    @Override
    public void enterProgram(CTPParser.ProgramContext ctx) {
        programBuilder = new ProgramBuilder();
        super.enterProgram(ctx);
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        System.out.println("Error in syntax: " + node.toString());
        throw new RuntimeException("Error in syntax: " + node.toString());
    }

    @Override
    public void enterThread(CTPParser.ThreadContext ctx) {
        processBuilder = new ProcessBuilder();
        processBuilder.setRank(programBuilder.size());
        sends = new HashMap<>();
        recv_rank = 0;
        super.enterThread(ctx);
    }

    @Override
    public void exitThread(CTPParser.ThreadContext ctx) {
        programBuilder.addProcess(processBuilder.finish());
        super.exitThread(ctx);
    }

    @Override
    public void enterSend(CTPParser.SendContext ctx) {
        int communicator = Integer.parseInt(ctx.children.get(1).getText());
        int destination = Integer.parseInt(ctx.children.get(2).getText());
        int tag = Integer.parseInt(ctx.children.get(3).getText());
        int rank = sends.getOrDefault(destination, 0);
        buildSend(communicator, destination, tag, rank, true);
        super.enterSend(ctx);
    }

    @Override
    public void enterIsend(CTPParser.IsendContext ctx) {
        int communicator = Integer.parseInt(ctx.children.get(1).getText());
        int destination = Integer.parseInt(ctx.children.get(2).getText());
        int tag = Integer.parseInt(ctx.children.get(3).getText());
        int rank = sends.getOrDefault(destination, 0);
        buildSend(communicator, destination, tag, rank, false);
        super.enterIsend(ctx);
    }

    // TODO: Nearest enclosing wait is ignored
    private void buildSend(int communicator, int destination, int tag, int rank, boolean blocks) {
        Operation op = new Send(
                  processBuilder.rank() + "_" + processBuilder.size()  // Name
                , communicator                                         // Communicator
                , processBuilder.rank()                                // Process Rank
                , rank                                                 // Operation rank
                , processBuilder.rank()                                // Source
                , destination                                          // Destination
                , tag                                                  // Tag
                , null                                                 // Nearest wait
                , false);                                              // Blocking?
        processBuilder.addOperation(op);
        sends.put(destination, rank + 1);
    }

    @Override
    public void enterReceive(CTPParser.ReceiveContext ctx) {
        int communicator = Integer.parseInt(ctx.children.get(1).getText());
        int source = Integer.parseInt(ctx.children.get(2).getText());
        int tag = Integer.parseInt(ctx.children.get(3).getText());
        buildRecv(communicator, source, tag, true);
        super.enterReceive(ctx);
    }

    @Override
    public void enterIreceive(CTPParser.IreceiveContext ctx) {
        int communicator = Integer.parseInt(ctx.children.get(1).getText());
        int source = Integer.parseInt(ctx.children.get(2).getText());
        int tag = Integer.parseInt(ctx.children.get(3).getText());
        buildRecv(communicator, source, tag, false);
        super.enterIreceive(ctx);
    }

    // TODO: Nearest enclosing wait is ignored
    private void buildRecv(int communicator, int source, int tag, boolean blocks) {
        Operation op = new Receive(
                  processBuilder.rank() + "_" + processBuilder.size()  // Name
                , communicator                                         // Communicator
                , processBuilder.rank()                                // Process Rank
                , recv_rank                                            // Operation Rank
                , source                                               // Source
                , processBuilder.rank()                                // Destination
                , tag                                                  // Tag
                , null                                                 // Nearest wait
                , blocks                                               // Blocking?
                , (source == -1));                                     // Wildcard?
        processBuilder.addOperation(op);
        recv_rank++;
    }

    @Override
    public void enterBarrier(CTPParser.BarrierContext ctx) {
        int communicator = Integer.parseInt(ctx.children.get(1).getText());
        Operation op = new Barrier(
                processBuilder.rank() + "_" + processBuilder.size()  // Name
                , communicator                                         // Communicator
                , processBuilder.size());                              // Operation rank
        processBuilder.addOperation(op);
        super.enterBarrier(ctx);
    }

    // TODO:
    @Override
    public void enterBlock(CTPParser.BlockContext ctx) {
        super.enterBlock(ctx);
    }

}
