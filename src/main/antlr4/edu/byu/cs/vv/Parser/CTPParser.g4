parser grammar CTPParser;

options {
tokenVocab=CTPLexer;
}

program      : thread+ EOF;

thread       : threadHeader
               (operation)*;

branch       : IF OpenBlock Identifier Comparator Number CloseBlock
               OpenBlock (operation)* CloseBlock
               OpenBlock (operation)* CloseBlock;

threadHeader : Thread Identifier;

operation    : (mutate | read | receive | send | block);

mutate       : Mutate Identifier;

read         : Read Identifier;

receive      : Recv Identifier Identifier;

send         : Send Identifier Identifier;

block        : Wait Identifier;
