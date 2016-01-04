package edu.byu.cs.vv.Finder;

import edu.byu.cs.vv.Finder.UnmatchedEndpoint.UmEPFinder;
import edu.byu.cs.vv.Parser.ProgramParser;
import edu.byu.cs.vv.Syntax.Program;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.net.URL;

import static org.junit.Assert.*;

@Category(edu.byu.cs.vv.Categories.Finder.class)
public class UnmatchedEndpointFinderTest {

    @Test
    public void test1 () throws Exception {
        URL test = UnmatchedEndpointFinderTest.class.getResource("umep1.jta");
        Program p = ProgramParser.loadProgramFromFile(test);

        AbstractFinder finder = new UmEPFinder(p);
        finder.verify();
        assertTrue(finder.getResult());
    }

    @Test
    public void test2 () throws Exception {
        URL test = UnmatchedEndpointFinderTest.class.getResource("umep2.jta");
        Program p = ProgramParser.loadProgramFromFile(test);

        AbstractFinder finder = new UmEPFinder(p);
        finder.verify();
        assertFalse(finder.getResult());
    }

    @Test
    public void test3 () throws Exception {
        URL test = UnmatchedEndpointFinderTest.class.getResource("umep3.jta");
        Program p = ProgramParser.loadProgramFromFile(test);

        AbstractFinder finder = new UmEPFinder(p);
        finder.verify();
        assertTrue(finder.getResult());
    }

}