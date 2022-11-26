package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESTATIC;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import java.util.Vector;

final class FloorCall extends FunctionCall {
   public FloorCall(QName fname, Vector arguments) {
      super(fname, arguments);
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      this.argument().translate(classGen, methodGen);
      methodGen.getInstructionList().append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESTATIC(classGen.getConstantPool().addMethodref("java.lang.Math", "floor", "(D)D"))));
   }
}
