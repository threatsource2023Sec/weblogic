package com.sun.faces.taglib.jsf_core;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class VerbatimTag extends UIComponentELTag {
   private ValueExpression escape = null;
   private ValueExpression rendered;

   public void setEscape(ValueExpression escape) {
      this.escape = escape;
   }

   public void setRendered(ValueExpression rendered) {
      this.rendered = rendered;
   }

   public String getRendererType() {
      return "javax.faces.Text";
   }

   public String getComponentType() {
      return "javax.faces.Output";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      if (null != this.escape) {
         component.setValueExpression("escape", this.escape);
      } else {
         component.getAttributes().put("escape", Boolean.FALSE);
      }

      if (null != this.rendered) {
         component.setValueExpression("rendered", this.rendered);
      }

      component.setTransient(true);
   }

   public int doAfterBody() throws JspException {
      if (this.getBodyContent() != null) {
         String value = this.getBodyContent().getString();
         if (value != null) {
            UIOutput output = (UIOutput)this.getComponentInstance();
            output.setValue(value);
            this.getBodyContent().clearBody();
         }
      }

      return this.getDoAfterBodyValue();
   }
}
