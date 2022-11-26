package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public final class AttributesHandler extends TagHandlerImpl implements javax.faces.view.facelets.AttributeHandler {
   private final TagAttribute value = this.getRequiredAttribute("value");

   public AttributesHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (parent == null) {
         throw new TagException(this.tag, "Parent UIComponent was null");
      } else {
         if (parent.getParent() == null) {
            Map tagAttrs = (Map)this.value.getObject(ctx, Map.class);
            Iterator var4 = tagAttrs.entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry cur = (Map.Entry)var4.next();
               String n = (String)cur.getKey();
               Object curVal = cur.getValue();
               if (!parent.getAttributes().containsKey(n)) {
                  if (curVal instanceof ValueExpression) {
                     parent.setValueExpression(n, (ValueExpression)curVal);
                  } else {
                     parent.getAttributes().put(n, curVal);
                  }
               }
            }
         }

      }
   }

   public String getAttributeName(FaceletContext ctxt) {
      return "value";
   }
}
