package com.sun.faces.taglib.html_basic;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.MethodExpressionValueChangeListener;
import javax.faces.validator.MethodExpressionValidator;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class InputTextareaTag extends UIComponentELTag {
   private ValueExpression converter;
   private ValueExpression converterMessage;
   private ValueExpression immediate;
   private ValueExpression required;
   private ValueExpression requiredMessage;
   private MethodExpression validator;
   private ValueExpression validatorMessage;
   private ValueExpression value;
   private MethodExpression valueChangeListener;
   private ValueExpression accesskey;
   private ValueExpression cols;
   private ValueExpression dir;
   private ValueExpression disabled;
   private ValueExpression label;
   private ValueExpression lang;
   private ValueExpression onblur;
   private ValueExpression onchange;
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
   private ValueExpression onselect;
   private ValueExpression readonly;
   private ValueExpression rows;
   private ValueExpression style;
   private ValueExpression styleClass;
   private ValueExpression tabindex;
   private ValueExpression title;

   public void setConverter(ValueExpression converter) {
      this.converter = converter;
   }

   public void setConverterMessage(ValueExpression converterMessage) {
      this.converterMessage = converterMessage;
   }

   public void setImmediate(ValueExpression immediate) {
      this.immediate = immediate;
   }

   public void setRequired(ValueExpression required) {
      this.required = required;
   }

   public void setRequiredMessage(ValueExpression requiredMessage) {
      this.requiredMessage = requiredMessage;
   }

   public void setValidator(MethodExpression validator) {
      this.validator = validator;
   }

   public void setValidatorMessage(ValueExpression validatorMessage) {
      this.validatorMessage = validatorMessage;
   }

   public void setValue(ValueExpression value) {
      this.value = value;
   }

   public void setValueChangeListener(MethodExpression valueChangeListener) {
      this.valueChangeListener = valueChangeListener;
   }

   public void setAccesskey(ValueExpression accesskey) {
      this.accesskey = accesskey;
   }

   public void setCols(ValueExpression cols) {
      this.cols = cols;
   }

   public void setDir(ValueExpression dir) {
      this.dir = dir;
   }

   public void setDisabled(ValueExpression disabled) {
      this.disabled = disabled;
   }

   public void setLabel(ValueExpression label) {
      this.label = label;
   }

   public void setLang(ValueExpression lang) {
      this.lang = lang;
   }

   public void setOnblur(ValueExpression onblur) {
      this.onblur = onblur;
   }

   public void setOnchange(ValueExpression onchange) {
      this.onchange = onchange;
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

   public void setOnselect(ValueExpression onselect) {
      this.onselect = onselect;
   }

   public void setReadonly(ValueExpression readonly) {
      this.readonly = readonly;
   }

   public void setRows(ValueExpression rows) {
      this.rows = rows;
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
      return "javax.faces.Textarea";
   }

   public String getComponentType() {
      return "javax.faces.HtmlInputTextarea";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      UIInput input = null;

      try {
         input = (UIInput)component;
      } catch (ClassCastException var4) {
         throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: javax.faces.component.UIInput.  Perhaps you're missing a tag?");
      }

      if (this.converter != null) {
         if (!this.converter.isLiteralText()) {
            input.setValueExpression("converter", this.converter);
         } else {
            Converter conv = FacesContext.getCurrentInstance().getApplication().createConverter(this.converter.getExpressionString());
            input.setConverter(conv);
         }
      }

      if (this.converterMessage != null) {
         input.setValueExpression("converterMessage", this.converterMessage);
      }

      if (this.immediate != null) {
         input.setValueExpression("immediate", this.immediate);
      }

      if (this.required != null) {
         input.setValueExpression("required", this.required);
      }

      if (this.requiredMessage != null) {
         input.setValueExpression("requiredMessage", this.requiredMessage);
      }

      if (this.validator != null) {
         input.addValidator(new MethodExpressionValidator(this.validator));
      }

      if (this.validatorMessage != null) {
         input.setValueExpression("validatorMessage", this.validatorMessage);
      }

      if (this.value != null) {
         input.setValueExpression("value", this.value);
      }

      if (this.valueChangeListener != null) {
         input.addValueChangeListener(new MethodExpressionValueChangeListener(this.valueChangeListener));
      }

      if (this.accesskey != null) {
         input.setValueExpression("accesskey", this.accesskey);
      }

      if (this.cols != null) {
         input.setValueExpression("cols", this.cols);
      }

      if (this.dir != null) {
         input.setValueExpression("dir", this.dir);
      }

      if (this.disabled != null) {
         input.setValueExpression("disabled", this.disabled);
      }

      if (this.label != null) {
         input.setValueExpression("label", this.label);
      }

      if (this.lang != null) {
         input.setValueExpression("lang", this.lang);
      }

      if (this.onblur != null) {
         input.setValueExpression("onblur", this.onblur);
      }

      if (this.onchange != null) {
         input.setValueExpression("onchange", this.onchange);
      }

      if (this.onclick != null) {
         input.setValueExpression("onclick", this.onclick);
      }

      if (this.ondblclick != null) {
         input.setValueExpression("ondblclick", this.ondblclick);
      }

      if (this.onfocus != null) {
         input.setValueExpression("onfocus", this.onfocus);
      }

      if (this.onkeydown != null) {
         input.setValueExpression("onkeydown", this.onkeydown);
      }

      if (this.onkeypress != null) {
         input.setValueExpression("onkeypress", this.onkeypress);
      }

      if (this.onkeyup != null) {
         input.setValueExpression("onkeyup", this.onkeyup);
      }

      if (this.onmousedown != null) {
         input.setValueExpression("onmousedown", this.onmousedown);
      }

      if (this.onmousemove != null) {
         input.setValueExpression("onmousemove", this.onmousemove);
      }

      if (this.onmouseout != null) {
         input.setValueExpression("onmouseout", this.onmouseout);
      }

      if (this.onmouseover != null) {
         input.setValueExpression("onmouseover", this.onmouseover);
      }

      if (this.onmouseup != null) {
         input.setValueExpression("onmouseup", this.onmouseup);
      }

      if (this.onselect != null) {
         input.setValueExpression("onselect", this.onselect);
      }

      if (this.readonly != null) {
         input.setValueExpression("readonly", this.readonly);
      }

      if (this.rows != null) {
         input.setValueExpression("rows", this.rows);
      }

      if (this.style != null) {
         input.setValueExpression("style", this.style);
      }

      if (this.styleClass != null) {
         input.setValueExpression("styleClass", this.styleClass);
      }

      if (this.tabindex != null) {
         input.setValueExpression("tabindex", this.tabindex);
      }

      if (this.title != null) {
         input.setValueExpression("title", this.title);
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
      this.converterMessage = null;
      this.immediate = null;
      this.required = null;
      this.requiredMessage = null;
      this.validator = null;
      this.validatorMessage = null;
      this.value = null;
      this.valueChangeListener = null;
      this.accesskey = null;
      this.cols = null;
      this.dir = null;
      this.disabled = null;
      this.label = null;
      this.lang = null;
      this.onblur = null;
      this.onchange = null;
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
      this.onselect = null;
      this.readonly = null;
      this.rows = null;
      this.style = null;
      this.styleClass = null;
      this.tabindex = null;
      this.title = null;
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }
}
