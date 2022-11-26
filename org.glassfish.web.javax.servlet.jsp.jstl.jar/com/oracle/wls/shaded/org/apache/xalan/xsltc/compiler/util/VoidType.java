package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util;

import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.Instruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;

public final class VoidType extends Type {
   protected VoidType() {
   }

   public String toString() {
      return "void";
   }

   public boolean identicalTo(Type other) {
      return this == other;
   }

   public String toSignature() {
      return "V";
   }

   public com.oracle.wls.shaded.org.apache.bcel.generic.Type toJCType() {
      return null;
   }

   public Instruction POP() {
      return NOP;
   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, Type type) {
      if (type == Type.String) {
         this.translateTo(classGen, methodGen, (StringType)type);
      } else {
         ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", this.toString(), type.toString());
         classGen.getParser().reportError(2, err);
      }

   }

   public void translateTo(ClassGenerator classGen, MethodGenerator methodGen, StringType type) {
      InstructionList il = methodGen.getInstructionList();
      il.append((CompoundInstruction)(new PUSH(classGen.getConstantPool(), "")));
   }

   public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen, Class clazz) {
      if (!clazz.getName().equals("void")) {
         ErrorMsg err = new ErrorMsg("DATA_CONVERSION_ERR", this.toString(), clazz.getName());
         classGen.getParser().reportError(2, err);
      }

   }
}
