// Define a grammar called Hello

lexer grammar JTALexer;

@header {
package Parser;
}


Thread     : ('Thread' | 'thread');
Read       : ('Read' | 'read');
Mutate     : ('Mutate' | 'mutate');
Recv       : ('Recv' | 'recv');
Send       : ('Send' | 'send');
Wait       : ('Wait' | 'wait');
//Identifier : (('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | Number)*) | Number;
Identifier : Number;
Space      : ' '+ | '\t' ;
LineBreak  : ('\r')? '\n' | 'r' | 'n';
Number     : ('-')? Digit+ ;
Digit      : '0'..'9' ;
