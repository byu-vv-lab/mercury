package edu.byu.cs.vv.parser;

import edu.byu.cs.vv.Parser.CTPLexer;
import edu.byu.cs.vv.Parser.CTPParser;
import edu.byu.cs.vv.ast.Program;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.net.URL;

public class ProgramParser {

    public static Program loadProgramFromFile(URL filename) throws IOException {
        ANTLRInputStream stream = new ANTLRInputStream(filename.openStream());
        CTPLexer lexer = new CTPLexer(stream);
        CTPParser parser = new CTPParser(new CommonTokenStream(lexer));
        ParserRuleContext tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();
        ProgramListener listener = new ProgramListener();

        walker.walk(listener, tree);

        return listener.getProgram();
    }

}
