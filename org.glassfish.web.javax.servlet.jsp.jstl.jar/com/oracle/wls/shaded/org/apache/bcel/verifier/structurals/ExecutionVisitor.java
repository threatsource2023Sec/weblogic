package com.oracle.wls.shaded.org.apache.bcel.verifier.structurals;

import com.oracle.wls.shaded.org.apache.bcel.classfile.Constant;
import com.oracle.wls.shaded.org.apache.bcel.classfile.ConstantDouble;
import com.oracle.wls.shaded.org.apache.bcel.classfile.ConstantFloat;
import com.oracle.wls.shaded.org.apache.bcel.classfile.ConstantInteger;
import com.oracle.wls.shaded.org.apache.bcel.classfile.ConstantLong;
import com.oracle.wls.shaded.org.apache.bcel.classfile.ConstantString;
import com.oracle.wls.shaded.org.apache.bcel.generic.AALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.AASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.ACONST_NULL;
import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.ANEWARRAY;
import com.oracle.wls.shaded.org.apache.bcel.generic.ARETURN;
import com.oracle.wls.shaded.org.apache.bcel.generic.ARRAYLENGTH;
import com.oracle.wls.shaded.org.apache.bcel.generic.ASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.ATHROW;
import com.oracle.wls.shaded.org.apache.bcel.generic.ArrayType;
import com.oracle.wls.shaded.org.apache.bcel.generic.BALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.BASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.BIPUSH;
import com.oracle.wls.shaded.org.apache.bcel.generic.CALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.CASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.CHECKCAST;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.D2F;
import com.oracle.wls.shaded.org.apache.bcel.generic.D2I;
import com.oracle.wls.shaded.org.apache.bcel.generic.D2L;
import com.oracle.wls.shaded.org.apache.bcel.generic.DADD;
import com.oracle.wls.shaded.org.apache.bcel.generic.DALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.DASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.DCMPG;
import com.oracle.wls.shaded.org.apache.bcel.generic.DCMPL;
import com.oracle.wls.shaded.org.apache.bcel.generic.DCONST;
import com.oracle.wls.shaded.org.apache.bcel.generic.DDIV;
import com.oracle.wls.shaded.org.apache.bcel.generic.DLOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.DMUL;
import com.oracle.wls.shaded.org.apache.bcel.generic.DNEG;
import com.oracle.wls.shaded.org.apache.bcel.generic.DREM;
import com.oracle.wls.shaded.org.apache.bcel.generic.DRETURN;
import com.oracle.wls.shaded.org.apache.bcel.generic.DSTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.DSUB;
import com.oracle.wls.shaded.org.apache.bcel.generic.DUP;
import com.oracle.wls.shaded.org.apache.bcel.generic.DUP2;
import com.oracle.wls.shaded.org.apache.bcel.generic.DUP2_X1;
import com.oracle.wls.shaded.org.apache.bcel.generic.DUP2_X2;
import com.oracle.wls.shaded.org.apache.bcel.generic.DUP_X1;
import com.oracle.wls.shaded.org.apache.bcel.generic.DUP_X2;
import com.oracle.wls.shaded.org.apache.bcel.generic.EmptyVisitor;
import com.oracle.wls.shaded.org.apache.bcel.generic.F2D;
import com.oracle.wls.shaded.org.apache.bcel.generic.F2I;
import com.oracle.wls.shaded.org.apache.bcel.generic.F2L;
import com.oracle.wls.shaded.org.apache.bcel.generic.FADD;
import com.oracle.wls.shaded.org.apache.bcel.generic.FALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.FASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.FCMPG;
import com.oracle.wls.shaded.org.apache.bcel.generic.FCMPL;
import com.oracle.wls.shaded.org.apache.bcel.generic.FCONST;
import com.oracle.wls.shaded.org.apache.bcel.generic.FDIV;
import com.oracle.wls.shaded.org.apache.bcel.generic.FLOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.FMUL;
import com.oracle.wls.shaded.org.apache.bcel.generic.FNEG;
import com.oracle.wls.shaded.org.apache.bcel.generic.FREM;
import com.oracle.wls.shaded.org.apache.bcel.generic.FRETURN;
import com.oracle.wls.shaded.org.apache.bcel.generic.FSTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.FSUB;
import com.oracle.wls.shaded.org.apache.bcel.generic.GETFIELD;
import com.oracle.wls.shaded.org.apache.bcel.generic.GETSTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO_W;
import com.oracle.wls.shaded.org.apache.bcel.generic.I2B;
import com.oracle.wls.shaded.org.apache.bcel.generic.I2C;
import com.oracle.wls.shaded.org.apache.bcel.generic.I2D;
import com.oracle.wls.shaded.org.apache.bcel.generic.I2F;
import com.oracle.wls.shaded.org.apache.bcel.generic.I2L;
import com.oracle.wls.shaded.org.apache.bcel.generic.I2S;
import com.oracle.wls.shaded.org.apache.bcel.generic.IADD;
import com.oracle.wls.shaded.org.apache.bcel.generic.IALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.IAND;
import com.oracle.wls.shaded.org.apache.bcel.generic.IASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.ICONST;
import com.oracle.wls.shaded.org.apache.bcel.generic.IDIV;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFEQ;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFGE;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFGT;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFLE;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFLT;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFNE;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFNONNULL;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFNULL;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ACMPEQ;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ACMPNE;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ICMPEQ;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ICMPGE;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ICMPGT;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ICMPLE;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ICMPLT;
import com.oracle.wls.shaded.org.apache.bcel.generic.IF_ICMPNE;
import com.oracle.wls.shaded.org.apache.bcel.generic.IINC;
import com.oracle.wls.shaded.org.apache.bcel.generic.ILOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.IMUL;
import com.oracle.wls.shaded.org.apache.bcel.generic.INEG;
import com.oracle.wls.shaded.org.apache.bcel.generic.INSTANCEOF;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESPECIAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.IOR;
import com.oracle.wls.shaded.org.apache.bcel.generic.IREM;
import com.oracle.wls.shaded.org.apache.bcel.generic.IRETURN;
import com.oracle.wls.shaded.org.apache.bcel.generic.ISHL;
import com.oracle.wls.shaded.org.apache.bcel.generic.ISHR;
import com.oracle.wls.shaded.org.apache.bcel.generic.ISTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.ISUB;
import com.oracle.wls.shaded.org.apache.bcel.generic.IUSHR;
import com.oracle.wls.shaded.org.apache.bcel.generic.IXOR;
import com.oracle.wls.shaded.org.apache.bcel.generic.JSR;
import com.oracle.wls.shaded.org.apache.bcel.generic.JSR_W;
import com.oracle.wls.shaded.org.apache.bcel.generic.L2D;
import com.oracle.wls.shaded.org.apache.bcel.generic.L2F;
import com.oracle.wls.shaded.org.apache.bcel.generic.L2I;
import com.oracle.wls.shaded.org.apache.bcel.generic.LADD;
import com.oracle.wls.shaded.org.apache.bcel.generic.LALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.LAND;
import com.oracle.wls.shaded.org.apache.bcel.generic.LASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.LCMP;
import com.oracle.wls.shaded.org.apache.bcel.generic.LCONST;
import com.oracle.wls.shaded.org.apache.bcel.generic.LDC;
import com.oracle.wls.shaded.org.apache.bcel.generic.LDC2_W;
import com.oracle.wls.shaded.org.apache.bcel.generic.LDC_W;
import com.oracle.wls.shaded.org.apache.bcel.generic.LDIV;
import com.oracle.wls.shaded.org.apache.bcel.generic.LLOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.LMUL;
import com.oracle.wls.shaded.org.apache.bcel.generic.LNEG;
import com.oracle.wls.shaded.org.apache.bcel.generic.LOOKUPSWITCH;
import com.oracle.wls.shaded.org.apache.bcel.generic.LOR;
import com.oracle.wls.shaded.org.apache.bcel.generic.LREM;
import com.oracle.wls.shaded.org.apache.bcel.generic.LRETURN;
import com.oracle.wls.shaded.org.apache.bcel.generic.LSHL;
import com.oracle.wls.shaded.org.apache.bcel.generic.LSHR;
import com.oracle.wls.shaded.org.apache.bcel.generic.LSTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.LSUB;
import com.oracle.wls.shaded.org.apache.bcel.generic.LUSHR;
import com.oracle.wls.shaded.org.apache.bcel.generic.LXOR;
import com.oracle.wls.shaded.org.apache.bcel.generic.MONITORENTER;
import com.oracle.wls.shaded.org.apache.bcel.generic.MONITOREXIT;
import com.oracle.wls.shaded.org.apache.bcel.generic.MULTIANEWARRAY;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEW;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEWARRAY;
import com.oracle.wls.shaded.org.apache.bcel.generic.NOP;
import com.oracle.wls.shaded.org.apache.bcel.generic.ObjectType;
import com.oracle.wls.shaded.org.apache.bcel.generic.POP;
import com.oracle.wls.shaded.org.apache.bcel.generic.POP2;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUTFIELD;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUTSTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.RET;
import com.oracle.wls.shaded.org.apache.bcel.generic.RETURN;
import com.oracle.wls.shaded.org.apache.bcel.generic.ReturnaddressType;
import com.oracle.wls.shaded.org.apache.bcel.generic.SALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.SASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.SIPUSH;
import com.oracle.wls.shaded.org.apache.bcel.generic.SWAP;
import com.oracle.wls.shaded.org.apache.bcel.generic.TABLESWITCH;
import com.oracle.wls.shaded.org.apache.bcel.generic.Type;
import com.oracle.wls.shaded.org.apache.bcel.generic.Visitor;

