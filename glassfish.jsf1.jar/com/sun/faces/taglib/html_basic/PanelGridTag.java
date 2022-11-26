package com.sun.faces.taglib.html_basic;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class PanelGridTag extends UIComponentELTag {
   private ValueExpression bgcolor;
   private ValueExpression border;
   private ValueExpression captionClass;
   private ValueExpression captionStyle;
   private ValueExpression cellpadding;
   private ValueExpression cellspacing;
   private ValueExpression columnClasses;
   private ValueExpression columns;
   private ValueExpression dir;
   private ValueExpression footerClass;
   private ValueExpression frame;
   private ValueExpression headerClass;
   private ValueExpression lang;
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
   private ValueExpression rowClasses;
   private ValueExpression rules;
   private ValueExpression style;
   private ValueExpression styleClass;
   private ValueExpression summary;
   private ValueExpression title;
   private ValueExpression width;

   public void setBgcolor(ValueExpression bgcolor) {
      this.bgcolor = bgcolor;
   }

   public void setBorder(ValueExpression border) {
      this.border = border;
   }

   public void setCaptionClass(ValueExpression captionClass) {
      this.captionClass = captionClass;
   }

   public void setCaptionStyle(ValueExpression captionStyle) {
      this.captionStyle = captionStyle;
   }

   public void setCellpadding(ValueExpression cellpadding) {
      this.cellpadding = cellpadding;
   }

   public void setCellspacing(ValueExpression cellspacing) {
      this.cellspacing = cellspacing;
   }

   public void setColumnClasses(ValueExpression columnClasses) {
      this.columnClasses = columnClasses;
   }

   public void setColumns(ValueExpression columns) {
      this.columns = columns;
   }

   public void setDir(ValueExpression dir) {
      this.dir = dir;
   }

   public void setFooterClass(ValueExpression footerClass) {
      this.footerClass = footerClass;
   }

   public void setFrame(ValueExpression frame) {
      this.frame = frame;
   }

   public void setHeaderClass(ValueExpression headerClass) {
      this.headerClass = headerClass;
   }

   public void setLang(ValueExpression lang) {
      this.lang = lang;
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

   public void setRowClasses(ValueExpression rowClasses) {
      this.rowClasses = rowClasses;
   }

   public void setRules(ValueExpression rules) {
      this.rules = rules;
   }

   public void setStyle(ValueExpression style) {
      this.style = style;
   }

   public void setStyleClass(ValueExpression styleClass) {
      this.styleClass = styleClass;
   }

   public void setSummary(ValueExpression summary) {
      this.summary = summary;
   }

   public void setTitle(ValueExpression title) {
      this.title = title;
   }

   public void setWidth(ValueExpression width) {
      this.width = width;
   }

   public String getRendererType() {
      return "javax.faces.Grid";
   }

   public String getComponentType() {
      return "javax.faces.HtmlPanelGrid";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      UIPanel panel = null;

      try {
         panel = (UIPanel)component;
      } catch (ClassCastException var4) {
         throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: javax.faces.component.UIPanel.  Perhaps you're missing a tag?");
      }

      if (this.bgcolor != null) {
         panel.setValueExpression("bgcolor", this.bgcolor);
      }

      if (this.border != null) {
         panel.setValueExpression("border", this.border);
      }

      if (this.captionClass != null) {
         panel.setValueExpression("captionClass", this.captionClass);
      }

      if (this.captionStyle != null) {
         panel.setValueExpression("captionStyle", this.captionStyle);
      }

      if (this.cellpadding != null) {
         panel.setValueExpression("cellpadding", this.cellpadding);
      }

      if (this.cellspacing != null) {
         panel.setValueExpression("cellspacing", this.cellspacing);
      }

      if (this.columnClasses != null) {
         panel.setValueExpression("columnClasses", this.columnClasses);
      }

      if (this.columns != null) {
         panel.setValueExpression("columns", this.columns);
      }

      if (this.dir != null) {
         panel.setValueExpression("dir", this.dir);
      }

      if (this.footerClass != null) {
         panel.setValueExpression("footerClass", this.footerClass);
      }

      if (this.frame != null) {
         panel.setValueExpression("frame", this.frame);
      }

      if (this.headerClass != null) {
         panel.setValueExpression("headerClass", this.headerClass);
      }

      if (this.lang != null) {
         panel.setValueExpression("lang", this.lang);
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

      if (this.rowClasses != null) {
         panel.setValueExpression("rowClasses", this.rowClasses);
      }

      if (this.rules != null) {
         panel.setValueExpression("rules", this.rules);
      }

      if (this.style != null) {
         panel.setValueExpression("style", this.style);
      }

      if (this.styleClass != null) {
         panel.setValueExpression("styleClass", this.styleClass);
      }

      if (this.summary != null) {
         panel.setValueExpression("summary", this.summary);
      }

      if (this.title != null) {
         panel.setValueExpression("title", this.title);
      }

      if (this.width != null) {
         panel.setValueExpression("width", this.width);
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
      this.bgcolor = null;
      this.border = null;
      this.captionClass = null;
      this.captionStyle = null;
      this.cellpadding = null;
      this.cellspacing = null;
      this.columnClasses = null;
      this.columns = null;
      this.dir = null;
      this.footerClass = null;
      this.frame = null;
      this.headerClass = null;
      this.lang = null;
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
      this.rowClasses = null;
      this.rules = null;
      this.style = null;
      this.styleClass = null;
      this.summary = null;
      this.title = null;
      this.width = null;
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }
}
