package org.apache.velocity.runtime.parser.node;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.runtime.parser.Parser;

public class ASTNENode extends SimpleNode {
   public ASTNENode(int id) {
      super(id);
   }

   public ASTNENode(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public boolean evaluate(InternalContextAdapter context) throws MethodInvocationException {
      Object left = this.jjtGetChild(0).value(context);
      Object right = this.jjtGetChild(1).value(context);
      if (left != null && right != null) {
         if (left.getClass().equals(right.getClass())) {
            return !left.equals(right);
         } else {
            super.rsvc.error("Error in evaluation of != expression. Both arguments must be of the same Class. Currently left = " + left.getClass() + ", right = " + right.getClass() + ". " + context.getCurrentTemplateName() + " [line " + this.getLine() + ", column " + this.getColumn() + "] (ASTEQNode)");
            return false;
         }
      } else {
         super.rsvc.error((left == null ? "Left" : "Right") + " side (" + this.jjtGetChild(left == null ? 0 : 1).literal() + ") of '!=' operation has null value." + " Operation not possible. " + context.getCurrentTemplateName() + " [line " + this.getLine() + ", column " + this.getColumn() + "]");
         return false;
      }
   }

   public Object value(InternalContextAdapter context) throws MethodInvocationException {
      boolean val = this.evaluate(context);
      return val ? Boolean.TRUE : Boolean.FALSE;
   }
}
