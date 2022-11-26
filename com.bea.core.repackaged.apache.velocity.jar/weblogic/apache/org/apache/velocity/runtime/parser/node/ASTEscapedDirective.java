package weblogic.apache.org.apache.velocity.runtime.parser.node;

import java.io.IOException;
import java.io.Writer;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.runtime.parser.Parser;

public class ASTEscapedDirective extends SimpleNode {
   public ASTEscapedDirective(int id) {
      super(id);
   }

   public ASTEscapedDirective(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit((SimpleNode)this, data);
   }

   public boolean render(InternalContextAdapter context, Writer writer) throws IOException {
      writer.write(this.getFirstToken().image);
      return true;
   }
}
