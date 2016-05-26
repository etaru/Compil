// UPP.java

import java.util.*;

/**************************************/
/* Arithmetic and boolean expressions */
/**************************************/

abstract class UPPExpr {

    abstract RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                            ArrayList<String> globals, PRegister reg,
                            RTLInst succ);

    PRegister getPRegister (ArrayList<Pair<String,PRegister>> locals) {
        return new PRegister();
    }//getPRegister

    public abstract String toString() ;

}//UPPExpr

class UPPCte extends UPPExpr {

    int val;

    UPPCte (int val) {
        this.val = val;
    }//UPPCte

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        return new RTLCte(reg, val, succ);
    }//toRTL

    public String toString() {
        return Integer.toString(val) ;
    }//toString

}//UPPCte

class UPPTrue extends UPPExpr {

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        return new RTLCte(reg, 1, succ);
    }//toRTL

     public String toString() {
        return "true" ;
    }//toString

}//UPPTrue

class UPPFalse extends UPPExpr {

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        return new RTLCte(reg, 0, succ);
    }//toRTL

    public String toString() {
        return "false" ;
    }//toString

}//UPPFalse

class UPPVar extends UPPExpr {

    String name;

    UPPVar (String name) {
        this.name = name;
    }//UPPVar

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        return succ;
    }//toRTL

    PRegister getPRegister (ArrayList<Pair<String,PRegister>> locals) {
        for (Pair<String,PRegister> e : locals)
            if (e.left.equals(name))
                return e.right;
        throw new RuntimeException();
    }//getPRegister

    public String toString() {
        return name.toString() ;
    }//toString

}//UPPVar

class UPPGVar extends UPPExpr {

    String name;

    UPPGVar (String name) {
        this.name = name;
    }//UPPGVar

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        return new RTLGetGVar(reg,name,succ);
    }//toRTL

    public String toString() {
        return name.toString() ;
    }//toString

}//UPPGVar

abstract class UPPUnOp extends UPPExpr {

    UPPExpr e;

}//UPPUnOp

class UPPNot extends UPPUnOp {

    UPPNot (UPPExpr e) {
        this.e = e;
    }//UPPNot

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE = e.getPRegister(locals);
        RTLInst xor = new RTLXOri(regE, reg, succ);
        return e.toRTL(locals, globals, regE, xor);
    }//toRTL

    public String toString() {
        return "not(" + e.toString() + ")" ;
    }//toString

}//UPPNot

abstract class UPPBinOp extends UPPExpr {

    UPPExpr e1, e2;

}//UPPBinOp

class UPPAdd extends UPPBinOp {

    UPPAdd (UPPExpr e1, UPPExpr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//UPPAdd

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE1 = e1.getPRegister(locals);
        PRegister regE2 = e2.getPRegister(locals);
        RTLInst add = new RTLAdd(regE1,regE2,reg,succ);
        RTLInst ne2 = e2.toRTL(locals,globals,regE2,add);
        return e1.toRTL(locals,globals,regE1,ne2);
    }//toRTL

    public String toString() {
        return e1.toString() + "+" + e2.toString() ;
    }//toString

}//UPPAdd

class UPPSub extends UPPBinOp {

    UPPSub (UPPExpr e1, UPPExpr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//UPPSub

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE1 = e1.getPRegister(locals);
        PRegister regE2 = e2.getPRegister(locals);
        RTLInst sub = new RTLSub(regE1,regE2,reg,succ);
        RTLInst ne2 = e2.toRTL(locals,globals,regE2,sub);
        return e1.toRTL(locals,globals,regE1,ne2);
    }//toRTL

    public String toString() {
        return e1.toString() + "-" + e2.toString() ;
    }//toString

}//UPPSub

class UPPMul extends UPPBinOp {

    UPPMul (UPPExpr e1, UPPExpr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//UPPMul

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE1 = e1.getPRegister(locals);
        PRegister regE2 = e2.getPRegister(locals);
        RTLInst mul = new RTLMul(regE1,regE2,reg,succ);
        RTLInst ne2 = e2.toRTL(locals,globals,regE2,mul);
        return e1.toRTL(locals,globals,regE1,ne2);
    }//toRTL

