grammar Cool;
program: (classdef';')+ EOF;
classdef: 'class' TYPE ('inherits' TYPE)? '{' (feature ';')* '}';
feature: ID '(' (formal (',' formal)* )? ')' ':' TYPE '{' expr '}'
    | ID ':' TYPE ('<-' expr)?;
expr: ID '<-' expr
    | expr ('@' TYPE)?'.'ID '(' (expr (','expr)* )? ')'
    | 'if' expr 'then' expr 'else' expr 'fi'
    | 'while' expr 'loop' expr 'pool'
    | '{' expr+ '}' 
    | 'let' ID ':' TYPE ('<-' expr)? (','ID ':' TYPE ('<-' expr)? )* 'in' expr
    | 'case' expr 'of' (ID ':' TYPE '=>' expr ';')+ 'esac'
    | 'new' TYPE
    | 'isvoid' expr
    | expr '+' expr
    | expr '-' expr
    | expr '*' expr
    | expr '/' expr
    | '~' expr
    | expr '<' expr
    | expr '<=' expr
    | expr '=' expr
    | 'not' expr
    | '(' expr ')'
    | ID
    | INTEGER
    | STRING
    | 'true'
    | 'false'
    ;

formal: ID ':' TYPE;

// types start uppercase
TYPE: [A-Z] [_0-9A-Za-z]*;
ID: [a-z] [_0-9A-Za-z]*;
STRING: '"' (ESC | ~ ["\\])* '"';
INTEGER: [0-9]+;

// escape sequences
fragment ESC: '\\' (["\\/bfnrt]);

LINECOMMENT: '--' (~ '\n')* '\n'? -> skip;
WS: [ \t\r\n\f]+ -> skip;
