package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.BranchInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.GOTO;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionHandle;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;

final class BooleanExpr extends Expression {
   private boolean _value;

   public BooleanExpr(boolean value) {
      this._value = value;
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      this._type = Type.Boolean;
      return this._type;
   }

   public String toString() {
      return this._value ? "true()" : "false()";
   }

   public boolean getValue() {
      return this._value;
   }

   public boolean contextDependent() {
      return false;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append((CompoundInstruction)(new PUSH(cpg, this._value)));
   }

   public void translateDesynthesized(ClassGenerator classGen, MethodGenerator methodGen) {
      InstructionList il = methodGen.getInstructionList();
      if (this._value) {
         il.append(NOP);
      } else {
         this._falseList.add(il.append((BranchInstruction)(new GOTO((InstructionHandle)null))));
      }

   }
}
