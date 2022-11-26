package com.sun.faces.renderkit.html_basic;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

public class CompositeFacetRenderer extends Renderer {
   private static final Logger logger;

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      Util.notNull("context", context);
      Util.notNull("component", component);
      String facetName = (String)component.getAttributes().get("javax.faces.component.FACETS_KEY");
      if (null != facetName) {
         UIComponent currentCompositeComponent = UIComponent.getCurrentCompositeComponent(context);
         if (null != currentCompositeComponent) {
            UIComponent facet = currentCompositeComponent.getFacet(facetName);
            if (null != facet) {
               facet.encodeAll(context);
            } else if (logger.isLoggable(Level.FINE)) {
               logger.log(Level.FINE, "Could not find facet named {0}", facetName);
            }
         }

      }
   }

   public boolean getRendersChildren() {
      return true;
   }

   static {
      logger = FacesLogger.RENDERKIT.getLogger();
   }
}
