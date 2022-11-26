package com.sun.faces.ext.taglib;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;

public class FocusTag extends UIComponentELTag {
   private static final String COMPONENT_TYPE = "com.sun.faces.ext.focus";
   private static final String RENDERER_TYPE = "com.sun.faces.ext.render.FocusHTMLRenderer";
   public ValueExpression forID = null;

   public String getComponentType() {
      return "com.sun.faces.ext.focus";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      if (this.forID != null) {
         if (!this.forID.isLiteralText()) {
            component.setValueExpression("for", this.forID);
         } else {
            component.getAttributes().put("for", this.forID.getExpressionString());
         }
      }

   }

   public String getRendererType() {
      return "com.sun.faces.ext.render.FocusHTMLRenderer";
   }

   public void release() {
      super.release();
      this.forID = null;
   }

   public void setFor(ValueExpression forID) {
      this.forID = forID;
   }
}
