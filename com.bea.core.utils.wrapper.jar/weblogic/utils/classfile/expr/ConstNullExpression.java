package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.Type;

class ConstNullExpression implements Expression {
   public void code(CodeAttribute ca, Bytecodes code) {
      code.add(new Op(1));
   }

   public Type getType() {
      return Type.OBJECT;
   }

   public int getMaxStack() {
      return 1;
   }
}
