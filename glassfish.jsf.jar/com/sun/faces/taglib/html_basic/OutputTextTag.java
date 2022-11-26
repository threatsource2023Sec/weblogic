package com.sun.faces.taglib.html_basic;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class OutputTextTag extends UIComponentELTag {
   private ValueExpression converter;
   private ValueExpression value;
   private ValueExpression dir;
   private ValueExpression escape;
   private ValueExpression lang;
   private ValueExpression role;
   private ValueExpression style;
   private ValueExpression styleClass;
   private ValueExpression title;

   public void setConverter(ValueExpression converter) {
      this.converter = converter;
   }

   public void setValue(ValueExpression value) {
      this.value = value;
   }

   public void setDir(ValueExpression dir) {
      this.dir = dir;
   }

   public void setEscape(ValueExpression escape) {
      this.escape = escape;
   }

   public void setLang(ValueExpression lang) {
      this.lang = lang;
   }

   public void setRole(ValueExpression role) {
      this.role = role;
   }

   public void setStyle(ValueExpression style) {
      this.style = style;
   }

   public void setStyleClass(ValueExpression styleClass) {
      this.styleClass = styleClass;
   }

   public void setTitle(ValueExpression title) {
      this.title = title;
   }

   public String getRendererType() {
      return "javax.faces.Text";
   }

   public String getComponentType() {
      return "javax.faces.HtmlOutputText";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      UIOutput output = null;

      try {
         output = (UIOutput)component;
      } catch (ClassCastException var4) {
         throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: javax.faces.component.UIOutput.  Perhaps you're missing a tag?");
      }

      if (this.converter != null) {
         if (!this.converter.isLiteralText()) {
            output.setValueExpression("converter", this.converter);
         } else {
            Converter conv = FacesContext.getCurrentInstance().getApplication().createConverter(this.converter.getExpressionString());
            output.setConverter(conv);
         }
      }

      if (this.value != null) {
         output.setValueExpression("value", this.value);
      }

      if (this.dir != null) {
         output.setValueExpression("dir", this.dir);
      }

      if (this.escape != null) {
         output.setValueExpression("escape", this.escape);
      }

      if (this.lang != null) {
         output.setValueExpression("lang", this.lang);
      }

      if (this.role != null) {
         output.setValueExpression("role", this.role);
      }

      if (this.style != null) {
         output.setValueExpression("style", this.style);
      }

      if (this.styleClass != null) {
         output.setValueExpression("styleClass", this.styleClass);
      }

      if (this.title != null) {
         output.setValueExpression("title", this.title);
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
      this.converter = null;
      this.value = null;
      this.dir = null;
      this.escape = null;
      this.lang = null;
      this.role = null;
      this.style = null;
      this.styleClass = null;
      this.title = null;
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }
}
