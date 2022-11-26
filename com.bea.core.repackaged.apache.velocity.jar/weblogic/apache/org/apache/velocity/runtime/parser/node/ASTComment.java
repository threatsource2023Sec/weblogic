package weblogic.apache.org.apache.velocity.runtime.parser.node;

import java.io.IOException;
import java.io.Writer;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.exception.ParseErrorException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.parser.Parser;
import weblogic.apache.org.apache.velocity.runtime.parser.Token;

public class ASTComment extends SimpleNode {
   private static final char[] ZILCH = "".toCharArray();
   private char[] carr;

   public ASTComment(int id) {
      super(id);
   }

   public ASTComment(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object init(InternalContextAdapter context, Object data) throws Exception {
      Token t = this.getFirstToken();
      int loc1 = t.image.indexOf("##");
      int loc2 = t.image.indexOf("#*");
      if (loc1 == -1 && loc2 == -1) {
         this.carr = ZILCH;
      } else {
         this.carr = t.image.substring(0, loc1 == -1 ? loc2 : loc1).toCharArray();
      }

      return data;
   }

   public boolean render(InternalContextAdapter context, Writer writer) throws IOException, MethodInvocationException, ParseErrorException, ResourceNotFoundException {
      writer.write(this.carr);
      return true;
   }
}
