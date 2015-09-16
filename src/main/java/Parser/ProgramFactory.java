package Parser;

import Syntax.Program;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class ProgramFactory {

    public static Program loadProgramFromFile(URL filename) throws IOException {
        ANTLRInputStream stream = new ANTLRInputStream(filename.openStream());
        JTALexer lexer = new JTALexer(stream);
        JTAParser parser = new JTAParser(new CommonTokenStream(lexer));
        ParserRuleContext tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();
        ProgramListener listener = new ProgramListener();

        walker.walk(listener, tree);

        return listener.getProgram();
    }

}
