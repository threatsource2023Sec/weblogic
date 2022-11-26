package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.ILOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.CompareGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TestGenerator;

final class LastCall extends FunctionCall {
   public LastCall(QName fname) {
      super(fname);
   }

   public boolean hasPositionCall() {
      return true;
   }

   public boolean hasLastCall() {
      return true;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      InstructionList il = methodGen.getInstructionList();
      if (methodGen instanceof CompareGenerator) {
         il.append(((CompareGenerator)methodGen).loadLastNode());
      } else if (methodGen instanceof TestGenerator) {
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(3)));
      } else {
         ConstantPoolGen cpg = classGen.getConstantPool();
         int getLast = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator", "getLast", "()I");
         il.append(methodGen.loadIterator());
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(getLast, 1)));
      }

   }
}
