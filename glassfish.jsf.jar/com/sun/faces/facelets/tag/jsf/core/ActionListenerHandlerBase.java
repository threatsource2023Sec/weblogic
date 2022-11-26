package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.facelets.tag.jsf.CompositeComponentTagHandler;
import java.io.IOException;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ActionSource2AttachedObjectHandler;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public abstract class ActionListenerHandlerBase extends TagHandlerImpl implements ActionSource2AttachedObjectHandler {
   public ActionListenerHandlerBase(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (null != parent && ComponentHandler.isNew(parent)) {
         if (parent instanceof ActionSource) {
            this.applyAttachedObject(ctx.getFacesContext(), parent);
         } else {
            if (!parent.getAttributes().containsKey("javax.faces.application.Resource.ComponentResource")) {
               throw new TagException(this.tag, "Parent is not of type ActionSource, type is: " + parent);
            }

            if (null == this.getFor()) {
               throw new TagException(this.tag, "actionListener tags nested within composite components must have a non-null \"for\" attribute");
            }

            CompositeComponentTagHandler.getAttachedObjectHandlers(parent).add(this);
         }

      }
   }

   public abstract void applyAttachedObject(FacesContext var1, UIComponent var2);

   public String getFor() {
      String result = null;
      TagAttribute attr = this.getAttribute("for");
      if (null != attr) {
         if (attr.isLiteral()) {
            result = attr.getValue();
         } else {
            FacesContext context = FacesContext.getCurrentInstance();
            FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
            result = (String)attr.getValueExpression(ctx, String.class).getValue(ctx);
         }
      }

      return result;
   }
}
