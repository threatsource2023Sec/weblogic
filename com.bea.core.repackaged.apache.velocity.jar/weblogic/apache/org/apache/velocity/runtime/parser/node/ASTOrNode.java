package weblogic.apache.org.apache.velocity.runtime.parser.node;

import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.runtime.parser.Parser;

public class ASTOrNode extends SimpleNode {
   public ASTOrNode(int id) {
      super(id);
   }

   public ASTOrNode(Parser p, int id) {
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
      if (left != null && left.evaluate(context)) {
         return true;
      } else {
         return right != null && right.evaluate(context);
      }
   }
}
