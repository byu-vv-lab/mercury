package edu.byu.cs.vv.finder.circlefinder;

import edu.byu.cs.vv.ast.operations.Receive;
import edu.byu.cs.vv.ast.operations.Send;

public class Vertex {
    public Receive recv;
    public Send send;
    public int pRank;

    public Vertex(Receive r, Send s) {
        recv = r;
        send = s;
        pRank = r.dest;
    }

    public Receive getRecv() {
        return recv;
    }

    public Send getSend() {
        return send;
    }

    public int getProcessRank() {
        return pRank;
    }

    public static Vertex generateV(Receive r, Send s) {
        return new Vertex(r, s);
    }

    public String toString() {
        return "(" + recv + "," + send + ")";
    }
}
