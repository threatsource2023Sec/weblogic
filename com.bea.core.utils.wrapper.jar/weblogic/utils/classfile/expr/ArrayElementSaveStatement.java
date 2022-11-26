package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.Type;

public class ArrayElementSaveStatement implements Statement {
   Expression index;
   Expression array;
   Expression val;

   public ArrayElementSaveStatement(Expression array, Expression index, Expression val) {
      this.index = index;
      this.array = array;
      this.val = val;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      this.array.code(ca, code);
      this.index.code(ca, code);
      this.val.code(ca, code);
      code.add(new Op(83));
   }

   public Type getType() {
      return this.array.getType();
   }

   public int getMaxStack() {
      return this.index.getMaxStack() + this.array.getMaxStack() + this.val.getMaxStack();
   }
}
