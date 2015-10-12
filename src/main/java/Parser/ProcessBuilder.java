package Parser;

import JTASyntax.Operation;
import JTASyntax.Process;
import JTASyntax.Receive;

import java.util.ArrayList;
import java.util.List;

class ProcessBuilder {
    private int rank = 0;
//    private int oprank = 0;
    private List<Operation> ops = new ArrayList<>();

    public Process finish () {
        return new Process(rank, ops);
    }

    public void setRank (int rank) {
        this.rank = rank;
    }

    public void addOperation (Operation op) {
        ops.add(op);
//        if ((op instanceof Receive) && (((Receive)op).isBlock)) {
//            oprank++;
//        }
    }

    public int rank () {
        return rank;
    }

//    public int getOpRank () {
//        return oprank;
//    }

    public int size () {
        return ops.size();
    }
}
