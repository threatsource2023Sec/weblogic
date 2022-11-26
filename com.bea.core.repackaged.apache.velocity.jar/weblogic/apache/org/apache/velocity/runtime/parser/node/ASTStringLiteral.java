package weblogic.apache.org.apache.velocity.runtime.parser.node;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.runtime.parser.Parser;

public class ASTStringLiteral extends SimpleNode {
   private boolean interpolate = true;
   private SimpleNode nodeTree = null;
   private String image = "";
   private String interpolateimage = "";

   public ASTStringLiteral(int id) {
      super(id);
   }

   public ASTStringLiteral(Parser p, int id) {
      super(p, id);
   }

   public Object init(InternalContextAdapter context, Object data) throws Exception {
      super.init(context, data);
      this.interpolate = super.rsvc.getBoolean("runtime.interpolate.string.literals", true) && this.getFirstToken().image.startsWith("\"") && (this.getFirstToken().image.indexOf(36) != -1 || this.getFirstToken().image.indexOf(35) != -1);
      this.image = this.getFirstToken().image.substring(1, this.getFirstToken().image.length() - 1);
      this.interpolateimage = this.image + " ";
      if (this.interpolate) {
         BufferedReader br = new BufferedReader(new StringReader(this.interpolateimage));
         this.nodeTree = super.rsvc.parse(br, context != null ? context.getCurrentTemplateName() : "StringLiteral", false);
         this.nodeTree.init(context, super.rsvc);
      }

      return data;
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object value(InternalContextAdapter context) {
      if (this.interpolate) {
         try {
            StringWriter writer = new StringWriter();
            this.nodeTree.render(context, writer);
            String ret = writer.toString();
            return ret.substring(0, ret.length() - 1);
         } catch (Exception var4) {
            super.rsvc.error("Error in interpolating string literal : " + var4);
         }
      }

      return this.image;
   }
}
