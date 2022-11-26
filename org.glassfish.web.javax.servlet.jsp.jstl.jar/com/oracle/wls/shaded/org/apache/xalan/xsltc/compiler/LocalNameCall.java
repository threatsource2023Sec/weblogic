package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import java.util.Vector;

final class LocalNameCall extends NameBase {
   public LocalNameCall(QName fname) {
      super(fname);
   }

   public LocalNameCall(QName fname, Vector arguments) {
      super(fname, arguments);
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      int getNodeName = cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getNodeName", "(I)Ljava/lang/String;");
      int getLocalName = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary", "getLocalName", "(Ljava/lang/String;)Ljava/lang/String;");
      super.translate(classGen, methodGen);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(getNodeName, 2)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESTATIC(getLocalName)));
   }
}
