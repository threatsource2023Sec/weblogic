package com.sun.faces.renderkit.html_basic;

import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

public class CompositeRenderer extends Renderer {
   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      Util.notNull("context", context);
      Util.notNull("component", component);
      Map facets = component.getFacets();
      UIComponent compositeRoot = (UIComponent)facets.get("javax.faces.component.COMPOSITE_FACET_NAME");
      if (null == compositeRoot) {
         throw new IOException("PENDING_I18N: Unable to find composite  component root for composite component with id " + component.getId() + " and class " + component.getClass().getName());
      } else {
         compositeRoot.encodeAll(context);
      }
   }

   public boolean getRendersChildren() {
      return true;
   }
}
