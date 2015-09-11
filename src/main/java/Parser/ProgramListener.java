package Parser;

import JTASyntax.*;
import JTASyntax.Thread;

public class ProgramListener extends JTAParserBaseListener {

    private Program program;
    private JTASyntax.Thread thread;
    private Expression expression;

    Program getProgram() {
        return program;
    }

    @Override
    public void enterProgram(JTAParser.ProgramContext ctx) {
        program = new Program();
    }

    @Override
    public void enterThread(JTAParser.ThreadContext ctx) {
        thread = new Thread(ctx.children.get(2).getText());
    }

    @Override
    public void exitThread(JTAParser.ThreadContext ctx) {
        program.addThread(thread);
    }

    @Override
    public void enterReceive(JTAParser.ReceiveContext ctx) {
        expression = Expression.new_recv(ctx.children.get(2).getText(), ctx.children.get(4).getText());
    }

    @Override
    public void exitReceive(JTAParser.ReceiveContext ctx) {
        thread.addExpression(expression);
    }

    @Override
    public void enterSend(JTAParser.SendContext ctx) {
        expression = Expression.new_send(thread.name, ctx.children.get(2).getText(), ctx.children.get(4).getText());
    }

    @Override
    public void exitSend(JTAParser.SendContext ctx) {
        thread.addExpression(expression);
    }
}
