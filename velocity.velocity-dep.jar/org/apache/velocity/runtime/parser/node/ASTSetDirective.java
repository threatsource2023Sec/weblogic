package org.apache.velocity.runtime.parser.node;

import java.io.IOException;
import java.io.Writer;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.runtime.parser.Parser;

public class ASTSetDirective extends SimpleNode {
   private String leftReference = "";
   private Node right;
   private ASTReference left;
   boolean blather = false;

   public ASTSetDirective(int id) {
      super(id);
   }

   public ASTSetDirective(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object init(InternalContextAdapter context, Object data) throws Exception {
      super.init(context, data);
      this.right = this.getRightHandSide();
      this.left = this.getLeftHandSide();
      this.blather = super.rsvc.getBoolean("runtime.log.invalid.references", true);
      this.leftReference = this.left.getFirstToken().image.substring(1);
      return data;
   }

   public boolean render(InternalContextAdapter context, Writer writer) throws IOException, MethodInvocationException {
      Object value = this.right.value(context);
      if (value == null) {
         if (this.blather) {
            EventCartridge ec = context.getEventCartridge();
            boolean doit = true;
            if (ec != null) {
               doit = ec.shouldLogOnNullSet(this.left.literal(), this.right.literal());
            }

            if (doit) {
               super.rsvc.error("RHS of #set statement is null. Context will not be modified. " + context.getCurrentTemplateName() + " [line " + this.getLine() + ", column " + this.getColumn() + "]");
            }
         }

         return false;
      } else {
         if (this.left.jjtGetNumChildren() == 0) {
            context.put(this.leftReference, value);
         } else {
            this.left.setValue(context, value);
         }

         return true;
      }
   }

   private ASTReference getLeftHandSide() {
      return (ASTReference)this.jjtGetChild(0);
   }

   private Node getRightHandSide() {
      return this.jjtGetChild(1);
   }
}
