package com.sun.faces.facelets.tag.composite;

import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;

public class BehaviorHolderAttachedObjectTargetHandler extends AttachedObjectTargetHandler {
   public BehaviorHolderAttachedObjectTargetHandler(TagConfig config) {
      super(config);
   }

   AttachedObjectTargetImpl newAttachedObjectTargetImpl() {
      BehaviorHolderAttachedObjectTargetImpl target = new BehaviorHolderAttachedObjectTargetImpl();
      TagAttribute event = this.getAttribute("event");
      FaceletContext ctx = null;
      if (null != event) {
         if (!event.isLiteral()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ctx = (FaceletContext)facesContext.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
            String eventStr = (String)event.getValueExpression(ctx, String.class).getValue(ctx);
            target.setEvent(eventStr);
         } else {
            target.setEvent(event.getValue());
         }
      }

      TagAttribute defaultAttr = this.getAttribute("default");
      if (null != defaultAttr) {
         if (null == ctx) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ctx = (FaceletContext)facesContext.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
         }

         target.setDefaultEvent(defaultAttr.getBoolean(ctx));
      }

      return target;
   }
}
