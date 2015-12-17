package Finder.CircleFinder;

import Syntax.Operations.Operation;
import Syntax.Operations.Receive;

import java.util.*;

public class Johnson {
    public List<Map<Integer, Receive>> patterns;
    private Stack<Operation> stack;
    Digraph G;
    private int leastvertex;

    /**
     * Computes the strong components of the digraph <tt>Graph</tt>.
     *
     * @param Graph the digraph
     */
    public Johnson(Digraph Graph) {
        G = Graph;
        stack = new Stack<>();
        patterns = new LinkedList<>();
        find();
    }

    public Set<Operation> leastSCC(int lowerbound) {
        TarjanSCC tarjan = new TarjanSCC(G, lowerbound);
        leastvertex = tarjan.leastvertex;
        //the result could be null
        return tarjan.leastSCC;
    }

    public List<CirclePattern> getPatterns () {
        List<CirclePattern> patt = new ArrayList<>();
        for (Map<Integer, Receive> map : patterns) {
            patt.add(new CirclePattern(map));
        }
        return patt;
    }

    public void find() {
        Map<Operation, Boolean> blocked = new HashMap<>();
        Map<Operation, List<Operation>> blockedNodes = new HashMap<>();
        stack.empty();
        int s = 0;
        while (s < G.vList.size() - 1) {
            Set<Operation> leastSCC = leastSCC(s);
            if (leastSCC != null) {
                s = leastvertex;
                for (Operation v : leastSCC) {
                    blocked.put(v, false);
                    blockedNodes.put(v, new LinkedList<Operation>());
                }

                boolean dummy = circuit(leastSCC, s, s, stack, blocked, blockedNodes);
                s++;
            } else {
                s = G.vList.size() - 1;
            }
        }

    }

    public boolean circuit(Set<Operation> dg,
                           int v,
                           int s,
                           Stack<Operation> stack,
                           Map<Operation, Boolean> blocked,
                           Map<Operation, List<Operation>> blockedNodes) {
        if (dg == null) {
            return false;
        }
        if (dg.size() == 0) {
            return false;
        }
        boolean f = false;
        Operation vertex = G.vList.get(v);
        Operation startvertex = G.vList.get(s);
        stack.push(vertex);
        blocked.put(vertex, true);
        //all the operation in leastSCC that v can connect
        HashSet<Operation> adj_leastSCC = new HashSet<>();
        continuepoint:
        for (Operation w : G.eTable.get(vertex)) {
            //only vertex in leastSCC is considered
            if (!dg.contains(w))
                continue continuepoint;
            adj_leastSCC.add(w);
            if (w == startvertex) {
                stack.push(startvertex);
                //pattern generation based on the detected circuit
                Hashtable<Integer, Receive> pattern = new Hashtable<Integer, Receive>();
                for (Operation op : stack) {
                    //add the first receive of each process in stack to the pattern
                    if (!(op instanceof Receive))
                        continue;
                    Receive r = (Receive) op;
                    if (!pattern.containsKey(r.dest)) {
                        pattern.put(r.dest, r);
                        continue;
                    }
                    //only keep the receive with lower rank
                    if (pattern.get(r.dest).rank > r.rank) {
                        pattern.put(r.dest, r);
                    }
                }
                if (pattern.size() > 1)
                    patterns.add(pattern);
                stack.pop();
                f = true;
            } else {
                if (!blocked.get(w)) {
                    if (circuit(dg, G.vList.indexOf(w), s, stack, blocked, blockedNodes)) {
                        f = true;
                    }
                }
            }
        }
        if (f) {
            unblock(vertex, blocked, blockedNodes);
        } else {
            for (Operation w : adj_leastSCC) {
                if (!blockedNodes.get(w).contains(vertex)) {
                    blockedNodes.get(w).add(vertex);
                }
            }
        }
        stack.pop();
        return f;
    }

    //recursion 
    public void unblock(Operation v,
                        Map<Operation, Boolean> blocked,
                        Map<Operation, List<Operation>> blockedNodes) {
        blocked.put(v, false);
        while (blockedNodes.get(v).size() > 0) {
            Operation w = blockedNodes.get(v).remove(0);
            if (blocked.get(w)) {
                unblock(w, blocked, blockedNodes);
            }
        }
    }

    public void printCircles(List<Map<Integer, Set<Operation>>> circles) {
        for (int i = 0; i < circles.size(); i++) {
            System.out.print("Circle[" + i + "]:");
            System.out.println(circles.get(i));
        }
    }
}
