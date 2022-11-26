package com.sun.faces.facelets.compiler;

import java.io.IOException;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;

final class LiteralAttributeInstruction implements Instruction {
   private final String attr;
   private final String text;

   public LiteralAttributeInstruction(String attr, String text) {
      this.attr = attr;
      this.text = text;
   }

   public void write(FacesContext context) throws IOException {
      context.getResponseWriter().writeAttribute(this.attr, this.text, (String)null);
   }

   public Instruction apply(ExpressionFactory factory, ELContext ctx) {
      return this;
   }

   public boolean isLiteral() {
      return true;
   }
}
