package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.el.ELText;
import java.io.IOException;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

final class AttributeInstruction implements Instruction {
   private final String alias;
   private final String attr;
   private final ELText txt;

   public AttributeInstruction(String alias, String attr, ELText txt) {
      this.alias = alias;
      this.attr = attr;
      this.txt = txt;
   }

   public void write(FacesContext context) throws IOException {
      ResponseWriter out = context.getResponseWriter();

      try {
         ELContext elContext = context.getELContext();
         String val = this.txt.toString(elContext);
         if (val != null && val.length() != 0) {
            out.writeAttribute(this.attr, val, (String)null);
         }

      } catch (ELException var5) {
         throw new ELException(this.alias + ": " + var5.getMessage(), var5.getCause());
      } catch (IOException var6) {
         throw new ELException(this.alias + ": " + var6.getMessage(), var6);
      }
   }

   public Instruction apply(ExpressionFactory factory, ELContext ctx) {
      ELText nt = this.txt.apply(factory, ctx);
      return nt == this.txt ? this : new AttributeInstruction(this.alias, this.attr, nt);
   }

   public boolean isLiteral() {
      return this.txt.isLiteral();
   }
}
