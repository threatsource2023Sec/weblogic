package org.apache.velocity.runtime.parser.node;

import java.util.ArrayList;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.runtime.parser.Parser;

public class ASTIntegerRange extends SimpleNode {
   public ASTIntegerRange(int id) {
      super(id);
   }

   public ASTIntegerRange(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit((SimpleNode)this, data);
   }

   public Object value(InternalContextAdapter context) throws MethodInvocationException {
      Object left = this.jjtGetChild(0).value(context);
      Object right = this.jjtGetChild(1).value(context);
      if (left != null && right != null) {
         if (left instanceof Integer && right instanceof Integer) {
            int l = (Integer)left;
            int r = (Integer)right;
            int num = Math.abs(l - r);
            ++num;
            int delta = l >= r ? -1 : 1;
            ArrayList foo = new ArrayList();
            int val = l;

            for(int i = 0; i < num; ++i) {
               foo.add(new Integer(val));
               val += delta;
            }

            return foo;
         } else {
            super.rsvc.error((!(left instanceof Integer) ? "Left" : "Right") + " side of range operator is not a valid type. " + "Currently only integers (1,2,3...) and Integer type is supported. " + context.getCurrentTemplateName() + " [line " + this.getLine() + ", column " + this.getColumn() + "]");
            return null;
         }
      } else {
         super.rsvc.error((left == null ? "Left" : "Right") + " side of range operator [n..m] has null value." + " Operation not possible. " + context.getCurrentTemplateName() + " [line " + this.getLine() + ", column " + this.getColumn() + "]");
         return null;
      }
   }
}