public class ExecutionVisitor extends EmptyVisitor implements Visitor {
   private Frame frame = null;
   private ConstantPoolGen cpg = null;

   private OperandStack stack() {
      return this.frame.getStack();
   }

   private LocalVariables locals() {
      return this.frame.getLocals();
   }

   public void setConstantPoolGen(ConstantPoolGen cpg) {
      this.cpg = cpg;
   }

   public void setFrame(Frame f) {
      this.frame = f;
   }

   public void visitAALOAD(AALOAD o) {
      this.stack().pop();
      Type t = this.stack().pop();
      if (t == Type.NULL) {
         this.stack().push(Type.NULL);
      } else {
         ArrayType at = (ArrayType)t;
         this.stack().push(at.getElementType());
      }

   }

   public void visitAASTORE(AASTORE o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().pop();
   }

   public void visitACONST_NULL(ACONST_NULL o) {
      this.stack().push(Type.NULL);
   }

   public void visitALOAD(ALOAD o) {
      this.stack().push(this.locals().get(o.getIndex()));
   }

   public void visitANEWARRAY(ANEWARRAY o) {
      this.stack().pop();
      this.stack().push(new ArrayType(o.getType(this.cpg), 1));
   }

   public void visitARETURN(ARETURN o) {
      this.stack().pop();
   }

