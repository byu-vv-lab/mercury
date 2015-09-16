package Syntax;
import java.util.BitSet;

public class Recv extends Operation {
	
	public int src;
	public int dest;
	public int rank;
	public Send match;
	public BitSet var;
	public boolean isBlock;
	public Wait NearestWait;
	
	public Recv(String event, Process process, int rank, int src, 
			int dest, Send match, boolean isBlock, Wait nw) {
		super(event, process);
		this.src = src;
		this.dest = dest;
		this.match = match;
		this.rank = rank;
		this.var = new BitSet();
		this.isBlock = isBlock;
		this.NearestWait = nw;
	}
	
//	public String toString()
//	{
//		return "Recv" + event;
//	}

	@Override
	public String toString() {
		return "Recv{" +
				"src=" + src +
				", dest=" + dest +
				", rank=" + rank +
				", isBlock=" + isBlock +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Recv recv = (Recv) o;

		if (src != recv.src) return false;
		if (dest != recv.dest) return false;
		if (rank != recv.rank) return false;
		return isBlock == recv.isBlock;
	}

	@Override
	public int hashCode() {
		int result = src;
		result = 31 * result + dest;
		result = 31 * result + rank;
		result = 31 * result + (isBlock ? 1 : 0);
		return result;
	}
}
