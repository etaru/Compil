// projetErickTaru.java



/*INTEGER*/

abstract class ExprArith {

    abstract int eval ();

}//ExprArith


class Cte extends ExprArith {

    int val;

    Cte (int val) {
        this.val = val;
    }//CteExpr

    int eval () {
        return val;
    }//eval

}//Cte

class Inv extends ExprArith {

    ExprArith e;

    Inv (ExprArith e) {
        this.e = e;
    }//Inv

    int eval () {
        return -e.eval();
    }//eval

}//Inv



/*BOOLEAN*/

abstract class ExprBool {

    abstract boolean eval ();

}//ExprBool


class Bool extends ExprBool {

	boolean b;

	Bool (boolean b) {
		this.b = b;
	}//Bool

	boolean eval () {
		return b;
	}//eval

}//Bool

class InvBool extends ExprBool {

    boolean b;

    InvBool (boolean b) {
        this.b = b;
    }//InvBool

    boolean eval () {
        return (!b);
    }//eval

}//Bool




/*TAKES 2 INT, RETURNS 1 INT*/

abstract class IntOp extends ExprArith {

    ExprArith e1, e2;

}//IntOp


class Add extends IntOp {

    Add (ExprArith e1, ExprArith e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//Add

    int eval () {
        return e1.eval() + e2.eval();
    }//eval

}//Add

class Sub extends IntOp {

    Sub (ExprArith e1, ExprArith e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//Sub

    int eval () {
        return e1.eval() - e2.eval();
    }//eval

}//Sub

class Mul extends IntOp {

    Mul (ExprArith e1, ExprArith e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//Mul

    int eval () {
        return e1.eval() * e2.eval();
    }//eval

}//Mul

class Div extends IntOp {

    Div (ExprArith e1, ExprArith e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//Div

    int eval () {
        return e1.eval() / e2.eval();
    }//eval

}//Div




/*TAKES 2 INT, RETURNS 1 BOOL*/

abstract class IntBool extends ExprBool {

    ExprArith e1, e2;

}//IntBool


class Inf extends IntBool {

    Inf (ExprArith e1, ExprArith e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//Inf

    boolean eval () {
        return (e1.eval() < e2.eval());
    }//eval

}//Inf

class InfEq extends IntBool {

    InfEq (ExprArith e1, ExprArith e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//InfEq

    boolean eval () {
        return (e1.eval() <= e2.eval());
    }//eval

}//InfEq

class Eq extends IntBool {

    Eq (ExprArith e1, ExprArith e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//Inf

    boolean eval () {
        return (e1.eval() == e2.eval());
    }//eval

}//Eq

class Neq extends IntBool {

    Neq (ExprArith e1, ExprArith e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//Neq

    boolean eval () {
        return (e1.eval() != e2.eval());
    }//eval

}//Neq

class SupEq extends IntBool {

    SupEq (ExprArith e1, ExprArith e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//SupEq

    boolean eval () {
        return (e1.eval() >= e2.eval());
    }//eval

}//SupEq

class Sup extends IntBool {

    Sup (ExprArith e1, ExprArith e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//Sup

    boolean eval () {
        return (e1.eval() > e2.eval());
    }//eval

}//Sup



/*TAKES 2 BOOL, RETURNS 1 BOOL*/

abstract class BinOp extends ExprBool {

    ExprBool e1, e2;

}//BinOp

class And extends BinOp {

    And (ExprBool e1, ExprBool e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//And

    boolean eval () {
        return (e1.eval() && e2.eval());
    }//eval

}//And

class Or extends BinOp {

    Or (ExprBool e1, ExprBool e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//Or

    boolean eval () {
        return (e1.eval() || e2.eval());
    }//eval

}//Or
