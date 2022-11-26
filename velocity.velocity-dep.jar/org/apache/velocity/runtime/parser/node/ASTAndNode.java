package org.apache.velocity.runtime.parser.node;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.runtime.parser.Parser;

public class ASTAndNode extends SimpleNode {
   public ASTAndNode(int id) {
      super(id);
   }

   public ASTAndNode(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object value(InternalContextAdapter context) throws MethodInvocationException {
      return new Boolean(this.evaluate(context));
   }

   public boolean evaluate(InternalContextAdapter context) throws MethodInvocationException {
      Node left = this.jjtGetChild(0);
      Node right = this.jjtGetChild(1);
      if (left != null && right != null) {
         return left.evaluate(context) && right.evaluate(context);
      } else {
         super.rsvc.error((left == null ? "Left" : "Right") + " side of '&&' operation is null." + " Operation not possible. " + context.getCurrentTemplateName() + " [line " + this.getLine() + ", column " + this.getColumn() + "]");
         return false;
      }
   }
}
