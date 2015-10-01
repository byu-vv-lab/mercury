package Finder;

import JTAFinder.AbstractFinder;
import JTAFinder.UnmatchedEndpointFinder;
import JTASyntax.Program;
import Parser.ProgramParser;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.net.URL;

import static org.junit.Assert.*;

@Category(Categories.Finder.class)
public class UnmatchedEndpointFinderTest {

    @Test
    public void test1 () throws Exception {
        URL test = UnmatchedEndpointFinderTest.class.getResource("umep1.jta");
        Program p = ProgramParser.loadProgramFromFile(test);

        AbstractFinder finder = new UnmatchedEndpointFinder(p);
        finder.verify();
        assertTrue(finder.getResult());
    }

    @Test
    public void test2 () throws Exception {
        URL test = UnmatchedEndpointFinderTest.class.getResource("umep2.jta");
        Program p = ProgramParser.loadProgramFromFile(test);

        AbstractFinder finder = new UnmatchedEndpointFinder(p);
        finder.verify();
        assertFalse(finder.getResult());
    }

    @Test
    public void test3 () throws Exception {
        URL test = UnmatchedEndpointFinderTest.class.getResource("umep3.jta");
        Program p = ProgramParser.loadProgramFromFile(test);

        AbstractFinder finder = new UnmatchedEndpointFinder(p);
        finder.verify();
        assertTrue(finder.getResult());
    }

}