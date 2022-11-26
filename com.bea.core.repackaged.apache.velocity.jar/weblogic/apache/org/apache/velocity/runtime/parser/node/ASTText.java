package weblogic.apache.org.apache.velocity.runtime.parser.node;

import java.io.IOException;
import java.io.Writer;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.runtime.parser.Parser;
import weblogic.apache.org.apache.velocity.runtime.parser.Token;

public class ASTText extends SimpleNode {
   private char[] ctext;

   public ASTText(int id) {
      super(id);
   }

   public ASTText(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object init(InternalContextAdapter context, Object data) throws Exception {
      Token t = this.getFirstToken();
      String text = NodeUtils.tokenLiteral(t);
      this.ctext = text.toCharArray();
      return data;
   }

   public boolean render(InternalContextAdapter context, Writer writer) throws IOException {
      writer.write(this.ctext);
      return true;
   }
}
