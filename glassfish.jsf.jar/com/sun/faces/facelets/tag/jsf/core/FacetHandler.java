package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public final class FacetHandler extends TagHandlerImpl implements javax.faces.view.facelets.FacetHandler {
   public static final String KEY = "facelets.FACET_NAME";
   protected final TagAttribute name = this.getRequiredAttribute("name");

   public FacetHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (parent == null) {
         throw new TagException(this.tag, "Parent UIComponent was null");
      } else {
         parent.getAttributes().put("facelets.FACET_NAME", this.getFacetName(ctx));

         try {
            this.nextHandler.apply(ctx, parent);
         } finally {
            parent.getAttributes().remove("facelets.FACET_NAME");
         }

      }
   }

   public String getFacetName(FaceletContext ctxt) {
      return this.name.getValue(ctxt);
   }
}
