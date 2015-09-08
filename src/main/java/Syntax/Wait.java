package Syntax;

public class Wait extends Operation {
	public Operation op;
	public boolean forR;
	public Wait(String event, Process process, Operation op,boolean fr) {
		super(event, process);
		this.op = op;
		forR = fr;
	}
}
