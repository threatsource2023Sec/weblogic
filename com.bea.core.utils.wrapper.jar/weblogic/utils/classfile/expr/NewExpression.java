package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPClass;
import weblogic.utils.classfile.cp.CPMethodref;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.ConstPoolOp;

public class NewExpression implements Expression {
   CPClass cls;
   CPMethodref constructor;
   Expression[] args;

   public NewExpression(CPMethodref constructor, Expression[] args) {
      this.cls = constructor.getContainingClass();
      this.constructor = constructor;
      this.args = args;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      code.add(new ConstPoolOp(187, cp, this.cls));
      code.add(new Op(89));

      for(int i = 0; i < this.args.length; ++i) {
         this.args[i].code(ca, code);
      }

      code.add(new ConstPoolOp(183, cp, this.constructor));
   }

   public Type getType() {
      return Type.OBJECT;
   }

   public int getMaxStack() {
      int argsMax = 2;
      int computeMax = 2;

      for(int i = 0; i < this.args.length; ++i) {
         if (argsMax + this.args[i].getMaxStack() > computeMax) {
            computeMax = argsMax + this.args[i].getMaxStack();
         }

         argsMax += this.args[i].getType().isWide() ? 2 : 1;
      }

      return Math.max(argsMax, computeMax);
   }
}
