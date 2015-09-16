package Parser;

import Syntax.Program;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

import static org.junit.Assert.*;

@Category(Categories.Parser.class)
public class ParserTest {

    @Test
    public void trivialPrograms () throws IOException {
        Program p = ProgramFactory.loadProgramFromFile("test1.jta");
        assertEquals(p, TestProgramFactory.test1());

        p = ProgramFactory.loadProgramFromFile("test2.jta");
        assertEquals(p, TestProgramFactory.test2());
    }

}