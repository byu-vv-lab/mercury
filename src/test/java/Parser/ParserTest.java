package Parser;

import JTASyntax.Program;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(Categories.Parser.class)
public class ParserTest {

    @Test
    public void stupidTest () {
        assertTrue(true);
    }

    @Test
    public void trivialProgram () throws IOException {
        Program p = ProgramFactory.loadProgramFromFile("trivial.jta");
        assertEquals(p, TestProgramFactory.dlg1());
    }
}
