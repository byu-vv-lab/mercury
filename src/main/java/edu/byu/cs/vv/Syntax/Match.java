package edu.byu.cs.vv.Syntax;

import Syntax.Operations.Receive;
import Syntax.Operations.Send;

public class Match {
    public Send send;
    public Receive receive;

    public Match(Send send, Receive receive) {
        this.send = send;
        this.receive = receive;
    }
}
