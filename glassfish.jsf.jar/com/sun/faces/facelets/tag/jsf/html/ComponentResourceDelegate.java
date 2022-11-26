package com.sun.faces.facelets.tag.jsf.html;

import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import com.sun.faces.facelets.tag.jsf.ComponentTagHandlerDelegateImpl;
import java.util.Iterator;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;

public abstract class ComponentResourceDelegate extends ComponentTagHandlerDelegateImpl {
   private TagAttributes attributes;

   public ComponentResourceDelegate(ComponentHandler owner) {
      super(owner);
      this.attributes = owner.getTag().getAttributes();
   }

   protected UIComponent findChild(FaceletContext ctx, UIComponent parent, String tagId) {
      String target = this.getLocationTarget(ctx);
      if (target != null) {
         UIViewRoot root = ctx.getFacesContext().getViewRoot();
         List resources = root.getComponentResources(ctx.getFacesContext(), target);
         Iterator var7 = resources.iterator();

         UIComponent c;
         String cid;
         do {
            if (!var7.hasNext()) {
               return null;
            }

            c = (UIComponent)var7.next();
            cid = (String)c.getAttributes().get("com.sun.faces.facelets.MARK_ID");
         } while(!tagId.equals(cid));

         return c;
      } else {
         return super.findChild(ctx, parent, tagId);
      }
   }

   protected void addComponentToView(FaceletContext ctx, UIComponent parent, UIComponent c, boolean componentFound) {
      if (!componentFound) {
         super.addComponentToView(ctx, parent, c, componentFound);
      } else {
         String target = this.getLocationTarget(ctx);
         if (target != null) {
            UIViewRoot root = ctx.getFacesContext().getViewRoot();
            root.addComponentResource(ctx.getFacesContext(), c, target);
         } else {
            super.addComponentToView(ctx, parent, c, componentFound);
         }
      }

   }

   protected void doOrphanedChildCleanup(FaceletContext ctx, UIComponent parent, UIComponent c) {
      FacesContext context = ctx.getFacesContext();
      boolean suppressEvents = ComponentSupport.suppressViewModificationEvents(context);
      if (suppressEvents) {
         context.setProcessingEvents(false);
      }

      ComponentSupport.finalizeForDeletion(c);
      UIViewRoot root = context.getViewRoot();
      root.removeComponentResource(context, c, this.getLocationTarget(ctx));
      if (suppressEvents) {
         context.setProcessingEvents(true);
      }

   }

   protected abstract String getLocationTarget(FaceletContext var1);

   protected TagAttribute getAttribute(String name) {
      return this.attributes.get(name);
   }
}
