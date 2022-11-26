package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Type;

public class CatchExceptionExpression implements Expression {
   public void code(CodeAttribute ca, Bytecodes code) {
   }

   public Type getType() {
      return Type.OBJECT;
   }

   public int getMaxStack() {
      return 1;
   }
}
