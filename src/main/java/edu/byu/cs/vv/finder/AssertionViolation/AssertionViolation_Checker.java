package Finder;

import com.microsoft.z3.Model;
import com.microsoft.z3.Status;

import Syntax.Program;

public class AssertionViolation_Checker {
	
	Program program;
	
	public long endtime = 0;
	
	public AssertionViolation_Checker(Program p)
	{
		program = p;
	}
	
	public void Run() throws Exception
	{
		if(program.match_table.isEmpty())
		{
			program.generateMatch();
			//program.displayMatch();
		}
		
		Encoder encoder = new Encoder(program);
		
		encoder.Encoding();
		encoder.solver.displayFormulas();
		System.out.println("Start solving SMT formulas...");
		Model model = encoder.solver.Check(Status.SATISFIABLE);
		if(model != null)
		{
			System.out.println("[SAT] Assertion is violated!");
			//System.out.println("Witness Example:\n" + model);
			System.out.println("Verification ends for this program!");
			if(endtime == 0)
				endtime = System.currentTimeMillis();
		}
		else System.out.println("[UNSAT]:No assertion is violated!");
		if(endtime ==0)
			endtime = System.currentTimeMillis();
	}

}
