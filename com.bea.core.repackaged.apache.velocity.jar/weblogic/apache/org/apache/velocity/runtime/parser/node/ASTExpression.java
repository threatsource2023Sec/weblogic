package weblogic.apache.org.apache.velocity.runtime.parser.node;

import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.runtime.parser.Parser;

public class ASTExpression extends SimpleNode {
   public ASTExpression(int id) {
      super(id);
   }

   public ASTExpression(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public boolean evaluate(InternalContextAdapter context) throws MethodInvocationException {
      return this.jjtGetChild(0).evaluate(context);
   }

   public Object value(InternalContextAdapter context) throws MethodInvocationException {
      return this.jjtGetChild(0).value(context);
   }
}