    public String toString() {
        return e1.toString() + "*" + e2.toString() ;
    }//toString

}//UPPMul

class UPPDiv extends UPPBinOp {

    UPPDiv (UPPExpr e1, UPPExpr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//UPPDiv

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE1 = e1.getPRegister(locals);
        PRegister regE2 = e2.getPRegister(locals);
        RTLInst div = new RTLDiv(regE1,regE2,reg,succ);
        RTLInst ne2 = e2.toRTL(locals,globals,regE2,div);
        return e1.toRTL(locals,globals,regE1,ne2);
    }//toRTL

    public String toString() {
        return e1.toString() + "/" + e2.toString() ;
    }//toString

}//UPPDiv

class UPPAnd extends UPPBinOp {

    UPPAnd (UPPExpr e1, UPPExpr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//UPPAnd

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE1 = e1.getPRegister(locals);
        PRegister regE2 = e2.getPRegister(locals);
        RTLInst and = new RTLAnd(regE1,regE2,reg,succ);
        RTLInst ne2 = e2.toRTL(locals,globals,regE2,and);
        return e1.toRTL(locals,globals,regE1,ne2);
    }//toRTL

    public String toString() {
        return e1.toString() + " && " + e2.toString() ;
    }//toString

}//UPPAnd

class UPPOr extends UPPBinOp {

    UPPOr (UPPExpr e1, UPPExpr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//UPPOr

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE1 = e1.getPRegister(locals);
        PRegister regE2 = e2.getPRegister(locals);
        RTLInst or = new RTLOr(regE1,regE2,reg,succ);
        RTLInst ne2 = e2.toRTL(locals,globals,regE2,or);
        return e1.toRTL(locals,globals,regE1,ne2);
    }//toRTL

    public String toString() {
        return e1.toString() + " || " + e2.toString() ;
    }//toString

}//UPPOr

class UPPLt extends UPPBinOp {

    UPPLt (UPPExpr e1, UPPExpr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//UPPLt

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE1 = e1.getPRegister(locals);
        PRegister regE2 = e2.getPRegister(locals);
        RTLInst cTrue = new RTLCte(reg, 1, succ);
        RTLInst cFalse = new RTLCte(reg, 0, succ);
        RTLInst branch = new RTLLt(regE1,regE2,cTrue,cFalse);
        RTLInst ne2 = e2.toRTL(locals,globals,regE2,branch);
        return e1.toRTL(locals,globals,regE1,ne2);
    }//toRTL

    public String toString() {
        return e1.toString() + " < " + e2.toString() ;
    }//toString

}//UPPLt

class UPPLe extends UPPBinOp {

    UPPLe (UPPExpr e1, UPPExpr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//UPPLe

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE1 = e1.getPRegister(locals);
        PRegister regE2 = e2.getPRegister(locals);
        RTLInst cTrue = new RTLCte(reg, 1, succ);
        RTLInst cFalse = new RTLCte(reg, 0, succ);
        RTLInst branch = new RTLLe(regE1,regE2,cTrue,cFalse);
        RTLInst ne2 = e2.toRTL(locals,globals,regE2,branch);
        return e1.toRTL(locals,globals,regE1,ne2);
    }//toRTL

    public String toString() {
        return e1.toString() + " <= " + e2.toString() ;
    }//toString

}//UPPLe

class UPPEq extends UPPBinOp {

    UPPEq (UPPExpr e1, UPPExpr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//UPPEq

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE1 = e1.getPRegister(locals);
        PRegister regE2 = e2.getPRegister(locals);
        RTLInst cTrue = new RTLCte(reg, 1, succ);
        RTLInst cFalse = new RTLCte(reg, 0, succ);
        RTLInst branch = new RTLEq(regE1,regE2,cTrue,cFalse);
        RTLInst ne2 = e2.toRTL(locals,globals,regE2,branch);
        return e1.toRTL(locals,globals,regE1,ne2);
    }//toRTL

    public String toString() {
        return e1.toString() + " = " + e2.toString() ;
    }//toString

}//UPPEq

class UPPNe extends UPPBinOp {

