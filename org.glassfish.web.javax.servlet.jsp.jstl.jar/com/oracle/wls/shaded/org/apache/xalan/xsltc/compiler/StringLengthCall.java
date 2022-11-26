package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import java.util.Vector;

final class StringLengthCall extends FunctionCall {
   public StringLengthCall(QName fname, Vector arguments) {
      super(fname, arguments);
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      if (this.argumentCount() > 0) {
         this.argument().translate(classGen, methodGen);
      } else {
         il.append(methodGen.loadContextNode());
         Type.Node.translateTo(classGen, methodGen, Type.String);
      }

      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(cpg.addMethodref("java.lang.String", "length", "()I"))));
   }
}
