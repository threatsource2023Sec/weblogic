package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.el.ELText;
import java.io.IOException;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class XMLInstruction implements Instruction {
   private static final char[] STOP = new char[0];
   private final ELText text;

   public XMLInstruction(ELText text) {
      this.text = text;
   }

   public void write(FacesContext context) throws IOException {
      ResponseWriter rw = context.getResponseWriter();
      rw.writeText(STOP, 0, 0);
      this.text.write(rw, context.getELContext());
   }

   public Instruction apply(ExpressionFactory factory, ELContext ctx) {
      return new XMLInstruction(this.text.apply(factory, ctx));
   }

   public boolean isLiteral() {
      return false;
   }
}
