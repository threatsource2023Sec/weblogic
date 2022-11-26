package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Op;

public class ThrowStatement implements Statement {
   Expression expression;

   public ThrowStatement(Expression expression) {
      this.expression = expression;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      this.expression.code(ca, code);
      code.add(new Op(191));
   }

   public int getMaxStack() {
      return this.expression.getMaxStack();
   }
}
