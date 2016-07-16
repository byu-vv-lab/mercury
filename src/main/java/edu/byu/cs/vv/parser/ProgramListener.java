package edu.byu.cs.vv.parser;

import edu.byu.cs.vv.Parser.CTPParser;
import edu.byu.cs.vv.Parser.CTPParserBaseListener;
import edu.byu.cs.vv.ast.operations.*;
import edu.byu.cs.vv.ast.Program;
import org.antlr.v4.runtime.tree.ErrorNode;

import java.lang.*;
import java.util.HashMap;
import java.util.Map;

class ProgramListener extends CTPParserBaseListener {

    private ProgramBuilder programBuilder;
    private ProcessBuilder processBuilder;
    private Map<Integer, Integer> sends;

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
        String name = processBuilder.rank() + "_" + processBuilder.sends();
        String waitName = processBuilder.rank() + "_" + processBuilder.waits();

        Operation op = new Send(
                  name                           // Name
                , communicator                   // Communicator
                , processBuilder.rank()          // Process Rank
                , rank                           // Operation rank
                , processBuilder.rank()          // Source
                , destination                    // Destination
                , tag);                          // Tag
        processBuilder.addOperation(op);
        sends.put(destination, rank + 1);

        Operation wait = new Wait(
                  waitName                       // Name
                , name                           // Witnessed operation
                , processBuilder.rank()          // Process rank
                , processBuilder.waits()         // Rank
        );
        processBuilder.addOperation(wait);
        super.enterSend(ctx);
    }

    @Override
    public void enterIsend(CTPParser.IsendContext ctx) {
        int communicator = Integer.parseInt(ctx.Communicator().getText());
        int destination = Integer.parseInt(ctx.Process().getText());
        int tag = Integer.parseInt(ctx.Tag().getText());
        int rank = sends.getOrDefault(destination, 0);
        String name = processBuilder.rank() + "_" + processBuilder.sends();

        Operation op = new Send(
                  name                           // Name
                , communicator                   // Communicator
                , processBuilder.rank()          // Process Rank
                , rank                           // Operation rank
                , processBuilder.rank()          // Source
                , destination                    // Destination
                , tag);                          // Tag
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
        String name = processBuilder.rank() + "_" + processBuilder.recvs();
        String waitName = processBuilder.rank() + "_" + processBuilder.waits();

        Operation op = new Receive(
                  name                           // Name
                , communicator                   // Communicator
                , processBuilder.rank()          // Process Rank
                , processBuilder.recvs()         // Operation Rank
                , source                         // Source
                , processBuilder.rank()          // Destination
                , tag);                          // Tag
        processBuilder.addOperation(op);

        Operation wait = new Wait(
                  waitName                       // Name
                , name                           // Witnessed operation
                , processBuilder.rank()          // Process rank
                , processBuilder.waits()         // Rank
        );
        processBuilder.addOperation(wait);

        super.enterReceive(ctx);
    }

    @Override
    public void enterIreceive(CTPParser.IreceiveContext ctx) {
        int communicator = Integer.parseInt(ctx.Communicator().getText());
        int source = Integer.parseInt(ctx.Process().getText());
        int tag = Integer.parseInt(ctx.Tag().getText());
        String name = processBuilder.rank() + "_" + processBuilder.recvs();

        Operation op = new Receive(
                  name                           // Name
                , communicator                   // Communicator
                , processBuilder.rank()          // Process Rank
                , processBuilder.recvs()         // Operation Rank
                , source                         // Source
                , processBuilder.rank()          // Destination
                , tag);                          // Tag
        processBuilder.addOperation(op);
        super.enterIreceive(ctx);
    }

    @Override
    public void enterBarrier(CTPParser.BarrierContext ctx) {
        int communicator = Integer.parseInt(ctx.Communicator().getText());
        String name = processBuilder.rank() + "_" + processBuilder.recvs();

        Operation op = new Barrier(
                  name                           // Name
                , communicator                   // Communicator
                , processBuilder.size());        // Operation rank
        processBuilder.addOperation(op);
        super.enterBarrier(ctx);
    }

    @Override
    public void enterBlock(CTPParser.BlockContext ctx){
        String name = processBuilder.rank() + "_" + processBuilder.waits();
        String witnessed = ctx.Identifier().getText();

        Operation wait = new Wait(
                  name                           // Name
                , witnessed                      // Witnessed operation
                , processBuilder.rank()          // Process rank
                , processBuilder.waits()         // Rank
        );
        processBuilder.addOperation(wait);
        super.enterBlock(ctx);
    }

}
