package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.Type;

public class ConstThisExpression implements Expression {
   public void code(CodeAttribute ca, Bytecodes code) {
      code.add(new Op(42));
   }

   public Type getType() {
      return Type.OBJECT;
   }

   public int getMaxStack() {
      return 1;
   }
}
