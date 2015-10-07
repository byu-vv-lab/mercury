package JTAFinder;

import JTASyntax.Program;
import JTASyntax.Receive;
import JTASyntax.Send;
import JTASyntax.Process;

import java.util.*;

public abstract class AbstractFinder {
    boolean result;
    Schedule schedule;
    String log;

    Program program;
    MatchGenerator matchGenerator;

    AbstractFinder(Program p) {
        program = p;
        matchGenerator = new MatchGenerator(p);
    }

    /**
     * Checks whether the program supplied is correct under the given finder
     * @return True if the program is verifiably correct, false otherwise
     */
    public abstract boolean verify();
    public abstract String printResults();

    public boolean getResult() {
        return result;
    }

    Schedule getSchedule() {
        return schedule;
    }

    static int totalNUM (Map<Integer, Map<Integer, Integer>> map, int src, int dest) {
        if(map.containsKey(dest)) {
            if(map.get(dest).containsKey(src)) {
                return map.get(dest).get(src);
            }
        }
        return 0;
    }

    protected boolean isPattern () { return false; }

//    boolean mayDeadlock(Receive deadlockPoint) {
//        int src = deadlockPoint.src;
//        int dest = deadlockPoint.dest;
//
//        int sendNum = 0;
//        int recvNum = 0;
//        if(sendNums.containsKey(dest)) {
//            if(sendNums.get(dest).containsKey(src)) {
//                sendNum = sendNums.get(dest).get(src);
//            }
//        }
//        if(recvNums.containsKey(dest)) {
//            if(recvNums.get(dest).containsKey(-1)) {
//                recvNum += recvNums.get(dest).get(-1);
//            }
//
//            //only deterministic receive needs to do this
//            if(src != -1 && recvNums.get(dest).containsKey(src)) {
//                recvNum += recvNums.get(dest).get(src);
//            }
//        }
//        return sendNum <= recvNum;
//    }
//
//    boolean checkAvailable(Receive r) {
//        int src = r.src;
//        int dest = r.dest;
//
//        //more sends than receives with identical src and dest
//        if(src!=-1) { //Deterministic receive
//            //two conditions should be satisfied:
//            return (totalNUM(sendNums, src, dest) > totalNUM(recvNums, src, dest))//S(c->0) > R(c)
//                    && (totalNUM(sendNums, -1, dest) >  //S(c->0) > R(*) + R(c)
//                    totalNUM(recvNums, -1, dest) + totalNUM(recvNums, src, dest));
//        } else {
//            //for wildcard receive, the number of send(*->dest) has to be greater than the number
//            //of {recv(*->dest), recv(c1->dest), ...}
//            if(recvNums.containsKey(dest)) {
//                int totalAvailableRecvs = 0;
//                for(Integer rsrc : recvNums.get(dest).keySet()) {
//                    totalAvailableRecvs += recvNums.get(dest).get(rsrc);
//                }
//                //should use ">" other than ">=" because
//                //at least one send is available for the next receive
//                return (totalNUM(sendNums,src,dest) > totalAvailableRecvs);
//            }
//            return false;
//        }
//    }
//
//    boolean reachBlockPoints()

//    void init()

//    void resetProgram()

//    boolean scheduling(Program program, Process patternProcess, UnmatchedEP_Pattern pattern) UnmatchedEP
//    boolean scheduling(Program program, Map<Integer, Recv> patternProcesses) Circle

//    boolean schedulable(UnmatchedEP_Pattern pattern) UnmatchedEP
//    boolean schedulable(Map<Integer, Recv> patternProcesses) Circle

//    void moveBlockPoints(Set<Integer> reachableRanks, Map<Integer, Recv> patternProcesses) Circle
//    void moveBlockPoints (Set<Integer> reachableRanks, UnmatchedEP_Pattern pattern) UnmatchedEP

//    Set<Integer> feasible (UnmatchedEP_Pattern pattern) UnmatchedEP
//    Set<Integer> feasible(Map<Integer, Recv> patternProcesses) Circle

}
