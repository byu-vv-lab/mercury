// Generated from /Users/joshuata/code/research/ppopp16/src/main/antlr/JTALexer.g4 by ANTLR 4.5

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
		Thread=1, Read=2, Mutate=3, Recv=4, Send=5, Identifier=6, Space=7, LineBreak=8, 
		Number=9, Digit=10;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"Thread", "Read", "Mutate", "Recv", "Send", "Identifier", "Space", "LineBreak", 
		"Number", "Digit"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\fo\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2$\n\2\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\5\3.\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\5\4<\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5F\n\5\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\5\6P\n\6\3\7\3\7\3\7\7\7U\n\7\f\7\16\7X\13\7\3"+
		"\7\5\7[\n\7\3\b\3\b\3\t\5\t`\n\t\3\t\3\t\5\td\n\t\3\n\5\ng\n\n\3\n\6\n"+
		"j\n\n\r\n\16\nk\3\13\3\13\2\2\f\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\3\2\5\5\2C\\aac|\4\2\13\13\"\"\4\2ppttz\2\3\3\2\2\2\2\5\3\2\2"+
		"\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\3#\3\2\2\2\5-\3\2\2\2\7;\3\2\2\2\t"+
		"E\3\2\2\2\13O\3\2\2\2\rZ\3\2\2\2\17\\\3\2\2\2\21c\3\2\2\2\23f\3\2\2\2"+
		"\25m\3\2\2\2\27\30\7V\2\2\30\31\7j\2\2\31\32\7t\2\2\32\33\7g\2\2\33\34"+
		"\7c\2\2\34$\7f\2\2\35\36\7v\2\2\36\37\7j\2\2\37 \7t\2\2 !\7g\2\2!\"\7"+
		"c\2\2\"$\7f\2\2#\27\3\2\2\2#\35\3\2\2\2$\4\3\2\2\2%&\7T\2\2&\'\7g\2\2"+
		"\'(\7c\2\2(.\7f\2\2)*\7t\2\2*+\7g\2\2+,\7c\2\2,.\7f\2\2-%\3\2\2\2-)\3"+
		"\2\2\2.\6\3\2\2\2/\60\7O\2\2\60\61\7w\2\2\61\62\7v\2\2\62\63\7c\2\2\63"+
		"\64\7v\2\2\64<\7g\2\2\65\66\7o\2\2\66\67\7w\2\2\678\7v\2\289\7c\2\29:"+
		"\7v\2\2:<\7g\2\2;/\3\2\2\2;\65\3\2\2\2<\b\3\2\2\2=>\7T\2\2>?\7g\2\2?@"+
		"\7e\2\2@F\7x\2\2AB\7t\2\2BC\7g\2\2CD\7e\2\2DF\7x\2\2E=\3\2\2\2EA\3\2\2"+
		"\2F\n\3\2\2\2GH\7U\2\2HI\7g\2\2IJ\7p\2\2JP\7f\2\2KL\7u\2\2LM\7g\2\2MN"+
		"\7p\2\2NP\7f\2\2OG\3\2\2\2OK\3\2\2\2P\f\3\2\2\2QV\t\2\2\2RU\t\2\2\2SU"+
		"\5\23\n\2TR\3\2\2\2TS\3\2\2\2UX\3\2\2\2VT\3\2\2\2VW\3\2\2\2W[\3\2\2\2"+
		"XV\3\2\2\2Y[\5\23\n\2ZQ\3\2\2\2ZY\3\2\2\2[\16\3\2\2\2\\]\t\3\2\2]\20\3"+
		"\2\2\2^`\7\17\2\2_^\3\2\2\2_`\3\2\2\2`a\3\2\2\2ad\7\f\2\2bd\t\4\2\2c_"+
		"\3\2\2\2cb\3\2\2\2d\22\3\2\2\2eg\7/\2\2fe\3\2\2\2fg\3\2\2\2gi\3\2\2\2"+
		"hj\5\25\13\2ih\3\2\2\2jk\3\2\2\2ki\3\2\2\2kl\3\2\2\2l\24\3\2\2\2mn\4\62"+
		";\2n\26\3\2\2\2\17\2#-;EOTVZ_cfk\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}