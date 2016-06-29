package Finder;

import com.microsoft.z3.*;

import Syntax.Program;

public class SendMode_Checker {
	
	Program program;
	
	public long endtime = 0;
	
	public SendMode_Checker(Program p)
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
		//solver push
		encoder.solver.s.Push();
		Model model = encoder.solver.Check(Status.SATISFIABLE);
		if(model != null)
		{
			System.out.println("[SAT] A feasible schedule is resolved!");
			//System.out.println("[Schedule]\n" + model);
			System.out.println("Now start checking violation sends");
			encoder.solver.s.Pop(1);
			encoder.solver.addFormula(encoder.solver.rdProperty);
			encoder.solver.displayFormulas();
			if(encoder.solver.Check(Status.SATISFIABLE) != null)
			{
				System.out.println("[SAT] A violation of ready send is detected!");
				//System.out.println("[Schedule with violation]\n" + model);
			}
			else System.out.println("[UNSAT]:No violation of ready send exists!"); 
			
			if(endtime == 0)
				endtime = System.currentTimeMillis();
		}
		else System.out.println("[UNSAT]:No feasible schedules exist (incompatible synchronous sends)!");
		if(endtime ==0)
			endtime = System.currentTimeMillis();
		
		System.out.println("Verification ends!");
	}

}
