package com.sun.faces.facelets.compiler;

import com.sun.faces.config.FaceletsConfiguration;
import java.io.IOException;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;

final class LiteralTextInstruction implements Instruction {
   private final String text;

   public LiteralTextInstruction(String text) {
      this.text = text;
   }

   public void write(FacesContext context) throws IOException {
      if (FaceletsConfiguration.getInstance(context).isEscapeInlineText(context)) {
         context.getResponseWriter().writeText(this.text, (String)null);
      } else {
         context.getResponseWriter().write(this.text);
      }

   }

   public Instruction apply(ExpressionFactory factory, ELContext ctx) {
      return this;
   }

   public boolean isLiteral() {
      return true;
   }
}
