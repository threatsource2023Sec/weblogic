package com.sun.faces.taglib.jsf_core;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.webapp.UIComponentELTag;

public class SelectItemsTag extends UIComponentELTag {
   private ValueExpression value;

   public void setValue(ValueExpression value) {
      this.value = value;
   }

   public String getRendererType() {
      return null;
   }

   public String getComponentType() {
      return "javax.faces.SelectItems";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      if (null != this.value) {
         if (!this.value.isLiteralText()) {
            component.setValueExpression("value", this.value);
         } else {
            ((UISelectItems)component).setValue(this.value.getExpressionString());
         }
      }

   }
}
