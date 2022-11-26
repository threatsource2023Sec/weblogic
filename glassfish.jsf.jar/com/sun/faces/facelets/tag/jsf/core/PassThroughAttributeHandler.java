package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public final class PassThroughAttributeHandler extends TagHandlerImpl implements javax.faces.view.facelets.AttributeHandler {
   private final TagAttribute name = this.getRequiredAttribute("name");
   private final TagAttribute value = this.getRequiredAttribute("value");

   public PassThroughAttributeHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (parent == null) {
         throw new TagException(this.tag, "Parent UIComponent was null");
      } else {
         if (parent.getParent() == null) {
            Map passThroughAttrs = parent.getPassThroughAttributes(true);
            String attrName = this.name.getValue(ctx);
            Object attrValue = this.value.isLiteral() ? this.value.getValue(ctx) : this.value.getValueExpression(ctx, Object.class);
            passThroughAttrs.put(attrName, attrValue);
         }

      }
   }

   public String getAttributeName(FaceletContext ctxt) {
      return this.name.getValue(ctxt);
   }
}
