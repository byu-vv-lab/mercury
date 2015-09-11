package Parser;

import JTASyntax.Program;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class ProgramFactory {

    public static Program loadProgramFromFile(String filename) throws IOException {
        ANTLRInputStream stream = new ANTLRInputStream(ProgramFactory.class.getResourceAsStream(filename));
        JTALexer lexer = new JTALexer(stream);
        JTAParser parser = new JTAParser(new CommonTokenStream(lexer));
        ParserRuleContext tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();
        ProgramListener listener = new ProgramListener();

        walker.walk(listener, tree);

        return listener.getProgram();
    }

}
