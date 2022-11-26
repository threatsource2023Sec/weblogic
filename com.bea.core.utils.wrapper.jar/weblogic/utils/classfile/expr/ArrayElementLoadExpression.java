package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.Type;

public class ArrayElementLoadExpression implements Expression {
   Expression index;
   Expression array;

   public ArrayElementLoadExpression(Expression array, ConstIntExpression index) {
      this.index = index;
      this.array = array;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      this.array.code(ca, code);
      this.index.code(ca, code);
      code.add(new Op(50));
   }

   public Type getType() {
      return Type.OBJECT;
   }

   public int getMaxStack() {
      return this.index.getMaxStack() + this.array.getMaxStack();
   }
}
