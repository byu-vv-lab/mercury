package JTASyntax;

public abstract class Operation {

    public final String name;
    public final int rank;
    public final boolean isBlock;
    public final int process_rank;

    Operation(String name, int rank, boolean isBlock, int process_rank) {
        this.name = name;
        this.rank = rank;
        this.isBlock = isBlock;
        this.process_rank = process_rank;
    }

}
