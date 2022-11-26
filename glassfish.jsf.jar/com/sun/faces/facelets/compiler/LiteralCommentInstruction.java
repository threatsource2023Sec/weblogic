package com.sun.faces.facelets.compiler;

import java.io.IOException;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;

final class LiteralCommentInstruction implements Instruction {
   private final String text;

   public LiteralCommentInstruction(String text) {
      this.text = text;
   }

   public void write(FacesContext context) throws IOException {
      context.getResponseWriter().writeComment(this.text);
   }

   public Instruction apply(ExpressionFactory factory, ELContext ctx) {
      return this;
   }

   public boolean isLiteral() {
      return true;
   }
}
