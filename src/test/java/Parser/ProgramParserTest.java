package Parser;

import JTASyntax.Program;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

@Category(Categories.Parser.class)
public class ProgramParserTest {

    @Test
    public void trivialPrograms () throws IOException {
        URL test1 = ProgramParserTest.class.getResource("test1.jta");
        Program p = ProgramParser.loadProgramFromFile(test1);
        assertEquals(TestProgramFactory.test1(), p);

        URL test2 = ProgramParserTest.class.getResource("test2.jta");
        p = ProgramParser.loadProgramFromFile(test2);
        assertEquals(TestProgramFactory.test2(), p);
    }

}