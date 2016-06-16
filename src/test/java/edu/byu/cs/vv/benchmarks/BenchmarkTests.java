package edu.byu.cs.vv.benchmarks;

import edu.byu.cs.vv.finder.AbstractFinder;
import edu.byu.cs.vv.finder.unmatchedendpoint.UmEPFinder;
import edu.byu.cs.vv.ast.Program;
import edu.byu.cs.vv.parser.ProgramParser;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.net.URL;

import static org.junit.Assert.*;

@Category(edu.byu.cs.vv.categories.Benchmark.class)
public class BenchmarkTests {

    // TODO: The MatG* tests loop
//    @Test
    public void MatG3() throws Exception {
        URL file = BenchmarkTests.class.getResource("matg3.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

//    @Test
    public void MatG4() throws Exception {
        URL file = BenchmarkTests.class.getResource("matg4.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

//    @Test
    public void MatG10() throws Exception {
        URL file = BenchmarkTests.class.getResource("matg10.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void heat8() throws Exception {
        URL file = BenchmarkTests.class.getResource("heat8.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void GE8() throws Exception {
        URL file = BenchmarkTests.class.getResource("ge8.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void GE16() throws Exception {
        URL file = BenchmarkTests.class.getResource("ge16.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void zero() throws Exception {
        URL file = BenchmarkTests.class.getResource("zero.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void circulars() throws Exception {
        URL file = BenchmarkTests.class.getResource("circulars.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void mismatches() throws Exception {
        URL file = BenchmarkTests.class.getResource("mismatches.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void dlg1() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg1.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void dlg2() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg2.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg3() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg3.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg4() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg4.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg5() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg5.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void dlg6() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg6.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg7() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg7.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void dlg8() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg8.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void dlg9() throws Exception {
        URL file = BenchmarkTests.class.getResource("dlg9.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void mismatch() throws Exception {
        URL file = BenchmarkTests.class.getResource("mismatch.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void circular() throws Exception {
        URL file = BenchmarkTests.class.getResource("circular.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void floyd_8core() throws Exception {
        URL file = BenchmarkTests.class.getResource("floyd_8core.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void floyd_16core() throws Exception {
        URL file = BenchmarkTests.class.getResource("floyd_16core.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void floyd_16core_mismatch() throws Exception {
        URL file = BenchmarkTests.class.getResource("floyd_16core_mismatch.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void monte_4core() throws Exception {
        URL file = BenchmarkTests.class.getResource("monte_4core.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void monte_8core() throws Exception {
        URL file = BenchmarkTests.class.getResource("monte_8core.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void monte_8core_mismatch() throws Exception {
        URL file = BenchmarkTests.class.getResource("monte_8core_mismatch.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void monte_16core() throws Exception {
        URL file = BenchmarkTests.class.getResource("monte_16core.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void diffusion2d_4core() throws Exception {
        URL file = BenchmarkTests.class.getResource("diffusion2d_4core.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void diffusion2d_8core() throws Exception {
        URL file = BenchmarkTests.class.getResource("diffusion2d_8core.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void diffusion2d_8core_mismatch() throws Exception {
        URL file = BenchmarkTests.class.getResource("diffusion2d_8core_mismatch.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void integrate() throws Exception {
        URL file = BenchmarkTests.class.getResource("integrate.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void integrate_10core() throws Exception {
        URL file = BenchmarkTests.class.getResource("integrate_10core.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void integrate_10core_mismatch() throws Exception {
        URL file = BenchmarkTests.class.getResource("integrate_10core.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void integrate_16core() throws Exception {
        URL file = BenchmarkTests.class.getResource("integrate_10core.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

    @Test
    public void integrate_16core_mismatch() throws Exception {
        URL file = BenchmarkTests.class.getResource("integrate_10core.ctp");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UmEPFinder(program);
        assertTrue(finder.verify());
    }

}
