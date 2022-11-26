package com.sun.faces.taglib.html_basic;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class PanelGroupTag extends UIComponentELTag {
   private ValueExpression layout;
   private ValueExpression style;
   private ValueExpression styleClass;

   public void setLayout(ValueExpression layout) {
      this.layout = layout;
   }

   public void setStyle(ValueExpression style) {
      this.style = style;
   }

   public void setStyleClass(ValueExpression styleClass) {
      this.styleClass = styleClass;
   }

   public String getRendererType() {
      return "javax.faces.Group";
   }

   public String getComponentType() {
      return "javax.faces.HtmlPanelGroup";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      UIPanel panel = null;

      try {
         panel = (UIPanel)component;
      } catch (ClassCastException var4) {
         throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: javax.faces.component.UIPanel.  Perhaps you're missing a tag?");
      }

      if (this.layout != null) {
         panel.setValueExpression("layout", this.layout);
      }

      if (this.style != null) {
         panel.setValueExpression("style", this.style);
      }

      if (this.styleClass != null) {
         panel.setValueExpression("styleClass", this.styleClass);
      }

   }

   public int doStartTag() throws JspException {
      try {
         return super.doStartTag();
      } catch (Exception var3) {
         Object root;
         for(root = var3; ((Throwable)root).getCause() != null; root = ((Throwable)root).getCause()) {
         }

         throw new JspException((Throwable)root);
      }
   }

   public int doEndTag() throws JspException {
      try {
         return super.doEndTag();
      } catch (Exception var3) {
         Object root;
         for(root = var3; ((Throwable)root).getCause() != null; root = ((Throwable)root).getCause()) {
         }

         throw new JspException((Throwable)root);
      }
   }

   public void release() {
      super.release();
      this.layout = null;
      this.style = null;
      this.styleClass = null;
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }
}
