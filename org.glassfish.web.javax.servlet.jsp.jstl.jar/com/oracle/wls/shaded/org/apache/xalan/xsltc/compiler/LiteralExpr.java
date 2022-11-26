package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;

final class LiteralExpr extends Expression {
   private final String _value;
   private final String _namespace;

   public LiteralExpr(String value) {
      this._value = value;
      this._namespace = null;
   }

   public LiteralExpr(String value, String namespace) {
      this._value = value;
      this._namespace = namespace.equals("") ? null : namespace;
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      return this._type = Type.String;
   }

   public String toString() {
      return "literal-expr(" + this._value + ')';
   }

   protected boolean contextDependent() {
      return false;
   }

   protected String getValue() {
      return this._value;
   }

   protected String getNamespace() {
      return this._namespace;
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      il.append((CompoundInstruction)(new PUSH(cpg, this._value)));
   }
}
