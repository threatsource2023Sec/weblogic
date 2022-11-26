package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public final class AttributeHandler extends TagHandlerImpl implements javax.faces.view.facelets.AttributeHandler {
   private final TagAttribute name = this.getRequiredAttribute("name");
   private final TagAttribute value = this.getRequiredAttribute("value");

   public AttributeHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (parent == null) {
         throw new TagException(this.tag, "Parent UIComponent was null");
      } else {
         if (parent.getParent() == null) {
            String n = this.getAttributeName(ctx);
            if (!parent.getAttributes().containsKey(n)) {
               if (this.value.isLiteral()) {
                  parent.getAttributes().put(n, this.value.getValue());
               } else {
                  parent.setValueExpression(n, this.value.getValueExpression(ctx, Object.class));
               }
            }
         }

      }
   }

   public String getAttributeName(FaceletContext ctxt) {
      return this.name.getValue(ctxt);
   }
}
