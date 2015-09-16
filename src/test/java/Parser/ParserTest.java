package Parser;

import Syntax.Program;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

@Category(Categories.Parser.class)
public class ParserTest {

    @Test
    public void trivialPrograms () throws IOException {
        URL test1 = ParserTest.class.getResource("test1.jta");
        Program p = ProgramFactory.loadProgramFromFile(test1);
        assertEquals(p, TestProgramFactory.test1());

        URL test2 = ParserTest.class.getResource("test2.jta");
        p = ProgramFactory.loadProgramFromFile(test2);
        assertEquals(p, TestProgramFactory.test2());
    }

}