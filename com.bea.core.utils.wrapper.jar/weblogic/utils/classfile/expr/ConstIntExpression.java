package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPInteger;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.BipushOp;
import weblogic.utils.classfile.ops.LdcOp;
import weblogic.utils.classfile.ops.SipushOp;

class ConstIntExpression implements Expression {
   int val;

   public ConstIntExpression(int val) {
      this.val = val;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      switch (this.val) {
         case -1:
            code.add(new Op(2));
            break;
         case 0:
            code.add(new Op(3));
            break;
         case 1:
            code.add(new Op(4));
            break;
         case 2:
            code.add(new Op(5));
            break;
         case 3:
            code.add(new Op(6));
            break;
         case 4:
            code.add(new Op(7));
            break;
         case 5:
            code.add(new Op(8));
            break;
         default:
            if (this.val >= -128 && this.val <= 127) {
               code.add(new BipushOp(16, this.val));
            } else if (this.val >= -32768 && this.val <= 32767) {
               code.add(new SipushOp(17, this.val));
            } else {
               ConstantPool cp = ca.getConstantPool();
               CPInteger theVal = cp.getInteger(this.val);
               code.add(new LdcOp(19, cp, theVal));
            }
      }

   }

   public Type getType() {
      return Type.INT;
   }

   public int getMaxStack() {
      return 1;
   }
}
