package com.sun.faces.facelets.compiler;

import java.io.IOException;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

final class StartElementInstruction implements Instruction {
   private final String element;

   public StartElementInstruction(String element) {
      this.element = element;
   }

   public void write(FacesContext context) throws IOException {
      context.getResponseWriter().startElement(this.element, (UIComponent)null);
   }

   public Instruction apply(ExpressionFactory factory, ELContext ctx) {
      return this;
   }

   public boolean isLiteral() {
      return true;
   }
}
