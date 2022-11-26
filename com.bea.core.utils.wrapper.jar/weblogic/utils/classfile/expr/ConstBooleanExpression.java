package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.Type;

class ConstBooleanExpression implements Expression {
   boolean val;

   public ConstBooleanExpression(boolean val) {
      this.val = val;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      if (this.val) {
         code.add(new Op(4));
      } else {
         code.add(new Op(3));
      }

   }

   public Type getType() {
      return Type.BOOLEAN;
   }

   public int getMaxStack() {
      return 1;
   }
}