   public void visitARRAYLENGTH(ARRAYLENGTH o) {
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitASTORE(ASTORE o) {
      this.locals().set(o.getIndex(), this.stack().pop());
   }

   public void visitATHROW(ATHROW o) {
      Type t = this.stack().pop();
      this.stack().clear();
      if (t.equals(Type.NULL)) {
         this.stack().push(Type.getType("Ljava/lang/NullPointerException;"));
      } else {
         this.stack().push(t);
      }

   }

   public void visitBALOAD(BALOAD o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitBASTORE(BASTORE o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().pop();
   }

   public void visitBIPUSH(BIPUSH o) {
      this.stack().push(Type.INT);
   }

   public void visitCALOAD(CALOAD o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitCASTORE(CASTORE o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().pop();
   }

   public void visitCHECKCAST(CHECKCAST o) {
      this.stack().pop();
      this.stack().push(o.getType(this.cpg));
   }

   public void visitD2F(D2F o) {
      this.stack().pop();
      this.stack().push(Type.FLOAT);
   }

   public void visitD2I(D2I o) {
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitD2L(D2L o) {
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitDADD(DADD o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.DOUBLE);
   }

   public void visitDALOAD(DALOAD o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.DOUBLE);
   }

   public void visitDASTORE(DASTORE o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().pop();
   }

   public void visitDCMPG(DCMPG o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitDCMPL(DCMPL o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitDCONST(DCONST o) {
      this.stack().push(Type.DOUBLE);
   }

   public void visitDDIV(DDIV o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.DOUBLE);
   }

   public void visitDLOAD(DLOAD o) {
      this.stack().push(Type.DOUBLE);
   }

   public void visitDMUL(DMUL o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.DOUBLE);
   }

   public void visitDNEG(DNEG o) {
      this.stack().pop();
      this.stack().push(Type.DOUBLE);
   }

   public void visitDREM(DREM o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.DOUBLE);
   }

   public void visitDRETURN(DRETURN o) {
      this.stack().pop();
   }

   public void visitDSTORE(DSTORE o) {
      this.locals().set(o.getIndex(), this.stack().pop());
      this.locals().set(o.getIndex() + 1, Type.UNKNOWN);
   }

   public void visitDSUB(DSUB o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.DOUBLE);
   }

   public void visitDUP(DUP o) {
      Type t = this.stack().pop();
      this.stack().push(t);
      this.stack().push(t);
   }

   public void visitDUP_X1(DUP_X1 o) {
      Type w1 = this.stack().pop();
      Type w2 = this.stack().pop();
      this.stack().push(w1);
      this.stack().push(w2);
      this.stack().push(w1);
   }

   public void visitDUP_X2(DUP_X2 o) {
      Type w1 = this.stack().pop();
      Type w2 = this.stack().pop();
      if (w2.getSize() == 2) {
         this.stack().push(w1);
         this.stack().push(w2);
         this.stack().push(w1);
      } else {
         Type w3 = this.stack().pop();
         this.stack().push(w1);
         this.stack().push(w3);
         this.stack().push(w2);
         this.stack().push(w1);
      }

   }

   public void visitDUP2(DUP2 o) {
      Type t = this.stack().pop();
      if (t.getSize() == 2) {
         this.stack().push(t);
         this.stack().push(t);
      } else {
         Type u = this.stack().pop();
         this.stack().push(u);
         this.stack().push(t);
         this.stack().push(u);
         this.stack().push(t);
      }

   }

   public void visitDUP2_X1(DUP2_X1 o) {
      Type t = this.stack().pop();
      Type u;
      if (t.getSize() == 2) {
         u = this.stack().pop();
         this.stack().push(t);
         this.stack().push(u);
         this.stack().push(t);
      } else {
         u = this.stack().pop();
         Type v = this.stack().pop();
         this.stack().push(u);
         this.stack().push(t);
         this.stack().push(v);
         this.stack().push(u);
         this.stack().push(t);
      }

   }

   public void visitDUP2_X2(DUP2_X2 o) {
      Type t = this.stack().pop();
      Type u;
      Type v;
      if (t.getSize() == 2) {
         u = this.stack().pop();
         if (u.getSize() == 2) {
            this.stack().push(t);
            this.stack().push(u);
            this.stack().push(t);
         } else {
            v = this.stack().pop();
            this.stack().push(t);
            this.stack().push(v);
            this.stack().push(u);
            this.stack().push(t);
         }
      } else {
         u = this.stack().pop();
         v = this.stack().pop();
         if (v.getSize() == 2) {
            this.stack().push(u);
            this.stack().push(t);
            this.stack().push(v);
            this.stack().push(u);
            this.stack().push(t);
         } else {
            Type w = this.stack().pop();
            this.stack().push(u);
            this.stack().push(t);
            this.stack().push(w);
            this.stack().push(v);
            this.stack().push(u);
            this.stack().push(t);
         }
      }

   }

   public void visitF2D(F2D o) {
      this.stack().pop();
      this.stack().push(Type.DOUBLE);
   }

   public void visitF2I(F2I o) {
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitF2L(F2L o) {
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitFADD(FADD o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.FLOAT);
   }

   public void visitFALOAD(FALOAD o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.FLOAT);
   }

   public void visitFASTORE(FASTORE o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().pop();
   }

   public void visitFCMPG(FCMPG o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitFCMPL(FCMPL o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitFCONST(FCONST o) {
      this.stack().push(Type.FLOAT);
   }

   public void visitFDIV(FDIV o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.FLOAT);
   }

   public void visitFLOAD(FLOAD o) {
      this.stack().push(Type.FLOAT);
   }

   public void visitFMUL(FMUL o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.FLOAT);
   }

   public void visitFNEG(FNEG o) {
      this.stack().pop();
      this.stack().push(Type.FLOAT);
   }

   public void visitFREM(FREM o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.FLOAT);
   }

