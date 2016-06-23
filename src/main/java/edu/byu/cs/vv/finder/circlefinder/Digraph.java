package edu.byu.cs.vv.finder.circlefinder;

import edu.byu.cs.vv.ast.operations.Operation;
import edu.byu.cs.vv.ast.operations.Receive;
import edu.byu.cs.vv.ast.operations.Send;
import edu.byu.cs.vv.ast.operations.Wait;
import edu.byu.cs.vv.ast.Process;
import edu.byu.cs.vv.ast.Program;

import java.util.*;

public class Digraph {

    private class ProcessGraph {
        public final List<Operation> vertices;
        public final Map<Operation, Set<Operation>> HB;

        public ProcessGraph(List<Operation> vertices, Map<Operation, Set<Operation>> HB) {
            this.vertices = vertices;
            this.HB = HB;
        }
    }

    public Program program;
    public final List<Operation> vList;
    public final Map<Operation, Set<Operation>> eTable;

    public Digraph(Program p, Map<Receive, List<Send>> match_table) {
        program = p;
        vList = new ArrayList<>();
        eTable = new HashMap<>();

        for (Process process : p) {
            ProcessGraph graph = graphProcess(process);
            vList.addAll(graph.vertices);
            eTable.putAll(graph.HB);
        }

        for (Operation op : vList) {
            if (!(op instanceof Receive))
                continue;

            Receive r = (Receive) op;
            if (!match_table.containsKey(r))
                continue;

            for (Send s : match_table.get(r)) {
                if (!vList.contains(s))
                    continue;

                if (!eTable.containsKey(s))
                    eTable.put(s, new HashSet<>());

                eTable.get(s).add(r);
            }
        }
    }

    public Set<Operation> adj(Operation vertex) {
        if (!eTable.containsKey(vertex))
            return null;
        return eTable.get(vertex);
    }

    public int size() {
        return vList.size();
    }

    private ProcessGraph graphProcess(Process process) {
        List<Operation> vertices = new ArrayList<>();
        Map<Operation, Set<Operation>> HB = new HashMap<>();
        Map<Wait, List<Receive>> witnessedR = new HashMap<>();

        Map<Integer, Send> firsts = new HashMap<>();
        Receive lastr = null;
        boolean wildcardissued = false;

        for (Operation op : process) {

            if (op instanceof Receive) {
                Receive r = (Receive) op;
                if (lastr != null) {
                    // we abandon the hb relation for a wildcard receive and a following deterministic receive
                    // because we do not consider this receive as a vertex in graph
                    if (r.src != -1 && wildcardissued)
                        continue;
                    HB.get(lastr).add(r);
                }
                // TODO: will r be added to vertices if it is a non-blocking receive?
                // but the edge of match relation is still added to edges
                // what if a wait witnesses multiple receives?
                // how to add those match relations to the edges and only add one wait to the vertices?
                vertices.add(r);
                // assume a program has only blocking receives or only non-blocking receives
                if (r.isBlock) {
                    lastr = r;
                    if (!HB.containsKey(lastr))
                        HB.put(lastr, new HashSet<>());
                } else {
                    //record this receive and its nw
                    Wait nw = r.nearestWait;
                    if (!witnessedR.containsKey(nw))
                        witnessedR.put(nw, new ArrayList<>());
                    witnessedR.get(nw).add(r);
                }
                if (r.src == -1) {
                    wildcardissued = true;
                }
                firsts.clear();

            } else if (op instanceof Wait) {

                Wait wait = (Wait) op;
                if (wait.forR) {
                    List<Receive> wR = witnessedR.get(wait);
                    if (wR != null) {
                        for (Receive r : wR) {
                            if (lastr != null) {
                                HB.get(lastr).add(r);
                                lastr = r;
                            } else {
                                lastr = r;
                                if (!HB.containsKey(lastr))
                                    HB.put(lastr, new HashSet<>());
                            }
                        }
                    }
                }

            } else if (op instanceof Send) {

                Send s = (Send) op;
                if (lastr == null || firsts.containsKey(s.dest))
                    continue;
                //add HB for lastr and s iff s is the first send with dest
                vertices.add(s);
                HB.get(lastr).add(s);
                firsts.put(s.dest, s);
            }
        }
        return new ProcessGraph(vertices, HB);
    }

}
