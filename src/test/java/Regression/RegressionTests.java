package Regression;

import Benchmarks.BenchmarkTests;
import Finder.UnmatchedEP_Finder;
import JTAFinder.AbstractFinder;
import JTAFinder.UnmatchedEndpoint.UmEPFinder;
import JTASyntax.Program;
import Parser.ProgramParser;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.net.URL;

import static org.junit.Assert.*;

@Category(Categories.Regression.class)
public class RegressionTests {

    // TODO: The MatG* tests loop
//    @Test
    public void MatG3() throws Exception {
        URL file = BenchmarkTests.class.getResource("matg3.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

//    @Test
    public void MatG4() throws Exception {
        URL file = BenchmarkTests.class.getResource("matg4.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

//    @Test
    public void MatG10() throws Exception {
        URL file = BenchmarkTests.class.getResource("matg10.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void heat8() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("heat8.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.heat8();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    // TODO: This test does not match
    @Test
    public void GE8() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("ge8.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(actual = finder.verify());

        Syntax.Program old = ProgramFactory.GE8();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertFalse(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void GE16() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("ge16.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.GE16();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void zero() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("zero.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(actual = finder.verify());

        Syntax.Program old = ProgramFactory.zero();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertFalse(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void circulars() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("circulars.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.circulars();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void mismatches() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("mismatches.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(actual = finder.verify());

        Syntax.Program old = ProgramFactory.mismatches();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertFalse(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void dlg1() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("dlg1.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(actual = finder.verify());

        Syntax.Program old = ProgramFactory.dlg1();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertFalse(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void dlg2() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("dlg2.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.dlg2();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void dlg3() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("dlg3.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.dlg3();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void dlg4() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("dlg4.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.dlg4();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void dlg5() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("dlg5.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(actual = finder.verify());

        Syntax.Program old = ProgramFactory.dlg5();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertFalse(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void dlg6() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("dlg6.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.dlg6();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void dlg7() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("dlg7.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.dlg7();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void dlg8() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("dlg8.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(actual = finder.verify());

        Syntax.Program old = ProgramFactory.dlg8();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertFalse(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void dlg9() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("dlg9.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.dlg9();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void mismatch() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(actual = finder.verify());

        Syntax.Program old = ProgramFactory.mismatch();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertFalse(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void circular() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("circular.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.circular();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void floyd_8core() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("floyd_8core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.floyd_8core();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void floyd_16core() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("floyd_16core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.floyd_16core();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    // TODO: This test does not match
    @Test
    public void floyd_16core_mismatch() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("floyd_16core_mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.floyd_16core_mismatch();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void monte_4core() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("monte_4core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.monte_4core();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void monte_8core() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("monte_8core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.monte_8core();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void monte_8core_mismatch() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("monte_8core_mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(actual = finder.verify());

        Syntax.Program old = ProgramFactory.monte_8core_mismatch();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertFalse(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void monte_16core() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("monte_16core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.monte_16core();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void diffusion2d_4core() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("diffusion2d_4core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.diffusion2d_4core();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void diffusion2d_8core() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("diffusion2d_8core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.diffusion2d_8core();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void diffusion2d_8core_mismatch() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("diffusion2d_8core_mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(actual = finder.verify());

        Syntax.Program old = ProgramFactory.diffusion2d_8core_mismatch();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertFalse(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void integrate() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("integrate.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.integrate();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    @Test
    public void integrate_10core() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("integrate_10core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.integrate_10core();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    // TODO: This test does not match
    @Test
    public void integrate_10core_mismatch() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("integrate_10core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.integrate_10core_mismatch();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertFalse(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    // TODO: Null Pointer Exception under old code
//    @Test
    public void integrate_16core() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("integrate_10core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.integrate_16core_mismatch();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

    // TODO: Null Pointer Exception under old code
//    @Test
    public void integrate_16core_mismatch() throws Exception {
        boolean actual, expected;
        URL file = BenchmarkTests.class.getResource("integrate_10core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(actual = finder.verify());

        Syntax.Program old = ProgramFactory.integrate_16core_mismatch();
        UnmatchedEP_Finder old_finder = new UnmatchedEP_Finder(old);
        assertTrue(expected = old_finder.run());
        assertEquals(actual, expected);
    }

}
