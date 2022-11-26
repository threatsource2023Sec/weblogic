package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.UIViewRoot;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagConfig;

public class MetadataHandler extends TagHandlerImpl {
   private static final Logger LOGGER;

   public MetadataHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      Util.notNull("parent", parent);
      UIViewRoot root;
      if (parent instanceof UIViewRoot) {
         root = (UIViewRoot)parent;
      } else {
         root = ctx.getFacesContext().getViewRoot();
      }

      if (root == null) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "jsf.metadata.uiviewroot.unavailable");
         }

      } else {
         UIComponent facetComponent = null;
         if (root.getFacetCount() > 0) {
            facetComponent = (UIComponent)root.getFacets().get("javax_faces_metadata");
         }

         if (facetComponent == null) {
            root.getAttributes().put("facelets.FACET_NAME", "javax_faces_metadata");

            try {
               this.nextHandler.apply(ctx, root);
            } finally {
               root.getAttributes().remove("facelets.FACET_NAME");
            }

            facetComponent = (UIComponent)root.getFacets().get("javax_faces_metadata");
            if (facetComponent != null && !(facetComponent instanceof UIPanel)) {
               Application app = ctx.getFacesContext().getApplication();
               UIComponent panelGroup = app.createComponent("javax.faces.Panel");
               panelGroup.getChildren().add(facetComponent);
               root.getFacets().put("javax_faces_metadata", panelGroup);
               facetComponent = panelGroup;
            }

            if (null != facetComponent) {
               facetComponent.setId("javax_faces_metadata");
            }
         }

      }
   }

   static {
      LOGGER = FacesLogger.TAGLIB.getLogger();
   }
}
