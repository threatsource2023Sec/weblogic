package com.sun.faces.facelets.tag.jstl.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;

public final class IfHandler extends TagHandlerImpl {
   private final TagAttribute test = this.getRequiredAttribute("test");
   private final TagAttribute var = this.getAttribute("var");

   public IfHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, ELException {
      boolean b = this.test.getBoolean(ctx);
      if (this.var != null) {
         ctx.setAttribute(this.var.getValue(ctx), b);
      }

      if (b) {
         this.nextHandler.apply(ctx, parent);
      }

   }
}
