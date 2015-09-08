parser grammar ThreadedParser;

options { tokenVocab=ThreadedLexer; }

program : thread+ EOF;

thread : header (expression)*;

header : Thread Identifier LineBreak+;

expression : (mutate | read | wait | signal) LineBreak+;

mutate : Mutate Identifier;

read   : Read Identifier;

wait   : Wait Identifier;

signal : Signal Identifier;
