package com.sun.faces.taglib.html_basic;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class PanelGroupTag extends UIComponentELTag {
   private ValueExpression layout;
   private ValueExpression onclick;
   private ValueExpression ondblclick;
   private ValueExpression onkeydown;
   private ValueExpression onkeypress;
   private ValueExpression onkeyup;
   private ValueExpression onmousedown;
   private ValueExpression onmousemove;
   private ValueExpression onmouseout;
   private ValueExpression onmouseover;
   private ValueExpression onmouseup;
   private ValueExpression style;
   private ValueExpression styleClass;

   public void setLayout(ValueExpression layout) {
      this.layout = layout;
   }

   public void setOnclick(ValueExpression onclick) {
      this.onclick = onclick;
   }

   public void setOndblclick(ValueExpression ondblclick) {
      this.ondblclick = ondblclick;
   }

   public void setOnkeydown(ValueExpression onkeydown) {
      this.onkeydown = onkeydown;
   }

   public void setOnkeypress(ValueExpression onkeypress) {
      this.onkeypress = onkeypress;
   }

   public void setOnkeyup(ValueExpression onkeyup) {
      this.onkeyup = onkeyup;
   }

   public void setOnmousedown(ValueExpression onmousedown) {
      this.onmousedown = onmousedown;
   }

   public void setOnmousemove(ValueExpression onmousemove) {
      this.onmousemove = onmousemove;
   }

   public void setOnmouseout(ValueExpression onmouseout) {
      this.onmouseout = onmouseout;
   }

   public void setOnmouseover(ValueExpression onmouseover) {
      this.onmouseover = onmouseover;
   }

   public void setOnmouseup(ValueExpression onmouseup) {
      this.onmouseup = onmouseup;
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

      if (this.onclick != null) {
         panel.setValueExpression("onclick", this.onclick);
      }

      if (this.ondblclick != null) {
         panel.setValueExpression("ondblclick", this.ondblclick);
      }

      if (this.onkeydown != null) {
         panel.setValueExpression("onkeydown", this.onkeydown);
      }

      if (this.onkeypress != null) {
         panel.setValueExpression("onkeypress", this.onkeypress);
      }

      if (this.onkeyup != null) {
         panel.setValueExpression("onkeyup", this.onkeyup);
      }

      if (this.onmousedown != null) {
         panel.setValueExpression("onmousedown", this.onmousedown);
      }

      if (this.onmousemove != null) {
         panel.setValueExpression("onmousemove", this.onmousemove);
      }

      if (this.onmouseout != null) {
         panel.setValueExpression("onmouseout", this.onmouseout);
      }

      if (this.onmouseover != null) {
         panel.setValueExpression("onmouseover", this.onmouseover);
      }

      if (this.onmouseup != null) {
         panel.setValueExpression("onmouseup", this.onmouseup);
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
      this.onclick = null;
      this.ondblclick = null;
      this.onkeydown = null;
      this.onkeypress = null;
      this.onkeyup = null;
      this.onmousedown = null;
      this.onmousemove = null;
      this.onmouseout = null;
      this.onmouseover = null;
      this.onmouseup = null;
      this.style = null;
      this.styleClass = null;
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }
}
