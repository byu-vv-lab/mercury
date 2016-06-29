package edu.byu.cs.vv.encoding;

import edu.byu.cs.vv.ast.operations.Receive;
import com.microsoft.z3.*;
import edu.byu.cs.vv.ast.operations.Send;

import java.util.Collection;

public class SMTSolver {
    
    IntSort intsort;
    TupleSort recv_sort;
    TupleSort send_sort;
    IntSort Wait;
    Context context;
    com.microsoft.z3.Solver solver;
    FuncDecl HB;
    FuncDecl MATCH;
    BoolExpr rdProperty; //ready send
    boolean display = false;
    
    AbstractEncoder encoder;
    
    public SMTSolver() {
    }
    
    public void setup() throws Z3Exception {
        context = new Context();
        solver = context.MkSolver();
        intsort = context.IntSort();
        recv_sort = context.MkTupleSort(context.MkSymbol("recv_sort"),
                                        new Symbol[]{
                                            context.MkSymbol("src"), context.MkSymbol("dest"), context.MkSymbol("time"),
                                            context.MkSymbol("match"), context.MkSymbol("var"), context.MkSymbol("nw")},
                                        new Sort[]{intsort, intsort, intsort, intsort, intsort, intsort});
        send_sort = context.MkTupleSort(context.MkSymbol("send_sort"),
                                        new Symbol[]{context.MkSymbol("src"), context.MkSymbol("dest"), context.MkSymbol("time"),
                                            context.MkSymbol("match"), context.MkSymbol("value")},
                                        new Sort[]{intsort, intsort, intsort, intsort, intsort});
        
        rdProperty = ctx.MkFalse();
    }
    
    public BoolExpr mkOr(BoolExpr a, BoolExpr b) throws Z3Exception {
        return context.MkOr(new BoolExpr[]{a, b});
    }
    
    public BoolExpr mkOr(Collection<BoolExpr> exprs) throws Z3Exception {
        BoolExpr[] exprArray = new BoolExpr[exprs.size()];
        return context.MkOr(exprs.toArray(exprArray));
    }
    
    public Expr getRecvField(Expr recv, int index) throws Z3Exception {
        return context.MkApp(recv_sort.FieldDecls()[index], recv);
    }
    
    public Expr getSendField(Expr send, int index) throws Z3Exception {
        return context.MkApp(send_sort.FieldDecls()[index], send);
    }
    
    public BoolExpr Match(Expr r, Expr s, Receive recv, Send send) throws Z3Exception {
        BoolExpr cond = context.MkAnd(new BoolExpr[]{
            context.MkOr(new BoolExpr[]{context.MkEq(getRecvField(r, 0), getSendField(s, 0)),
                context.MkEq(getRecvField(r, 0), context.MkInt(-1))}),//r.src=s.src or r.src=-1
            context.MkEq(getRecvField(r, 1), getSendField(s, 1)),//r.dest=s.dest
            context.MkEq(getRecvField(r, 4), getSendField(s, 4)),//r.var=s.value
            //HB((IntExpr) getSendField(s, 2), (IntExpr) getRecvField(r, 2)),//could be send < nw for non-blocking operations
            context.MkEq(getRecvField(r, 3), getSendField(s, 2)),//r.match = s.time
            context.MkEq(getRecvField(r, 2), getSendField(s, 3))//s.match=r.time
        });
        
        //s.time <hb the next operation (or r.wait) after r in an identical process of r
        if(!recv.isBlock)
        {
            //s.time <hb nw of r
            cond = ctx.MkAnd(new BoolExpr[]{cond,HB((IntExpr)getSendField(s,2),(IntExpr)getRecvField(r,5))});
        }
        else
        {
            //check if a next operation after recv exists
            //TODO: now assume that the next op has a record in operation_expr_map
            if(recv.nextOP!=null)
            {
                IntExpr nextOPTime = null;
                if(encoder.operation_expr_map.containsKey(recv.nextOP))
                {
                    if(encoder.operation_expr_map.get(recv.nextOP) != null)
                    {
                        nextOPTime = encoder.operation_expr_map.get(recv.nextOP).getSecond();
                    }
                }
                
                if(nextOPTime != null)
                {
                    cond = ctx.MkAnd(new BoolExpr[]{cond,HB((IntExpr)getSendField(s,2),nextOPTime)});
                }
            }
        }
        
        return cond;
    }
    
