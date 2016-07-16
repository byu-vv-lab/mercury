parser grammar CTPParser;

options {
tokenVocab=CTPLexer;
}

program      : OpenBlock (OpenBlock thread CloseBlock)+ CloseBlock EOF;

thread       : Thread (operation)*;

operation    : OpenBlock (send | isend | receive | ireceive | block | barrier) CloseBlock;

send         : Send Communicator Process Tag;

isend        : ISend Communicator Process Tag;

receive      : Recv Communicator Process Tag;

ireceive     : IRecv Communicator Process Tag;

barrier      : Barrier Communicator;

block        : Block Identifier;
