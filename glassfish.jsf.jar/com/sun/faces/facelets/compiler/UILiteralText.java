package com.sun.faces.facelets.compiler;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public final class UILiteralText extends UILeaf {
   private final String text;

   public UILiteralText(String text) {
      this.text = text;
   }

   public void encodeBegin(FacesContext faces) throws IOException {
      if (this.isRendered()) {
         ResponseWriter writer = faces.getResponseWriter();
         writer.write(this.text);
      }

   }

   public String toString() {
      return this.text;
   }
}
