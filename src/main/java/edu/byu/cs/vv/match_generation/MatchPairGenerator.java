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
            matches.addAll(getMatches(recvProcess, sendMap));
        }
        return matches;
    }

    private Set<Match> getMatches(Process receiver, Map<Integer, QueueMap<Integer, Send>> sendMap) {
        Set<Match> matches = new HashSet<>();
        int[][] counters = new int[program.size()][program.numberTags];
        for(Receive recv : receiver.getReceiveList()) {
            if (recv.isSourceWildcard() && recv.isTagWildcard()) {
                for (int[] arr : counters) {
                    for (int val : arr) {
                        val++;
                        // TODO: Add matches
                    }
                }
            } else if (recv.isSourceWildcard()) {
                for (int[] arr : counters) {
                    arr[recv.tag]++;
                    // TODO: Add matches
                }
            } else if (recv.isTagWildcard()) {
                for(int arr : counters[recv.src]) {
                    arr++;
                    // TODO: Add matches
                }
            } else {
                Send match = sendMap.get(recv.src).get(recv.tag).remove();
                matches.add(new Match(match, recv));
            }
            // TODO: Add matches
        }
        return matches;
    }

}
