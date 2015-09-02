package Syntax;

public class Barrier extends Operation {

	public String comm;
	public Barrier(String event, Process process, String cm) {
		super(event, process);
		comm = cm;
	}

}
