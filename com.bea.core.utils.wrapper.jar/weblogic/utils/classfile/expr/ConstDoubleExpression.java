package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPDouble;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.ConstPoolOp;

class ConstDoubleExpression implements Expression {
   private double value;

   public ConstDoubleExpression(double value) {
      this.value = value;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      CPDouble val = cp.getDouble(this.value);
      code.add(new ConstPoolOp(19, cp, val));
   }

   public Type getType() {
      return Type.DOUBLE;
   }

   public int getMaxStack() {
      return 2;
   }
}
