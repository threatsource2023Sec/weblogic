package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.el.ELText;
import java.io.IOException;
import javax.el.ELException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public final class UIText extends UILeaf {
   private final ELText txt;
   private final String alias;

   public UIText(String alias, ELText txt) {
      this.txt = txt;
      this.alias = alias;
   }

   public String getFamily() {
      return null;
   }

   public void encodeBegin(FacesContext context) throws IOException {
      if (this.isRendered()) {
         ResponseWriter out = context.getResponseWriter();

         try {
            this.txt.write(out, context.getELContext());
         } catch (ELException var4) {
            throw new ELException(this.alias + ": " + var4.getMessage(), var4.getCause());
         } catch (IOException var5) {
            throw new ELException(this.alias + ": " + var5.getMessage(), var5);
         }
      }

   }

   public String getRendererType() {
      return null;
   }

   public boolean getRendersChildren() {
      return true;
   }

   public String toString() {
      return this.txt.toString();
   }
}
