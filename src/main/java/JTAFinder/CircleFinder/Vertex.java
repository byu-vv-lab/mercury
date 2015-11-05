package JTAFinder.CircleFinder;

import Syntax.Recv;
import Syntax.Send;

public class Vertex {
    public Recv recv;
    public Send send;
    public int pRank;

    public Vertex(Recv r, Send s) {
        recv = r;
        send = s;
        pRank = r.dest;
    }

    public Recv getRecv() {
        return recv;
    }

    public Send getSend() {
        return send;
    }

    public int getProcessRank() {
        return pRank;
    }

    public static Vertex generateV(Recv r, Send s) {
        return new Vertex(r, s);
    }

    public String toString() {
        return "(" + recv + "," + send + ")";
    }
}
