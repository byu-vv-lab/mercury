package edu.byu.cs.vv.Syntax;

import edu.byu.cs.vv.Syntax.Operations.Receive;
import edu.byu.cs.vv.Syntax.Operations.Send;

public class Match {
    public Send send;
    public Receive receive;

    public Match(Send send, Receive receive) {
        this.send = send;
        this.receive = receive;
    }
}
