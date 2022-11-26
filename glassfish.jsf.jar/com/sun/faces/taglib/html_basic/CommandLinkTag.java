package com.sun.faces.taglib.html_basic;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class CommandLinkTag extends UIComponentELTag {
   private MethodExpression action;
   private MethodExpression actionListener;
   private ValueExpression immediate;
   private ValueExpression value;
   private ValueExpression accesskey;
   private ValueExpression charset;
   private ValueExpression coords;
   private ValueExpression dir;
   private ValueExpression disabled;
   private ValueExpression hreflang;
   private ValueExpression lang;
   private ValueExpression onblur;
   private ValueExpression onclick;
   private ValueExpression ondblclick;
   private ValueExpression onfocus;
   private ValueExpression onkeydown;
   private ValueExpression onkeypress;
   private ValueExpression onkeyup;
   private ValueExpression onmousedown;
   private ValueExpression onmousemove;
   private ValueExpression onmouseout;
   private ValueExpression onmouseover;
   private ValueExpression onmouseup;
   private ValueExpression rel;
   private ValueExpression rev;
   private ValueExpression role;
   private ValueExpression shape;
   private ValueExpression style;
   private ValueExpression styleClass;
   private ValueExpression tabindex;
   private ValueExpression target;
   private ValueExpression title;
   private ValueExpression type;

   public void setAction(MethodExpression action) {
      this.action = action;
   }

   public void setActionListener(MethodExpression actionListener) {
      this.actionListener = actionListener;
   }

   public void setImmediate(ValueExpression immediate) {
      this.immediate = immediate;
   }

   public void setValue(ValueExpression value) {
      this.value = value;
   }

   public void setAccesskey(ValueExpression accesskey) {
      this.accesskey = accesskey;
   }

   public void setCharset(ValueExpression charset) {
      this.charset = charset;
   }

   public void setCoords(ValueExpression coords) {
      this.coords = coords;
   }

   public void setDir(ValueExpression dir) {
      this.dir = dir;
   }

   public void setDisabled(ValueExpression disabled) {
      this.disabled = disabled;
   }

   public void setHreflang(ValueExpression hreflang) {
      this.hreflang = hreflang;
   }

   public void setLang(ValueExpression lang) {
      this.lang = lang;
   }

   public void setOnblur(ValueExpression onblur) {
      this.onblur = onblur;
   }

   public void setOnclick(ValueExpression onclick) {
      this.onclick = onclick;
   }

   public void setOndblclick(ValueExpression ondblclick) {
      this.ondblclick = ondblclick;
   }

   public void setOnfocus(ValueExpression onfocus) {
      this.onfocus = onfocus;
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

   public void setRel(ValueExpression rel) {
      this.rel = rel;
   }

   public void setRev(ValueExpression rev) {
      this.rev = rev;
   }

   public void setRole(ValueExpression role) {
      this.role = role;
   }

   public void setShape(ValueExpression shape) {
      this.shape = shape;
   }

   public void setStyle(ValueExpression style) {
      this.style = style;
   }

   public void setStyleClass(ValueExpression styleClass) {
      this.styleClass = styleClass;
   }

   public void setTabindex(ValueExpression tabindex) {
      this.tabindex = tabindex;
   }

   public void setTarget(ValueExpression target) {
      this.target = target;
   }

   public void setTitle(ValueExpression title) {
      this.title = title;
   }

   public void setType(ValueExpression type) {
      this.type = type;
   }

   public String getRendererType() {
      return "javax.faces.Link";
   }

   public String getComponentType() {
      return "javax.faces.HtmlCommandLink";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      UICommand command = null;

      try {
         command = (UICommand)component;
      } catch (ClassCastException var4) {
         throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: javax.faces.component.UICommand.  Perhaps you're missing a tag?");
      }

      if (this.action != null) {
         command.setActionExpression(this.action);
      }

      if (this.actionListener != null) {
         command.addActionListener(new MethodExpressionActionListener(this.actionListener));
      }

      if (this.immediate != null) {
         command.setValueExpression("immediate", this.immediate);
      }

      if (this.value != null) {
         command.setValueExpression("value", this.value);
      }

      if (this.accesskey != null) {
         command.setValueExpression("accesskey", this.accesskey);
      }

      if (this.charset != null) {
         command.setValueExpression("charset", this.charset);
      }

      if (this.coords != null) {
         command.setValueExpression("coords", this.coords);
      }

      if (this.dir != null) {
         command.setValueExpression("dir", this.dir);
      }

      if (this.disabled != null) {
         command.setValueExpression("disabled", this.disabled);
      }

      if (this.hreflang != null) {
         command.setValueExpression("hreflang", this.hreflang);
      }

      if (this.lang != null) {
         command.setValueExpression("lang", this.lang);
      }

      if (this.onblur != null) {
         command.setValueExpression("onblur", this.onblur);
      }

      if (this.onclick != null) {
         command.setValueExpression("onclick", this.onclick);
      }

      if (this.ondblclick != null) {
         command.setValueExpression("ondblclick", this.ondblclick);
      }

      if (this.onfocus != null) {
         command.setValueExpression("onfocus", this.onfocus);
      }

      if (this.onkeydown != null) {
         command.setValueExpression("onkeydown", this.onkeydown);
      }

      if (this.onkeypress != null) {
         command.setValueExpression("onkeypress", this.onkeypress);
      }

      if (this.onkeyup != null) {
         command.setValueExpression("onkeyup", this.onkeyup);
      }

      if (this.onmousedown != null) {
         command.setValueExpression("onmousedown", this.onmousedown);
      }

      if (this.onmousemove != null) {
         command.setValueExpression("onmousemove", this.onmousemove);
      }

      if (this.onmouseout != null) {
         command.setValueExpression("onmouseout", this.onmouseout);
      }

      if (this.onmouseover != null) {
         command.setValueExpression("onmouseover", this.onmouseover);
      }

      if (this.onmouseup != null) {
         command.setValueExpression("onmouseup", this.onmouseup);
      }

      if (this.rel != null) {
         command.setValueExpression("rel", this.rel);
      }

      if (this.rev != null) {
         command.setValueExpression("rev", this.rev);
      }

      if (this.role != null) {
         command.setValueExpression("role", this.role);
      }

      if (this.shape != null) {
         command.setValueExpression("shape", this.shape);
      }

      if (this.style != null) {
         command.setValueExpression("style", this.style);
      }

      if (this.styleClass != null) {
         command.setValueExpression("styleClass", this.styleClass);
      }

      if (this.tabindex != null) {
         command.setValueExpression("tabindex", this.tabindex);
      }

      if (this.target != null) {
         command.setValueExpression("target", this.target);
      }

      if (this.title != null) {
         command.setValueExpression("title", this.title);
      }

      if (this.type != null) {
         command.setValueExpression("type", this.type);
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
      this.action = null;
      this.actionListener = null;
      this.immediate = null;
      this.value = null;
      this.accesskey = null;
      this.charset = null;
      this.coords = null;
      this.dir = null;
      this.disabled = null;
      this.hreflang = null;
      this.lang = null;
      this.onblur = null;
      this.onclick = null;
      this.ondblclick = null;
      this.onfocus = null;
      this.onkeydown = null;
      this.onkeypress = null;
      this.onkeyup = null;
      this.onmousedown = null;
      this.onmousemove = null;
      this.onmouseout = null;
      this.onmouseover = null;
      this.onmouseup = null;
      this.rel = null;
      this.rev = null;
      this.role = null;
      this.shape = null;
      this.style = null;
      this.styleClass = null;
      this.tabindex = null;
      this.target = null;
      this.title = null;
      this.type = null;
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }
}
