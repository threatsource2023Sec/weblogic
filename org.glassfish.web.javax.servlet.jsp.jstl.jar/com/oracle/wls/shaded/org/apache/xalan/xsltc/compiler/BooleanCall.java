package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import java.util.Vector;

final class BooleanCall extends FunctionCall {
   private Expression _arg = null;

   public BooleanCall(QName fname, Vector arguments) {
      super(fname, arguments);
      this._arg = this.argument(0);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      this._arg.typeCheck(stable);
      return this._type = Type.Boolean;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      this._arg.translate(classGen, methodGen);
      Type targ = this._arg.getType();
      if (!targ.identicalTo(Type.Boolean)) {
         this._arg.startIterator(classGen, methodGen);
         targ.translateTo(classGen, methodGen, Type.Boolean);
      }

   }
}
