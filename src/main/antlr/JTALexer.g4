lexer grammar JTALexer;

@header {
package Parser;
}

Comment    : '//' .*? LineBreak+ -> channel(HIDDEN);
IF         : ('IF' | 'If' | 'if');
Thread     : ('Thread' | 'thread');
Read       : ('Read' | 'read');
Mutate     : ('Mutate' | 'mutate');
Recv       : ('Recv' | 'recv');
Send       : ('Send' | 'send');
Wait       : ('Wait' | 'wait');
//Identifier : (('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | Number)*) | Number;
Identifier : Number;
Space      : WhiteSpace+ -> channel(HIDDEN);
WhiteSpace : ' ' | '\t';
Comparator : ('==' | '<' | '<=' | '>' | '>=');
OpenBlock  : '(';
CloseBlock : ')';
LineBreak  : ('\r')? '\n' -> channel(HIDDEN);
Number     : ('-')? Digit+ ;
Digit      : '0'..'9' ;
