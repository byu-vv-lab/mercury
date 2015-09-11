// Generated from /Users/joshuata/code/research/ppopp16/src/main/antlr/JTAParser.g4 by ANTLR 4.5

package Parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JTAParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Thread=1, Read=2, Mutate=3, Recv=4, Send=5, Identifier=6, Space=7, LineBreak=8, 
		Number=9, Digit=10;
	public static final int
		RULE_program = 0, RULE_thread = 1, RULE_expression = 2, RULE_mutate = 3, 
		RULE_read = 4, RULE_receive = 5, RULE_send = 6;
	public static final String[] ruleNames = {
		"program", "thread", "expression", "mutate", "read", "receive", "send"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "Thread", "Read", "Mutate", "Recv", "Send", "Identifier", "Space", 
		"LineBreak", "Number", "Digit"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "JTAParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JTAParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(JTAParser.EOF, 0); }
		public List<ThreadContext> thread() {
			return getRuleContexts(ThreadContext.class);
		}
		public ThreadContext thread(int i) {
			return getRuleContext(ThreadContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(15); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(14);
				thread();
				}
				}
				setState(17); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Thread );
			setState(19);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ThreadContext extends ParserRuleContext {
		public TerminalNode Thread() { return getToken(JTAParser.Thread, 0); }
		public TerminalNode Identifier() { return getToken(JTAParser.Identifier, 0); }
		public List<TerminalNode> LineBreak() { return getTokens(JTAParser.LineBreak); }
		public TerminalNode LineBreak(int i) {
			return getToken(JTAParser.LineBreak, i);
		}
		public List<TerminalNode> Space() { return getTokens(JTAParser.Space); }
		public TerminalNode Space(int i) {
			return getToken(JTAParser.Space, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ThreadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_thread; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).enterThread(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).exitThread(this);
		}
	}

	public final ThreadContext thread() throws RecognitionException {
		ThreadContext _localctx = new ThreadContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_thread);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			match(Thread);
			setState(23); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(22);
				match(Space);
				}
				}
				setState(25); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(27);
			match(Identifier);
			setState(28);
			match(LineBreak);
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Read) | (1L << Mutate) | (1L << Recv) | (1L << Send))) != 0)) {
				{
				{
				setState(29);
				expression();
				}
				}
				setState(34);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LineBreak) {
				{
				{
				setState(35);
				match(LineBreak);
				}
				}
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public MutateContext mutate() {
			return getRuleContext(MutateContext.class,0);
		}
		public ReadContext read() {
			return getRuleContext(ReadContext.class,0);
		}
		public ReceiveContext receive() {
			return getRuleContext(ReceiveContext.class,0);
		}
		public SendContext send() {
			return getRuleContext(SendContext.class,0);
		}
		public TerminalNode LineBreak() { return getToken(JTAParser.LineBreak, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			switch (_input.LA(1)) {
			case Mutate:
				{
				setState(41);
				mutate();
				}
				break;
			case Read:
				{
				setState(42);
				read();
				}
				break;
			case Recv:
				{
				setState(43);
				receive();
				}
				break;
			case Send:
				{
				setState(44);
				send();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(48);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(47);
				match(LineBreak);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MutateContext extends ParserRuleContext {
		public TerminalNode Mutate() { return getToken(JTAParser.Mutate, 0); }
		public TerminalNode Identifier() { return getToken(JTAParser.Identifier, 0); }
		public List<TerminalNode> Space() { return getTokens(JTAParser.Space); }
		public TerminalNode Space(int i) {
			return getToken(JTAParser.Space, i);
		}
		public MutateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mutate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).enterMutate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).exitMutate(this);
		}
	}

	public final MutateContext mutate() throws RecognitionException {
		MutateContext _localctx = new MutateContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_mutate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(Mutate);
			setState(52); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(51);
				match(Space);
				}
				}
				setState(54); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(56);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReadContext extends ParserRuleContext {
		public TerminalNode Read() { return getToken(JTAParser.Read, 0); }
		public TerminalNode Identifier() { return getToken(JTAParser.Identifier, 0); }
		public List<TerminalNode> Space() { return getTokens(JTAParser.Space); }
		public TerminalNode Space(int i) {
			return getToken(JTAParser.Space, i);
		}
		public ReadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_read; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).enterRead(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).exitRead(this);
		}
	}

	public final ReadContext read() throws RecognitionException {
		ReadContext _localctx = new ReadContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_read);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(Read);
			setState(60); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(59);
				match(Space);
				}
				}
				setState(62); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(64);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReceiveContext extends ParserRuleContext {
		public TerminalNode Recv() { return getToken(JTAParser.Recv, 0); }
		public List<TerminalNode> Identifier() { return getTokens(JTAParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(JTAParser.Identifier, i);
		}
		public List<TerminalNode> Space() { return getTokens(JTAParser.Space); }
		public TerminalNode Space(int i) {
			return getToken(JTAParser.Space, i);
		}
		public ReceiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_receive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).enterReceive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).exitReceive(this);
		}
	}

	public final ReceiveContext receive() throws RecognitionException {
		ReceiveContext _localctx = new ReceiveContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_receive);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(Recv);
			setState(68); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(67);
				match(Space);
				}
				}
				setState(70); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(72);
			match(Identifier);
			setState(74); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(73);
				match(Space);
				}
				}
				setState(76); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(78);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SendContext extends ParserRuleContext {
		public TerminalNode Send() { return getToken(JTAParser.Send, 0); }
		public List<TerminalNode> Identifier() { return getTokens(JTAParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(JTAParser.Identifier, i);
		}
		public List<TerminalNode> Space() { return getTokens(JTAParser.Space); }
		public TerminalNode Space(int i) {
			return getToken(JTAParser.Space, i);
		}
		public SendContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_send; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).enterSend(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).exitSend(this);
		}
	}

	public final SendContext send() throws RecognitionException {
		SendContext _localctx = new SendContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_send);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(Send);
			setState(82); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(81);
				match(Space);
				}
				}
				setState(84); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(86);
			match(Identifier);
			setState(88); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(87);
				match(Space);
				}
				}
				setState(90); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(92);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\fa\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\6\2\22\n\2\r\2\16\2\23"+
		"\3\2\3\2\3\3\3\3\6\3\32\n\3\r\3\16\3\33\3\3\3\3\3\3\7\3!\n\3\f\3\16\3"+
		"$\13\3\3\3\7\3\'\n\3\f\3\16\3*\13\3\3\4\3\4\3\4\3\4\5\4\60\n\4\3\4\5\4"+
		"\63\n\4\3\5\3\5\6\5\67\n\5\r\5\16\58\3\5\3\5\3\6\3\6\6\6?\n\6\r\6\16\6"+
		"@\3\6\3\6\3\7\3\7\6\7G\n\7\r\7\16\7H\3\7\3\7\6\7M\n\7\r\7\16\7N\3\7\3"+
		"\7\3\b\3\b\6\bU\n\b\r\b\16\bV\3\b\3\b\6\b[\n\b\r\b\16\b\\\3\b\3\b\3\b"+
		"\2\2\t\2\4\6\b\n\f\16\2\2g\2\21\3\2\2\2\4\27\3\2\2\2\6/\3\2\2\2\b\64\3"+
		"\2\2\2\n<\3\2\2\2\fD\3\2\2\2\16R\3\2\2\2\20\22\5\4\3\2\21\20\3\2\2\2\22"+
		"\23\3\2\2\2\23\21\3\2\2\2\23\24\3\2\2\2\24\25\3\2\2\2\25\26\7\2\2\3\26"+
		"\3\3\2\2\2\27\31\7\3\2\2\30\32\7\t\2\2\31\30\3\2\2\2\32\33\3\2\2\2\33"+
		"\31\3\2\2\2\33\34\3\2\2\2\34\35\3\2\2\2\35\36\7\b\2\2\36\"\7\n\2\2\37"+
		"!\5\6\4\2 \37\3\2\2\2!$\3\2\2\2\" \3\2\2\2\"#\3\2\2\2#(\3\2\2\2$\"\3\2"+
		"\2\2%\'\7\n\2\2&%\3\2\2\2\'*\3\2\2\2(&\3\2\2\2()\3\2\2\2)\5\3\2\2\2*("+
		"\3\2\2\2+\60\5\b\5\2,\60\5\n\6\2-\60\5\f\7\2.\60\5\16\b\2/+\3\2\2\2/,"+
		"\3\2\2\2/-\3\2\2\2/.\3\2\2\2\60\62\3\2\2\2\61\63\7\n\2\2\62\61\3\2\2\2"+
		"\62\63\3\2\2\2\63\7\3\2\2\2\64\66\7\5\2\2\65\67\7\t\2\2\66\65\3\2\2\2"+
		"\678\3\2\2\28\66\3\2\2\289\3\2\2\29:\3\2\2\2:;\7\b\2\2;\t\3\2\2\2<>\7"+
		"\4\2\2=?\7\t\2\2>=\3\2\2\2?@\3\2\2\2@>\3\2\2\2@A\3\2\2\2AB\3\2\2\2BC\7"+
		"\b\2\2C\13\3\2\2\2DF\7\6\2\2EG\7\t\2\2FE\3\2\2\2GH\3\2\2\2HF\3\2\2\2H"+
		"I\3\2\2\2IJ\3\2\2\2JL\7\b\2\2KM\7\t\2\2LK\3\2\2\2MN\3\2\2\2NL\3\2\2\2"+
		"NO\3\2\2\2OP\3\2\2\2PQ\7\b\2\2Q\r\3\2\2\2RT\7\7\2\2SU\7\t\2\2TS\3\2\2"+
		"\2UV\3\2\2\2VT\3\2\2\2VW\3\2\2\2WX\3\2\2\2XZ\7\b\2\2Y[\7\t\2\2ZY\3\2\2"+
		"\2[\\\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]^\3\2\2\2^_\7\b\2\2_\17\3\2\2\2\16"+
		"\23\33\"(/\628@HNV\\";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}