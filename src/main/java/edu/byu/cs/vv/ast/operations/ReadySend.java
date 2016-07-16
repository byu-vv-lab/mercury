package edu.byu.cs.vv.ast.operations;

import edu.byu.cs.vv.encoding.SMTContext;

public class ReadySend extends Send {

    public ReadySend(String name, int communicator, int process_rank, int order, int src, int dest, int tag) {
        super(name, communicator, process_rank, order, src, dest, tag);
    }

    @Override
    public String toSexp() {
        return "(RSend " + name + " " + communicator + " " + dest + " " + tag + ")";
    }

    @Override
    public Operation setOrder(int time) {
        return new ReadySend(name, communicator, process_rank, time, src, dest, tag);
    }

    // TODO: custom encoding
    @Override
    public void encode(SMTContext ctx) {
        super.encode(ctx);
    }
}
