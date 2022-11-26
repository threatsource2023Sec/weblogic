package com.sun.faces.facelets.tag.composite;

import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagException;

public class RenderFacetHandler extends ComponentHandler {
   private static final String NAME_ATTRIBUTE = "name";
   private static final String REQUIRED_ATTRIBUTE = "required";
   TagAttribute name = this.getRequiredAttribute("name");
   TagAttribute required = this.getAttribute("required");

   public RenderFacetHandler(ComponentConfig config) {
      super(config);
   }

   public void onComponentPopulated(FaceletContext ctx, UIComponent c, UIComponent parent) {
      UIComponent compositeParent = UIComponent.getCurrentCompositeComponent(ctx.getFacesContext());
      if (compositeParent != null) {
         boolean requiredValue = this.required != null && this.required.getBoolean(ctx);
         String nameValue = this.name.getValue(ctx);
         if (compositeParent.getFacetCount() == 0 && requiredValue) {
            this.throwRequiredException(ctx, nameValue, compositeParent);
         }

         Map facetMap = compositeParent.getFacets();
         c.getAttributes().put("javax.faces.component.FACETS_KEY", nameValue);
         if (requiredValue && !facetMap.containsKey(nameValue)) {
            this.throwRequiredException(ctx, nameValue, compositeParent);
         }

      }
   }

   private void throwRequiredException(FaceletContext ctx, String name, UIComponent compositeParent) {
      throw new TagException(this.tag, "Unable to find facet named '" + name + "' in parent composite component with id '" + compositeParent.getClientId(ctx.getFacesContext()) + '\'');
   }
}
