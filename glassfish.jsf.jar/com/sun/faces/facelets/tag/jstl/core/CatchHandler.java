package com.sun.faces.facelets.tag.jstl.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;

public final class CatchHandler extends TagHandlerImpl {
   private final TagAttribute var = this.getAttribute("var");

   public CatchHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      try {
         this.nextHandler.apply(ctx, parent);
      } catch (Exception var4) {
         if (this.var != null) {
            ctx.setAttribute(this.var.getValue(ctx), var4);
         }
      }

   }
}
