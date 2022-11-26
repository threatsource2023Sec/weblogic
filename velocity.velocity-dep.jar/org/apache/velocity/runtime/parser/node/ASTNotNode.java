package org.apache.velocity.runtime.parser.node;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.runtime.parser.Parser;

public class ASTNotNode extends SimpleNode {
   public ASTNotNode(int id) {
      super(id);
   }

   public ASTNotNode(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public boolean evaluate(InternalContextAdapter context) throws MethodInvocationException {
      return !this.jjtGetChild(0).evaluate(context);
   }

   public Object value(InternalContextAdapter context) throws MethodInvocationException {
      return this.jjtGetChild(0).evaluate(context) ? Boolean.FALSE : Boolean.TRUE;
   }
}
