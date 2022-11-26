package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public final class PassThroughAttributesHandler extends TagHandlerImpl implements javax.faces.view.facelets.AttributeHandler {
   private final TagAttribute value = this.getRequiredAttribute("value");

   public PassThroughAttributesHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (parent == null) {
         throw new TagException(this.tag, "Parent UIComponent was null");
      } else {
         if (parent.getParent() == null) {
            Map componentPassThroughAttrs = parent.getPassThroughAttributes(true);
            Map tagPassThroughAttrs = (Map)this.value.getObject(ctx, Map.class);
            Iterator var5 = tagPassThroughAttrs.entrySet().iterator();

            while(var5.hasNext()) {
               Map.Entry cur = (Map.Entry)var5.next();
               componentPassThroughAttrs.put(cur.getKey(), cur.getValue());
            }
         }

      }
   }

   public String getAttributeName(FaceletContext ctxt) {
      return "value";
   }
}
