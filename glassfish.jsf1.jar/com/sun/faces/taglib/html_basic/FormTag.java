package com.sun.faces.taglib.html_basic;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class FormTag extends UIComponentELTag {
   private ValueExpression prependId;
   private ValueExpression accept;
   private ValueExpression acceptcharset;
   private ValueExpression dir;
   private ValueExpression enctype;
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
   private ValueExpression onreset;
   private ValueExpression onsubmit;
   private ValueExpression style;
   private ValueExpression styleClass;
   private ValueExpression target;
   private ValueExpression title;

   public void setPrependId(ValueExpression prependId) {
      this.prependId = prependId;
   }

   public void setAccept(ValueExpression accept) {
      this.accept = accept;
   }

   public void setAcceptcharset(ValueExpression acceptcharset) {
      this.acceptcharset = acceptcharset;
   }

   public void setDir(ValueExpression dir) {
      this.dir = dir;
   }

   public void setEnctype(ValueExpression enctype) {
      this.enctype = enctype;
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

   public void setOnreset(ValueExpression onreset) {
      this.onreset = onreset;
   }

   public void setOnsubmit(ValueExpression onsubmit) {
      this.onsubmit = onsubmit;
   }

   public void setStyle(ValueExpression style) {
      this.style = style;
   }

   public void setStyleClass(ValueExpression styleClass) {
      this.styleClass = styleClass;
   }

   public void setTarget(ValueExpression target) {
      this.target = target;
   }

   public void setTitle(ValueExpression title) {
      this.title = title;
   }

   public String getRendererType() {
      return "javax.faces.Form";
   }

   public String getComponentType() {
      return "javax.faces.HtmlForm";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      UIForm form = null;

      try {
         form = (UIForm)component;
      } catch (ClassCastException var4) {
         throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: javax.faces.component.UIForm.  Perhaps you're missing a tag?");
      }

      if (this.prependId != null) {
         form.setValueExpression("prependId", this.prependId);
      }

      if (this.accept != null) {
         form.setValueExpression("accept", this.accept);
      }

      if (this.acceptcharset != null) {
         form.setValueExpression("acceptcharset", this.acceptcharset);
      }

      if (this.dir != null) {
         form.setValueExpression("dir", this.dir);
      }

      if (this.enctype != null) {
         form.setValueExpression("enctype", this.enctype);
      }

      if (this.lang != null) {
         form.setValueExpression("lang", this.lang);
      }

      if (this.onclick != null) {
         form.setValueExpression("onclick", this.onclick);
      }

      if (this.ondblclick != null) {
         form.setValueExpression("ondblclick", this.ondblclick);
      }

      if (this.onkeydown != null) {
         form.setValueExpression("onkeydown", this.onkeydown);
      }

      if (this.onkeypress != null) {
         form.setValueExpression("onkeypress", this.onkeypress);
      }

      if (this.onkeyup != null) {
         form.setValueExpression("onkeyup", this.onkeyup);
      }

      if (this.onmousedown != null) {
         form.setValueExpression("onmousedown", this.onmousedown);
      }

      if (this.onmousemove != null) {
         form.setValueExpression("onmousemove", this.onmousemove);
      }

      if (this.onmouseout != null) {
         form.setValueExpression("onmouseout", this.onmouseout);
      }

      if (this.onmouseover != null) {
         form.setValueExpression("onmouseover", this.onmouseover);
      }

      if (this.onmouseup != null) {
         form.setValueExpression("onmouseup", this.onmouseup);
      }

      if (this.onreset != null) {
         form.setValueExpression("onreset", this.onreset);
      }

      if (this.onsubmit != null) {
         form.setValueExpression("onsubmit", this.onsubmit);
      }

      if (this.style != null) {
         form.setValueExpression("style", this.style);
      }

      if (this.styleClass != null) {
         form.setValueExpression("styleClass", this.styleClass);
      }

      if (this.target != null) {
         form.setValueExpression("target", this.target);
      }

      if (this.title != null) {
         form.setValueExpression("title", this.title);
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
      this.prependId = null;
      this.accept = null;
      this.acceptcharset = null;
      this.dir = null;
      this.enctype = null;
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
      this.onreset = null;
      this.onsubmit = null;
      this.style = null;
      this.styleClass = null;
      this.target = null;
      this.title = null;
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }
}
