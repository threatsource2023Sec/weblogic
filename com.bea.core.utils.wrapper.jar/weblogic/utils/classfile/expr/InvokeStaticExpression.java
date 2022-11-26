package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPMethodref;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.ConstPoolOp;

public class InvokeStaticExpression implements Expression {
   CPMethodref meth;
   Expression[] args;

   public InvokeStaticExpression(CPMethodref meth, Expression[] args) {
      this.meth = meth;
      this.args = args;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();

      for(int i = 0; i < this.args.length; ++i) {
         this.args[i].code(ca, code);
      }

      code.add(new ConstPoolOp(184, cp, this.meth));
   }

   public Type getType() {
      return Utilities.getReturnType(this.meth.getDescriptor());
   }

   public int getMaxStack() {
      int argsMax = 0;
      int computeMax = 0;

      int i;
      for(i = 0; i < this.args.length; ++i) {
         if (argsMax + this.args[i].getMaxStack() > computeMax) {
            computeMax = argsMax + this.args[i].getMaxStack();
         }

         argsMax += this.args[i].getType().isWide() ? 2 : 1;
      }

      i = this.getType().isWide() ? 2 : 1;
      return Math.max(Math.max(argsMax, computeMax), i);
   }
}
