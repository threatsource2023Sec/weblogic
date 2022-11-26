package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ErrorMsg;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;

final class Otherwise extends Instruction {
   public void display(int indent) {
      this.indent(indent);
      Util.println("Otherwise");
      this.indent(indent + 4);
      this.displayContents(indent + 4);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      this.typeCheckContents(stable);
      return Type.Void;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      Parser parser = this.getParser();
      ErrorMsg err = new ErrorMsg("STRAY_OTHERWISE_ERR", this);
      parser.reportError(3, err);
   }
}
