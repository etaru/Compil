// projetErickTaru.g4

grammar projetErickTaru;



// A type is
Type : ('Number' | 'Bool' | 'Array');



// Expressions
e : ( k | X | additionExpr | boolExpr | targetExpr | newArray);



//Instructions
i : (X ':=' e | tabExpr ':=' e | 'if' e 'then' e 'else' e | 'while' e 'do' i | targetExpr);



// Functions/Procedures
d : 'f((' X ':' Type ')*)' '[:' Type ']' 'var' '(' X ':' Type ')*' i;



// Programs
p : 'var(' X ':' Type ')*' d'*' i;



// This will be the entry point of our parser.
expr : (Type | X | boolExpr | atomExpr | additionExpr | multiplyExpr);

// tabExpr
tabExpr : e '[' e ']' ;

// newArray
newArray : 'new' 'array' 'of' Type '[' e ']' ;

// boolExpr
boolExpr : Bool | '('logicExpr')' | compExpr | 'not' boolExpr ;



// Addition and subtraction have the lowest precedence.
additionExpr : multiplyExpr ('+' multiplyExpr | '-' multiplyExpr ) * ;

// Multiplication and division have a higher precedence.
multiplyExpr : atomExpr ('*' atomExpr | '/' atomExpr)* ;

/* An expression atom is the smallest part of an expression: a number. Or 
   when we encounter parenthesis, we're making a recursive call back to the
   rule 'additionExpr'. As you can see, an 'atomExpr' has the highest
   precedence. */
atomExpr : Number | '(' additionExpr ')' | '-' atomExpr ;



// Comparison expression
compExpr : atomExpr ('<' atomExpr | '>' atomExpr |'<=' atomExpr | '<' atomExpr | '>=' atomExpr | '=' atomExpr | '!=' atomExpr )*;



// ANd-OR expression
logicExpr : compExpr('And' compExpr | 'Or' compExpr)*;



// Call target
targetExpr: ('read' | 'write' | 'f') ('('e')')*;



// A number is an integer value
Number : ('0'..'9')+ ;

// A boolean is a string value
Bool : ('true' | 'false');

// Array of T
Array : '['Type']';

// A constant k is a number or boolean
k : (Number | Bool);

// Variable
X : [a-z]+ ;



// We're going to ignore all white space characters
WS : [ \t\r\n]+ -> skip ;
