package com.sun.faces.facelets.tag.jstl.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;

public final class ChooseWhenHandler extends TagHandlerImpl {
   private final TagAttribute test = this.getRequiredAttribute("test");

   public ChooseWhenHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      this.nextHandler.apply(ctx, parent);
   }

   public boolean isTestTrue(FaceletContext ctx) {
      return this.test.getBoolean(ctx);
   }
}
