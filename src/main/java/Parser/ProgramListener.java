package Parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created by joshuata on 9/10/15.
 */
public class ProgramListener extends JTAParserBaseListener {
    @Override
    public void enterProgram(JTAParser.ProgramContext ctx) {
        super.enterProgram(ctx);
    }

    @Override
    public void exitProgram(JTAParser.ProgramContext ctx) {
        super.exitProgram(ctx);
    }

    @Override
    public void enterThread(JTAParser.ThreadContext ctx) {
        super.enterThread(ctx);
    }

    @Override
    public void exitThread(JTAParser.ThreadContext ctx) {
        super.exitThread(ctx);
    }

    @Override
    public void enterHeader(JTAParser.HeaderContext ctx) {
        super.enterHeader(ctx);
    }

    @Override
    public void exitHeader(JTAParser.HeaderContext ctx) {
        super.exitHeader(ctx);
    }

    @Override
    public void enterExpression(JTAParser.ExpressionContext ctx) {
        super.enterExpression(ctx);
    }

    @Override
    public void exitExpression(JTAParser.ExpressionContext ctx) {
        super.exitExpression(ctx);
    }

    @Override
    public void enterMutate(JTAParser.MutateContext ctx) {
        super.enterMutate(ctx);
    }

    @Override
    public void exitMutate(JTAParser.MutateContext ctx) {
        super.exitMutate(ctx);
    }

    @Override
    public void enterRead(JTAParser.ReadContext ctx) {
        super.enterRead(ctx);
    }

    @Override
    public void exitRead(JTAParser.ReadContext ctx) {
        super.exitRead(ctx);
    }

    @Override
    public void enterBlock(JTAParser.BlockContext ctx) {
        super.enterBlock(ctx);
    }

    @Override
    public void exitBlock(JTAParser.BlockContext ctx) {
        super.exitBlock(ctx);
    }

    @Override
    public void enterSignal(JTAParser.SignalContext ctx) {
        super.enterSignal(ctx);
    }

    @Override
    public void exitSignal(JTAParser.SignalContext ctx) {
        super.exitSignal(ctx);
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        super.enterEveryRule(ctx);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        super.exitEveryRule(ctx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
    }
}
