package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.Type;

public class ArrayExpression implements LHSExpression {
   Expression index;
   Expression array;

   public ArrayExpression(Expression index, Expression array) {
      if (array.getType() != Type.ARRAY) {
         throw new AssertionError("invalid array type: " + array.getType());
      } else {
         this.index = index;
         this.array = array;
      }
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      this.array.code(ca, code);
      this.index.code(ca, code);
      code.add(new Op(50));
   }

   public void codeAssign(CodeAttribute ca, Bytecodes code, Expression val) {
      this.array.code(ca, code);
      this.index.code(ca, code);
      code.add(new Op(83));
   }

   public Type getType() {
      return this.array.getType();
   }

   public int getMaxStack() {
      return Math.max(this.index.getMaxStack(), this.array.getMaxStack());
   }
}
