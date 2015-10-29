package JTASyntax;

import JTASyntax.Operations.Receive;
import JTASyntax.Operations.Send;

public class Match {
    public Send send;
    public Receive receive;

    public Match(Send send, Receive receive) {
        this.send = send;
        this.receive = receive;
    }
}