   public void visitFRETURN(FRETURN o) {
      this.stack().pop();
   }

   public void visitFSTORE(FSTORE o) {
      this.locals().set(o.getIndex(), this.stack().pop());
   }

   public void visitFSUB(FSUB o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.FLOAT);
   }

   public void visitGETFIELD(GETFIELD o) {
      this.stack().pop();
      Type t = o.getFieldType(this.cpg);
      if (t.equals(Type.BOOLEAN) || t.equals(Type.CHAR) || t.equals(Type.BYTE) || t.equals(Type.SHORT)) {
         t = Type.INT;
      }

      this.stack().push((Type)t);
   }

   public void visitGETSTATIC(GETSTATIC o) {
      Type t = o.getFieldType(this.cpg);
      if (t.equals(Type.BOOLEAN) || t.equals(Type.CHAR) || t.equals(Type.BYTE) || t.equals(Type.SHORT)) {
         t = Type.INT;
      }

      this.stack().push((Type)t);
   }

   public void visitGOTO(GOTO o) {
   }

   public void visitGOTO_W(GOTO_W o) {
   }

   public void visitI2B(I2B o) {
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitI2C(I2C o) {
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitI2D(I2D o) {
      this.stack().pop();
      this.stack().push(Type.DOUBLE);
   }

   public void visitI2F(I2F o) {
      this.stack().pop();
      this.stack().push(Type.FLOAT);
   }

   public void visitI2L(I2L o) {
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitI2S(I2S o) {
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitIADD(IADD o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitIALOAD(IALOAD o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitIAND(IAND o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitIASTORE(IASTORE o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().pop();
   }

   public void visitICONST(ICONST o) {
      this.stack().push(Type.INT);
   }

   public void visitIDIV(IDIV o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitIF_ACMPEQ(IF_ACMPEQ o) {
      this.stack().pop();
      this.stack().pop();
   }

   public void visitIF_ACMPNE(IF_ACMPNE o) {
      this.stack().pop();
      this.stack().pop();
   }

   public void visitIF_ICMPEQ(IF_ICMPEQ o) {
      this.stack().pop();
      this.stack().pop();
   }

   public void visitIF_ICMPGE(IF_ICMPGE o) {
      this.stack().pop();
      this.stack().pop();
   }

   public void visitIF_ICMPGT(IF_ICMPGT o) {
      this.stack().pop();
      this.stack().pop();
   }

   public void visitIF_ICMPLE(IF_ICMPLE o) {
      this.stack().pop();
      this.stack().pop();
   }

   public void visitIF_ICMPLT(IF_ICMPLT o) {
      this.stack().pop();
      this.stack().pop();
   }

   public void visitIF_ICMPNE(IF_ICMPNE o) {
      this.stack().pop();
      this.stack().pop();
   }

   public void visitIFEQ(IFEQ o) {
      this.stack().pop();
   }

   public void visitIFGE(IFGE o) {
      this.stack().pop();
   }

   public void visitIFGT(IFGT o) {
      this.stack().pop();
   }

   public void visitIFLE(IFLE o) {
      this.stack().pop();
   }

   public void visitIFLT(IFLT o) {
      this.stack().pop();
   }

   public void visitIFNE(IFNE o) {
      this.stack().pop();
   }

   public void visitIFNONNULL(IFNONNULL o) {
      this.stack().pop();
   }

   public void visitIFNULL(IFNULL o) {
      this.stack().pop();
   }

   public void visitIINC(IINC o) {
   }

   public void visitILOAD(ILOAD o) {
      this.stack().push(Type.INT);
   }

   public void visitIMUL(IMUL o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitINEG(INEG o) {
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitINSTANCEOF(INSTANCEOF o) {
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitINVOKEINTERFACE(INVOKEINTERFACE o) {
      this.stack().pop();

      for(int i = 0; i < o.getArgumentTypes(this.cpg).length; ++i) {
         this.stack().pop();
      }

      if (o.getReturnType(this.cpg) != Type.VOID) {
         Type t = o.getReturnType(this.cpg);
         if (t.equals(Type.BOOLEAN) || t.equals(Type.CHAR) || t.equals(Type.BYTE) || t.equals(Type.SHORT)) {
            t = Type.INT;
         }

         this.stack().push((Type)t);
      }

   }

   public void visitINVOKESPECIAL(INVOKESPECIAL o) {
      if (o.getMethodName(this.cpg).equals("<init>")) {
         UninitializedObjectType t = (UninitializedObjectType)this.stack().peek(o.getArgumentTypes(this.cpg).length);
         if (t == Frame._this) {
            Frame._this = null;
         }

         this.stack().initializeObject(t);
         this.locals().initializeObject(t);
      }

      this.stack().pop();

      for(int i = 0; i < o.getArgumentTypes(this.cpg).length; ++i) {
         this.stack().pop();
      }

      if (o.getReturnType(this.cpg) != Type.VOID) {
         Type t = o.getReturnType(this.cpg);
         if (t.equals(Type.BOOLEAN) || t.equals(Type.CHAR) || t.equals(Type.BYTE) || t.equals(Type.SHORT)) {
            t = Type.INT;
         }

         this.stack().push((Type)t);
      }

   }

   public void visitINVOKESTATIC(INVOKESTATIC o) {
      for(int i = 0; i < o.getArgumentTypes(this.cpg).length; ++i) {
         this.stack().pop();
      }

      if (o.getReturnType(this.cpg) != Type.VOID) {
         Type t = o.getReturnType(this.cpg);
         if (t.equals(Type.BOOLEAN) || t.equals(Type.CHAR) || t.equals(Type.BYTE) || t.equals(Type.SHORT)) {
            t = Type.INT;
         }

         this.stack().push((Type)t);
      }

   }

   public void visitINVOKEVIRTUAL(INVOKEVIRTUAL o) {
      this.stack().pop();

      for(int i = 0; i < o.getArgumentTypes(this.cpg).length; ++i) {
         this.stack().pop();
      }

      if (o.getReturnType(this.cpg) != Type.VOID) {
         Type t = o.getReturnType(this.cpg);
         if (t.equals(Type.BOOLEAN) || t.equals(Type.CHAR) || t.equals(Type.BYTE) || t.equals(Type.SHORT)) {
            t = Type.INT;
         }

         this.stack().push((Type)t);
      }

   }

   public void visitIOR(IOR o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitIREM(IREM o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitIRETURN(IRETURN o) {
      this.stack().pop();
   }

   public void visitISHL(ISHL o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitISHR(ISHR o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitISTORE(ISTORE o) {
      this.locals().set(o.getIndex(), this.stack().pop());
   }

   public void visitISUB(ISUB o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitIUSHR(IUSHR o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitIXOR(IXOR o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitJSR(JSR o) {
      this.stack().push(new ReturnaddressType(o.physicalSuccessor()));
   }

   public void visitJSR_W(JSR_W o) {
      this.stack().push(new ReturnaddressType(o.physicalSuccessor()));
   }

   public void visitL2D(L2D o) {
      this.stack().pop();
      this.stack().push(Type.DOUBLE);
   }

   public void visitL2F(L2F o) {
      this.stack().pop();
      this.stack().push(Type.FLOAT);
   }

   public void visitL2I(L2I o) {
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitLADD(LADD o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitLALOAD(LALOAD o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitLAND(LAND o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitLASTORE(LASTORE o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().pop();
   }

   public void visitLCMP(LCMP o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitLCONST(LCONST o) {
      this.stack().push(Type.LONG);
   }

   public void visitLDC(LDC o) {
      Constant c = this.cpg.getConstant(o.getIndex());
      if (c instanceof ConstantInteger) {
         this.stack().push(Type.INT);
      }

      if (c instanceof ConstantFloat) {
         this.stack().push(Type.FLOAT);
      }

      if (c instanceof ConstantString) {
         this.stack().push(Type.STRING);
      }

   }

   public void visitLDC_W(LDC_W o) {
      Constant c = this.cpg.getConstant(o.getIndex());
      if (c instanceof ConstantInteger) {
         this.stack().push(Type.INT);
      }

      if (c instanceof ConstantFloat) {
         this.stack().push(Type.FLOAT);
      }

      if (c instanceof ConstantString) {
         this.stack().push(Type.STRING);
      }

   }

   public void visitLDC2_W(LDC2_W o) {
      Constant c = this.cpg.getConstant(o.getIndex());
      if (c instanceof ConstantLong) {
         this.stack().push(Type.LONG);
      }

      if (c instanceof ConstantDouble) {
         this.stack().push(Type.DOUBLE);
      }

   }

   public void visitLDIV(LDIV o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitLLOAD(LLOAD o) {
      this.stack().push(this.locals().get(o.getIndex()));
   }

   public void visitLMUL(LMUL o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitLNEG(LNEG o) {
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitLOOKUPSWITCH(LOOKUPSWITCH o) {
      this.stack().pop();
   }

   public void visitLOR(LOR o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitLREM(LREM o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitLRETURN(LRETURN o) {
      this.stack().pop();
   }

   public void visitLSHL(LSHL o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitLSHR(LSHR o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitLSTORE(LSTORE o) {
      this.locals().set(o.getIndex(), this.stack().pop());
      this.locals().set(o.getIndex() + 1, Type.UNKNOWN);
   }

   public void visitLSUB(LSUB o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitLUSHR(LUSHR o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitLXOR(LXOR o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.LONG);
   }

   public void visitMONITORENTER(MONITORENTER o) {
      this.stack().pop();
   }

   public void visitMONITOREXIT(MONITOREXIT o) {
      this.stack().pop();
   }

   public void visitMULTIANEWARRAY(MULTIANEWARRAY o) {
      for(int i = 0; i < o.getDimensions(); ++i) {
         this.stack().pop();
      }

      this.stack().push(o.getType(this.cpg));
   }

   public void visitNEW(NEW o) {
      this.stack().push(new UninitializedObjectType((ObjectType)o.getType(this.cpg)));
   }

   public void visitNEWARRAY(NEWARRAY o) {
      this.stack().pop();
      this.stack().push(o.getType());
   }

   public void visitNOP(NOP o) {
   }

   public void visitPOP(POP o) {
      this.stack().pop();
   }

   public void visitPOP2(POP2 o) {
      Type t = this.stack().pop();
      if (t.getSize() == 1) {
         this.stack().pop();
      }

   }

   public void visitPUTFIELD(PUTFIELD o) {
      this.stack().pop();
      this.stack().pop();
   }

   public void visitPUTSTATIC(PUTSTATIC o) {
      this.stack().pop();
   }

   public void visitRET(RET o) {
   }

   public void visitRETURN(RETURN o) {
   }

   public void visitSALOAD(SALOAD o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().push(Type.INT);
   }

   public void visitSASTORE(SASTORE o) {
      this.stack().pop();
      this.stack().pop();
      this.stack().pop();
   }

   public void visitSIPUSH(SIPUSH o) {
      this.stack().push(Type.INT);
   }

   public void visitSWAP(SWAP o) {
      Type t = this.stack().pop();
      Type u = this.stack().pop();
      this.stack().push(t);
      this.stack().push(u);
   }

   public void visitTABLESWITCH(TABLESWITCH o) {
      this.stack().pop();
   }
}
