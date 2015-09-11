parser grammar JTAParser;

@header {
package Parser;
}
options {
tokenVocab=JTALexer;
}

program : thread+ EOF;

thread     : Thread Space+ Identifier LineBreak
             (expression)* LineBreak*;

expression : (mutate | read | receive | send) LineBreak?;

mutate     : Mutate Space+ Identifier;

read       : Read Space+ Identifier;

receive    : Recv Space+ Identifier Space+ Identifier;

send       : Send Space+ Identifier Space+ Identifier;
