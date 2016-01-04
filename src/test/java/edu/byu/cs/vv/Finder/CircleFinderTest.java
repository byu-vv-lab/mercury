package edu.byu.cs.vv.Finder;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.net.URL;

@Category(Categories.Finder.class)
public class CircleFinderTest {
    @Test
    public void test1 () throws Exception {
        URL test = UnmatchedEndpointFinderTest.class.getResource("cf1.jta");
//        Program p = Parser.loadProgramFromFile(test);

//        Circle_Finder finder = new Circle_Finder(p);
//        finder.verify();
        // assertEquals(1, p.getUnmatchedEP_Pattern().size());
    }

    @Test
    public void test2 () throws Exception {
        URL test = UnmatchedEndpointFinderTest.class.getResource("cf2.jta");
//        Program p = Parser.loadProgramFromFile(test);

//        Circle_Finder finder = new Circle_Finder(p);
//        finder.verify();
        // assertEquals(1, p.getUnmatchedEP_Pattern().size());
    }

    @Test
    public void test3 () throws Exception {
        URL test = UnmatchedEndpointFinderTest.class.getResource("cf3.jta");
//        Program p = Parser.loadProgramFromFile(test);
//
//        Circle_Finder finder = new Circle_Finder(p);
//        finder.verify();
        // assertEquals(1, p.getUnmatchedEP_Pattern().size());
    }
}
