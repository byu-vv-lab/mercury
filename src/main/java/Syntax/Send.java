package Syntax;

public class Send extends Operation {

	public int src;
	public int dest;
	public Recv match;
	public int value;// should be bitset
	public int rank;
	public boolean isBlock;
	public Wait NearestWait;
	public Send(String event, Process process, int rank, int src, int dest, Recv match, int value, 
			boolean isBlock, Wait nw) {
		super(event, process);
		this.src = src;
		this.dest = dest;
		this.match = match;
		this.rank = rank;
		this.value = value;
		this.isBlock = isBlock;
		this.NearestWait = nw;
	}
	
//	public String toString() {
//		return "Send" + event;
//	}

	@Override
	public String toString() {
		return "Send{" +
				"src=" + src +
				", dest=" + dest +
				", value=" + value +
				", rank=" + rank +
				", isBlock=" + isBlock +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Send send = (Send) o;

		if (src != send.src) return false;
		if (dest != send.dest) return false;
		if (value != send.value) return false;
		if (rank != send.rank) return false;
		return isBlock == send.isBlock;
	}

	@Override
	public int hashCode() {
		int result = src;
		result = 31 * result + dest;
		result = 31 * result + value;
		result = 31 * result + rank;
		result = 31 * result + (isBlock ? 1 : 0);
		return result;
	}

}
