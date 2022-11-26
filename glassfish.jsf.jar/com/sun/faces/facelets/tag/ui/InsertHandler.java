package com.sun.faces.facelets.tag.ui;

import com.sun.faces.facelets.FaceletContextImplBase;
import com.sun.faces.facelets.TemplateClient;
import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;

public final class InsertHandler extends TagHandlerImpl implements TemplateClient {
   private final String name;

   public InsertHandler(TagConfig config) {
      super(config);
      TagAttribute attr = this.getAttribute("name");
      if (attr != null) {
         if (!attr.isLiteral()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
            this.name = (String)attr.getValueExpression(ctx, String.class).getValue(ctx);
         } else {
            this.name = attr.getValue();
         }
      } else {
         this.name = null;
      }

   }

   public void apply(FaceletContext ctxObj, UIComponent parent) throws IOException {
      FaceletContextImplBase ctx = (FaceletContextImplBase)ctxObj;
      ctx.extendClient(this);
      boolean found = false;

      try {
         found = ctx.includeDefinition(parent, this.name);
      } finally {
         ctx.popClient(this);
      }

      if (!found) {
         this.nextHandler.apply(ctx, parent);
      }

   }

   public boolean apply(FaceletContext ctx, UIComponent parent, String name) throws IOException {
      if (this.name != null && this.name.equals(name)) {
         this.nextHandler.apply(ctx, parent);
         return true;
      } else {
         return false;
      }
   }
}
