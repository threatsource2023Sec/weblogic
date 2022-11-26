package com.sun.faces.taglib.html_basic;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessage;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class MessageTag extends UIComponentELTag {
   private ValueExpression _for;
   private ValueExpression showDetail;
   private ValueExpression showSummary;
   private ValueExpression dir;
   private ValueExpression errorClass;
   private ValueExpression errorStyle;
   private ValueExpression fatalClass;
   private ValueExpression fatalStyle;
   private ValueExpression infoClass;
   private ValueExpression infoStyle;
   private ValueExpression lang;
   private ValueExpression role;
   private ValueExpression style;
   private ValueExpression styleClass;
   private ValueExpression title;
   private ValueExpression tooltip;
   private ValueExpression warnClass;
   private ValueExpression warnStyle;

   public void setFor(ValueExpression _for) {
      this._for = _for;
   }

   public void setShowDetail(ValueExpression showDetail) {
      this.showDetail = showDetail;
   }

   public void setShowSummary(ValueExpression showSummary) {
      this.showSummary = showSummary;
   }

   public void setDir(ValueExpression dir) {
      this.dir = dir;
   }

   public void setErrorClass(ValueExpression errorClass) {
      this.errorClass = errorClass;
   }

   public void setErrorStyle(ValueExpression errorStyle) {
      this.errorStyle = errorStyle;
   }

   public void setFatalClass(ValueExpression fatalClass) {
      this.fatalClass = fatalClass;
   }

   public void setFatalStyle(ValueExpression fatalStyle) {
      this.fatalStyle = fatalStyle;
   }

   public void setInfoClass(ValueExpression infoClass) {
      this.infoClass = infoClass;
   }

   public void setInfoStyle(ValueExpression infoStyle) {
      this.infoStyle = infoStyle;
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

   public void setTooltip(ValueExpression tooltip) {
      this.tooltip = tooltip;
   }

   public void setWarnClass(ValueExpression warnClass) {
      this.warnClass = warnClass;
   }

   public void setWarnStyle(ValueExpression warnStyle) {
      this.warnStyle = warnStyle;
   }

   public String getRendererType() {
      return "javax.faces.Message";
   }

   public String getComponentType() {
      return "javax.faces.HtmlMessage";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      UIMessage message = null;

      try {
         message = (UIMessage)component;
      } catch (ClassCastException var4) {
         throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: javax.faces.component.UIMessage.  Perhaps you're missing a tag?");
      }

      if (this._for != null) {
         message.setValueExpression("for", this._for);
      }

      if (this.showDetail != null) {
         message.setValueExpression("showDetail", this.showDetail);
      }

      if (this.showSummary != null) {
         message.setValueExpression("showSummary", this.showSummary);
      }

      if (this.dir != null) {
         message.setValueExpression("dir", this.dir);
      }

      if (this.errorClass != null) {
         message.setValueExpression("errorClass", this.errorClass);
      }

      if (this.errorStyle != null) {
         message.setValueExpression("errorStyle", this.errorStyle);
      }

      if (this.fatalClass != null) {
         message.setValueExpression("fatalClass", this.fatalClass);
      }

      if (this.fatalStyle != null) {
         message.setValueExpression("fatalStyle", this.fatalStyle);
      }

      if (this.infoClass != null) {
         message.setValueExpression("infoClass", this.infoClass);
      }

      if (this.infoStyle != null) {
         message.setValueExpression("infoStyle", this.infoStyle);
      }

      if (this.lang != null) {
         message.setValueExpression("lang", this.lang);
      }

      if (this.role != null) {
         message.setValueExpression("role", this.role);
      }

      if (this.style != null) {
         message.setValueExpression("style", this.style);
      }

      if (this.styleClass != null) {
         message.setValueExpression("styleClass", this.styleClass);
      }

      if (this.title != null) {
         message.setValueExpression("title", this.title);
      }

      if (this.tooltip != null) {
         message.setValueExpression("tooltip", this.tooltip);
      }

      if (this.warnClass != null) {
         message.setValueExpression("warnClass", this.warnClass);
      }

      if (this.warnStyle != null) {
         message.setValueExpression("warnStyle", this.warnStyle);
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
      this._for = null;
      this.showDetail = null;
      this.showSummary = null;
      this.dir = null;
      this.errorClass = null;
      this.errorStyle = null;
      this.fatalClass = null;
      this.fatalStyle = null;
      this.infoClass = null;
      this.infoStyle = null;
      this.lang = null;
      this.role = null;
      this.style = null;
      this.styleClass = null;
      this.title = null;
      this.tooltip = null;
      this.warnClass = null;
      this.warnStyle = null;
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }
}
