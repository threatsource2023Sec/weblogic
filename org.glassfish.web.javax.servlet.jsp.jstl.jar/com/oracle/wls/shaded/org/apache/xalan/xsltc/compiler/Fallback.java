package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;

final class Fallback extends Instruction {
   private boolean _active = false;

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      return this._active ? this.typeCheckContents(stable) : Type.Void;
   }

   public void activate() {
      this._active = true;
   }

   public String toString() {
      return "fallback";
   }

   public void parseContents(Parser parser) {
      if (this._active) {
         this.parseChildren(parser);
      }

   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      if (this._active) {
         this.translateContents(classGen, methodGen);
      }

   }
}
