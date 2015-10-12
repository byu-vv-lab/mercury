package Finder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import com.microsoft.z3.Model;
import com.microsoft.z3.Status;

import Syntax.*;
import Syntax.Process;

public class UnmatchedEP_Finder {
	Program program;
	int tracker[];
	//HashMap<Pair<Integer,Integer>, LinkedList<Send>> unvisitedSends;
	HashMap<Integer, HashMap<Integer,Integer>> sendNums;
	HashMap<Integer, HashMap<Integer,Integer>> recvNums;
	
	public HashMap<Wait, LinkedList<Recv>> witnessedRecv; //used to record the receives that are witnessed
	
	//used for match pair generation on Encoder
//	public LinkedList<Recv>[] recvlist;
//	public LinkedList<Send>[][] sendlist;
	
	public int[] lastrInShape;
	public int[][] lastsInShape;
	
	//????should consider combine recvlist and recvNums, same for sendlist and sendNums to save space
	public long endtime = 0;
	
	public UnmatchedEP_Finder(Program p)
	{
		
		program = p;
		tracker = new int[p.size()];
		sendNums = new HashMap<Integer, HashMap<Integer, Integer>>();
		recvNums = new HashMap<Integer, HashMap<Integer, Integer>>();
		
		witnessedRecv = new HashMap<Wait, LinkedList<Recv>>();
		
		//do not need to declare all the recvlists and sendlists
		//TODO: consider revise
//		recvlist = new LinkedList[program.size()];
//		for(int i = 0; i < program.size(); i++){
//			recvlist[i] = new LinkedList<Recv>();
//		}
//		sendlist = new LinkedList[program.size()][program.size()];
//		for(int i = 0; i < program.size(); i++){
//			for(int j = 0; j < program.size(); j++){
//				sendlist[i][j] = new LinkedList<Send>();
//			}
//		}	
		
		lastrInShape = new int[program.size()];
		lastsInShape = new int[program.size()][program.size()];
		//initialize the lastr and lasts in shape to -1 meaning no receive and send in shape
		for(int i = 0; i < program.size(); i++)
		{
			lastrInShape[i] = -1;
			for(int j = 0; j < program.size(); j++)
				lastsInShape[i][j] = -1;
		}
	}
	
	public boolean run() throws Exception
	{
		Object[] patterns =  program.getUnmatchedEP_Pattern().toArray();
		//Iterator<UnmatchedEP_Pattern> it = patterns.iterator();
//		System.out.println("mismatched send-receive patterns: " + patterns.length);
		
		if(patterns.length == 0)
		{
			//report no deadlock for unmatched ep pattern
//			System.out.printf("No deadlock is found for unmatched Endpoint patterns!\n");
			return true;
		}
		else
		{
			program.InitGraph();
			//System.out.println("Pattern Detected!");
		}
		
		int count = 0;
		Random rn = new Random();
		//int start = (patterns.length == 0) ? 0 : rn.nextInt(patterns.length);
		int start = 0;
//		System.out.println("start = " + start);
		for(int s = start; s< patterns.length + start; s++)
		{
			UnmatchedEP_Pattern pattern = (UnmatchedEP_Pattern)patterns[s%patterns.length];
			Process patternProcess = pattern.process;
			
//			LinkedList<LinkedList<Operation>> prefix = new LinkedList<LinkedList<Operation>>();
			
			//initialize recvNums and sendNums
			recvNums.clear();
			sendNums.clear();
			
			witnessedRecv.clear();
			
			for(int i = 0; i < program.size(); i++)
			{
				lastrInShape[i] = -1;
				for(int j = 0; j < program.size(); j++)
					lastsInShape[i][j] = -1;
			}
			
			//initialize the prefix
			for(int i =0; i < program.size();i++) {
				program.get(i).NextBlockPoint();

				tracker[i] = 0;
			}
			
			
			//judge prefix's feasibility 
			breakpoint:
			while(schedulable(pattern))
			{	
				//System.out.println("fgfgfgfg");

				scheduling(program,patternProcess,pattern);
				if(reachBlockPoints())
				{
					HashSet<Integer> reachableRanks = feasible(pattern);
					if(reachableRanks.isEmpty())
					{
						//may deadlock 
						//apply SMT here to check the feasibility of prefix
						//if it is feasible, deadlock for this pattern
						//if not, no deadlock for this pattern(could be A, deadlock in the prefix; or B, there is no deadlock)
						//A
						// 0      1       2
						//S(2)   R(*)    R(*)
						//       S(2)    S(1)
						//               R(0)
						//--------------------
						//<R(2)>          ...
						
						//B
						// 0      1       2
						//R(*)   R(*)    S(0)
						//S(1)   S(0)    
						//--------------------
						//<R(1)>          ...

						
						Encoder encoder = new Encoder(program, pattern, lastrInShape, lastsInShape);
						
						encoder.Encoding();
						//encoder.solver.displayFormulas();
						Model model = encoder.solver.Check(Status.SATISFIABLE);
						if(model != null) {
							//System.out.println("[SAT] Witness Example:\n" + model);
//							System.out.println("Verification ends for this program!");
//							System.out.println(count);
							if(endtime == 0)
								endtime = System.currentTimeMillis();
							//continue to check even when a deadlock is found
							return false;
						}
						else {
//							System.out.println("[UNSAT]:No deadlock is found for pattern: ["
//									+ pattern.determinstic.toString() + "]");
						}
						//System.out.printf("May Deadlock!\n");
						//System.exit(0);
						break breakpoint;
					}
					
					if(reachableRanks.contains(patternProcess.getRank()) )
					{
						Operation rv = program.get(patternProcess.getRank()).get(patternProcess.indicator);
						if(rv.equals(pattern.determinstic))
						{
							//report no deadlock for this pattern
							//could be deadlock in the prefix, 
							// 0      1       2
							//S(2)   R(*)    R(*)
							//       S(2)    S(1)
							//               R(0)
							//				 S(0)
							//--------------------
							//<R(2)>          ...
							//System.out.printf(count + "No deadlock is found for pattern: [" 
								//	+ pattern.determinstic.toString() + "]\n");
							
							break breakpoint;
						}
					}
					
					moveBlockPoints(reachableRanks,pattern);
				}
			}
			
			//reset the indicator for the next check;
			resetProgram();
			count++;
		}
		return true;
	}
	
