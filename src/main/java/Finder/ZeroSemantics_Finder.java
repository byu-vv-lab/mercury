package Finder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import com.microsoft.z3.Model;
import com.microsoft.z3.Status;

import Syntax.Barrier;
import Syntax.Operation;
import Syntax.Pair;
import Syntax.Process;
import Syntax.Program;
import Syntax.Recv;
import Syntax.Send;
import Syntax.UnmatchedEP_Pattern;
import Syntax.Wait;

public class ZeroSemantics_Finder {
	
	Program program;
	int tracker[];
	//HashMap<Pair<Integer,Integer>, LinkedList<Send>> unvisitedSends;
	HashMap<Integer, HashMap<Integer,Integer>> sendNums;
	HashMap<Integer, HashMap<Integer,Integer>> recvNums;
	
	public HashMap<Wait, LinkedList<Recv>> witnessedRecv; //used to record the receives that are witnessed
	
	Stack<Pair<Send,int[]>> choices; //mapping a send to a program state (tracker)
	
	boolean ispostponed;
	boolean[] checked;
	
	
	public ZeroSemantics_Finder(Program p)
	{
		
		program = p;
		tracker = new int[p.size()];
		sendNums = new HashMap<Integer, HashMap<Integer, Integer>>();
		recvNums = new HashMap<Integer, HashMap<Integer, Integer>>();
		
		witnessedRecv = new HashMap<Wait, LinkedList<Recv>>();
		
		choices = new Stack<Pair<Send, int[]>>();
		ispostponed = false;
		checked = new boolean[program.size()];
		for(boolean x : checked)
			x = false;
	}
	
	public void Run() throws Exception
	{
		//Object[] patterns =  program.getUnmatchedEP_Pattern().toArray();
		//Iterator<UnmatchedEP_Pattern> it = patterns.iterator();
		//System.out.println("mismatched send-receive patterns: " + patterns.length);

		program.InitGraph();
			
		//initialize recvNums and sendNums
		recvNums.clear();
		sendNums.clear();
			
		witnessedRecv.clear();
		
		//initialize the prefix
		for(int i =0; i < program.size();i++)
		{
			//program.get(i).NextBlockPoint();
			tracker[i] = 0;
				
		}
		
			
		//judge prefix's feasibility 
		breakpoint:
		while(true)
		{	
			while(isMoved())
			{
				scheduling(program);
			}
			
			//TODO: need to verify if we can directly set it to true
			//since there is no way to proceed the program according to isMoved(), 
			//either the program is not schedulable, or the sends can be proceeded.
			ispostponed = true;
			
			if(!schedulable())
			{
				if(isEnd(tracker))//reach the end
				{
					if(choices.isEmpty())
					{
						//report no deadlock and return
						System.out.printf("No deadlock is found for zero buffer semantics!\n");
						return;
					}
					
					Pair<Send,int[]> nextchoice = choices.pop();
					resetTracker(nextchoice);
					continue;
				}
				
				//check if the current positions has multiple sends may match less receives
				//if there is, then generate new choices and push them to the stack
				boolean hasChoices = checkChoices(choices,tracker); 
				if(!hasChoices)
				{
					//report deadlock and return;
					System.out.printf("A deadlock is found for zero buffer semantics!\n");
					return;
				}
				Pair<Send,int[]> nextchoice = choices.pop();//check the next choice
				resetTracker(nextchoice);
				continue;
				//TODO: find a potential deadlock that multiple sends can not be completely matched
				//TODO: check if all the processes reach the end
			}
		}
	}

	boolean isMoved()
	{
		for(boolean x : checked)
		{
			if(!x)
				return true;
		}
		
		return false;
	}
	
	void resetTracker(Pair<Send,int[]> choice)
	{
		int[] t = choice.getSecond();
		Send s = choice.getFirst();
		for(int i : t)
		{
			tracker[i] = t[i];
			if(s.src == i)
			{
				//move the target send if this send is the current choice
				//TODO:may check in the main method (if this send is the last op on the process, 
				//then there is no deadlock for this send, just ignore this choice) 
				tracker[i]++;
			}
		}
	}
	
