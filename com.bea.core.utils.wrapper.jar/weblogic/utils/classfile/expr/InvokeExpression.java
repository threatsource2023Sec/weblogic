package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPInterfaceMethodref;
import weblogic.utils.classfile.cp.CPMemberType;
import weblogic.utils.classfile.cp.CPMethodref;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.ConstPoolOp;
import weblogic.utils.classfile.ops.InvokeInterfaceOp;

public class InvokeExpression implements Expression {
   Expression ref;
   CPMemberType meth;
   Expression[] args;

   public InvokeExpression(CPMemberType meth, Expression ref, Expression[] args) {
      this.ref = ref;
      this.meth = meth;
      this.args = args;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      this.ref.code(ca, code);
      int argsSize = 1;

      for(int i = 0; i < this.args.length; ++i) {
         this.args[i].code(ca, code);
         argsSize += this.args[i].getType().isWide() ? 2 : 1;
      }

      if (this.meth instanceof CPInterfaceMethodref) {
         code.add(new InvokeInterfaceOp(185, cp, (CPInterfaceMethodref)this.meth, argsSize));
      } else {
         if (!(this.meth instanceof CPMethodref)) {
            throw new AssertionError("Unknown method type used in invoke: " + this.meth.getClass().getName());
         }

         code.add(new ConstPoolOp(182, cp, this.meth));
      }

   }

   public Type getType() {
      return Utilities.getReturnType(this.meth.getDescriptor());
   }

   public int getMaxStack() {
      int argsMax = this.ref.getType().isWide() ? 2 : 1;
      int computeMax = this.ref.getMaxStack();
      int argsLength = this.args.length;

      int retStack;
      for(retStack = 0; retStack < argsLength; ++retStack) {
         Expression e = this.args[retStack];
         if (argsMax + e.getMaxStack() > computeMax) {
            computeMax = argsMax + e.getMaxStack();
         }

         argsMax += e.getType().isWide() ? 2 : 1;
      }

      retStack = this.getType().isWide() ? 2 : 1;
      return Math.max(Math.max(argsMax, computeMax), retStack);
   }
}
