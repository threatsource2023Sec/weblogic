package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKEINTERFACE;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.StringType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import java.util.Vector;

final class UnparsedEntityUriCall extends FunctionCall {
   private Expression _entity = this.argument();

   public UnparsedEntityUriCall(QName fname, Vector arguments) {
      super(fname, arguments);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      Type entity = this._entity.typeCheck(stable);
      if (!(entity instanceof StringType)) {
         this._entity = new CastExpr(this._entity, Type.String);
      }

      return this._type = Type.String;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append(methodGen.loadDOM());
      this._entity.translate(classGen, methodGen);
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKEINTERFACE(cpg.addInterfaceMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM", "getUnparsedEntityURI", "(Ljava/lang/String;)Ljava/lang/String;"), 2)));
   }
}
