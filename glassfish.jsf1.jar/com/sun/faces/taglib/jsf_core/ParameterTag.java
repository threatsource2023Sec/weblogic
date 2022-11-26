package com.sun.faces.taglib.jsf_core;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.webapp.UIComponentELTag;

public class ParameterTag extends UIComponentELTag {
   private ValueExpression name;
   private ValueExpression value;

   public void setName(ValueExpression name) {
      this.name = name;
   }

   public void setValue(ValueExpression value) {
      this.value = value;
   }

   public String getRendererType() {
      return null;
   }

   public String getComponentType() {
      return "javax.faces.Parameter";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      UIParameter parameter = (UIParameter)component;
      if (this.name != null) {
         if (!this.name.isLiteralText()) {
            parameter.setValueExpression("name", this.name);
         } else {
            parameter.setName(this.name.getExpressionString());
         }
      }

      if (this.value != null) {
         if (!this.value.isLiteralText()) {
            component.setValueExpression("value", this.value);
         } else {
            parameter.setValue(this.value.getExpressionString());
         }
      }

   }
}
