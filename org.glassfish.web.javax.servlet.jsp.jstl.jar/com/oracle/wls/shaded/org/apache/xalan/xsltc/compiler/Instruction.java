package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ErrorMsg;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;

abstract class Instruction extends SyntaxTreeNode {
   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      return this.typeCheckContents(stable);
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ErrorMsg msg = new ErrorMsg("NOT_IMPLEMENTED_ERR", this.getClass(), this);
      this.getParser().reportError(2, msg);
   }
}
