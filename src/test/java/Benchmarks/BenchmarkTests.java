package Benchmarks;

import Finder.AbstractFinder;
import Finder.UnmatchedEndpoint.UmEPFinder;
import Syntax.Program;
import Parser.ProgramParser;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.net.URL;

import static org.junit.Assert.*;

@Category(Categories.Benchmark.class)
public class BenchmarkTests {

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
        URL file = BenchmarkTests.class.getResource("heat8.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void GE8() throws Exception {
        URL file = BenchmarkTests.class.getResource("ge8.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void GE16() throws Exception {
        URL file = BenchmarkTests.class.getResource("ge16.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void zero() throws Exception {
        URL file = BenchmarkTests.class.getResource("zero.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void circulars() throws Exception {
        URL file = BenchmarkTests.class.getResource("circulars.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void mismatches() throws Exception {
        URL file = BenchmarkTests.class.getResource("mismatches.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void dlg1() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg1.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void dlg2() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg2.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg3() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg3.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg4() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg4.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg5() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg5.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void dlg6() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg6.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg7() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg7.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg8() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg8.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void dlg9() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg9.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void mismatch() throws Exception {
        URL file = BenchmarkTests.class.getResource("mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void circular() throws Exception {
        URL file = BenchmarkTests.class.getResource("circular.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void floyd_8core() throws Exception {
        URL file = BenchmarkTests.class.getResource("floyd_8core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void floyd_16core() throws Exception {
        URL file = BenchmarkTests.class.getResource("floyd_16core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void floyd_16core_mismatch() throws Exception {
        URL file = BenchmarkTests.class.getResource("floyd_16core_mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void monte_4core() throws Exception {
        URL file = BenchmarkTests.class.getResource("monte_4core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void monte_8core() throws Exception {
        URL file = BenchmarkTests.class.getResource("monte_8core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void monte_8core_mismatch() throws Exception {
        URL file = BenchmarkTests.class.getResource("monte_8core_mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void monte_16core() throws Exception {
        URL file = BenchmarkTests.class.getResource("monte_16core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void diffusion2d_4core() throws Exception {
        URL file = BenchmarkTests.class.getResource("diffusion2d_4core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void diffusion2d_8core() throws Exception {
        URL file = BenchmarkTests.class.getResource("diffusion2d_8core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void diffusion2d_8core_mismatch() throws Exception {
        URL file = BenchmarkTests.class.getResource("diffusion2d_8core_mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void integrate() throws Exception {
        URL file = BenchmarkTests.class.getResource("integrate.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void integrate_10core() throws Exception {
        URL file = BenchmarkTests.class.getResource("integrate_10core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void integrate_10core_mismatch() throws Exception {
        URL file = BenchmarkTests.class.getResource("integrate_10core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void integrate_16core() throws Exception {
        URL file = BenchmarkTests.class.getResource("integrate_10core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void integrate_16core_mismatch() throws Exception {
        URL file = BenchmarkTests.class.getResource("integrate_10core.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

}
