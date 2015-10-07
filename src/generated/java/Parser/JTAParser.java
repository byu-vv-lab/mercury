// Generated from JTAParser.g4 by ANTLR 4.5

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
		Comment=1, Thread=2, Read=3, Mutate=4, Recv=5, Send=6, Wait=7, Identifier=8, 
		Space=9, LineBreak=10, Number=11, Digit=12;
	public static final int
		RULE_program = 0, RULE_thread = 1, RULE_operation = 2, RULE_mutate = 3, 
		RULE_read = 4, RULE_receive = 5, RULE_send = 6, RULE_block = 7;
	public static final String[] ruleNames = {
		"program", "thread", "operation", "mutate", "read", "receive", "send", 
		"block"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "Comment", "Thread", "Read", "Mutate", "Recv", "Send", "Wait", "Identifier", 
		"Space", "LineBreak", "Number", "Digit"
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
			setState(17); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(16);
				thread();
				}
				}
				setState(19); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Thread );
			setState(21);
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
		public List<OperationContext> operation() {
			return getRuleContexts(OperationContext.class);
		}
		public OperationContext operation(int i) {
			return getRuleContext(OperationContext.class,i);
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
			setState(23);
			match(Thread);
			setState(25); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(24);
				match(Space);
				}
				}
				setState(27); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(29);
			match(Identifier);
			setState(30);
			match(LineBreak);
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Read) | (1L << Mutate) | (1L << Recv) | (1L << Send) | (1L << Wait))) != 0)) {
				{
				{
				setState(31);
				operation();
				}
				}
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(40);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LineBreak) {
				{
				{
				setState(37);
				match(LineBreak);
				}
				}
				setState(42);
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

	public static class OperationContext extends ParserRuleContext {
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
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode LineBreak() { return getToken(JTAParser.LineBreak, 0); }
		public OperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).enterOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).exitOperation(this);
		}
	}

	public final OperationContext operation() throws RecognitionException {
		OperationContext _localctx = new OperationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_operation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			switch (_input.LA(1)) {
			case Mutate:
				{
				setState(43);
				mutate();
				}
				break;
			case Read:
				{
				setState(44);
				read();
				}
				break;
			case Recv:
				{
				setState(45);
				receive();
				}
				break;
			case Send:
				{
				setState(46);
				send();
				}
				break;
			case Wait:
				{
				setState(47);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(51);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(50);
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
			setState(53);
			match(Mutate);
			setState(55); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(54);
				match(Space);
				}
				}
				setState(57); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(59);
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
			setState(61);
			match(Read);
			setState(63); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(62);
				match(Space);
				}
				}
				setState(65); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(67);
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
			setState(69);
			match(Recv);
			setState(71); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(70);
				match(Space);
				}
				}
				setState(73); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(75);
			match(Identifier);
			setState(77); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(76);
				match(Space);
				}
				}
				setState(79); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(81);
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
			setState(83);
			match(Send);
			setState(85); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(84);
				match(Space);
				}
				}
				setState(87); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(89);
			match(Identifier);
			setState(91); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(90);
				match(Space);
				}
				}
				setState(93); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(95);
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

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode Wait() { return getToken(JTAParser.Wait, 0); }
		public TerminalNode Identifier() { return getToken(JTAParser.Identifier, 0); }
		public List<TerminalNode> Space() { return getTokens(JTAParser.Space); }
		public TerminalNode Space(int i) {
			return getToken(JTAParser.Space, i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JTAParserListener ) ((JTAParserListener)listener).exitBlock(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(Wait);
			setState(99); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(98);
				match(Space);
				}
				}
				setState(101); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Space );
			setState(103);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\16l\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\6\2\24\n\2\r\2"+
		"\16\2\25\3\2\3\2\3\3\3\3\6\3\34\n\3\r\3\16\3\35\3\3\3\3\3\3\7\3#\n\3\f"+
		"\3\16\3&\13\3\3\3\7\3)\n\3\f\3\16\3,\13\3\3\4\3\4\3\4\3\4\3\4\5\4\63\n"+
		"\4\3\4\5\4\66\n\4\3\5\3\5\6\5:\n\5\r\5\16\5;\3\5\3\5\3\6\3\6\6\6B\n\6"+
		"\r\6\16\6C\3\6\3\6\3\7\3\7\6\7J\n\7\r\7\16\7K\3\7\3\7\6\7P\n\7\r\7\16"+
		"\7Q\3\7\3\7\3\b\3\b\6\bX\n\b\r\b\16\bY\3\b\3\b\6\b^\n\b\r\b\16\b_\3\b"+
		"\3\b\3\t\3\t\6\tf\n\t\r\t\16\tg\3\t\3\t\3\t\2\2\n\2\4\6\b\n\f\16\20\2"+
		"\2s\2\23\3\2\2\2\4\31\3\2\2\2\6\62\3\2\2\2\b\67\3\2\2\2\n?\3\2\2\2\fG"+
		"\3\2\2\2\16U\3\2\2\2\20c\3\2\2\2\22\24\5\4\3\2\23\22\3\2\2\2\24\25\3\2"+
		"\2\2\25\23\3\2\2\2\25\26\3\2\2\2\26\27\3\2\2\2\27\30\7\2\2\3\30\3\3\2"+
		"\2\2\31\33\7\4\2\2\32\34\7\13\2\2\33\32\3\2\2\2\34\35\3\2\2\2\35\33\3"+
		"\2\2\2\35\36\3\2\2\2\36\37\3\2\2\2\37 \7\n\2\2 $\7\f\2\2!#\5\6\4\2\"!"+
		"\3\2\2\2#&\3\2\2\2$\"\3\2\2\2$%\3\2\2\2%*\3\2\2\2&$\3\2\2\2\')\7\f\2\2"+
		"(\'\3\2\2\2),\3\2\2\2*(\3\2\2\2*+\3\2\2\2+\5\3\2\2\2,*\3\2\2\2-\63\5\b"+
		"\5\2.\63\5\n\6\2/\63\5\f\7\2\60\63\5\16\b\2\61\63\5\20\t\2\62-\3\2\2\2"+
		"\62.\3\2\2\2\62/\3\2\2\2\62\60\3\2\2\2\62\61\3\2\2\2\63\65\3\2\2\2\64"+
		"\66\7\f\2\2\65\64\3\2\2\2\65\66\3\2\2\2\66\7\3\2\2\2\679\7\6\2\28:\7\13"+
		"\2\298\3\2\2\2:;\3\2\2\2;9\3\2\2\2;<\3\2\2\2<=\3\2\2\2=>\7\n\2\2>\t\3"+
		"\2\2\2?A\7\5\2\2@B\7\13\2\2A@\3\2\2\2BC\3\2\2\2CA\3\2\2\2CD\3\2\2\2DE"+
		"\3\2\2\2EF\7\n\2\2F\13\3\2\2\2GI\7\7\2\2HJ\7\13\2\2IH\3\2\2\2JK\3\2\2"+
		"\2KI\3\2\2\2KL\3\2\2\2LM\3\2\2\2MO\7\n\2\2NP\7\13\2\2ON\3\2\2\2PQ\3\2"+
		"\2\2QO\3\2\2\2QR\3\2\2\2RS\3\2\2\2ST\7\n\2\2T\r\3\2\2\2UW\7\b\2\2VX\7"+
		"\13\2\2WV\3\2\2\2XY\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z[\3\2\2\2[]\7\n\2\2\\"+
		"^\7\13\2\2]\\\3\2\2\2^_\3\2\2\2_]\3\2\2\2_`\3\2\2\2`a\3\2\2\2ab\7\n\2"+
		"\2b\17\3\2\2\2ce\7\t\2\2df\7\13\2\2ed\3\2\2\2fg\3\2\2\2ge\3\2\2\2gh\3"+
		"\2\2\2hi\3\2\2\2ij\7\n\2\2j\21\3\2\2\2\17\25\35$*\62\65;CKQY_g";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}