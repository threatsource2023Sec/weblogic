package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.el.ELText;
import java.io.IOException;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;

final class CommentInstruction implements Instruction {
   private final ELText text;

   public CommentInstruction(ELText text) {
      this.text = text;
   }

   public void write(FacesContext context) throws IOException {
      ELContext elContext = context.getELContext();
      context.getResponseWriter().writeComment(this.text.toString(elContext));
   }

   public Instruction apply(ExpressionFactory factory, ELContext ctx) {
      ELText t = this.text.apply(factory, ctx);
      return new CommentInstruction(t);
   }

   public boolean isLiteral() {
      return false;
   }
}
