package com.sun.faces.taglib.html_basic;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class GraphicImageTag extends UIComponentELTag {
   private ValueExpression url;
   private ValueExpression value;
   private ValueExpression alt;
   private ValueExpression dir;
   private ValueExpression height;
   private ValueExpression ismap;
   private ValueExpression lang;
   private ValueExpression library;
   private ValueExpression longdesc;
   private ValueExpression name;
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
   private ValueExpression role;
   private ValueExpression style;
   private ValueExpression styleClass;
   private ValueExpression title;
   private ValueExpression usemap;
   private ValueExpression width;

   public void setUrl(ValueExpression url) {
      this.url = url;
   }

   public void setValue(ValueExpression value) {
      this.value = value;
   }

   public void setAlt(ValueExpression alt) {
      this.alt = alt;
   }

   public void setDir(ValueExpression dir) {
      this.dir = dir;
   }

   public void setHeight(ValueExpression height) {
      this.height = height;
   }

   public void setIsmap(ValueExpression ismap) {
      this.ismap = ismap;
   }

   public void setLang(ValueExpression lang) {
      this.lang = lang;
   }

   public void setLibrary(ValueExpression library) {
      this.library = library;
   }

   public void setLongdesc(ValueExpression longdesc) {
      this.longdesc = longdesc;
   }

   public void setName(ValueExpression name) {
      this.name = name;
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

   public void setUsemap(ValueExpression usemap) {
      this.usemap = usemap;
   }

   public void setWidth(ValueExpression width) {
      this.width = width;
   }

   public String getRendererType() {
      return "javax.faces.Image";
   }

   public String getComponentType() {
      return "javax.faces.HtmlGraphicImage";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      UIGraphic graphic = null;

      try {
         graphic = (UIGraphic)component;
      } catch (ClassCastException var4) {
         throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: javax.faces.component.UIGraphic.  Perhaps you're missing a tag?");
      }

      if (this.url != null) {
         graphic.setValueExpression("url", this.url);
      }

      if (this.value != null) {
         graphic.setValueExpression("value", this.value);
      }

      if (this.alt != null) {
         graphic.setValueExpression("alt", this.alt);
      }

      if (this.dir != null) {
         graphic.setValueExpression("dir", this.dir);
      }

      if (this.height != null) {
         graphic.setValueExpression("height", this.height);
      }

      if (this.ismap != null) {
         graphic.setValueExpression("ismap", this.ismap);
      }

      if (this.lang != null) {
         graphic.setValueExpression("lang", this.lang);
      }

      if (this.library != null) {
         graphic.setValueExpression("library", this.library);
      }

      if (this.longdesc != null) {
         graphic.setValueExpression("longdesc", this.longdesc);
      }

      if (this.name != null) {
         graphic.setValueExpression("name", this.name);
      }

      if (this.onclick != null) {
         graphic.setValueExpression("onclick", this.onclick);
      }

      if (this.ondblclick != null) {
         graphic.setValueExpression("ondblclick", this.ondblclick);
      }

      if (this.onkeydown != null) {
         graphic.setValueExpression("onkeydown", this.onkeydown);
      }

      if (this.onkeypress != null) {
         graphic.setValueExpression("onkeypress", this.onkeypress);
      }

      if (this.onkeyup != null) {
         graphic.setValueExpression("onkeyup", this.onkeyup);
      }

      if (this.onmousedown != null) {
         graphic.setValueExpression("onmousedown", this.onmousedown);
      }

      if (this.onmousemove != null) {
         graphic.setValueExpression("onmousemove", this.onmousemove);
      }

      if (this.onmouseout != null) {
         graphic.setValueExpression("onmouseout", this.onmouseout);
      }

      if (this.onmouseover != null) {
         graphic.setValueExpression("onmouseover", this.onmouseover);
      }

      if (this.onmouseup != null) {
         graphic.setValueExpression("onmouseup", this.onmouseup);
      }

      if (this.role != null) {
         graphic.setValueExpression("role", this.role);
      }

      if (this.style != null) {
         graphic.setValueExpression("style", this.style);
      }

      if (this.styleClass != null) {
         graphic.setValueExpression("styleClass", this.styleClass);
      }

      if (this.title != null) {
         graphic.setValueExpression("title", this.title);
      }

      if (this.usemap != null) {
         graphic.setValueExpression("usemap", this.usemap);
      }

      if (this.width != null) {
         graphic.setValueExpression("width", this.width);
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
      this.url = null;
      this.value = null;
      this.alt = null;
      this.dir = null;
      this.height = null;
      this.ismap = null;
      this.lang = null;
      this.library = null;
      this.longdesc = null;
      this.name = null;
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
      this.role = null;
      this.style = null;
      this.styleClass = null;
      this.title = null;
      this.usemap = null;
      this.width = null;
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }
}
