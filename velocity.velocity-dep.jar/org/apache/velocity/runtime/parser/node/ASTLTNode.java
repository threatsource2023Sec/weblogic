package org.apache.velocity.runtime.parser.node;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.runtime.parser.Parser;

public class ASTLTNode extends SimpleNode {
   public ASTLTNode(int id) {
      super(id);
   }

   public ASTLTNode(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public boolean evaluate(InternalContextAdapter context) throws MethodInvocationException {
      Object left = this.jjtGetChild(0).value(context);
      Object right = this.jjtGetChild(1).value(context);
      if (left != null && right != null) {
         if (left instanceof Integer && right instanceof Integer) {
            return (Integer)left < (Integer)right;
         } else {
            super.rsvc.error((!(left instanceof Integer) ? "Left" : "Right") + " side of '<' operation is not a valid type. " + " It is a " + (!(left instanceof Integer) ? left.getClass() : right.getClass()) + ". Currently only integers (1,2,3...) and Integer type is supported. " + context.getCurrentTemplateName() + " [line " + this.getLine() + ", column " + this.getColumn() + "]");
            return false;
         }
      } else {
         super.rsvc.error((left == null ? "Left" : "Right") + " side (" + this.jjtGetChild(left == null ? 0 : 1).literal() + ") of '<' operation has null value." + " Operation not possible. " + context.getCurrentTemplateName() + " [line " + this.getLine() + ", column " + this.getColumn() + "]");
         return false;
      }
   }

   public Object value(InternalContextAdapter context) throws MethodInvocationException {
      boolean val = this.evaluate(context);
      return val ? Boolean.TRUE : Boolean.FALSE;
   }
}
