package weblogic.apache.org.apache.velocity.runtime.directive;

import java.io.IOException;
import java.io.Writer;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;
import weblogic.apache.org.apache.velocity.runtime.parser.node.Node;

public class Literal extends Directive {
   String literalText;

   public String getName() {
      return "literal";
   }

   public int getType() {
      return 1;
   }

   public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws Exception {
      super.init(rs, context, node);
      this.literalText = node.jjtGetChild(0).literal();
   }

   public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException {
      writer.write(this.literalText);
      return true;
   }
}
