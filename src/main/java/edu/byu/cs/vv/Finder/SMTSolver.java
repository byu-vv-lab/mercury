package Finder;

import Syntax.Operations.Receive;
import com.microsoft.z3.*;

import java.util.Collection;

public class SMTSolver {

    IntSort intsort;
    TupleSort Recv;
    TupleSort Send;
    Context context;
    com.microsoft.z3.Solver solver;
    FuncDecl HB;
    FuncDecl MATCH;
    boolean display = false;

    public SMTSolver() {
    }

    public void setup() throws Z3Exception {
        context = new Context();
        solver = context.MkSolver();
        intsort = context.IntSort();
        Recv = context.MkTupleSort(context.MkSymbol("Recv"),
                new Symbol[]{
                        context.MkSymbol("src"), context.MkSymbol("dest"), context.MkSymbol("time"),
                        context.MkSymbol("match"), context.MkSymbol("var"), context.MkSymbol("nw")},
                new Sort[]{intsort, intsort, intsort, intsort, intsort, intsort});
        Send = context.MkTupleSort(context.MkSymbol("Send"),
                new Symbol[]{context.MkSymbol("src"), context.MkSymbol("dest"), context.MkSymbol("time"),
                        context.MkSymbol("match"), context.MkSymbol("value")},
                new Sort[]{intsort, intsort, intsort, intsort, intsort});
    }

    public BoolExpr mkOr(BoolExpr a, BoolExpr b) throws Z3Exception {
        return context.MkOr(new BoolExpr[]{a, b});
    }

    public BoolExpr mkOr(Collection<BoolExpr> exprs) throws Z3Exception {
        BoolExpr[] exprArray = new BoolExpr[exprs.size()];
        return context.MkOr(exprs.toArray(exprArray));
    }

    public Expr getRecvField(Expr recv, int index) throws Z3Exception {
        return context.MkApp(Recv.FieldDecls()[index], recv);
    }

    public Expr getSendField(Expr send, int index) throws Z3Exception {
        return context.MkApp(Send.FieldDecls()[index], send);
    }

    public BoolExpr Match(Expr r, Expr s) throws Z3Exception {
        BoolExpr cond = context.MkAnd(new BoolExpr[]{
                context.MkOr(new BoolExpr[]{context.MkEq(getRecvField(r, 0), getSendField(s, 0)),
                        context.MkEq(getRecvField(r, 0), context.MkInt(-1))}),//r.src=s.src or r.src=-1
                context.MkEq(getRecvField(r, 1), getSendField(s, 1)),//r.dest=s.dest
                context.MkEq(getRecvField(r, 4), getSendField(s, 4)),//r.var=s.value
                HB((IntExpr) getSendField(s, 2), (IntExpr) getRecvField(r, 2)),//could be send < nw for non-blocking operations
                context.MkEq(getRecvField(r, 3), getSendField(s, 2)),//r.match = s.time
                context.MkEq(getRecvField(r, 2), getSendField(s, 3))//s.match=r.time
        });

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
        Expr recv = context.MkConst("R", Recv);
        Expr send = context.MkConst("S", Send);
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
        Expr sExpr = context.MkConst(name, Send);
        if (display)
            System.out.println(sExpr);
        return sExpr;
    }

    public BoolExpr initSend(Expr expr, IntExpr time, Syntax.Operations.Send send) throws Z3Exception {
        BoolExpr sDf = context.MkAnd(
                new BoolExpr[]{
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
        Expr rExpr = context.MkConst(name, Recv);
        if (display)
            System.out.println(rExpr);
        return rExpr;
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

    public void addFormula(BoolExpr expr) throws Z3Exception {
        solver.Assert(expr);
    }

    public Model Check(Status sat) throws Exception {
        if (solver.Check() != sat) {
//            System.out.println("UNSAT!");
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
