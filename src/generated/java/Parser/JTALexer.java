// Generated from JTALexer.g4 by ANTLR 4.5

package Parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JTALexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Comment=1, Thread=2, Read=3, Mutate=4, Recv=5, Send=6, Wait=7, Identifier=8, 
		Space=9, LineBreak=10, Number=11, Digit=12;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"Comment", "Thread", "Read", "Mutate", "Recv", "Send", "Wait", "Identifier", 
		"Space", "LineBreak", "Number", "Digit"
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


	public JTALexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "JTALexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\16\u0085\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\7\2 \n\2\f\2\16\2#\13\2\3\2\3"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\65\n\3"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4?\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\5\5M\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6W\n\6"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7a\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\5\bk\n\b\3\t\3\t\3\n\6\np\n\n\r\n\16\nq\3\n\5\nu\n\n\3\13\5\13x\n"+
		"\13\3\13\3\13\3\f\5\f}\n\f\3\f\6\f\u0080\n\f\r\f\16\f\u0081\3\r\3\r\3"+
		"!\2\16\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\3\2\2"+
		"\u0090\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\3\33\3\2\2\2\5\64\3\2\2\2\7>\3\2\2\2\tL\3\2\2\2\13"+
		"V\3\2\2\2\r`\3\2\2\2\17j\3\2\2\2\21l\3\2\2\2\23t\3\2\2\2\25w\3\2\2\2\27"+
		"|\3\2\2\2\31\u0083\3\2\2\2\33\34\7\61\2\2\34\35\7\61\2\2\35!\3\2\2\2\36"+
		" \13\2\2\2\37\36\3\2\2\2 #\3\2\2\2!\"\3\2\2\2!\37\3\2\2\2\"$\3\2\2\2#"+
		"!\3\2\2\2$%\5\25\13\2%&\3\2\2\2&\'\b\2\2\2\'\4\3\2\2\2()\7V\2\2)*\7j\2"+
		"\2*+\7t\2\2+,\7g\2\2,-\7c\2\2-\65\7f\2\2./\7v\2\2/\60\7j\2\2\60\61\7t"+
		"\2\2\61\62\7g\2\2\62\63\7c\2\2\63\65\7f\2\2\64(\3\2\2\2\64.\3\2\2\2\65"+
		"\6\3\2\2\2\66\67\7T\2\2\678\7g\2\289\7c\2\29?\7f\2\2:;\7t\2\2;<\7g\2\2"+
		"<=\7c\2\2=?\7f\2\2>\66\3\2\2\2>:\3\2\2\2?\b\3\2\2\2@A\7O\2\2AB\7w\2\2"+
		"BC\7v\2\2CD\7c\2\2DE\7v\2\2EM\7g\2\2FG\7o\2\2GH\7w\2\2HI\7v\2\2IJ\7c\2"+
		"\2JK\7v\2\2KM\7g\2\2L@\3\2\2\2LF\3\2\2\2M\n\3\2\2\2NO\7T\2\2OP\7g\2\2"+
		"PQ\7e\2\2QW\7x\2\2RS\7t\2\2ST\7g\2\2TU\7e\2\2UW\7x\2\2VN\3\2\2\2VR\3\2"+
		"\2\2W\f\3\2\2\2XY\7U\2\2YZ\7g\2\2Z[\7p\2\2[a\7f\2\2\\]\7u\2\2]^\7g\2\2"+
		"^_\7p\2\2_a\7f\2\2`X\3\2\2\2`\\\3\2\2\2a\16\3\2\2\2bc\7Y\2\2cd\7c\2\2"+
		"de\7k\2\2ek\7v\2\2fg\7y\2\2gh\7c\2\2hi\7k\2\2ik\7v\2\2jb\3\2\2\2jf\3\2"+
		"\2\2k\20\3\2\2\2lm\5\27\f\2m\22\3\2\2\2np\7\"\2\2on\3\2\2\2pq\3\2\2\2"+
		"qo\3\2\2\2qr\3\2\2\2ru\3\2\2\2su\7\13\2\2to\3\2\2\2ts\3\2\2\2u\24\3\2"+
		"\2\2vx\7\17\2\2wv\3\2\2\2wx\3\2\2\2xy\3\2\2\2yz\7\f\2\2z\26\3\2\2\2{}"+
		"\7/\2\2|{\3\2\2\2|}\3\2\2\2}\177\3\2\2\2~\u0080\5\31\r\2\177~\3\2\2\2"+
		"\u0080\u0081\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\30\3"+
		"\2\2\2\u0083\u0084\4\62;\2\u0084\32\3\2\2\2\17\2!\64>LV`jqtw|\u0081\3"+
		"\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}