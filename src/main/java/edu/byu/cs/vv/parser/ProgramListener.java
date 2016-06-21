package edu.byu.cs.vv.parser;

import edu.byu.cs.vv.Parser.CTPParser;
import edu.byu.cs.vv.Parser.CTPParserBaseListener;
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
        int communicator = Integer.parseInt(ctx.Communicator().getText());
        int destination = Integer.parseInt(ctx.Process().getText());
        int tag = Integer.parseInt(ctx.Tag().getText());
        int rank = sends.getOrDefault(destination, 0);
        Operation op = new Send(
                  processBuilder.rank() + "_" + processBuilder.size()  // Name
                , communicator                                         // Communicator
                , processBuilder.rank()                                // Process Rank
                , rank                                                 // Operation rank
                , processBuilder.rank()                                // Source
                , destination                                          // Destination
                , tag                                                  // Tag
                , null                                                 // Nearest wait
                , true);                                               // Blocking?
        processBuilder.addOperation(op);
        sends.put(destination, rank + 1);
        super.enterSend(ctx);
    }

    @Override
    public void enterIsend(CTPParser.IsendContext ctx) {
        int communicator = Integer.parseInt(ctx.Communicator().getText());
        int destination = Integer.parseInt(ctx.Process().getText());
        int tag = Integer.parseInt(ctx.Tag().getText());
        int rank = sends.getOrDefault(destination, 0);
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
        super.enterIsend(ctx);
    }

    // TODO: Nearest enclosing wait is ignored
    @Override
    public void enterReceive(CTPParser.ReceiveContext ctx) {
        int communicator = Integer.parseInt(ctx.Communicator().getText());
        int source = Integer.parseInt(ctx.Process().getText());
        int tag = Integer.parseInt(ctx.Tag().getText());
        Operation op = new Receive(
                  processBuilder.rank() + "_" + processBuilder.size()  // Name
                , communicator                                         // Communicator
                , processBuilder.rank()                                // Process Rank
                , recv_rank                                            // Operation Rank
                , source                                               // Source
                , processBuilder.rank()                                // Destination
                , tag                                                  // Tag
                , null                                                 // Nearest wait
                , true);                                               // Blocking?
        processBuilder.addOperation(op);
        recv_rank++;
        super.enterReceive(ctx);
    }

    @Override
    public void enterIreceive(CTPParser.IreceiveContext ctx) {
        int communicator = Integer.parseInt(ctx.Communicator().getText());
        int source = Integer.parseInt(ctx.Process().getText());
        int tag = Integer.parseInt(ctx.Tag().getText());
        Operation op = new Receive(
                  processBuilder.rank() + "_" + processBuilder.size()  // Name
                , communicator                                         // Communicator
                , processBuilder.rank()                                // Process Rank
                , recv_rank                                            // Operation Rank
                , source                                               // Source
                , processBuilder.rank()                                // Destination
                , tag                                                  // Tag
                , null                                                 // Nearest wait
                , false);                                              // Blocking?
        processBuilder.addOperation(op);
        recv_rank++;
        super.enterIreceive(ctx);
    }

    @Override
    public void enterBarrier(CTPParser.BarrierContext ctx) {
        int communicator = Integer.parseInt(ctx.Communicator().getText());
        Operation op = new Barrier(
                processBuilder.rank() + "_" + processBuilder.size()  // Name
                , communicator                                         // Communicator
                , processBuilder.size());                              // Operation rank
        processBuilder.addOperation(op);
        super.enterBarrier(ctx);
    }

    // TODO:
    @Override
    public void enterBlock(CTPParser.BlockContext ctx){
        super.enterBlock(ctx);
    }

}
