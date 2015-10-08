package JTAFinder.UnmatchedEndpoint;

import JTASyntax.Program;
import JTASyntax.Receive;
import JTASyntax.Send;

import java.util.*;

public class UmEPMatchGenerator {

    private final Program program;
    private final Map<Receive, List<Send>> match_table = new HashMap<>();
    private final Map<Send, List<Receive>> pattern_match = new HashMap<>();

    public UmEPMatchGenerator(Program program) {
        this.program = program;
        generateMatch();
    }

    public Map<Receive, List<Send>> getMatch_table () {
        return Collections.unmodifiableMap(match_table);
    }

    public Map<Send, List<Receive>> getPattern_match () {
        return Collections.unmodifiableMap(pattern_match);
    }

    public void generateMatch() {
        List<Receive>[] recvlist = new ArrayList[program.size()];
        List<Send>[][] sendlist = new ArrayList[program.size()][program.size()];

        //store the rlist and slist of each process
        for (JTASyntax.Process process : program) {
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

                            if (!pattern_match.containsKey(s)) {
                                pattern_match.put(s, new LinkedList<Receive>());
                            }
                            pattern_match.get(s).add(r);
                        }
                    }
                }
                //add the list with r to the match table
                if (!sendlistforR.isEmpty())
                    match_table.put(r, sendlistforR);
            }
        }
    }
}
