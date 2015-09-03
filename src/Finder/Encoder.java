 package Finder;

import Syntax.*;
import Syntax.Process;

import com.microsoft.z3.*;

import java.util.*;

public class Encoder 
{
	SMTSolver solver;
	Program program;
	UnmatchedEP_Pattern pattern;
	public LinkedList<Recv>[] recvlist;
	public LinkedList<Send>[][] sendlist;
	int[] lastrInShape;
	int[][] lastsInShape;
	//for unmatched endpoint, make sure all sends in source endpoint should be matched
	public Hashtable<Send, LinkedList<Recv>> pattern_match;
	public Hashtable<Recv, LinkedList<Send>> match_table;
	Operation lastr = null;
	Operation lastsz = null;
	Hashtable<Integer, Operation> lasts = null;
	
	//pair.first is op expr, pair.second is op time
	Hashtable<Operation, Pair<Expr, IntExpr>> operation_expr_map = null;
	
	//data structure to record the last wait on each process
	Wait[] lastwait;
	
	public boolean isZero = false;
	
	public Encoder(Program program, 
			UnmatchedEP_Pattern pattern, int[] lastrInShape, int[][] lastsInShape) throws Z3Exception
	{
		this.pattern = pattern;
		this.recvlist = program.recvlist;
		this.sendlist = program.sendlist;
		this.match_table = program.match_table;
		this.pattern_match = program.pattern_match;
		if(!program.isPattern())
		{
			System.out.println("Program should be set to mismatched endpoint pattern available!");
			System.exit(0);
		}
		this.lastrInShape = lastrInShape;
		this.lastsInShape = lastsInShape;
		lasts = new Hashtable<Integer,Operation>();
		operation_expr_map = new Hashtable<Operation,Pair<Expr,IntExpr>>();
		lastwait = new Wait[program.size()];
		//initialize the waits to be null
		for(int i = 0; i < program.size(); i++)
		{
			lastwait[i] = null;
		}
		this.program = program;
		solver = new SMTSolver();
		solver.definition();
	}
	
	public void Encoding() throws Z3Exception
	{
		for(Process process: program.processes)
		{
			lastr = null;
			lasts.clear();
			for(int i = 0; i < process.indicator; i++)//up to process.indicator 
			{
				Encoding(process.get(i));
			}
		}
		
//		generateMatch();
		encodeMatch();
	}
	
	public void Encoding(Operation op) throws Z3Exception
	{
		if(op instanceof Recv)
		{
			IntExpr time = solver.MkTime("T" + op.event);
			Expr recv = solver.mkRecv("R" + op.event);
			Pair<Expr,IntExpr> recvinfo = new Pair<Expr,IntExpr>(recv,time);
			operation_expr_map.put(op, recvinfo);
			IntExpr var = solver.MkTime("var" + op.event);
			//TODO: add nw when the receive is non-blocking
			IntExpr nw = null;
			if(!((Recv)op).isBlock)
			{
				Wait wait = ((Recv)op).NearestWait;
				//add nearest inclosing wait for recv
				if(!operation_expr_map.containsKey(wait))
				{
					//TODO:generate the time for wait?
					IntExpr waittime = solver.MkTime("T" + op.event);
					Pair<Expr,IntExpr> waitinfo = new Pair<Expr,IntExpr>(null,waittime);
					operation_expr_map.put(wait, waitinfo);
				}
				//add HB over a receive and its nw
				solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(op).getSecond(), 
						(IntExpr)operation_expr_map.get(wait).getSecond()));		
			}
			solver.addFormula(solver.initRecv(recv, ((Recv)op).src, ((Recv)op).dest, time, var, nw));
			if(lastr != null)
			{
				solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastr).getSecond(), 
						time));
			}
			
			if(lastsz!=null && isZero)
			{
				solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastsz).getSecond(), 
						time));
			}
			
			lastr = op;
			//lastr will be initilized to null at beginning of traversing each process
		}
		else if(op instanceof Wait)//assume only receives have waits
		{
			//HB over this wait and its next send is added when op is a send
			lastwait[op.process.getRank()] = (Wait)op;
		}
		//TODO: add HB for barriers and other operations
		else if(op instanceof Send)
		{
			IntExpr time = solver.MkTime("T" + op.event);
			Expr send = solver.mkSend("S" + op.event);
			Pair<Expr,IntExpr> sendinfo = new Pair<Expr,IntExpr>(send,time);
			operation_expr_map.put(op, sendinfo);
			solver.addFormula(solver.initSend(send, ((Send)op).src, ((Send)op).dest, time, ((Send)op).value));
			
			if(lastr != null)
			{
//				System.out.println("send: " + send + "recv: " + operation_expr_map.get(lastr).getFirst());
//				System.out.println("lastr_time: "+ (IntExpr)operation_expr_map.get(lastr).getSecond()+ " send_time: "+time);
				//if non-blocking receive is applied, should be nw < send
				solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastr).getSecond(), 
						time));
			}
			
			if(lastsz !=null)
			{
				solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastsz).getSecond(), 
						time));
			}
			
			if(lasts.containsKey(((Send)op).dest))
			{
				//do not repeatedly add hb for last send and op under zero buffer
				if(!lasts.get(((Send)op).dest).equals(lastsz))
					solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lasts.get(((Send)op).dest)).getSecond(), 
						time));
			}
			