    public BoolExpr Match_ss(Expr r, Expr s, Receive recv, Send send) throws Z3Exception
    {
        //		return (BoolExpr)ctx.MkApp(MATCH, new Expr[]{r, s});
        BoolExpr cond = ctx.MkAnd(new BoolExpr[]{
            ctx.MkOr(new BoolExpr[]{ctx.MkEq(getRecvField( r, 0), getSendField( s, 0)),
                ctx.MkEq(getRecvField( r, 0), ctx.MkInt(-1))}),//r.src=s.src or r.src=-1
            ctx.MkEq(getRecvField( r, 1), getSendField( s, 1)),//r.dest=s.dest
            ctx.MkEq(getRecvField( r, 4), getSendField( s, 4)),//r.var=s.value
            //HB((IntExpr)getRecvField(r,2),(IntExpr)getSendField(s,2)),
            ctx.MkEq(getRecvField( r,3), getSendField(s,2)),//r.match = s.time
            ctx.MkEq(getRecvField( r,2), getSendField(s,3))//s.match=r.time
        });
        
        //s.time <hb the next operation (or r.wait) after r in an identical process of r
        if(!recv.isBlock)
        {
            cond = ctx.MkAnd(new BoolExpr[]{cond,HB((IntExpr)getSendField(s,2),(IntExpr)getRecvField(r,5))});
        }
        else
        {
            //check if a next operation after recv exists
            //TODO: now assume that the next op has a record in operation_expr_map
            if(recv.nextOP!=null)
            {
                IntExpr nextOPTime = null;
                if(encoder.operation_expr_map.containsKey(recv.nextOP))
                {
                    if(encoder.operation_expr_map.get(recv.nextOP) != null)
                    {
                        nextOPTime = encoder.operation_expr_map.get(recv.nextOP).getSecond();
                    }
                }
                
                if(nextOPTime != null)
                {
                    cond = ctx.MkAnd(new BoolExpr[]{cond,HB((IntExpr)getSendField(s,2),nextOPTime)});
                }
            }
        }
        
        //TODO: should be r.time <hb the next operation (or s.wait) after s in an identical process of s
        if(!send.isBlock)
        {
            cond = ctx.MkAnd(new BoolExpr[]{cond,HB((IntExpr)getRecvField(r,2),(IntExpr)getSendField(s,5))});
        }
        else
        {
            //check if a next operation after send exists
            //TODO: now assume that the next op has a record in operation_expr_map
            if(send.nextOP!=null)
            {
                IntExpr nextOPTime = null;
                if(encoder.operation_expr_map.containsKey(send.nextOP))
                {
                    if(encoder.operation_expr_map.get(send.nextOP) != null)
                    {
                        nextOPTime = encoder.operation_expr_map.get(send.nextOP).getSecond();
                    }
                }
                
                if(nextOPTime != null)
                {
                    cond = ctx.MkAnd(new BoolExpr[]{cond,HB((IntExpr)getRecvField(r,2),nextOPTime)});
                }
            }
        }
        
        return cond;
    }
    
