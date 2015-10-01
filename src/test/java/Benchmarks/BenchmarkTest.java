package Benchmarks;

import JTAFinder.AbstractFinder;
import JTAFinder.UnmatchedEndpointFinder;
import JTASyntax.Program;
import Parser.ProgramParser;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

@Category(Categories.Benchmark.class)
public class BenchmarkTest {

    @Test
    public void MatG3() {
        assertTrue(true);
    }

    @Test
    public void MatG4() {
    }

    @Test
    public void MatG10() {

    }

    @Test
    public void heat8() {

    }

    @Test
    public void GE8() {

    }

    @Test
    public void GE16() {

    }

    @Test
    public void zero() {
    }

    @Test
    public void circulars() {
    }

    @Test
    public void mismatches() {
    }

    @Test
    public void dlg1() {
    }

    @Test
    public void dlg2() {
    }

    @Test
    public void dlg3() {
    }

    @Test
    public void dlg4() {
    }

    @Test
    public void dlg5() {

    }

    @Test
    public void dlg6() {
    }

    @Test
    public void dlg7() {
    }

    @Test
    public void dlg8() {

    }

    @Test
    public void dlg9() {
    }

    @Test
    public void mismatch() {
    }

    @Test
    public void circular() {
    }

    @Test
    public void floyd_8core() {

    }

    @Test
    public void floyd_16core() {
    }

    @Test
    public void floyd_16core_mismatch() {
    }

    @Test
    public void monte_4core() {
    }

    @Test
    public void monte_8core() {
    }

    @Test
    public void monte_8core_mismatch() throws IOException {
        URL file = BenchmarkTest.class.getResource("monte_8core_mismatch.jta");
        Program program = ProgramParser.loadProgramFromFile(file);
        AbstractFinder finder = new UnmatchedEndpointFinder(program);
        assertFalse(finder.verify());
    }

    @Test
    public void monte_16core() {
    }

    @Test
    public void test1() {
    }

    @Test
    public void diffusion2d_4core() {
    }

    @Test
    public void diffusion2d_8core() {
    }

    @Test
    public void diffusion2d_8core_mismatch() {
    }

    @Test
    public void integrate() {
    }

    @Test
    public void integrate_10core() {
    }

    @Test
    public void integrate_10core_mismatch() {
    }

    @Test
    public void integrate_16core() {
    }

    @Test
    public void integrate_16core_mismatch() {

    }

}
