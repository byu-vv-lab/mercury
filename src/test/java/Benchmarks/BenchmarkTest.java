package Benchmarks;

import JTAFinder.AbstractFinder;
import JTAFinder.UnmatchedEndpoint.UmEPFinder;
import JTASyntax.Program;
import Parser.ProgramParser;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

@Category(Categories.Benchmark.class)
public class BenchmarkTest {

    // TODO: The MatG* tests loop
//    @Test
//    public void MatG3() throws IOException {
//        URL file = BenchmarkTest.class.getResource("matg3.jta");
//        Program program = ProgramParser.loadProgramFromFile(file);
//        AbstractFinder finder = new UmEPFinder(program);
//        assertTrue(finder.verify());
//    }
//
//    @Test
//    public void MatG4() throws IOException {
//        URL file = BenchmarkTest.class.getResource("matg4.jta");
//        Program program = ProgramParser.loadProgramFromFile(file);
//        AbstractFinder finder = new UmEPFinder(program);
//        assertTrue(finder.verify());
//    }
//
//    @Test
//    public void MatG10() throws IOException {
//        URL file = BenchmarkTest.class.getResource("matg10.jta");
//        Program program = ProgramParser.loadProgramFromFile(file);
//        AbstractFinder finder = new UmEPFinder(program);
//        assertTrue(finder.verify());
//    }

    @Test
    public void heat8() throws IOException {
        URL file = BenchmarkTest.class.getResource("heat8.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void GE8() throws IOException {
        URL file = BenchmarkTest.class.getResource("ge8.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void GE16() throws IOException {
        URL file = BenchmarkTest.class.getResource("ge16.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    // TODO: False?
    @Test
    public void zero() throws IOException {
        URL file = BenchmarkTest.class.getResource("zero.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    // TODO: True?
    @Test
    public void circulars() throws IOException {
        URL file = BenchmarkTest.class.getResource("circulars.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    // TODO: False?
    @Test
    public void mismatches() throws IOException {
        URL file = BenchmarkTest.class.getResource("mismatches.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    // TODO: False?
    @Test
    public void dlg1() throws IOException {
        URL file = BenchmarkTest.class.getResource("dlg1.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void dlg2() throws IOException {
        URL file = BenchmarkTest.class.getResource("dlg2.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg3() throws IOException {
        URL file = BenchmarkTest.class.getResource("dlg3.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg4() throws IOException {
        URL file = BenchmarkTest.class.getResource("dlg4.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    // TODO: False?
    @Test
    public void dlg5() throws IOException {
        URL file = BenchmarkTest.class.getResource("dlg5.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void dlg6() throws IOException {
        URL file = BenchmarkTest.class.getResource("dlg6.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg7() throws IOException {
        URL file = BenchmarkTest.class.getResource("dlg7.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    // TODO: False?
    @Test
    public void dlg8() throws IOException {
        URL file = BenchmarkTest.class.getResource("dlg8.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void dlg9() throws IOException {
        URL file = BenchmarkTest.class.getResource("dlg9.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    // TODO: False?
    @Test
    public void mismatch() throws IOException {
        URL file = BenchmarkTest.class.getResource("mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    // TODO: True?
    @Test
    public void circular() throws IOException {
        URL file = BenchmarkTest.class.getResource("circular.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void floyd_8core() throws IOException {
        URL file = BenchmarkTest.class.getResource("floyd_8core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void floyd_16core() throws IOException {
        URL file = BenchmarkTest.class.getResource("floyd_16core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    // TODO: Shouldn't this be false?
    @Test
    public void floyd_16core_mismatch() throws IOException {
        URL file = BenchmarkTest.class.getResource("floyd_16core_mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void monte_4core() throws IOException {
        URL file = BenchmarkTest.class.getResource("monte_4core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void monte_8core() throws IOException {
        URL file = BenchmarkTest.class.getResource("monte_8core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void monte_8core_mismatch() throws IOException {
        URL file = BenchmarkTest.class.getResource("monte_8core_mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void monte_16core() throws IOException {
        URL file = BenchmarkTest.class.getResource("monte_16core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void diffusion2d_4core() throws IOException {
        URL file = BenchmarkTest.class.getResource("diffusion2d_4core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void diffusion2d_8core() throws IOException {
        URL file = BenchmarkTest.class.getResource("diffusion2d_8core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void diffusion2d_8core_mismatch() throws IOException {
        URL file = BenchmarkTest.class.getResource("diffusion2d_8core_mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void integrate() throws IOException {
        URL file = BenchmarkTest.class.getResource("integrate.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void integrate_10core() throws IOException {
        URL file = BenchmarkTest.class.getResource("integrate_10core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void integrate_10core_mismatch() throws IOException {
        URL file = BenchmarkTest.class.getResource("integrate_10core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void integrate_16core() throws IOException {
        URL file = BenchmarkTest.class.getResource("integrate_10core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    // TODO: Shouldn't this be false?
    @Test
    public void integrate_16core_mismatch() throws IOException {
        URL file = BenchmarkTest.class.getResource("integrate_10core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

}
