package org.apache.velocity.runtime.parser.node;

import java.io.IOException;
import java.io.Writer;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.parser.Parser;

public class ASTIfStatement extends SimpleNode {
   public ASTIfStatement(int id) {
      super(id);
   }

   public ASTIfStatement(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public boolean render(InternalContextAdapter context, Writer writer) throws IOException, MethodInvocationException, ResourceNotFoundException, ParseErrorException {
      if (this.jjtGetChild(0).evaluate(context)) {
         this.jjtGetChild(1).render(context, writer);
         return true;
      } else {
         int totalNodes = this.jjtGetNumChildren();

         for(int i = 2; i < totalNodes; ++i) {
            if (this.jjtGetChild(i).evaluate(context)) {
               this.jjtGetChild(i).render(context, writer);
               return true;
            }
         }

         return true;
      }
   }

   public void process(InternalContextAdapter context, ParserVisitor visitor) {
   }
}
