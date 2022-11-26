package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.el.ELText;
import java.io.IOException;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

final class TextInstruction implements Instruction {
   private final ELText txt;
   private final String alias;

   public TextInstruction(String alias, ELText txt) {
      this.alias = alias;
      this.txt = txt;
   }

   public void write(FacesContext context) throws IOException {
      ResponseWriter out = context.getResponseWriter();

      try {
         ELContext elContext = context.getELContext();
         this.txt.writeText(out, elContext);
      } catch (ELException var4) {
         throw new ELException(this.alias + ": " + var4.getMessage(), var4.getCause());
      } catch (IOException var5) {
         throw new ELException(this.alias + ": " + var5.getMessage(), var5);
      }
   }

   public Instruction apply(ExpressionFactory factory, ELContext ctx) {
      ELText nt = this.txt.apply(factory, ctx);
      return nt == this.txt ? this : new TextInstruction(this.alias, nt);
   }

   public boolean isLiteral() {
      return this.txt.isLiteral();
   }
}
