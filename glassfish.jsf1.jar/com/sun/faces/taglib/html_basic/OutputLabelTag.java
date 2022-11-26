package com.sun.faces.taglib.html_basic;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class OutputLabelTag extends UIComponentELTag {
   private ValueExpression converter;
   private ValueExpression value;
   private ValueExpression accesskey;
   private ValueExpression dir;
   private ValueExpression escape;
   private ValueExpression _for;
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
   private ValueExpression style;
   private ValueExpression styleClass;
   private ValueExpression tabindex;
   private ValueExpression title;

   public void setConverter(ValueExpression converter) {
      this.converter = converter;
   }

   public void setValue(ValueExpression value) {
      this.value = value;
   }

   public void setAccesskey(ValueExpression accesskey) {
      this.accesskey = accesskey;
   }

   public void setDir(ValueExpression dir) {
      this.dir = dir;
   }

   public void setEscape(ValueExpression escape) {
      this.escape = escape;
   }

   public void setFor(ValueExpression _for) {
      this._for = _for;
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

   public void setStyle(ValueExpression style) {
      this.style = style;
   }

   public void setStyleClass(ValueExpression styleClass) {
      this.styleClass = styleClass;
   }

   public void setTabindex(ValueExpression tabindex) {
      this.tabindex = tabindex;
   }

   public void setTitle(ValueExpression title) {
      this.title = title;
   }

   public String getRendererType() {
      return "javax.faces.Label";
   }

   public String getComponentType() {
      return "javax.faces.HtmlOutputLabel";
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

      if (this.accesskey != null) {
         output.setValueExpression("accesskey", this.accesskey);
      }

      if (this.dir != null) {
         output.setValueExpression("dir", this.dir);
      }

      if (this.escape != null) {
         output.setValueExpression("escape", this.escape);
      }

      if (this._for != null) {
         output.setValueExpression("for", this._for);
      }

      if (this.lang != null) {
         output.setValueExpression("lang", this.lang);
      }

      if (this.onblur != null) {
         output.setValueExpression("onblur", this.onblur);
      }

      if (this.onclick != null) {
         output.setValueExpression("onclick", this.onclick);
      }

      if (this.ondblclick != null) {
         output.setValueExpression("ondblclick", this.ondblclick);
      }

      if (this.onfocus != null) {
         output.setValueExpression("onfocus", this.onfocus);
      }

      if (this.onkeydown != null) {
         output.setValueExpression("onkeydown", this.onkeydown);
      }

      if (this.onkeypress != null) {
         output.setValueExpression("onkeypress", this.onkeypress);
      }

      if (this.onkeyup != null) {
         output.setValueExpression("onkeyup", this.onkeyup);
      }

      if (this.onmousedown != null) {
         output.setValueExpression("onmousedown", this.onmousedown);
      }

      if (this.onmousemove != null) {
         output.setValueExpression("onmousemove", this.onmousemove);
      }

      if (this.onmouseout != null) {
         output.setValueExpression("onmouseout", this.onmouseout);
      }

      if (this.onmouseover != null) {
         output.setValueExpression("onmouseover", this.onmouseover);
      }

      if (this.onmouseup != null) {
         output.setValueExpression("onmouseup", this.onmouseup);
      }

      if (this.style != null) {
         output.setValueExpression("style", this.style);
      }

      if (this.styleClass != null) {
         output.setValueExpression("styleClass", this.styleClass);
      }

      if (this.tabindex != null) {
         output.setValueExpression("tabindex", this.tabindex);
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
      this.accesskey = null;
      this.dir = null;
      this.escape = null;
      this._for = null;
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
      this.style = null;
      this.styleClass = null;
      this.tabindex = null;
      this.title = null;
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }
}
