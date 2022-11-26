package com.sun.faces.facelets.tag.composite;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.util.FacesLogger;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.view.Location;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public class InsertChildrenHandler extends TagHandlerImpl {
   private final Logger LOGGER;
   private static final String REQUIRED_ATTRIBUTE = "required";
   private TagAttribute required;

   public InsertChildrenHandler(TagConfig config) {
      super(config);
      this.LOGGER = FacesLogger.TAGLIB.getLogger();
      this.required = this.getAttribute("required");
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      UIComponent compositeParent = UIComponent.getCurrentCompositeComponent(ctx.getFacesContext());
      if (compositeParent != null) {
         int count = parent.getChildCount();
         compositeParent.subscribeToEvent(PostAddToViewEvent.class, new RelocateChildrenListener(ctx, parent, count, this.tag.getLocation()));
      }

   }

   private class RelocateChildrenListener extends RelocateListener {
      private FaceletContext ctx;
      private UIComponent component;
      private int idx;
      private Location location;

      RelocateChildrenListener(FaceletContext ctx, UIComponent component, int idx, Location location) {
         this.ctx = ctx;
         this.component = component;
         if (!component.getAttributes().containsKey("idx")) {
            component.getAttributes().put("idx", idx);
         }

         this.idx = idx;
         this.location = location;
      }

      public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
         UIComponent compositeParent = event.getComponent();
         if (compositeParent != null) {
            Resource resource = this.getBackingResource(compositeParent);

            while(compositeParent != null && !this.resourcesMatch(resource, this.location)) {
               compositeParent = UIComponent.getCompositeComponentParent(compositeParent);
               if (compositeParent != null) {
                  resource = this.getBackingResource(compositeParent);
               }
            }

            if (compositeParent == null) {
               if (InsertChildrenHandler.this.LOGGER.isLoggable(Level.WARNING)) {
                  InsertChildrenHandler.this.LOGGER.log(Level.WARNING, "jsf.composite.component.insertchildren.missing.template", this.location.toString());
               }

            } else {
               if (compositeParent.getChildCount() == 0 && this.isRequired()) {
                  this.throwRequiredException(this.ctx, compositeParent);
               }

               List compositeChildren = compositeParent.getChildren();
               List parentChildren = this.component.getChildren();
               Iterator var6 = compositeChildren.iterator();

               while(var6.hasNext()) {
                  UIComponent c = (UIComponent)var6.next();
                  String key = (String)c.getAttributes().get("com.sun.faces.facelets.MARK_ID");
                  String value = this.component.getId();
                  if (key != null && value != null) {
                     compositeParent.getAttributes().put(key, value);
                  }
               }

               if (parentChildren.size() < this.getIdx()) {
                  parentChildren.addAll(compositeChildren);
               } else {
                  parentChildren.addAll(this.getIdx(), compositeChildren);
               }

            }
         }
      }

      private int getIdx() {
         Integer idx = (Integer)this.component.getAttributes().get("idx");
         return idx != null ? idx : this.idx;
      }

      private void throwRequiredException(FaceletContext ctx, UIComponent compositeParent) {
         throw new TagException(InsertChildrenHandler.this.tag, "Unable to find any children components nested within parent composite component with id '" + compositeParent.getClientId(ctx.getFacesContext()) + '\'');
      }

      private boolean isRequired() {
         return InsertChildrenHandler.this.required != null && InsertChildrenHandler.this.required.getBoolean(this.ctx);
      }
   }
}
