parser grammar JTAParser;

@header {
package Parser;
}
options {
tokenVocab=JTALexer;
}

program : thread+ EOF;

thread     : Thread Space+ Identifier LineBreak
             (operation)* LineBreak*;

operation  : (mutate | read | receive | send | block) LineBreak?;

mutate     : Mutate Space+ Identifier;

read       : Read Space+ Identifier;

receive    : Recv Space+ Identifier Space+ Identifier;

send       : Send Space+ Identifier Space+ Identifier;

block      : Wait Space+ Identifier;