    UPPNe (UPPExpr e1, UPPExpr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//UPPNe

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE1 = e1.getPRegister(locals);
        PRegister regE2 = e2.getPRegister(locals);
        RTLInst cTrue = new RTLCte(reg, 1, succ);
        RTLInst cFalse = new RTLCte(reg, 0, succ);
        RTLInst branch = new RTLNe(regE1,regE2,cTrue,cFalse);
        RTLInst ne2 = e2.toRTL(locals,globals,regE2,branch);
        return e1.toRTL(locals,globals,regE1,ne2);
    }//toRTL

    public String toString() {
        return e1.toString() + " != " + e2.toString() ;
    }//toString

}//UPPNe

class UPPGe extends UPPBinOp {

    UPPGe (UPPExpr e1, UPPExpr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//UPPGe

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE1 = e1.getPRegister(locals);
        PRegister regE2 = e2.getPRegister(locals);
        RTLInst cTrue = new RTLCte(reg, 1, succ);
        RTLInst cFalse = new RTLCte(reg, 0, succ);
        RTLInst branch = new RTLGe(regE1,regE2,cTrue,cFalse);
        RTLInst ne2 = e2.toRTL(locals,globals,regE2,branch);
        return e1.toRTL(locals,globals,regE1,ne2);
    }//toRTL

    public String toString() {
        return e1.toString() + " >= " + e2.toString() ;
    }//toString

}//UPPGe

class UPPGt extends UPPBinOp {

    UPPGt (UPPExpr e1, UPPExpr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }//UPPGt

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regE1 = e1.getPRegister(locals);
        PRegister regE2 = e2.getPRegister(locals);
        RTLInst cTrue = new RTLCte(reg, 1, succ);
        RTLInst cFalse = new RTLCte(reg, 0, succ);
        RTLInst branch = new RTLGt(regE1,regE2,cTrue,cFalse);
        RTLInst ne2 = e2.toRTL(locals,globals,regE2,branch);
        return e1.toRTL(locals,globals,regE1,ne2);
    }//toRTL

    public String toString() {
        return e1.toString() + " > " + e2.toString() ;
    }//toString

}//UPPGt

class Alloc extends Callee {

    public String toString() {
        return "alloc";
    }//Alloc

class UPPFunCall extends UPPExpr {

    Callee callee;
    ArrayList<UPPExpr> args;

    UPPFunCall (Callee callee, ArrayList<UPPExpr> args) {
        this.callee = callee;
        this.args = args;
    }//FunCall

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        ArrayList<PRegister> regs = new ArrayList<PRegister>();
        for (UPPExpr e : args)
            regs.add(e.getPRegister(locals));
        RTLInst fun = new RTLFunCall(callee,regs,reg,succ);
        RTLInst acc = fun;
        for (int i = args.size() - 1; i >= 0; i--)
            acc = args.get(i).toRTL(locals,globals,regs.get(i),acc);
        return acc;
    }//toRTL

    public String toString() {
        return callee.toString() + args.toString() ;
    }//toString

}//FunCall

class UPPLoad extends UPPExpr {

    UPPExpr addr;

    UPPLoad (UPPExpr addr) {
        this.addr = addr;
    }//UPPLoad

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, PRegister reg, RTLInst succ) {
        PRegister regAddr = addr.getPRegister(locals);
        RTLInst load = new RTLLoad(regAddr,reg,succ);
        return addr.toRTL(locals,globals,regAddr,load);
    }//toRTL

    public String toString() {
        return "Read at this address: "+ addr.toString() ;
    }//toString

}//UPPLoad

/****************/
/* Instructions */
/****************/

abstract class UPPInst {

    abstract RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                            ArrayList<String> globals, RTLInst succ);

    public abstract String toString();

}//UPPInst

class UPPAssign extends UPPInst {

    String name;
    UPPExpr val;

    UPPAssign (String name, UPPExpr val) {
        this.name = name;
        this.val = val;
    }//UPPAssign

    PRegister assoc (ArrayList<Pair<String,PRegister>> locals) {
        for (Pair<String,PRegister> e : locals)
            if (e.left.equals(name))
                return e.right;
        throw new RuntimeException();
    }//assoc

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, RTLInst succ) {
        PRegister regVal = val.getPRegister(locals);
        try {
            PRegister reg = assoc(locals);
            RTLInst move = new RTLMove(regVal,reg,succ);
            return val.toRTL(locals,globals,regVal,move);
        }
        catch (RuntimeException e) {
            RTLInst gvar = new RTLSetGVar(name,regVal,succ);
            return val.toRTL(locals,globals,regVal,gvar);
        }
    }//toRTL

     public String toString() {
        return name.toString() + " := " + val.toString() ;
    }//toString

}//UPPAssign

