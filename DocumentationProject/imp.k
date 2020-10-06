/**Lecture: Slides
*Bibliography: Chapter 4 from the BOOK
*Lecture demo:
*/
module IMP-SYNTAX
  syntax AExp ::= Int // builtin
                | Id  // builtin
                | String // builtin
                | "read" "(" ")"
                | Id "(" Params ")" [strict(2)]
                | "++" Id
                > left:
                  AExp "*" AExp [left]
                | AExp "/" AExp [left]
                > left:
                  AExp "+" AExp [left, strict]
                | AExp "-" AExp [left]
                | "(" AExp ")"  [bracket]

  syntax BExp ::= Bool // builtin
                | "!" BExp
                | BExp "&&" BExp [non-assoc]
                | AExp "<=" AExp [strict]
                | AExp "<"  AExp
                | "(" BExp ")" [bracket]

  syntax Block ::= "{" Stmt "}"
                 | "{" "}"

  syntax Stmt ::= Block
                | "int" Id ";"
                | "print" "(" AExp ")" ";" [strict]
                | Id "=" AExp ";"  [strict(2)]
                | "if" BExp Block "else" Block [strict(1)]
                | "while" BExp Block
                | "return" ";"
                | "return" AExp ";"  [strict]
                > Stmt Stmt [right]

  syntax Pgm ::= List{Decl, ";"}
  syntax Decl ::= FunDecl | VarDecl
  syntax VarDecl ::= "int" Id
  syntax FunDecl ::= "function" Id "(" VarDecls ")" Block
  syntax VarDecls ::= List{VarDecl, ","}
  syntax Params ::= List{AExp, ","}  [strict]
endmodule

module IMP
  imports IMP-SYNTAX

  syntax Val ::= Bool | Int | String
  syntax Vals ::= List{Val, ","}
  syntax KResult ::= Val | Vals 
  syntax AExp ::= Val
  syntax Params ::= Vals 

  syntax KItem ::= "execute"
  configuration <k> $PGM:Pgm ~> execute </k>
                <env> .Map </env>
                <store> .Map </store>
                <stack> .List </stack>
                <fstack> .List </fstack>
                <in stream="stdin"> .List </in>
                <out stream="stdout"> .List </out>

				
/**
* Some comentary to see it work
* The documentation shoud be shown
*/
  // trigger main 
  syntax Id ::= "main" [token]
  rule .Pgm ~> execute => main(.Params)

  rule I1:Int <= I2:Int => I1 <=Int I2
  rule I1:Int + I2:Int => I1 +Int I2

  rule S1:Stmt S2:Stmt => S1 ~> S2
  rule D:Decl ; Ds:Pgm => D ~> Ds 

  // local declarations
  rule <k> int X:Id ; => . ...</k>
       <env> M:Map => M[X <- !L:Int] </env>
       <store> M':Map (.Map => !L |-> 0) </store>

  // global declarations
  rule <k> (int X:Id => .) ...</k>
       <env> Env:Map => Env[X <- !L:Int] </env>
       <store> Mem:Map => Mem[!L <- 0] </store>

  // function declaration
  syntax Lambda ::= "lambda" "(" VarDecls "," Block ")"
  rule <k> (function F:Id (VD:VarDecls) B => .) ...</k>
       <env> Env:Map => Env[F <- !L:Int] </env>
       <store> Mem:Map => Mem[!L <- lambda(VD, B)] </store>

   rule <k> (X:Id = V:Int ; => .)  ...</k>
        <env>... X |-> L:Int  ...</env>
        <store>... L |-> (_ => V) ...</store>

   rule <k> (X:Id => V) ...</k>
        <env>... X |-> L:Int ...</env>
        <store>... L |-> V:Int ...</store>

  rule if true  B1:Block else B2:Block => B1 
  rule if false B1:Block else B2:Block => B2 

  // syntax KItem ::= "restoreEnv"
  // rule <k> { S:Stmt } => S ~> restoreEnv ...</k>
  //      <env> Env </env>
  //      <stack> (.List => ListItem(Env)) ...</stack>

  // rule <k> (restoreEnv => .) ...</k>
  //      <env> _ => Env:Map </env>
  //      <stack> (ListItem(Env) => .List) ...</stack>

  rule { S } => S 
  rule { } => .

  rule while B:BExp { S:Stmt } =>
       if B { S while B { S } } else { }

  // increment
  rule <k> ( ++ X:Id => V +Int 1) ...</k>
       <env>... X |-> L ...</env>
       <store>... (L:Int |-> (V:Int=> V +Int 1)) ...</store>

  // read
  rule <k> (read() => V) ...</k>
       <in> (ListItem(V:Int) => .List) ...</in>

  // print
  syntax Printable ::= Int | String 
  syntax AExp ::= Printable
  rule <k> (print(V:Printable); => .) ...</k>
       <out>... (.List => ListItem(V)) </out>

  // function call utils
  syntax KItem ::= "saveEnv"
  syntax KItem ::= "mkDecl" "(" VarDecls "," Params ")" 

  // function call
  rule <k> F:Id ( PA:Params ) => saveEnv ~> mkDecl(PF, PA) ~> B ...</k>
       <env>... F |-> L:Int ...</env>
       <store>... L |-> lambda(PF:VarDecls, B:Block) ...</store>

  rule <k> saveEnv => . ...</k>
       <env> Env:Map </env>
       <fstack> (.List => ListItem(Env)) ...</fstack>

  rule mkDecl(.VarDecls, .Params) => .
  rule <k> mkDecl( (int X:Id, Ds) , (V, Ps) ) => mkDecl(Ds, Ps) ...</k>
       <env> Env:Map => Env[X <- !L:Int] </env>
       <store> Mem:Map => Mem[!L <- V] </store>

  rule <k> return V:Int ; => V ...</k>
       <env> _ => Env </env>
       <fstack> (ListItem(Env) => .List) ...</fstack>

endmodule
//@ Program pgm.imp with global variables and recursive functions:

int a ;
int b ;

function sum(int n) {
  if (n <= 0) {
    return 0;
  } else {
    return n + sum(n + (-1));
  }
};

function main() {
  b = 10;
  a = sum(b);
  print(a);
  print("\n");
}
//@ Result:

-$ krun pgm.imp 
55
<generatedTop> <k> . </k> <env> b |-> 2 a |-> 1
sum |-> 3 main |-> 4 </env> <store> 1 |-> 55
2 |-> 10 3 |-> lambda ( ( int n ) , .VarDecls ,
{ if ( n <= 0 ) { return 0 ; } else { return n + sum ( ( n + -1 ) , .VarDecls ) ; } } )
4 |-> lambda ( .VarDecls , { b = 10 ; a = sum ( b , .VarDecls ) ; print ( a ) ; print ( "\n" ) ; } )
5 |-> 10 6 |-> 9 7 |-> 8 8 |-> 7 9 |-> 6 10 |-> 5 11 |-> 4 12 |-> 3 13 |-> 2 14 |-> 1 15 |-> 0
</store> <stack> .List </stack> <fstack> ListItem ( b |-> 2 a |-> 1 sum |-> 3 main |-> 4 ) </fstack> <in>
ListItem ( #buffer ( "" ) ) ListItem ( "on" ) ListItem ( #istream ( 0 ) ) </in> <out> ListItem
( #ostream ( 1 ) ) ListItem ( "on" ) ListItem ( #buffer ( "" ) ) </out> </generatedTop>