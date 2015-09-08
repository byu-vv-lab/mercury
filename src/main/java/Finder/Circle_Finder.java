package Finder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import Syntax.*;
import Syntax.Process;

public class Circle_Finder {
	Program program;
	int tracker[];
//	Digraph graph;
//	TarjanSCC circleFinder;
	boolean deadlockFound = false;
	
	HashMap<Integer, HashMap<Integer,Integer>> sendNums;
	HashMap<Integer, HashMap<Integer,Integer>> recvNums;
	
	public HashMap<Wait, LinkedList<Recv>> witnessedRecv; //used to record the receives that are witnessed
	public long endtime = 0;
	
	public Circle_Finder(Program p)
	{
		program = p;
		tracker = new int[p.size()];
		sendNums = new HashMap<Integer, HashMap<Integer, Integer>>();
		recvNums = new HashMap<Integer, HashMap<Integer, Integer>>();
		
		witnessedRecv = new HashMap<Wait, LinkedList<Recv>>();

	}
	
	public void Run() throws Exception
	{
		Digraph graph = new Digraph(program);
		Johnson tc = new Johnson(graph);
		
		//TODO: how does a circular pattern look like? 
		//if only considering receive and send, 
		//how to push the operations between the receive and its nw to the shape
		LinkedList<Hashtable<Integer, Recv>> circles = tc.patterns;
		Object[] patterns = circles.toArray();
		//Iterator<Hashtable<Integer, Recv>> it = circles.iterator();
		
		System.out.println("circular patterns: " + tc.patterns.size());

		
		if(patterns.length == 0)
		{
			//report no deadlock for unmatched ep pattern
			System.out.printf("No deadlock is found for circular patterns!\n");
		}
		
		int count = 0;
		Random rn = new Random();
		int start = (patterns.length == 0) ? 0 : rn.nextInt(patterns.length);
		System.out.println("start = " + start);
		for(int s = start; s < start + patterns.length; s++)
		{
			//return if a deadlock is found in the process of schedulable
			if(deadlockFound)
				return;
			
			Hashtable<Integer, Recv> patternProcesses = (Hashtable<Integer, Recv>)patterns[s%patterns.length];
			//System.out.println("[Circle " + circles.indexOf(patternProcesses) + "]: " + patternProcesses);
			
			//initialize recvNums and sendNums
			recvNums.clear();
			sendNums.clear();
			
			//initialize the prefix
			for(int i =0; i < program.size();i++)
			{
				if(!patternProcesses.containsKey(i)){
					program.get(i).NextBlockPoint();
				} 
				else 
				{
					//iterate up to the receive of each process in the circular pattern
					program.get(i).ToPoint(patternProcesses.get(i));
				}
				
				//initialize trackers to the first line of each process
				tracker[i] = 0;
			}
						
			//judge prefix's feasibility 
			breakpoint: 
			while(schedulable(patternProcesses))
			{	
				scheduling(program,patternProcesses);
				if(reachBlockPoints())
				{
					HashSet<Integer> reachableRanks = feasible(patternProcesses);
					if(reachableRanks.isEmpty())
					{
						//may deadlock, a prefix is found, no need to test in SMT encoding
						//(1) if the prefix is satisfiable, deadlock exists for this pattern
						//(2) if the prefix is unsatisfiable, deadlock exists in the prefix
						//TODO: need to verify if (2) is true
						System.out.println("Deadlock is found!");
						if(endtime == 0)
							endtime = System.currentTimeMillis();
						//continue to check other instances even when a deadlock is found
						//return;
						System.out.println(count);
						break breakpoint;
					}
					
					for(Integer rank : reachableRanks)
					{	
						//report no deadlock for this pattern
						//could be deadlock in the prefix, see A above
						if(patternProcesses.containsKey(rank))
						{
							//System.out.print(count + " No deadlock is found for pattern: " 
								//	+ patternProcesses + "\n");
							break breakpoint;
						}
					}
					
					moveBlockPoints(reachableRanks,patternProcesses);
				}
			}
			
			//reset the indicator for the next check;
			//reset all data structures used before
			resetProgram();
			count++;
		}
	}
	
