package edu.byu.cs.vv.finder.circlefinder;

import edu.byu.cs.vv.ast.operations.Operation;

import java.util.*;

public class TarjanSCC {

    private boolean[] marked;        // marked[v] = has v been visited?

    private List<Set<Operation>> sccs;
    public Set<Operation> leastSCC;
    public int leastvertex;
    private int[] low;               // low[v] = low number of v
    private int pre;                 // preorder number counter
    private int count;               // number of strongly-connected components
    private Stack<Operation> stack;
    Digraph G;

    //used to filter any vertices lower than lowerbound and any edge connecting those vertices
    private int lowerbound;


    /**
     * Computes the strong components of the digraph <tt>G</tt>.
     *
     * @param Graph the digraph
     */
    public TarjanSCC(Digraph Graph, int lb) {
        lowerbound = lb;
        G = Graph;
        int Vsize = G.size();
        marked = new boolean[Vsize];
        stack = new Stack<>();
        sccs = new ArrayList<>();
        leastSCC = null;
        leastvertex = lowerbound;
        low = new int[Vsize];

        int minrank = Integer.MAX_VALUE;

        //only start from lowerbound
        for (int i = lowerbound; i < Vsize; i++) {
            if (!marked[i]) dfs(G, G.vList.get(i), minrank);
        }

    }

    private void dfs(Digraph G, Operation v, int minrank) {
        int vrank = G.vList.indexOf(v);
        marked[vrank] = true;
        low[vrank] = pre++;
        int min = low[vrank];
        stack.push(v);
        if (G.adj(v) != null) {
            for (Operation w : G.adj(v)) {
                //only consider all the edges with vertices >= lowerbound
                if (G.vList.indexOf(w) >= lowerbound) {
                    int wrank = G.vList.indexOf(w);
                    if (!marked[wrank]) dfs(G, w, minrank);
                    if (low[wrank] < min) min = low[wrank];
                }
            }
        }
        if (min < low[vrank]) {
            low[vrank] = min;
            return;
        }
        Operation w;
        int minlocal = Integer.MAX_VALUE;
        do {
            w = stack.pop();
            int wrank = G.vList.indexOf(w);
            if (wrank < minlocal) {
                minlocal = wrank;
            }
            if (sccs.size() <= count)
                sccs.add(new HashSet<Operation>());
            sccs.get(count).add(w);
//            id[wrank] = count;
            low[wrank] = G.size();
        } while (w != v);

        //this scc is assigned leastSCC iff it has the least vertex and the size of this scc > 1 
        if (minlocal < minrank && sccs.get(count).size() > 1) {
            minrank = minlocal;
            leastvertex = minrank;
            leastSCC = sccs.get(count);
        }
        count++;
    }

    /**
     * Returns the number of strong components.
     *
     * @return the number of strong components
     */
    public int count() {
        return count;
    }

    public void printCircles(List<Set<Vertex>> circles) {
        for (Set<Vertex> vset : circles) {
            System.out.println("Circle[" + circles.indexOf(vset) + "]:");
            System.out.print("{");
            for (Vertex v : vset) {
                System.out.print(v + " ");
            }
            System.out.println("}");
        }
    }

}