package com.sun.faces.facelets.tag.composite;

import com.sun.faces.application.view.FaceletViewHandlingStrategy;
import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagConfig;

public class ImplementationHandler extends TagHandlerImpl {
   public static final String Name = "implementation";

   public ImplementationHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (!FaceletViewHandlingStrategy.isBuildingMetadata(ctx.getFacesContext())) {
         this.nextHandler.apply(ctx, parent);
      }

   }
}
