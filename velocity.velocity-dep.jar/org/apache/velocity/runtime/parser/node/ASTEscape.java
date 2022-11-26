package org.apache.velocity.runtime.parser.node;

import java.io.IOException;
import java.io.Writer;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.parser.Parser;

public class ASTEscape extends SimpleNode {
   public String val;
   private char[] ctext;

   public ASTEscape(int id) {
      super(id);
   }

   public ASTEscape(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit((SimpleNode)this, data);
   }

   public Object init(InternalContextAdapter context, Object data) throws Exception {
      this.ctext = this.val.toCharArray();
      return data;
   }

   public boolean render(InternalContextAdapter context, Writer writer) throws IOException {
      writer.write(this.ctext);
      return true;
   }
}
