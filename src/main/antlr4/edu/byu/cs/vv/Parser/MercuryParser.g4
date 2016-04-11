parser grammar MercuryParser;

options {
tokenVocab=MercuryLexer;
}

program      : thread+ EOF;

thread       : threadHeader
               ( threadBody | nt_threadBody );

threadHeader : Thread Identifier;

threadBody   : ( operation )*;

operation    : ( send | receive | barrier );

send         : ( Send endpoint tag communicator );

receive      : ( Recv endpoint tag communicator );

barrier      : Wait communicator;

endpoint     : Identifier;

tag          : Identifier;

communicator : Identifier;


nt_threadBody : (nt_operation)*;
nt_operation  : ( nt_send | nt_receive | nt_barrier );
nt_send       : ( Send endpoint endpoint );
nt_receive    : ( Recv endpoint endpoint );
nt_barrier    : ( Wait communicator );
