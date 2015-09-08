// Define a grammar called Hello
lexer grammar ThreadedLexer;

Thread     : 'thread' Space+;
Read       : 'read' Space+;
Mutate     : 'mutate' Space+;
Wait       : 'wait' Space+;
Signal     : 'signal' Space+;
Identifier : ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | Number)* ;
Space      : ' ' | '\t' ;
LineBreak  : ('\r')? '\n' | 'r' | 'n';
Number     : Digit+ ;
Digit      : '0'..'9' ;
