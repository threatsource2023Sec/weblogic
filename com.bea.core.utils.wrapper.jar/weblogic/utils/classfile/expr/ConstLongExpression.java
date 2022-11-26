package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPLong;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.ConstPoolOp;

class ConstLongExpression implements Expression {
   private long value;

   public ConstLongExpression(long value) {
      this.value = value;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      CPLong val = cp.getLong(this.value);
      code.add(new ConstPoolOp(20, cp, val));
   }

   public Type getType() {
      return Type.LONG;
   }

   public int getMaxStack() {
      return 2;
   }
}
