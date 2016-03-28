lexer grammar MercuryLexer;

Comment    : '//' .*? LineBreak+ -> channel(HIDDEN);
Thread     : ('Thread' | 'thread');
Recv       : ('Recv' | 'recv');
Send       : ('Send' | 'send');
Wait       : ('Wait' | 'wait');
Identifier : (('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | Digit)*) | PosInt;
Comparator : ('==' | '<' | '<=' | '>' | '>=');
Space      : WhiteSpace+ -> channel(HIDDEN);
WhiteSpace : ' ' | '\t';
LineBreak  : ('\r')? '\n' -> channel(HIDDEN);
Integer    : ('-')? PosInt ;
PosInt     : Digit+ ;
Digit      : '0'..'9' ;
