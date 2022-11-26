package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEVIRTUAL;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ErrorMsg;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import java.util.Vector;

final class StartsWithCall extends FunctionCall {
   private Expression _base = null;
   private Expression _token = null;

   public StartsWithCall(QName fname, Vector arguments) {
      super(fname, arguments);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      if (this.argumentCount() != 2) {
         ErrorMsg err = new ErrorMsg("ILLEGAL_ARG_ERR", this.getName(), this);
         throw new TypeCheckError(err);
      } else {
         this._base = this.argument(0);
         Type baseType = this._base.typeCheck(stable);
         if (baseType != Type.String) {
            this._base = new CastExpr(this._base, Type.String);
         }

         this._token = this.argument(1);
         Type tokenType = this._token.typeCheck(stable);
         if (tokenType != Type.String) {
            this._token = new CastExpr(this._token, Type.String);
         }

         return this._type = Type.Boolean;
      }
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      this._base.translate(classGen, methodGen);
      this._token.translate(classGen, methodGen);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEVIRTUAL(cpg.addMethodref("java.lang.String", "startsWith", "(Ljava/lang/String;)Z"))));
   }
}
