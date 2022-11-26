package org.apache.velocity.runtime.parser.node;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.runtime.parser.Parser;

public class ASTDivNode extends SimpleNode {
   public ASTDivNode(int id) {
      super(id);
   }

   public ASTDivNode(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object value(InternalContextAdapter context) throws MethodInvocationException {
      Object left = this.jjtGetChild(0).value(context);
      Object right = this.jjtGetChild(1).value(context);
      if (left != null && right != null) {
         if (left instanceof Integer && right instanceof Integer) {
            if ((Integer)right == 0) {
               super.rsvc.error("Right side of division operation is zero. Must be non-zero. " + context.getCurrentTemplateName() + " [line " + this.getLine() + ", column " + this.getColumn() + "]");
               return null;
            } else {
               return new Integer((Integer)left / (Integer)right);
            }
         } else {
            super.rsvc.error((!(left instanceof Integer) ? "Left" : "Right") + " side of division operation is not a valid type. " + "Currently only integers (1,2,3...) and Integer type is supported. " + context.getCurrentTemplateName() + " [line " + this.getLine() + ", column " + this.getColumn() + "]");
            return null;
         }
      } else {
         super.rsvc.error((left == null ? "Left" : "Right") + " side (" + this.jjtGetChild(left == null ? 0 : 1).literal() + ") of division operation has null value." + " Operation not possible. " + context.getCurrentTemplateName() + " [line " + this.getLine() + ", column " + this.getColumn() + "]");
         return null;
      }
   }
}
