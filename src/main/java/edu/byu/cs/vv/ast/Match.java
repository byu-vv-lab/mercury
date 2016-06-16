package edu.byu.cs.vv.ast;

import edu.byu.cs.vv.encoding.Encodeable;
import edu.byu.cs.vv.encoding.SMTContext;
import edu.byu.cs.vv.ast.operations.Receive;
import edu.byu.cs.vv.ast.operations.Send;

public class Match implements Encodeable {

    public Send send;
    public Receive receive;

    public Match(Send send, Receive receive) {
        this.send = send;
        this.receive = receive;
    }

    @Override
    public void encode(SMTContext ctx) {
        // TODO:
        return;
    }
}
