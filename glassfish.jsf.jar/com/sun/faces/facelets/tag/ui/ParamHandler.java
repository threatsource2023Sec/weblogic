package com.sun.faces.facelets.tag.ui;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;

public class ParamHandler extends TagHandlerImpl {
   private final TagAttribute name = this.getRequiredAttribute("name");
   private final TagAttribute value = this.getRequiredAttribute("value");

   public ParamHandler(TagConfig config) {
      super(config);
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      String nameStr = this.name.getValue(ctx);
      ValueExpression valueVE = this.value.getValueExpression(ctx, Object.class);
      ctx.getVariableMapper().setVariable(nameStr, valueVE);
   }
}