    public BoolExpr Match_rs(Expr r, Expr s, Receive recv, Send send) throws Z3Exception
    {
        //		return (BoolExpr)ctx.MkApp(MATCH, new Expr[]{r, s});
        BoolExpr cond = ctx.MkAnd(new BoolExpr[]{
            ctx.MkOr(new BoolExpr[]{ctx.MkEq(getRecvField( r, 0), getSendField( s, 0)),
                ctx.MkEq(getRecvField( r, 0), ctx.MkInt(-1))}),//r.src=s.src or r.src=-1
            ctx.MkEq(getRecvField( r, 1), getSendField( s, 1)),//r.dest=s.dest
            ctx.MkEq(getRecvField( r, 4), getSendField( s, 4)),//r.var=s.value
            //HB((IntExpr)getRecvField(r,2),(IntExpr)getSendField(s,2)),//TODO:should be s.time <hb the next operation (or r.wait) after r in an identical process of r
            ctx.MkEq(getRecvField( r,3), getSendField(s,2)),//r.match = s.time
            ctx.MkEq(getRecvField( r,2), getSendField(s,3))//s.match=r.time
        });
        
        //s.time <hb the next operation (or r.wait) after r in an identical process of r
        if(!recv.isBlock)
        {
            cond = ctx.MkAnd(new BoolExpr[]{cond,HB((IntExpr)getSendField(s,2),(IntExpr)getRecvField(r,5))});
        }
        else
        {
            //check if a next operation after recv exists
            //TODO: now assume that the next op has a record in operation_expr_map
            if(recv.nextOP!=null)
            {
                IntExpr nextOPTime = null;
                if(encoder.operation_expr_map.containsKey(recv.nextOP))
                {
                    if(encoder.operation_expr_map.get(recv.nextOP) != null)
                    {
                        nextOPTime = encoder.operation_expr_map.get(recv.nextOP).getSecond();
                    }
                }
                
                if(nextOPTime != null)
                {
                    cond = ctx.MkAnd(new BoolExpr[]{cond,HB((IntExpr)getSendField(s,2),nextOPTime)});
                }
            }
        }
        
        rdProperty = ctx.MkOr(new BoolExpr[]{rdProperty,HB((IntExpr)getSendField(s,2),(IntExpr)getRecvField(r,2))});
        
        return cond;
    }
    
    public BoolExpr Match_Zero(Expr r, Expr s) throws Z3Exception {
        BoolExpr cond = context.MkAnd(new BoolExpr[]{
            context.MkOr(new BoolExpr[]{context.MkEq(getRecvField(r, 0), getSendField(s, 0)),
                context.MkEq(getRecvField(r, 0), context.MkInt(-1))}),//r.src=s.src or r.src=-1
            context.MkEq(getRecvField(r, 1), getSendField(s, 1)),//r.dest=s.dest
            context.MkEq(getRecvField(r, 4), getSendField(s, 4)),//r.var=s.value
            //s.time = r.time -1 for zero buffer semantics
            context.MkEq((IntExpr) getSendField(s, 2), context.MkAdd(new IntExpr[]{context.MkInt(-1), (IntExpr) getRecvField(r, 2)})),
            context.MkEq(getRecvField(r, 3), getSendField(s, 2)),//r.match = s.time
            context.MkEq(getRecvField(r, 2), getSendField(s, 3))//s.match=r.time
        });
        return cond;
    }
    
    public BoolExpr initMATCH() throws Z3Exception {
        Expr recv = context.MkConst("R", recv_sort);
        Expr send = context.MkConst("S", send_sort);
        Expr fapp = context.MkApp(MATCH, new Expr[]{recv, send});
        BoolExpr cond = context.MkAnd(new BoolExpr[]{
            context.MkOr(new BoolExpr[]{context.MkEq(getRecvField(recv, 0), getSendField(send, 0)),
                context.MkEq(getRecvField(recv, 0), context.MkInt(-1))}),//r.src=s.src or r.src=-1
            context.MkEq(getRecvField(recv, 1), getSendField(send, 1)),//r.dest=s.dest
            context.MkEq(getRecvField(recv, 4), getSendField(send, 4)),//r.var=s.value
            HB((IntExpr) getSendField(send, 2), (IntExpr) getRecvField(recv, 2)),//could be send < nw for non-blocking operations
            context.MkEq(getRecvField(recv, 3), getSendField(send, 2)),//r.match = s.time
            context.MkEq(getRecvField(recv, 2), getSendField(send, 3))//s.match=r.time
        });
        Expr ite = context.MkITE(cond, context.MkTrue(), context.MkFalse());
        Quantifier q = context.MkForall(new Expr[]{recv, send},
                                        context.MkEq(fapp, ite), 1, null, null, null, null);
        return q;
    }
    
    public IntExpr MkTime(String event) throws Z3Exception {
        return context.MkIntConst(event);
    }
    
