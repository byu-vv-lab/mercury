package edu.byu.cs.vv.Finder;

import edu.byu.cs.vv.Syntax.Match;
import edu.byu.cs.vv.Syntax.Operations.Receive;
import edu.byu.cs.vv.Syntax.Operations.Send;
import edu.byu.cs.vv.Syntax.Program;
import edu.byu.cs.vv.Syntax.Process;

import java.util.*;

public abstract class AbstractMatchGenerator {

    protected final Program program;
    private Map<Receive, List<Send>> matchTable = new HashMap<>();
    private Map<Send, List<Receive>> patternMatch = new HashMap<>();

    protected AbstractMatchGenerator(Program program) {
        this.program = program;
        generateTables();
    }

    /**
     * TODO: Specify Ors/Ands for matches
     * @return
     */
    public abstract Collection<Collection<Match>> generateMatches();

    public Map<Receive, List<Send>> getMatchTable() { return Collections.unmodifiableMap(matchTable); }

    public Map<Send, List<Receive>> getPatternMatch() { return Collections.unmodifiableMap(patternMatch); }

    protected void generateTables() {
        List<Receive>[] recvlist = new ArrayList[program.size()];
        List<Send>[][] sendlist = new ArrayList[program.size()][program.size()];

        //store the rlist and slist of each process
        for (Process process : program) {
            recvlist[process.rank] = new ArrayList<>(process.rlist);
            for (Integer dest : process.smap.keySet()) {
                sendlist[dest][process.rank] = new ArrayList<>(process.smap.get(dest));
            }
        }

        for (int i = 0; i < recvlist.length; i++) {
            Iterator<Receive> ite_r = recvlist[i].iterator();

            //calculate # of sends from any source to i
            int sendstoi = 0;
            for (int j = 0; j < sendlist[i].length; j++) {
                if (sendlist[i][j] != null)
                    sendstoi += sendlist[i][j].size();
            }

            while (ite_r.hasNext()) {
                Receive r = ite_r.next();

                //	 System.out.println("recv in thread i = " + i + ": " + r.exp +" with rank " + r.rank);
                LinkedList<Send> sendlistforR = new LinkedList<Send>();
                for (int j = 0; j < sendlist[i].length; j++) {
                    //no sends in sendlist[i][j]
                    if (sendlist[i][j] == null)
                        continue;
                    Iterator<Send> ite_s = sendlist[i][j].iterator();
                    while (ite_s.hasNext()) {
                        Send s = ite_s.next();
                        //compare and set
                        if ((r.rank >= s.rank) //rule 1
                                && (r.rank <= s.rank + (sendstoi - sendlist[i][j].size())) //rule2
                                && (r.src == -1 || r.src == s.src)) { //either it is a wildcard receive or the endpoints commit
                            sendlistforR.add(s);

                            //generate pattern_match

                            if (!patternMatch.containsKey(s)) {
                                patternMatch.put(s, new LinkedList<Receive>());
                            }
                            patternMatch.get(s).add(r);
                        }
                    }
                }
                //add the list with r to the match table
                if (!sendlistforR.isEmpty())
                    matchTable.put(r, sendlistforR);
            }
        }
    }
}
