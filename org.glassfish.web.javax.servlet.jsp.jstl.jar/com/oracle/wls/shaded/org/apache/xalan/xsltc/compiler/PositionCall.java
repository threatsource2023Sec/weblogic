package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.ILOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.CompareGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TestGenerator;

final class PositionCall extends FunctionCall {
   public PositionCall(QName fname) {
      super(fname);
   }

   public boolean hasPositionCall() {
      return true;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      InstructionList il = methodGen.getInstructionList();
      if (methodGen instanceof CompareGenerator) {
         il.append(((CompareGenerator)methodGen).loadCurrentNode());
      } else if (methodGen instanceof TestGenerator) {
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(2)));
      } else {
         ConstantPoolGen cpg = classGen.getConstantPool();
         int index = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator", "getPosition", "()I");
         il.append(methodGen.loadIterator());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(index, 1)));
      }

   }
}
