package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.application.ApplicationAssociate;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PostRenderViewEvent;
import javax.faces.event.PreRenderViewEvent;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

public class EventHandler extends TagHandler {
   protected final TagAttribute type = this.getRequiredAttribute("type");
   protected final TagAttribute listener = this.getRequiredAttribute("listener");

   public EventHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (ComponentHandler.isNew((UIComponent)parent)) {
         Class eventClass = this.getEventClass(ctx);
         UIViewRoot viewRoot = ctx.getFacesContext().getViewRoot();
         if (null != viewRoot && (PreRenderViewEvent.class == eventClass || PostRenderViewEvent.class == eventClass) && parent != viewRoot) {
            parent = viewRoot;
         }

         if (eventClass != null) {
            ((UIComponent)parent).subscribeToEvent(eventClass, new DeclarativeSystemEventListener(this.listener.getMethodExpression(ctx, Object.class, new Class[]{ComponentSystemEvent.class}), this.listener.getMethodExpression(ctx, Object.class, new Class[0])));
         }
      }

   }

   protected Class getEventClass(FaceletContext ctx) {
      String eventType = (String)this.type.getValueExpression(ctx, String.class).getValue(ctx);
      if (eventType == null) {
         throw new FacesException("Attribute 'type' can not be null");
      } else {
         return ApplicationAssociate.getInstance(ctx.getFacesContext().getExternalContext()).getNamedEventManager().getNamedEvent(eventType);
      }
   }
}