	HashSet<Integer> feasible(Hashtable<Integer, Recv> patternProcesses) throws Exception
	{
		HashSet<Integer> reachableRanks = new HashSet<Integer>();
		for(int i = 0; i < program.size(); i++)
		{
			Process process = program.get(i);
			if(process.indicator < process.size())
			{
				Operation blockOp = process.get(process.indicator);
				//for the pattern process
				if(patternProcesses.containsKey(i))
				{
					Recv deadlockPoint = (Recv)blockOp;
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
	
	//consider when dealockpoint is a deterministic receive or a wildcard receive
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
	
	void moveBlockPoints(HashSet<Integer> reachableRanks, Hashtable<Integer, Recv> patternProcesses) throws Exception
	{
		for(int rank : reachableRanks)
		{
			if(!patternProcesses.containsKey(rank))
			{
				program.get(rank).indicator++;
				program.get(rank).NextBlockPoint();
			}
		}
	}
	
	void resetProgram() throws Exception
	{
		for(int i = 0; i < program.size();i++)
		{
			program.get(i).resetIndicator();
		}
	}
	
	boolean schedulable(Hashtable<Integer, Recv> patternProcesses) throws Exception
	{
		boolean reachpatternpoint = true;
		for(int i = 0; i < program.size(); i++)
		{
			Process process = program.get(i);
			int rank = process.getRank();
			//if process does not reach the end of each process
			if(tracker[rank] < process.indicator || (process.indicator == 0 && tracker[rank] == 0))
			{
				Operation op = program.get(rank).get(tracker[rank]);
				
				//process belongs to pattern and the indicator of pattern is reached
				if(patternProcesses.containsKey(rank) && tracker[rank] < process.indicator)
				{
					reachpatternpoint = false;
				}
				//send still exist
				if(op instanceof Recv)
				{
					Recv rv = (Recv)op;
					//if recv is non-blocking, return true
					if(!rv.isBlock)
						return true;
					if(checkAvailable(rv))
						return true;
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
							//if the first receive for this wait is a receive 
							//of the circular pattern instance, then this process is not schedulable
							if(!firstR.equals(patternProcesses.get(firstR.rank)))
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
		
		if(reachpatternpoint)
		{
			System.out.println("Deadlock is found for pattern " + patternProcesses);
			//use a global variable to mark deadlock is found
			deadlockFound = true;
			return false;
		}
		
		System.out.print("No deadlock is found for pattern: " 
				 + patternProcesses + "\n");
		
		//when false, is it a deadlock for the prefix?
		return false;
	}
	
	boolean scheduling(Program program, Hashtable<Integer, Recv> patternProcesses) throws Exception
	{		
		for(int rank = 0; rank < program.size(); rank++)
		{
			if(tracker[rank] == 0 && program.get(rank).indicator == 0)
			{
				Operation op = program.get(rank).get(tracker[rank]);
				//generate entry in recvNums for a indicator receive
				if(op instanceof Recv)
				{
					Recv rv = (Recv)op;
					if(rv.isBlock)
					{
						int src = rv.src;
						int dest = rv.dest;
											
						if(!recvNums.containsKey(dest))
							recvNums.put(dest, new HashMap<Integer, Integer>());
						
						if(!recvNums.get(dest).containsKey(src))
							recvNums.get(dest).put(src, 0);
						
						continue;	
					}
				}
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
				
				if(op instanceof Recv)
				{
					Recv rv = (Recv)op;
					int src = rv.src;
					int dest = rv.dest;
						
					if(rv.isBlock)
					{
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
							if(patternProcesses.containsKey(rank) && r.equals(patternProcesses.get(rank)))
							{
								//when r is a receive in the circular pattern instance
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
	
	boolean reachBlockPoints() throws Exception
	{
		for(int i = 0; i < program.size(); i++)
		{
			if(tracker[i] < program.get(i).indicator )
				return false;
		}
		
		return true;
	}
	
	boolean checkAvailable(Recv r)
	{
		int src = r.src;
		int dest = r.dest;
		
		//more sends than receives with identical src and dest
		if(src!=-1)//Deterministic receive
			//two conditions should be satisfied:
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
		}
		return 0;
	}
	
}
