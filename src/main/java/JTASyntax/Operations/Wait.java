package JTASyntax.Operations;

public class Wait extends Operation {

    public final boolean forR;

    public Wait(String name, int rank, boolean forR) {
        super(name, rank, false, 0);
        this.forR = forR;
    }

    public boolean forR() {
        return forR;
    }

}
