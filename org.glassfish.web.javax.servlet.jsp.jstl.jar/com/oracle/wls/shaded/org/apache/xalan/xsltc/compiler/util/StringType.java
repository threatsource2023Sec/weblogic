package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

import com.oracle.wls.shaded.org.apache.bcel.generic.ALOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.ASTORE;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFEQ;
import com.oracle.wls.shaded.org.apache.bcel.generic.IFNONNULL;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.Instruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.FlowList;

public class StringType extends Type {
   // $FF: synthetic field
   static Class class$java$lang$String;

   protected StringType() {
   }

   public String toString() {
      return "string";
   }

   public boolean identicalTo(Type other) {
      return this == other;
   }

   public String toSignature() {
      return "Ljava/lang/String;";
   }

   public boolean isSimple() {
      return true;
   }

   public com.oracle.wls.shaded.org.apache.bcel.generic.Type toJCType() {
      return com.oracle.wls.shaded.org.apache.bcel.generic.Type.STRING;
   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type) {
      if (type == Type.Boolean) {
         this.translateTo(classGen, methodGen, (BooleanType)type);
      } else if (type == Type.Real) {
         this.translateTo(classGen, methodGen, (RealType)type);
      } else if (type == Type.Reference) {
         this.translateTo(classGen, methodGen, (ReferenceType)type);
      } else {
         ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", this.toString(), type.toString());
         classGen.getParser().reportError(2, err);
      }

   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type) {
      InstructionList il = methodGen.getInstructionList();
      FlowList falsel = this.translateToDesynthesized(classGen, methodGen, type);
      il.append(ICONST_1);
      BranchHandle truec = il.append((BranchInstruction)(new GOTO((InstructionHandle)null)));
      falsel.backPatch(il.append(ICONST_0));
      truec.setTarget(il.append(NOP));
   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, RealType type) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append((Instruction)(new INVOKESTATIC(cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary", "stringToReal", "(Ljava/lang/String;)D"))));
   }

   public FlowList translateToDesynthesized(ClassGenerator classGen, MethodGenerator methodGen, BooleanType type) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append((Instruction)(new INVOKEVIRTUAL(cpg.addMethodref("java.lang.String", "length", "()I"))));
      return new FlowList(il.append((BranchInstruction)(new IFEQ((InstructionHandle)null))));
   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, ReferenceType type) {
      methodGen.getInstructionList().append(NOP);
   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Class clazz) {
      if (clazz.isAssignableFrom(class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String)) {
         methodGen.getInstructionList().append(NOP);
      } else {
         ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", this.toString(), clazz.getName());
         classGen.getParser().reportError(2, err);
      }

   }

   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      if (clazz.getName().equals("java.lang.String")) {
         il.append((Instruction)DUP);
         BranchHandle ifNonNull = il.append((BranchInstruction)(new IFNONNULL((InstructionHandle)null)));
         il.append((Instruction)POP);
         il.append((CompoundInstruction)(new PUSH(cpg, "")));
         ifNonNull.setTarget(il.append(NOP));
      } else {
         ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", this.toString(), clazz.getName());
         classGen.getParser().reportError(2, err);
      }

   }

   public void translateBox(ClassGenerator classGen, MethodGenerator methodGen) {
      this.translateTo(classGen, methodGen, Type.Reference);
   }

   public void translateUnBox(ClassGenerator classGen, MethodGenerator methodGen) {
      methodGen.getInstructionList().append(NOP);
   }

   public String getClassName() {
      return "java.lang.String";
   }

   public Instruction LOAD(int slot) {
      return new ALOAD(slot);
   }

   public Instruction STORE(int slot) {
      return new ASTORE(slot);
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
