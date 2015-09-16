package Syntax;

public class Operation {
	public String event;
	public Process process;

	public Operation(String event, Process process)
	{
		this.event = event;
		this.process = process;
	}
	
	public String toString()
	{
		return "Operation " + event;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Operation operation = (Operation) o;

		if (!event.equals(operation.event)) return false;
		return process.equals(operation.process);
	}

	@Override
	public int hashCode() {
		int result = event.hashCode();
		result = 31 * result + process.hashCode();
		return result;
	}
}