	void resetProgram() throws Exception
	{
		for(int i = 0; i < program.size();i++)
		{
			program.get(i).resetIndicator();
		}
	}
	
	boolean reachBlockPoints() throws Exception
	{
		for(int i = 0; i < program.size(); i++)
		{
			if(tracker[i] < program.get(i).indicator )
					//|| (program.get(i).indicator == 0 && tracker[i] == 0))
				return false;
		}
		
		return true;
	}
	
	//TODO: does unschedulable means a deadlock in shape? verify
	boolean schedulable(UnmatchedEP_Pattern pattern) throws Exception
	{
		for(int i = 0; i < program.size(); i++)
		{
			Process process = program.get(i);
			int rank = process.getRank();
			//if process does not reach the end of each process
			if(tracker[rank] < process.indicator || (process.indicator == 0 && tracker[rank] == 0))
			{
				Operation op = program.get(rank).get(tracker[rank]);
				//send still exist
				if(op instanceof Barrier)
				{
					//TODO: if barriers are matched then return true
				}
				if(op instanceof Recv)
				{
					Recv rv = (Recv)op;
					
					//if recv is non-blocking, return true
					if(!rv.isBlock)
						return true;
					if(checkAvailable(rv))
					{
//						System.out.println(rv);
						return true;
					}
				}
				// add a case when op is a wait
				else if(op instanceof Wait)
				{
					Wait wait = (Wait)op;
					if(witnessedRecv.containsKey(wait)){
						LinkedList<Recv> witnessedR = witnessedRecv.get(wait);
						if(!witnessedR.isEmpty())
						{
							//only needs to check the first recv that the wait witnessed, 
							//if this receive is schedulable, then return true
							//otherwise, it blocks other receives to be matched
							Recv firstR = witnessedR.getFirst();
							//if the first receive for this wait is the deterministic receive 
							//of the mismatched send-receive, then this process is not schedulable
							if(!firstR.equals(pattern.determinstic))
								if(checkAvailable(firstR))
									return true;
						}
					}
					
				}
				else 
				{
					return true;
				}
				
			}
		}
		
//		System.out.printf("No deadlock is found for this pattern: ("
//				 + pattern.determinstic.toString() + ")\n");
		
		//when false, is it a deadlock for the prefix?
		return false;
	}
	
	boolean checkAvailable(Recv r)
	{
		int src = r.src;
		int dest = r.dest;
		
		//more sends than receives with identical src and dest
		if(src!=-1)
			return (totalNUM(sendNums,src,dest) > totalNUM(recvNums,src,dest))//S(c->0) > R(c)
					&& (totalNUM(sendNums, -1, dest) >  //S(c->0) > R(*) + R(c)
					totalNUM(recvNums, -1, dest) + totalNUM(recvNums, src,dest));
		
		else 
		{
			//for wildcard receive, the number of send(*->dest) has to be greater than the number 
			//of {recv(*->dest), recv(c1->dest), ...}
			if(recvNums.containsKey(dest))
			{
				int totalAvailableRecvs = 0;
				for(Integer rsrc : recvNums.get(dest).keySet())
					totalAvailableRecvs += recvNums.get(dest).get(rsrc);
				//should use ">" other than ">=" because 
				//at least one send is available for the next receive
				return (totalNUM(sendNums,src,dest) > totalAvailableRecvs);
			}
			
			return false;
		}
	}
	