class UPPStore extends UPPInst {

    UPPExpr addr, val;

    UPPStore (UPPExpr addr, UPPExpr val) {
        this.addr = addr;
        this.val = val;
    }//UPPStore

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, RTLInst succ) {
        PRegister regAddr = addr.getPRegister(locals);
        PRegister regVal = val.getPRegister(locals);
        RTLInst store = new RTLStore(regAddr,regVal,succ);
        RTLInst nval = val.toRTL(locals,globals,regVal,store);
        return addr.toRTL(locals,globals,regAddr,nval);
    }//toRTL

    public String toString() {
        return "Stored at this address: "+ addr.toString() +" the value "+ val.toString() ;
    }//toString

}//UPPStore

class UPPCond extends UPPInst {

    UPPExpr cond;
    UPPInst i1, i2;

    UPPCond (UPPExpr cond, UPPInst i1, UPPInst i2) {
        this.cond = cond;
        this.i1 = i1;
        this.i2 = i2;
    }//UPPCond

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, RTLInst succ) {
        PRegister regCond = cond.getPRegister(locals);
        RTLInst ni1 = i1.toRTL(locals,globals,succ);
        RTLInst ni2 = i2.toRTL(locals,globals,succ);
        RTLGtz gtz = new RTLGtz(regCond,ni1,ni2);
        RTLInst ncond = cond.toRTL(locals,globals,regCond,gtz);
        return ncond;
    }//toRTL

     public String toString() {
        return "if " + cond.toString() + " then " + i1.toString() + " else " + i2.toString() ;
    }//toString

}//UPPCond

class UPPWhile extends UPPInst {

    UPPExpr cond;
    UPPInst i;

    UPPWhile (UPPExpr cond, UPPInst i) {
        this.cond = cond;
        this.i = i;
    }//UPPWhile

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, RTLInst succ) {
        PRegister regCond = cond.getPRegister(locals);
        RTLGtz gtz = new RTLGtz(regCond,null,succ);
        RTLInst ncond = cond.toRTL(locals,globals,regCond,gtz);
        RTLInst ni = i.toRTL(locals,globals,ncond);
        gtz.succ1 = ni;
        return ncond;
    }//toRTL

    public String toString() {
        return "while " + cond.toString() + " do " + i.toString() ;
    }//toString

}//UPPWhile

class UPPProcCall extends UPPInst {

    Callee callee;
    ArrayList<UPPExpr> args;

    UPPProcCall (Callee callee, ArrayList<UPPExpr> args) {
        this.callee = callee;
        this.args = args;
    }//UPPProcCall

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, RTLInst succ) {
        ArrayList<PRegister> regs = new ArrayList<PRegister>();
        for (UPPExpr e : args) {
            regs.add(e.getPRegister(locals));
        }
        RTLInst proc = new RTLProcCall(callee,regs,succ);
        RTLInst acc = proc;
        for (int i = args.size() - 1; i >= 0; i--) {
            acc = args.get(i).toRTL(locals,globals,regs.get(i),acc);
        }
        return acc;
    }//toRTL

    public String toString() {
        return callee.toString() + args.toString() ;
    }//toString

}//UPPProcCall
    
class UPPSkip extends UPPInst {

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, RTLInst succ) {
        return succ;
    }//toRTL

    public String toString() {
        return "skip" ;
    }//toString

}//UPPSkip

class UPPSeq extends UPPInst {

    UPPInst i1, i2;

    UPPSeq (UPPInst i1, UPPInst i2) {
        this.i1 = i1;
        this.i2 = i2;
    }//UPPSeq

    RTLInst toRTL (ArrayList<Pair<String,PRegister>> locals,
                   ArrayList<String> globals, RTLInst succ) {
        RTLInst ni2 = i2.toRTL(locals,globals,succ);
        return i1.toRTL(locals,globals,ni2);
    }//toRTL

