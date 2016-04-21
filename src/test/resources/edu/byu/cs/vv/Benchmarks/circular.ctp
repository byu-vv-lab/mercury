//  Send: String event, Process process, int rank, int src, int dest, Recv match, int value, boolean isBlock, Wait nw
//  Recv: String event, Process process, int rank, int src, int dest, Send match, boolean isBlock, Wait nw

Thread 0
Send	1	4
Recv	-1	0
Send	1	2

Thread 1
Recv	-1	1
Recv	-1	1
Send	2	3

Thread 2
Recv	-1	2
Send	0	5