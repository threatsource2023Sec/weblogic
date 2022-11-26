package com.sun.faces.facelets.tag.ui;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;

public final class DefineHandler extends TagHandlerImpl {
   private final String name;

   public DefineHandler(TagConfig config) {
      super(config);
      TagAttribute attr = this.getRequiredAttribute("name");
      if (!attr.isLiteral()) {
         FacesContext context = FacesContext.getCurrentInstance();
         FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
         this.name = (String)attr.getValueExpression(ctx, String.class).getValue(ctx);
      } else {
         this.name = attr.getValue();
      }

   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
   }

   public void applyDefinition(FaceletContext ctx, UIComponent parent) throws IOException {
      this.nextHandler.apply(ctx, parent);
   }

   public String getName() {
      return this.name;
   }
}