    public String toString() {
        return i1.toString() + "; " + i2.toString() ;
    }//toString        

}//UPPSeq

/***************************************/
/* Definitions of functions/procedures */
/***************************************/

abstract class UPPDef {

    String name;
    ArrayList<String> args, locals;
    UPPInst code;

    abstract RTLDef toRTL (ArrayList<String> globals);

    public abstract String toString() ;

}//UPPDef

class UPPFun extends UPPDef {

    UPPFun (String name, ArrayList<String> args, ArrayList<String> locals,
            UPPInst code) {
        this.name = name;
        this.args = args;
        this.locals = locals;
        this.code = code;
    }//UPPFun

    RTLDef toRTL (ArrayList<String> globals) {
        ArrayList<PRegister> regArgs = new ArrayList<PRegister>();
        ArrayList<PRegister> regLocals = new ArrayList<PRegister>();
        ArrayList<Pair<String,PRegister>> regTrans =
            new ArrayList<Pair<String,PRegister>>();
        PRegister regRet = null;
        for (String e : args) {
            PRegister reg = new PRegister ();
            Pair<String,PRegister> p = new Pair<String,PRegister>(e,reg);
            regArgs.add(reg);
            regLocals.add(reg);
            regTrans.add(p);
        }
        for (String e : locals) {
            PRegister reg = new PRegister ();
            Pair<String,PRegister> p = new Pair<String,PRegister>(e,reg);
            regLocals.add(reg);
            regTrans.add(p);
            if (e.equals(name))
                regRet = reg;
        }
        RTLInst body = code.toRTL(regTrans,globals,new RTLEnd());
        return new RTLFun(name,regArgs,regLocals,regRet,body);
    }//toRTL

    public String toString() {
        return name.toString() + "(" + args.toString() + ") var " + locals.toString() + " " + code.toString() ;
    }//toString

}//UPPFun

class UPPProc extends UPPDef {

    UPPProc (String name, ArrayList<String> args, ArrayList<String> locals,
             UPPInst code) {
        this.name = name;
        this.args = args;
        this.locals = locals;
        this.code = code;
    }//UPPProc

    RTLDef toRTL (ArrayList<String> globals) {
        ArrayList<PRegister> regArgs = new ArrayList<PRegister>();
        ArrayList<PRegister> regLocals = new ArrayList<PRegister>();
        ArrayList<Pair<String,PRegister>> regTrans = new ArrayList<Pair<String,PRegister>>();
        for (String e : args) {
            PRegister reg = new PRegister ();
            Pair<String,PRegister> p = new Pair<String,PRegister>(e,reg);
            regArgs.add(reg);
            regLocals.add(reg);
            regTrans.add(p);
        }
        for (String e : locals) {
            PRegister reg = new PRegister ();
            Pair<String,PRegister> p = new Pair<String,PRegister>(e,reg);
            regLocals.add(reg);
            regTrans.add(p);
        }
        RTLInst body = code.toRTL(regTrans,globals,new RTLEnd());
        return new RTLProc(name,regArgs,regLocals,body);
    }//toRTL

    public String toString() {
        return name.toString() + "(" + args.toString() + ") var " + locals.toString() + " " + code.toString() ;
    }//toString

}//UPPProc

/************/
/* Programs */
/************/

class UPPProg {

    ArrayList<String> globals;
    ArrayList<UPPDef> defs;
    UPPInst code;

    UPPProg (ArrayList<String> globals, ArrayList<UPPDef> defs, UPPInst code) {
        this.globals = globals;
        this.defs = defs;
        this.code = code;
    }//UPPProg

    RTLProg toRTL () {
        ArrayList<String> args = new ArrayList<String>();
        ArrayList<String> locals = new ArrayList<String>();
        UPPDef mainProc = new UPPProc("_main",args,locals,code);
        defs.add(mainProc);
        ArrayList<RTLDef> ndefs = new ArrayList<RTLDef>();
        for (UPPDef e : defs) {
            ndefs.add(e.toRTL(globals));
            PRegister.reset();
        }
        return new RTLProg(globals,ndefs);
    }//toRTL

    public String toString() {
        return   "var " + globals.toString() + " " + defs.toString() + " " + code.toString() ;
    }//toString

}//UPPProg
