package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

import com.oracle.wls.shaded.org.apache.bcel.generic.BranchHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.CHECKCAST;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.DLOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.DSTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFEQ;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFNE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESPECIAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.Instruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionConstants;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.LocalVariableGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.NEW;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.FlowList;

public final class RealType extends NumberType {
   // $FF: synthetic field
   static Class class$java$lang$Double;

   protected RealType() {
   }

   public String toString() {
      return "real";
   }

   public boolean identicalTo(Type other) {
      return this == other;
   }

   public String toSignature() {
      return "D";
   }

   public com.oracle.wls.shaded.org.apache.bcel.generic.Type toJCType() {
      return com.oracle.wls.shaded.org.apache.bcel.generic.Type.DOUBLE;
   }

   public int distanceTo(Type type) {
      if (type == this) {
         return 0;
      } else {
         return type == Type.Int ? 1 : Integer.MAX_VALUE;
      }
   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type) {
      if (type == Type.String) {
         this.translateTo(classGen, methodGen, (StringType)type);
      } else if (type == Type.Boolean) {
         this.translateTo(classGen, methodGen, (BooleanType)type);
      } else if (type == Type.Reference) {
         this.translateTo(classGen, methodGen, (ReferenceType)type);
      } else if (type == Type.Int) {
         this.translateTo(classGen, methodGen, (IntType)type);
      } else {
         ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", this.toString(), type.toString());
         classGen.getParser().reportError(2, err);
      }

   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append((Instruction)(new INVOKESTATIC(cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary", "realToString", "(D)Ljava/lang/String;"))));
   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type) {
      InstructionList il = methodGen.getInstructionList();
      FlowList falsel = this.translateToDesynthesized(classGen, methodGen, type);
      il.append(ICONST_1);
      BranchHandle truec = il.append((BranchInstruction)(new GOTO((InstructionHandle)null)));
      falsel.backPatch(il.append(ICONST_0));
      truec.setTarget(il.append(NOP));
   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, IntType type) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append((Instruction)(new INVOKESTATIC(cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary", "realToInt", "(D)I"))));
   }

   public FlowList translateToDesynthesized(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type) {
      FlowList flowlist = new FlowList();
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append((Instruction)DUP2);
      LocalVariableGen local = methodGen.addLocalVariable("real_to_boolean_tmp", com.oracle.wls.shaded.org.apache.bcel.generic.Type.DOUBLE, (InstructionHandle)null, (InstructionHandle)null);
      local.setStart(il.append((Instruction)(new DSTORE(local.getIndex()))));
      il.append(DCONST_0);
      il.append(DCMPG);
      flowlist.add(il.append((BranchInstruction)(new IFEQ((InstructionHandle)null))));
      il.append((Instruction)(new DLOAD(local.getIndex())));
      local.setEnd(il.append((Instruction)(new DLOAD(local.getIndex()))));
      il.append(DCMPG);
      flowlist.add(il.append((BranchInstruction)(new IFNE((InstructionHandle)null))));
      return flowlist;
   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ReferenceType type) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append((Instruction)(new NEW(cpg.addClass("java.lang.Double"))));
      il.append((Instruction)DUP_X2);
      il.append((Instruction)DUP_X2);
      il.append((Instruction)POP);
      il.append((Instruction)(new INVOKESPECIAL(cpg.addMethodref("java.lang.Double", "<init>", "(D)V"))));
   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz) {
      InstructionList il = methodGen.getInstructionList();
      if (clazz == Character.TYPE) {
         il.append((Instruction)D2I);
         il.append((Instruction)I2C);
      } else if (clazz == Byte.TYPE) {
         il.append((Instruction)D2I);
         il.append((Instruction)I2B);
      } else if (clazz == Short.TYPE) {
         il.append((Instruction)D2I);
         il.append((Instruction)I2S);
      } else if (clazz == Integer.TYPE) {
         il.append((Instruction)D2I);
      } else if (clazz == Long.TYPE) {
         il.append((Instruction)D2L);
      } else if (clazz == Float.TYPE) {
         il.append((Instruction)D2F);
      } else if (clazz == Double.TYPE) {
         il.append(NOP);
      } else if (clazz.isAssignableFrom(class$java$lang$Double == null ? (class$java$lang$Double = class$("java.lang.Double")) : class$java$lang$Double)) {
         this.translateTo(classGen, methodGen, Type.Reference);
      } else {
         ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", this.toString(), clazz.getName());
         classGen.getParser().reportError(2, err);
      }

   }

   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz) {
      InstructionList il = methodGen.getInstructionList();
      if (clazz != Character.TYPE && clazz != Byte.TYPE && clazz != Short.TYPE && clazz != Integer.TYPE) {
         if (clazz == Long.TYPE) {
            il.append((Instruction)L2D);
         } else if (clazz == Float.TYPE) {
            il.append((Instruction)F2D);
         } else if (clazz == Double.TYPE) {
            il.append(NOP);
         } else {
            ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", this.toString(), clazz.getName());
            classGen.getParser().reportError(2, err);
         }
      } else {
         il.append((Instruction)I2D);
      }

   }

   public void translateBox(ClassGenerator classGen, MethodGenerator methodGen) {
      this.translateTo(classGen, methodGen, Type.Reference);
   }

   public void translateUnBox(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append((Instruction)(new CHECKCAST(cpg.addClass("java.lang.Double"))));
      il.append((Instruction)(new INVOKEVIRTUAL(cpg.addMethodref("java.lang.Double", "doubleValue", "()D"))));
   }

   public Instruction ADD() {
      return InstructionConstants.DADD;
   }

   public Instruction SUB() {
      return InstructionConstants.DSUB;
   }

   public Instruction MUL() {
      return InstructionConstants.DMUL;
   }

   public Instruction DIV() {
      return InstructionConstants.DDIV;
   }

   public Instruction REM() {
      return InstructionConstants.DREM;
   }

   public Instruction NEG() {
      return InstructionConstants.DNEG;
   }

   public Instruction LOAD(int slot) {
      return new DLOAD(slot);
   }

   public Instruction STORE(int slot) {
      return new DSTORE(slot);
   }

   public Instruction POP() {
      return POP2;
   }

   public Instruction CMP(boolean less) {
      return less ? InstructionConstants.DCMPG : InstructionConstants.DCMPL;
   }

   public Instruction DUP() {
      return DUP2;
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
