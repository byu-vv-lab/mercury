package Finder;

import Parser.ProgramFactory;
import Syntax.Program;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.net.URL;

import static org.junit.Assert.*;

@Category(Categories.Finder.class)
public class Unmatched_EPTest {

    @Test
    public void test1 () throws Exception {
        URL test = Unmatched_EPTest.class.getResource("umep1.jta");
        Program p = ProgramFactory.loadProgramFromFile(test);

        UnmatchedEP_Finder finder = new UnmatchedEP_Finder(p);
        finder.run();
        assertEquals(1, p.getUnmatchedEP_Pattern().size());
    }

    @Test
    public void test2 () throws Exception {
        URL test = Unmatched_EPTest.class.getResource("umep2.jta");
        Program p = ProgramFactory.loadProgramFromFile(test);

        UnmatchedEP_Finder finder = new UnmatchedEP_Finder(p);
        finder.run();
        assertEquals(1, p.getUnmatchedEP_Pattern().size());
    }

    @Test
    public void test3 () throws Exception {
        URL test = Unmatched_EPTest.class.getResource("umep3.jta");
        Program p = ProgramFactory.loadProgramFromFile(test);

        UnmatchedEP_Finder finder = new UnmatchedEP_Finder(p);
//        finder.run();
//        assertEquals(1, p.getUnmatchedEP_Pattern().size());
    }

}