package com.sun.faces.taglib.html_basic;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessages;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class MessagesTag extends UIComponentELTag {
   private ValueExpression globalOnly;
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
   private ValueExpression layout;
   private ValueExpression style;
   private ValueExpression styleClass;
   private ValueExpression title;
   private ValueExpression tooltip;
   private ValueExpression warnClass;
   private ValueExpression warnStyle;

   public void setGlobalOnly(ValueExpression globalOnly) {
      this.globalOnly = globalOnly;
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

   public void setLayout(ValueExpression layout) {
      this.layout = layout;
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
      return "javax.faces.Messages";
   }

   public String getComponentType() {
      return "javax.faces.HtmlMessages";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      UIMessages messages = null;

      try {
         messages = (UIMessages)component;
      } catch (ClassCastException var4) {
         throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: javax.faces.component.UIMessages.  Perhaps you're missing a tag?");
      }

      if (this.globalOnly != null) {
         messages.setValueExpression("globalOnly", this.globalOnly);
      }

      if (this.showDetail != null) {
         messages.setValueExpression("showDetail", this.showDetail);
      }

      if (this.showSummary != null) {
         messages.setValueExpression("showSummary", this.showSummary);
      }

      if (this.dir != null) {
         messages.setValueExpression("dir", this.dir);
      }

      if (this.errorClass != null) {
         messages.setValueExpression("errorClass", this.errorClass);
      }

      if (this.errorStyle != null) {
         messages.setValueExpression("errorStyle", this.errorStyle);
      }

      if (this.fatalClass != null) {
         messages.setValueExpression("fatalClass", this.fatalClass);
      }

      if (this.fatalStyle != null) {
         messages.setValueExpression("fatalStyle", this.fatalStyle);
      }

      if (this.infoClass != null) {
         messages.setValueExpression("infoClass", this.infoClass);
      }

      if (this.infoStyle != null) {
         messages.setValueExpression("infoStyle", this.infoStyle);
      }

      if (this.lang != null) {
         messages.setValueExpression("lang", this.lang);
      }

      if (this.layout != null) {
         messages.setValueExpression("layout", this.layout);
      }

      if (this.style != null) {
         messages.setValueExpression("style", this.style);
      }

      if (this.styleClass != null) {
         messages.setValueExpression("styleClass", this.styleClass);
      }

      if (this.title != null) {
         messages.setValueExpression("title", this.title);
      }

      if (this.tooltip != null) {
         messages.setValueExpression("tooltip", this.tooltip);
      }

      if (this.warnClass != null) {
         messages.setValueExpression("warnClass", this.warnClass);
      }

      if (this.warnStyle != null) {
         messages.setValueExpression("warnStyle", this.warnStyle);
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
      this.globalOnly = null;
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
      this.layout = null;
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
