// Generated from /Users/joshuata/code/research/ppopp16/src/main/antlr/JTAParser.g4 by ANTLR 4.5

package Parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JTAParser}.
 */
public interface JTAParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JTAParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(JTAParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link JTAParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(JTAParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link JTAParser#thread}.
	 * @param ctx the parse tree
	 */
	void enterThread(JTAParser.ThreadContext ctx);
	/**
	 * Exit a parse tree produced by {@link JTAParser#thread}.
	 * @param ctx the parse tree
	 */
	void exitThread(JTAParser.ThreadContext ctx);
	/**
	 * Enter a parse tree produced by {@link JTAParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterOperation(JTAParser.OperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JTAParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitOperation(JTAParser.OperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JTAParser#mutate}.
	 * @param ctx the parse tree
	 */
	void enterMutate(JTAParser.MutateContext ctx);
	/**
	 * Exit a parse tree produced by {@link JTAParser#mutate}.
	 * @param ctx the parse tree
	 */
	void exitMutate(JTAParser.MutateContext ctx);
	/**
	 * Enter a parse tree produced by {@link JTAParser#read}.
	 * @param ctx the parse tree
	 */
	void enterRead(JTAParser.ReadContext ctx);
	/**
	 * Exit a parse tree produced by {@link JTAParser#read}.
	 * @param ctx the parse tree
	 */
	void exitRead(JTAParser.ReadContext ctx);
	/**
	 * Enter a parse tree produced by {@link JTAParser#receive}.
	 * @param ctx the parse tree
	 */
	void enterReceive(JTAParser.ReceiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link JTAParser#receive}.
	 * @param ctx the parse tree
	 */
	void exitReceive(JTAParser.ReceiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link JTAParser#send}.
	 * @param ctx the parse tree
	 */
	void enterSend(JTAParser.SendContext ctx);
	/**
	 * Exit a parse tree produced by {@link JTAParser#send}.
	 * @param ctx the parse tree
	 */
	void exitSend(JTAParser.SendContext ctx);
	/**
	 * Enter a parse tree produced by {@link JTAParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(JTAParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link JTAParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(JTAParser.BlockContext ctx);
}