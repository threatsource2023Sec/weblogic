package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.ILOAD;
import com.oracle.wls.shaded.org.apache.bcel.generic.INVOKESTATIC;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.FilterGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.StringType;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import java.util.Vector;

final class LangCall extends FunctionCall {
   private Expression _lang = this.argument(0);
   private Type _langType;

   public LangCall(QName fname, Vector arguments) {
      super(fname, arguments);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      this._langType = this._lang.typeCheck(stable);
      if (!(this._langType instanceof StringType)) {
         this._lang = new CastExpr(this._lang, Type.String);
      }

      return Type.Boolean;
   }

   public Type getType() {
      return Type.Boolean;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      int tst = cpg.addMethodref("com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary", "testLanguage", "(Ljava/lang/String;Lorg/apache/xalan/xsltc/DOM;I)Z");
      this._lang.translate(classGen, methodGen);
      il.append(methodGen.loadDOM());
      if (classGen instanceof FilterGenerator) {
         il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new ILOAD(1)));
      } else {
         il.append(methodGen.loadContextNode());
      }

      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)(new INVOKESTATIC(tst)));
   }
}