	int totalNUM(HashMap<Integer, HashMap<Integer, Integer>> map, int src, int dest)
	{
		if(map.containsKey(dest))
		{
			if(map.get(dest).containsKey(src))
			{
				return map.get(dest).get(src);
			}
			
//			if(src == -1)
//			{
//				int total = 0;
//				for( Integer size : map.get(dest).values())
//				{
//					total += size;
//				}
//				return total;
//			}
		}
		return 0;
	}
	
	boolean scheduling(Program program, Process patternProcess, UnmatchedEP_Pattern pattern) throws Exception
	{
		int patternRank = patternProcess.getRank();
//		if(tracker[patternRank] >= patternProcess.indicator)
//		{
//			return false;
//		}
		
		for(int i = patternRank+1; i < patternRank + program.size()+1; i++)
		{
			int rank = i % program.size();
			if(tracker[rank] == 0 && program.get(rank).indicator == 0)
			{
				Operation op = program.get(rank).get(tracker[rank]);
				//generate entry in recvNums for a indicator receive
				//TODO: if the first op is a barrier, then check if it is matched
				if(op instanceof Recv)
				{
					//if op is a non-blocking receive, no need to do this
					Recv rv = (Recv)op;
					if(rv.isBlock)
					{	
						int src = rv.src;
						int dest = rv.dest;
						
						lastrInShape[dest] = rv.rank;
	//					recvlist[dest].add(rv);
						
						if(!recvNums.containsKey(dest))
							recvNums.put(dest, new HashMap<Integer, Integer>());
						
						if(!recvNums.get(dest).containsKey(src))
							recvNums.get(dest).put(src, 0);
						
						
						continue;	
					}
				}
				//ignore the case where a wait is the first operation on a process
			}
			
			//process has no operation left until indicator
			if(tracker[rank] >= program.get(rank).indicator)
			{
				continue;
			}
				
			Operation op;
			while(tracker[rank] < program.get(rank).indicator)
			{
				op = program.get(rank).get(tracker[rank]);
				if(op instanceof Send){
					Send sendop = (Send)op;
					int dest = sendop.dest;
					int src = sendop.src;
					
					lastsInShape[dest][src] = sendop.rank;
//					sendlist[dest][src].add(sendop);
					
					if(!sendNums.containsKey(dest))
					{
						sendNums.put(dest, new HashMap<Integer,Integer>());
					}
					
					if(!sendNums.get(dest).containsKey(src))
					{
						sendNums.get(dest).put(src, 0);
					}
					
					if(!sendNums.get(dest).containsKey(-1))
					{
						sendNums.get(dest).put(-1, 0);
					}
					
					//increment the size of send in sendNums
					sendNums.get(dest).put(src, sendNums.get(dest).get(src)+1);
					sendNums.get(dest).put(-1, sendNums.get(dest).get(-1)+1);
					tracker[rank]++;
				}
				
				//TODO: if op is a barrier, check if it can be advanced, or stop here 
				if(op instanceof Barrier)
				{
					
				}
				
				if(op instanceof Recv)
				{
					//TODO: add a case when recv is non-blocking
					
					Recv rv = (Recv)op;
					int src = rv.src;
					int dest = rv.dest;
					
					lastrInShape[dest] = rv.rank;
//					recvlist[dest].add(rv);
					
					
					if(rv.isBlock){
						if(!recvNums.containsKey(dest))
						{
							recvNums.put(dest, new HashMap<Integer, Integer>());
							
						}
						
						if(!recvNums.get(dest).containsKey(src))
						{
							recvNums.get(dest).put(src, 0);
						}
						
						//increment the size of recv in recvNums
						if(checkAvailable(rv))
						{
							recvNums.get(dest).put(src, recvNums.get(dest).get(src)+1);
	//						if(src != -1)
	//							recvNums.get(dest).put(-1, recvNums.get(dest).get(-1)+1);
							tracker[rank]++;
						}
						else //when recv can not be matched, scheduling stops for this process
						{
							break;
						}
					}
					else
					{
						//if rv is non-blocking, add it to the data structure witnessedRecv
						//suppose the nearest-enclosing wait exists for rv in this case
						Wait nw = rv.NearestWait;
						if(!witnessedRecv.containsKey(nw))
							witnessedRecv.put(nw, new LinkedList<Recv>());
						witnessedRecv.get(nw).addLast(rv);
						tracker[rank]++;
					}
					
				}
				
				//add a case when op is a wait
				if(op instanceof Wait)
				{
					Wait wait = (Wait)op;
					if(witnessedRecv.containsKey(wait))
					{
						//iterately check the availability for each receive for this wait
						//blocking when a receive is unavailable
						//if the process is in the pattern instance, then block
						//when the deterministic receive is witnessed
						LinkedList<Recv> witnessedR = witnessedRecv.get(wait);
						for(Recv r : witnessedR)
						{
							//TODO: add a case when r is in pattern process
							if(i == patternRank && r.equals(pattern.determinstic))
							{
								//when r is a determinstic receive in mismatched send-receive
								//stop here and start traversing other processes
								break;
							}
							int src = r.src;
							int dest = r.dest;
							if(!recvNums.containsKey(dest))
							{
								recvNums.put(dest, new HashMap<Integer, Integer>());
								
							}
							
							if(!recvNums.get(dest).containsKey(src))
							{
								recvNums.get(dest).put(src, 0);
							}
							
							//increment the size of recv in recvNums
							if(checkAvailable(r))
							{
								recvNums.get(dest).put(src, recvNums.get(dest).get(src)+1);
								//remove this receive from the data structure witnessedRecv
								witnessedR.remove(r);
							}
							else //when recv can not be matched, scheduling stops for this process
							{
								break;
							}
						}
						//remove the wait if all the receives are witnessed, and increment the tracker 
						if(witnessedR.isEmpty())
						{
							witnessedRecv.remove(wait);
							tracker[rank]++;
						}
					}
					
				}
			}
			
		}
		
		return true;
		
		
	}
	
