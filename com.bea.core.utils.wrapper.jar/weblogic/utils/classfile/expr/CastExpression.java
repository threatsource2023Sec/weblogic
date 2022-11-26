package weblogic.utils.classfile.expr;

import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPClass;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.ConstPoolOp;

public class CastExpression implements Expression {
   Class c;
   Expression expression;

   public CastExpression(Class c, Expression expression) {
      this.c = c;
      this.expression = expression;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      this.expression.code(ca, code);
      CPClass cls = cp.getClass(this.c);
      code.add(new ConstPoolOp(192, cp, cls));
   }

   public Type getType() {
      return Type.getType(this.c);
   }

   public int getMaxStack() {
      return this.expression.getMaxStack();
   }
}