	boolean checkChoices(Stack<Pair<Send,int[]>> choices,int[] tracker) throws Exception
	{
		HashSet<Send> sends = new HashSet<Send>();
		HashSet<Recv> recvs = new HashSet<Recv>();
		//add current operations to two data structures
		for(int i = 0; i < program.size(); i++)
		{
			if(tracker[i] < program.get(i).size())
			{
				Operation op = program.get(i).get(tracker[i]);
				if(op instanceof Send)
				{
					sends.add((Send)op);
				}
				if(op instanceof Recv)
				{
					recvs.add((Recv)op);
				}
				
				/*if(tracker[i] > 0)
				{
					//add the previous operation as a candidate receive
					Operation prev_op = program.get(i).get(tracker[i]-1);
					if(prev_op instanceof Recv)
					{
						recvs.add((Recv)prev_op);
					}
				}*/
			}
		}
		
		//check and return true if some receive can match more than one send in these two sets
		boolean haschoice = false;
		for(Recv r : recvs)
		{
			//only a wildcard receive can match multiple sends at some state
			if(r.src != -1)
				continue;
			HashSet<Send> matchedS = new HashSet<Send>();
			for(Send s: sends)
			{
				if((s.dest==r.dest)) 
				{
					matchedS.add(s);
				}
			}
			//more than one send can match r
			if(matchedS.size() > 1)
			{
				for(Send s_match : matchedS)
				{
					int[] copy = tracker.clone();
					Pair<Send,int[]> newChoice = new Pair(s_match, copy);
					choices.push(newChoice);
				}
				haschoice = true;
			}
		}
		
		return haschoice;
	}
	
	
	boolean isEnd(int[] tracker) throws Exception
	{
		for(int i = 0; i < program.size(); i++)
		{
			if(tracker[i] < program.get(i).size())
				return false;
		}
		
		return true;
		
	}
	
	void scheduling(Program program) throws Exception
	{
		
		for(int i = 0; i < program.size(); i++)
		{
			int rank = i;
				
			Operation op;
			if(tracker[rank] < program.get(rank).size())
			{
				op = program.get(rank).get(tracker[rank]);
				if(op instanceof Send){
					Send sendop = (Send)op;
					int dest = sendop.dest;
					int src = sendop.src;
							
					//if(ispostponed && checkAvailable(sendop))
					if(ispostponed && checkAvailable(sendop))
					{
						tracker[rank]++;
						checked[rank] = false;
						//ispostponed = false;
						//high priority for all checked operations
						continue;
					}
					
					if(!checked[rank]){
					
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
						
						checked[rank] = true;
					}
					
					//send is always break, it is only proceeded if all sends are postponed 
					//and are able to be proceeded
					//break;
					
				}
				
				if(op instanceof Recv)
				{
					
					Recv rv = (Recv)op;
					int src = rv.src;
					int dest = rv.dest;
					
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
							tracker[rank]++;
							//this value is immediately changed from false to true and back to true, 
							//because tracker is incremented
							checked[rank] = false; 
							
							ispostponed = false;
						}
						else //when recv can not be matched, scheduling stops for this process
						{
							//if this receive is not available, check it 
							checked[rank] = true;
							//break;
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
				/*if(op instanceof Wait)
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
					
				}*/
			}
			else
			{
				checked[rank] = true;
			}
			
		}		
		ispostponed = false;
	}
	
	boolean schedulable() throws Exception
	{
		for(int i = 0; i < program.size(); i++)
		{
			Process process = program.get(i);
			int rank = process.getRank();
			//if process does not reach the end of each process
			if(tracker[rank] < process.size())
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
					{
						checked[rank] = false;
						return true;
					}
					if(checkAvailable(rv))
					{
						checked[rank] = false;
						return true;
					}
				}
				else if(op instanceof Send)
				{
					Send sd = (Send)op;
					if(ispostponed)
					{
						if(checkAvailable(sd))
						{
							checked[rank] = false;
							return true;
						}
					}
					else 
					{
						checked[rank] = false;
						return true;
					}
				}
				// add a case when op is a wait
				/*else if(op instanceof Wait)
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
					
				}*/
				else 
				{
					checked[rank] = false;
					return true;
				}
				
			}
		}
		
		
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
	
	boolean checkAvailable(Send s)
	{
		int src = s.src;
		int dest = s.dest;
		
		if(totalNUM(sendNums,src,dest) <= totalNUM(recvNums,src,dest) && totalNUM(recvNums,src,dest) >0)
			return true;
		
		int count = 0;
		if(sendNums.containsKey(dest))
		{
			for(Integer rsrc : sendNums.get(dest).keySet())
			{
				if(rsrc != -1)
				{
					int diff = totalNUM(sendNums,rsrc,dest) - totalNUM(recvNums,rsrc,dest);
					count += (diff <= 0) ? 0 : diff;
				}
			}		
		}
		
		return count <= totalNUM(recvNums,-1,dest) && totalNUM(recvNums,-1,dest) > 0;
	
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