	boolean mayDeadlock(Recv deadlockPoint)
	{
		//src must not be equal to -1
		
		int src = deadlockPoint.src;
		int dest = deadlockPoint.dest;
		
		int sendNum = 0;
		int recvNum = 0;
		if(sendNums.containsKey(dest))
		{
			if(sendNums.get(dest).containsKey(src))
			{
				sendNum = sendNums.get(dest).get(src);
			}
		}
		
		if(recvNums.containsKey(dest))
		{
			if(recvNums.get(dest).containsKey(-1))
				recvNum += recvNums.get(dest).get(-1);
			
			//only deterministic receive needs to do this
			if(src != -1 && recvNums.get(dest).containsKey(src))
				recvNum += recvNums.get(dest).get(src);
		}
		
		return sendNum <= recvNum;
	}
	
	HashSet<Integer> feasible(UnmatchedEP_Pattern pattern) throws Exception
	{
		HashSet<Integer> reachableRanks = new HashSet<Integer>();
		Process patternProcess = pattern.process;
		for(int i = 0; i < program.size(); i++)
		{
			Process process = program.get(i);
			if(process.indicator < process.size())
			{
				Operation blockOp = process.get(process.indicator);
				//for the pattern process
				if(i == patternProcess.getRank())
				{
					Recv deadlockPoint = (Recv)blockOp;
					if(!deadlockPoint.equals(pattern.determinstic))
					{
						reachableRanks.add(i);
					}
					
					if(!mayDeadlock(deadlockPoint))
					{
						reachableRanks.add(i);
						return reachableRanks;
					}
					
					continue;
				}
				
				//check if each block receive has no sends to match
				if(blockOp instanceof Recv)
				{
					Recv rv = (Recv)blockOp;
					if(checkAvailable(rv))
					{
						reachableRanks.add(i);
					}
				}
				
				//(done!)need to reconsider the availablility for R(1) in the pattern process,
				//(done!)also need to consider when send for 1 is larger 
				//enough (i.e., the previous receives is less than s(1)), abort this iteration for this pattern
			}
		}
		return reachableRanks;
	}
	
	void moveBlockPoints(HashSet<Integer> reachableRanks, UnmatchedEP_Pattern pattern) throws Exception
	{
		Process patternProcess = pattern.process;
		for(int rank : reachableRanks)
		{
			//if it is a pattern process and it reaches the determinstic receive in pattern, 
			//do not need to increment indicator
			if(rank == patternProcess.getRank() 
					&& program.get(rank).get(program.get(rank).indicator).equals(pattern.determinstic))
			{
				continue;
			}

			program.get(rank).indicator++;
			program.get(rank).NextBlockPoint();
		}
	}
		
}