//			if(lastwait.length > op.process.getRank())
//			{
				if(lastwait[op.process.getRank()] != null)
				{
					//does not constrain the HB for a wait and the next receive, defined in NFM paper
					solver.addFormula(solver.HB((IntExpr)operation_expr_map.get(lastwait[op.process.getRank()]).getSecond(), 
							time));
				}
//			}

			//add nearest inclosing send for dest
			lasts.put(((Send)op).dest,op);
			if(isZero)
				lastsz = op;
		}
	}
	
	public void encodeMatch() throws Z3Exception
	{
		//two parts: first, for every receive r, there must be a match, (r,.)
		continuepoint:
		for(Recv r : match_table.keySet())
		{
			//only encode match when r is in shape
			if(r.rank > lastrInShape[r.dest])
				continue continuepoint;
			Expr rExpr = operation_expr_map.get(r).getFirst();
//			IntExpr rTime = operation_expr_map.get(r).getSecond();
			BoolExpr a = null;
			BoolExpr b = null;
			
			continuepoint1:
			for(Send s : match_table.get(r))
			{
				//only encode match when s is in shape
				if(s.rank > lastsInShape[s.dest][s.src])
					continue continuepoint1;
				Expr sExpr = operation_expr_map.get(s).getFirst();
//				IntExpr sTime = operation_expr_map.get(s).getSecond();
				if(rExpr != null && sExpr != null)//should not be null
				{
					if(!isZero)
						a = solver.Match(rExpr, sExpr);
					else a = solver.Match_Zero(rExpr, sExpr);
					b = (b!=null)?solver.mkOr(a, b):a;//make or for all matches for receive r
				}
			}
			solver.addFormula(b);
		}
		
		//second, for every send s that can match pattern receive R(c), there must be a match (s,.)
		
		for(Send s : pattern_match.keySet())
		{
			//only encode send if dest and src is pattern requires and it is in shape
			if(s.dest != pattern.process.getRank() || s.src != pattern.determinstic.src 
				|| s.rank > lastsInShape[s.dest][s.src])
				continue; 
			Expr sExpr = operation_expr_map.get(s).getFirst();
//			IntExpr sTime = operation_expr_map.get(s).getSecond();
			BoolExpr a = null;
			BoolExpr b = null;
			for(Recv r : pattern_match.get(s))
			{
				//only encode when r is in shape
				if(r.rank > lastrInShape[r.dest])
					continue;
				Expr rExpr = operation_expr_map.get(r).getFirst();
//				IntExpr rTime = operation_expr_map.get(r).getSecond();
				if(rExpr != null && sExpr != null)//should not be null
				{
					a = solver.Match(rExpr, sExpr);
					b = (b!=null)?solver.mkOr(a, b):a;//make or for all matches for send s
				}
			}
			solver.addFormula(b);
		}
	}

}