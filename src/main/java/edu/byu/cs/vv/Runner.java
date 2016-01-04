package edu.byu.cs.vv;

import edu.udel.cis.vsl.civl.run.IF.UserInterface;

public class Runner {

    public static void main(String[] args) {
        String[] opts = {"run", "examples/mpi/diffusion2d.c"};
        UserInterface ui = new UserInterface();
        ui.run(opts);
    }

}
