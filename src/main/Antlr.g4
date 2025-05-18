grammar Antlr;

prog
    : stmt* EOF
    ;

stmt
    : assignment           # assignStmt
    | expr                 # exprStmt
    ;

assignment
    : ID EQ expr    // Genera AssignmentContext
    ;

expr
    : expr POW expr        # powExpr
    | expr MULT expr       # mulExpr
    | expr DIV expr        # divExpr
    | expr PLUS expr       # addExpr
    | expr MINUS expr      # subExpr
    | primary              # primaryExpr
    ;

primary
    : NUMBER               # number
    | ID                   # id
    | LP expr RP           # paren
    ;

// Tokens
EQ: '==';
LP: '(';
RP: ')';
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
POW: '^';

NUMBER: [0-9]+ ('.' [0-9]+)?;
ID: [a-zA-Z_][a-zA-Z0-9_]*;

WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;