    public BoolExpr HB(IntExpr a, IntExpr b) throws Z3Exception {
        return context.MkLt(a, b);
    }
    
    public BoolExpr initHB() throws Z3Exception {
        IntExpr a = context.MkIntConst("a");
        IntExpr b = context.MkIntConst("b");
        Expr fapp = context.MkApp(HB, new Expr[]{a, b});
        Expr ite = context.MkITE(context.MkLt(a, b), context.MkTrue(), context.MkFalse());
        
        return context.MkForall(new Expr[]{a, b},
                                context.MkEq(fapp, ite), 1, null, null,
                                null, null);
    }
    
    public Expr mkSend(String name) throws Z3Exception {
        Expr sExpr = context.MkConst(name, send_sort);
        if (display)
            System.out.println(sExpr);
        return sExpr;
    }
    
    public BoolExpr initSend(Expr expr, IntExpr time, Send send,IntExpr nw) throws Z3Exception {
        BoolExpr sDf;
        if(nw!=null)
            sDf = context.MkAnd(new BoolExpr[]{
                context.MkEq(getSendField(expr, 0), context.MkInt(send.src)),
                context.MkEq(getSendField(expr, 1), context.MkInt(send.dest)),
                context.MkEq(getSendField(expr, 4), context.MkInt(send.value)),
                context.MkEq(getSendField(expr, 5), nw),
                context.MkEq(getSendField(expr, 2), time)
            });
        else sDf = context.MkAnd(new BoolExpr[]{
                                         context.MkEq(getSendField(expr, 0), context.MkInt(send.src)),
                                         context.MkEq(getSendField(expr, 1), context.MkInt(send.dest)),
                                         context.MkEq(getSendField(expr, 4), context.MkInt(send.value)),
                                         context.MkEq(getSendField(expr, 2), time)
                                     });
        if (display)
            System.out.println(sDf);
        return sDf;
    }
    
    public Expr mkRecv(String name) throws Z3Exception {
        Expr rExpr = context.MkConst(name, recv_sort);
        if (display)
            System.out.println(rExpr);
        return rExpr;
    }
    
    public Expr mkWait(String name) throws Z3Exception
    {
        Expr wExpr = context.MkConst(name, Wait);
        if(display)
            System.out.println(wExpr);
        
        return wExpr;
    }
    
    public BoolExpr initRecv(Expr expr, IntExpr time, IntExpr var, IntExpr nw, Receive recv) throws Z3Exception {
        BoolExpr rDf;
        if (nw != null)
            rDf = context.MkAnd(
                                new BoolExpr[]{
                                    context.MkEq(getRecvField(expr, 0), context.MkInt(recv.src)),
                                    context.MkEq(getRecvField(expr, 1), context.MkInt(recv.dest)),
                                    context.MkEq(getRecvField(expr, 4), var),
                                    context.MkEq(getRecvField(expr, 5), nw),
                                    context.MkEq(getRecvField(expr, 2), time)
                                });
        else rDf = context.MkAnd(
                                 new BoolExpr[]{
                                     context.MkEq(getRecvField(expr, 0), context.MkInt(recv.src)),
                                     context.MkEq(getRecvField(expr, 1), context.MkInt(recv.dest)),
                                     context.MkEq(getRecvField(expr, 4), var),
                                     context.MkEq(getRecvField(expr, 2), time)
                                 });
        if (display)
            System.out.println(rDf);
        return rDf;
    }
    
    public IntExpr mkInt(int a) throws Z3Exception
    {
        return context.MkInt(a);
    }
    
    public void addFormula(BoolExpr expr) throws Z3Exception {
        solver.Assert(expr);
    }
    
    public Model Check(Status sat) throws Exception {
        if (solver.Check() != sat) {
            //
            System.out.println("UNSAT!");
            return null;
        }
        if (sat == Status.SATISFIABLE)
            return solver.Model();
        else
            return null;
    }
    
    public void displayFormulas() throws Z3Exception {
        for (BoolExpr assertion : solver.Assertions()) {
            System.out.println(assertion);
        }
    }
    
    
}
