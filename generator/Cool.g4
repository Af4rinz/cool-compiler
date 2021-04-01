grammar Cool;
@header {
    package generator;
}

program: (classdef';')+ EOF;

classdef: CLASS className=TYPE (INHERITS classParent=TYPE)? '{' (feature ';')* '}';

feature: (methodDec | fieldDec);

expr: expr ('@' TYPE)?'.'ID '(' (expr (','expr)* )? ')' #objMethodCall
    | ID '(' (expr (',' expr)* )? ')' # ownMethodCall
    | IF expr THEN expr ELSE expr FI #if
    | WHILE expr LOOP expr POOL #while
    | '{' (expr ';' )+ '}' #block
    | LET fieldDec (',' fieldDec)* IN expr #let 
    | CASE expr OF (ID ':' TYPE '=>' expr ';')+ ESAC #case
    | NEW TYPE #new
    | '~' expr #negate
    | ISVOID expr #isvoid
    | expr '+' expr #plus
    | expr '-' expr #minus
    | expr '*' expr #multiply
    | expr '/' expr #divide
    | expr '<' expr #less
    | expr '<=' expr #lessequal
    | expr '=' expr #equal
    | NOT expr #not
    | '(' expr ')' #parantheses
    | ID #id
    | INTEGER #int
    | STRING #string
    | TRUE #true
    | FALSE #false
    | ID '<-' expr #assignment
    ;

formal: parameterName=ID ':' parameterType=TYPE;

fieldDec: fieldName=ID ':' fieldType=TYPE ('<-' expr)?;

methodDec: methodName=ID '(' ((parameterName+=ID ':' parameterType+=TYPE) 
            (',' parameterName+=ID ':' parameterType+=TYPE)* )? ')' ':' 
            returnType=TYPE '{' methodBody=expr '}';

// Lexers
CLASS: C L A S S;
INHERITS: I N H E R I T S;
IF: I F;
THEN: T H E N;
ELSE: E L S E;
FI: F I;
WHILE: W H I L E;
LOOP: L O O P;
POOL: P O O L;
CASE: C A S E;
ESAC: E S A C;
OF: O F;
LET: L E T;
IN: I N;
NEW: N E W;
ISVOID: I S V O I D;
NOT: N O T;
TRUE: 't' R U E;
FALSE: 'f' A L S E;

COMMENT: '(*' (COMMENT | .)*? '*)' -> skip;
LINECOMMENT: '--' (~ '\n')* '\n'? -> skip;

// types start uppercase
TYPE: [A-Z] [_0-9A-Za-z]*;
// identifiers start with lowercase
ID: [a-z] [_0-9A-Za-z]*;

STRING: '"' (ESC | ~ ["\\])* '"';
INTEGER: [0-9]+;

// escape sequences
fragment ESC: '\\' (["\\/bfnrt]);

WS: [ \t\r\n\f]+ -> skip;

// letter fragments
fragment A: [aA];
fragment B: [bB];
fragment C: [cC];
fragment D: [dD];
fragment E: [eE];
fragment F: [fF];
fragment G: [gG];
fragment H: [hH];
fragment I: [iI];
fragment J: [jJ];
fragment K: [kK];
fragment L: [lL];
fragment M: [mM];
fragment N: [nN];
fragment O: [oO];
fragment P: [pP];
fragment R: [rR];
fragment S: [sS];
fragment T: [tT];
fragment U: [uU];
fragment V: [vV];
fragment W: [wW];
fragment Y: [yY];

