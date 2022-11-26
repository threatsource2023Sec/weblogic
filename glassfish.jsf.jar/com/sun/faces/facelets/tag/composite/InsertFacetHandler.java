package com.sun.faces.facelets.tag.composite;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.util.FacesLogger;
import java.io.IOException;
import java.util.Map;
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

public class InsertFacetHandler extends TagHandlerImpl {
   private final Logger LOGGER;
   private static final String NAME_ATTRIBUTE = "name";
   private static final String REQUIRED_ATTRIBUTE = "required";
   private TagAttribute name;
   private TagAttribute required;

   public InsertFacetHandler(TagConfig config) {
      super(config);
      this.LOGGER = FacesLogger.TAGLIB.getLogger();
      this.name = this.getRequiredAttribute("name");
      this.required = this.getAttribute("required");
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      UIComponent compositeParent = UIComponent.getCurrentCompositeComponent(ctx.getFacesContext());
      if (compositeParent != null) {
         compositeParent.subscribeToEvent(PostAddToViewEvent.class, new RelocateFacetListener(ctx, parent, this.tag.getLocation()));
      }

   }

   private class RelocateFacetListener extends RelocateListener {
      private FaceletContext ctx;
      private UIComponent component;
      private Location location;

      RelocateFacetListener(FaceletContext ctx, UIComponent component, Location location) {
         this.ctx = ctx;
         this.component = component;
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
               if (InsertFacetHandler.this.LOGGER.isLoggable(Level.WARNING)) {
                  InsertFacetHandler.this.LOGGER.log(Level.WARNING, "jsf.composite.component.insertfacet.missing.template", this.location.toString());
               }

            } else {
               boolean req = this.isRequired();
               String facetName = InsertFacetHandler.this.name.getValue(this.ctx);
               if (compositeParent.getFacetCount() == 0 && req) {
                  this.throwRequiredException(this.ctx, facetName, compositeParent);
               }

               Map facets = compositeParent.getFacets();
               UIComponent facet = (UIComponent)facets.remove(facetName);
               if (facet == null) {
                  facet = (UIComponent)compositeParent.getParent().getFacets().remove(facetName);
               }

               if (facet != null) {
                  this.component.getFacets().put(facetName, facet);
                  String key = (String)facet.getAttributes().get("com.sun.faces.facelets.MARK_ID");
                  String value = this.component.getId();
                  if (key != null && value != null) {
                     compositeParent.getAttributes().put(key, value);
                  }
               } else if (req && this.component.getFacets().get(facetName) == null) {
                  this.throwRequiredException(this.ctx, facetName, compositeParent);
               }

            }
         }
      }

      private void throwRequiredException(FaceletContext ctx, String facetName, UIComponent compositeParent) {
         throw new TagException(InsertFacetHandler.this.tag, "Unable to find facet named '" + facetName + "' in parent composite component with id '" + compositeParent.getClientId(ctx.getFacesContext()) + '\'');
      }

      private boolean isRequired() {
         return InsertFacetHandler.this.required != null && InsertFacetHandler.this.required.getBoolean(this.ctx);
      }
   }
}
