package edu.byu.cs.vv.match_generation;

import edu.byu.cs.vv.ast.Match;
import edu.byu.cs.vv.ast.Process;
import edu.byu.cs.vv.ast.Program;
import edu.byu.cs.vv.ast.operations.Receive;
import edu.byu.cs.vv.ast.operations.Send;
import edu.byu.cs.vv.util.QueueMap;

import java.util.*;

public class MatchPairGenerator implements IMatchPairGenerator {

    private Program program;

    @Override
    public Set<Match> getMatches(Program input) {
        this.program = input;
        Set<Match> matches = new TreeSet<>();
        for (Process recvProcess : program) {
            Map<Integer, QueueMap<Integer, Send>> sendMap = new HashMap<>();
            for (Process sendProcess : program) {
                QueueMap<Integer, Send> tagMap = new QueueMap<>();
                Collection<Send> sends = sendProcess.getSendMap().get(recvProcess.rank);
                for (Send send : sends) {
                    tagMap.add(send.tag, send);
                }
                sendMap.put(sendProcess.rank, tagMap);
            }
            matches.addAll(getMatchesForProcess(recvProcess, sendMap));
        }
        return matches;
    }

    private Set<Match> getMatchesForProcess(Process receiver, Map<Integer, QueueMap<Integer, Send>> sendMap) {
        Set<Match> matches = new HashSet<>();
        int[][] counters = new int[program.size()][program.numberTags];
        for(Receive recv : receiver.getReceiveList()) {
            if (recv.isSourceWildcard() && recv.isTagWildcard()) {
                for (Integer sender : sendMap.keySet()) {
                    for (Integer tag : sendMap.get(sender).keySet()) {
                        int send_count = ++counters[sender][tag];
                        List<Send> sends = sendMap.get(sender).get(tag).take(send_count);
                        for (Send match : sends) {
                            matches.add(new Match(match, recv));
                        }
                    }
                }
            } else if (recv.isSourceWildcard()) {
                for (Integer sender : sendMap.keySet()) {
                    int tag = recv.tag;
                    int send_count = ++counters[sender][tag];
                    List<Send> sends = sendMap.get(sender).get(tag).take(send_count);
                    for (Send match : sends) {
                        matches.add(new Match(match, recv));
                    }
                }
            } else if (recv.isTagWildcard()) {
                int sender = recv.src;
                for(Integer tag : sendMap.get(sender).keySet()) {
                    int send_count = ++counters[sender][tag];
                    List<Send> sends = sendMap.get(sender).get(tag).take(send_count);
                    for (Send match : sends) {
                        matches.add(new Match(match, recv));
                    }
                }
            } else {
                Send consumed = sendMap.get(recv.src).get(recv.tag).remove();
                matches.add(new Match(consumed, recv));

                int sender = recv.src;
                int tag = recv.tag;
                int send_count = counters[sender][tag];
                List<Send> sends = sendMap.get(sender).get(tag).take(send_count);
                for (Send match : sends) {
                    matches.add(new Match(match, recv));
                }
            }
        }
        return matches;
    }

}
