package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.ConstPoolOp;

public class NewArrayExpression implements Expression {
   private ConstIntExpression expr;
   private Class c;
   private Expression size;
   private Expression[] initializers;

   public NewArrayExpression(Class c, Expression size) {
      this.c = c;
      this.size = size;
   }

   public NewArrayExpression(Class c, Expression[] initializers) {
      this.c = c;
      this.initializers = initializers;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      if (this.initializers == null) {
         this.size.code(ca, code);
         code.add(new ConstPoolOp(189, cp, cp.getClass(this.c)));
      } else {
         Expression size = new ConstIntExpression(this.initializers.length);
         size.code(ca, code);
         code.add(new ConstPoolOp(189, cp, cp.getClass(this.c)));

         for(int i = 0; i < this.initializers.length; ++i) {
            code.add(new Op(89));
            Expression index = new ConstIntExpression(i);
            index.code(ca, code);
            this.initializers[i].code(ca, code);
            code.add(new Op(83));
         }
      }

   }

   public Type getType() {
      return Type.ARRAY;
   }

   public int getMaxStack() {
      if (this.initializers == null) {
         return Math.max(this.size.getMaxStack(), 3);
      } else {
         int max = 3;

         for(int i = 0; i < this.initializers.length; ++i) {
            int tmp = 3 + i + this.initializers[i].getMaxStack();
            if (tmp > max) {
               max = tmp;
            }
         }

         return max;
      }
   }
}
