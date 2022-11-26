package weblogic.utils.classfile.cp;

import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.expr.Const;
import weblogic.utils.classfile.expr.Expression;
import weblogic.utils.classfile.expr.InvokeExpression;
import weblogic.utils.classfile.expr.InvokeStaticExpression;

public class CPMethodref extends CPMemberType {
   public CPMethodref() {
      this.setTag(10);
   }

   public Type getType() {
      String descriptor = this.getDescriptor();
      int i = descriptor.indexOf(")") + 1;
      return Type.getType(descriptor.substring(i));
   }

   public Expression invoke(Expression[] args) {
      return this.invoke(Const.THIS, args);
   }

   public Expression invoke(Expression obj, Expression[] args) {
      return new InvokeExpression(this, obj, args);
   }

   public Expression invokeStatic(Expression[] args) {
      return new InvokeStaticExpression(this, args);
   }
}
