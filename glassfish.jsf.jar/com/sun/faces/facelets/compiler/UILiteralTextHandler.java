package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.component.UniqueIdVendor;
import javax.faces.view.facelets.FaceletContext;

final class UILiteralTextHandler extends AbstractUIHandler {
   protected final String txtString;

   public UILiteralTextHandler(String txtString) {
      this.txtString = txtString;
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (parent != null) {
         UIComponent c = new UILiteralText(this.txtString);
         UIComponent ancestorNamingContainer = parent.getNamingContainer();
         String uid;
         if (null != ancestorNamingContainer && ancestorNamingContainer instanceof UniqueIdVendor) {
            uid = ((UniqueIdVendor)ancestorNamingContainer).createUniqueId(ctx.getFacesContext(), (String)null);
         } else {
            uid = ComponentSupport.getViewRoot(ctx, parent).createUniqueId();
         }

         c.setId(uid);
         this.addComponent(ctx, parent, c);
      }

   }

   public String getText() {
      return this.txtString;
   }

   public String getText(FaceletContext ctx) {
      return this.txtString;
   }
}
