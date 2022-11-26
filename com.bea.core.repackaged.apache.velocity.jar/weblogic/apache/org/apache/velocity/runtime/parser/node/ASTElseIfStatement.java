package weblogic.apache.org.apache.velocity.runtime.parser.node;

import java.io.IOException;
import java.io.Writer;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.exception.ParseErrorException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.parser.Parser;

public class ASTElseIfStatement extends SimpleNode {
   public ASTElseIfStatement(int id) {
      super(id);
   }

   public ASTElseIfStatement(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public boolean evaluate(InternalContextAdapter context) throws MethodInvocationException {
      return this.jjtGetChild(0).evaluate(context);
   }

   public boolean render(InternalContextAdapter context, Writer writer) throws IOException, MethodInvocationException, ResourceNotFoundException, ParseErrorException {
      return this.jjtGetChild(1).render(context, writer);
   }
}
