package weblogic.apache.org.apache.velocity.runtime.parser.node;

import java.io.IOException;
import java.io.Writer;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.exception.ParseErrorException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.directive.Directive;
import weblogic.apache.org.apache.velocity.runtime.parser.Parser;

public class ASTDirective extends SimpleNode {
   private Directive directive;
   private String directiveName = "";
   private boolean isDirective;

   public ASTDirective(int id) {
      super(id);
   }

   public ASTDirective(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object init(InternalContextAdapter context, Object data) throws Exception {
      super.init(context, data);
      if (super.parser.isDirective(this.directiveName)) {
         this.isDirective = true;
         this.directive = (Directive)super.parser.getDirective(this.directiveName).getClass().newInstance();
         this.directive.init(super.rsvc, context, this);
         this.directive.setLocation(this.getLine(), this.getColumn());
      } else if (super.rsvc.isVelocimacro(this.directiveName, context.getCurrentTemplateName())) {
         this.isDirective = true;
         this.directive = super.rsvc.getVelocimacro(this.directiveName, context.getCurrentTemplateName());
         this.directive.init(super.rsvc, context, this);
         this.directive.setLocation(this.getLine(), this.getColumn());
      } else {
         this.isDirective = false;
      }

      return data;
   }

   public boolean render(InternalContextAdapter context, Writer writer) throws IOException, MethodInvocationException, ResourceNotFoundException, ParseErrorException {
      if (this.isDirective) {
         this.directive.render(context, writer, this);
      } else {
         writer.write("#");
         writer.write(this.directiveName);
      }

      return true;
   }

   public void setDirectiveName(String str) {
      this.directiveName = str;
   }

   public String getDirectiveName() {
      return this.directiveName;
   }
}
