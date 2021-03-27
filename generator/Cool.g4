grammar Cool;
@header {
    package generator;
}
program: (classdef';')+ EOF;
classdef: CLASS className=TYPE (INHERITS classParent=TYPE)? '{' (feature ';')* '}';
feature: (methodDec | fieldDec);
expr: ID '<-' expr
    | expr ('@' TYPE)?'.'ID '(' (expr (','expr)* )? ')'
    | IF expr THEN expr ELSE expr FI
    | WHILE expr LOOP expr POOL
    | nestedStmnt='{' expr+ '}'
    | LET fieldDec (',' fieldDec)* IN expr ';'
    | CASE expr OF (ID ':' TYPE '=>' expr ';')+ ESAC
    | NEW TYPE
    | ISVOID expr
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
    | TRUE
    | FALSE
    ;

formal: parameterName=ID ':' parameterType=TYPE;
fieldDec: fieldName=ID ':' fieldType=TYPE ('<-' expr)?;
methodDec: methodName=ID '(' ((parameterName+=ID ':' parameterType+=TYPE) 
            (',' parameterName+=ID ':' parameterType+=TYPE)* )? ')' ':' 
            returnType=TYPE '{' methodBody=expr '}';

CLASS: 'class';
INHERITS: 'inherits';
IF: 'if';
THEN: 'then';
ELSE: 'else';
FI: 'fi';
WHILE: 'while';
LOOP: 'loop';
POOL: 'pool';
CASE: 'case';
ESAC: 'esac';
OF: 'of';
LET: 'let';
IN: 'in';
NEW: 'new';
ISVOID: 'isvoid';
TRUE: 'true';
FALSE: 'false';

// types start uppercase
TYPE: [A-Z] [_0-9A-Za-z]*;
ID: [a-z] [_0-9A-Za-z]*;
STRING: '"' (ESC | ~ ["\\])* '"';
INTEGER: [0-9]+;

// escape sequences
fragment ESC: '\\' (["\\/bfnrt]);

LINECOMMENT: '--' (~ '\n')* '\n'? -> skip;
WS: [ \t\r\n\f]+ -> skip;
