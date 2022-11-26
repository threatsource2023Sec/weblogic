package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPString;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.LdcOp;

class ConstStringExpression implements Expression {
   String string;

   public ConstStringExpression(String string) {
      this.string = string;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      CPString errorMsg = cp.getString(this.string);
      code.add(new LdcOp(19, cp, errorMsg));
   }

   public Type getType() {
      return Type.OBJECT;
   }

   public int getMaxStack() {
      return 1;
   }
}
