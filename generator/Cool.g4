grammar Cool;
@header {
    package generator;
}
program: (classdef';')+ EOF;
classdef: CLASS className=TYPE (INHERITS classParent=TYPE)? '{' (feature ';')* '}';
feature: (methodDec | fieldDec);
expr: ID '<-' expr
    | expr ('@' TYPE)?'.'ID '(' (expr (','expr)* )? ')' // method call
    | IF expr THEN expr ELSE expr FI // if
    | WHILE expr LOOP expr POOL // while loop
    | '{' (expr ';' )+ '}' // block
    | LET fieldDec (',' fieldDec)* IN expr // let 
    | CASE expr OF (ID ':' TYPE '=>' expr ';')+ ESAC // case
    | NEW TYPE // new
    | '~' expr // negation of int
    | ISVOID expr // isvoid
    | expr '+' expr
    | expr '-' expr
    | expr '*' expr
    | expr '/' expr
    | expr '<' expr
    | expr '<=' expr
    | expr '=' expr
    | NOT expr 
    | '(' expr ')' // parentheses
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
            returnType=TYPE '{' methodBody=expr+ '}';

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
NOT: 'not';
TRUE: 'true';
FALSE: 'false';

// types start uppercase
TYPE: [A-Z] [_0-9A-Za-z]*;
// identifiers start with lowercase
ID: [a-z] [_0-9A-Za-z]*;
STRING: '"' (ESC | ~ ["\\])* '"';
INTEGER: [0-9]+;

// escape sequences
fragment ESC: '\\' (["\\/bfnrt]);

LINECOMMENT: '--' (~ '\n')* '\n'? -> skip;
WS: [ \t\r\n\f]+ -> skip;
