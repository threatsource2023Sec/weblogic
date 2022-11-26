package com.sun.faces.taglib.html_basic;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.MethodExpressionValueChangeListener;
import javax.faces.validator.MethodExpressionValidator;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class SelectManyMenuTag extends UIComponentELTag {
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
   private ValueExpression dir;
   private ValueExpression disabled;
   private ValueExpression disabledClass;
   private ValueExpression enabledClass;
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

   public void setDir(ValueExpression dir) {
      this.dir = dir;
   }

   public void setDisabled(ValueExpression disabled) {
      this.disabled = disabled;
   }

   public void setDisabledClass(ValueExpression disabledClass) {
      this.disabledClass = disabledClass;
   }

   public void setEnabledClass(ValueExpression enabledClass) {
      this.enabledClass = enabledClass;
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
      return "javax.faces.Menu";
   }

   public String getComponentType() {
      return "javax.faces.HtmlSelectManyMenu";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      UISelectMany selectmany = null;

      try {
         selectmany = (UISelectMany)component;
      } catch (ClassCastException var4) {
         throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: javax.faces.component.UISelectMany.  Perhaps you're missing a tag?");
      }

      if (this.converter != null) {
         if (!this.converter.isLiteralText()) {
            selectmany.setValueExpression("converter", this.converter);
         } else {
            Converter conv = FacesContext.getCurrentInstance().getApplication().createConverter(this.converter.getExpressionString());
            selectmany.setConverter(conv);
         }
      }

      if (this.converterMessage != null) {
         selectmany.setValueExpression("converterMessage", this.converterMessage);
      }

      if (this.immediate != null) {
         selectmany.setValueExpression("immediate", this.immediate);
      }

      if (this.required != null) {
         selectmany.setValueExpression("required", this.required);
      }

      if (this.requiredMessage != null) {
         selectmany.setValueExpression("requiredMessage", this.requiredMessage);
      }

      if (this.validator != null) {
         selectmany.addValidator(new MethodExpressionValidator(this.validator));
      }

      if (this.validatorMessage != null) {
         selectmany.setValueExpression("validatorMessage", this.validatorMessage);
      }

      if (this.value != null) {
         selectmany.setValueExpression("value", this.value);
      }

      if (this.valueChangeListener != null) {
         selectmany.addValueChangeListener(new MethodExpressionValueChangeListener(this.valueChangeListener));
      }

      if (this.accesskey != null) {
         selectmany.setValueExpression("accesskey", this.accesskey);
      }

      if (this.dir != null) {
         selectmany.setValueExpression("dir", this.dir);
      }

      if (this.disabled != null) {
         selectmany.setValueExpression("disabled", this.disabled);
      }

      if (this.disabledClass != null) {
         selectmany.setValueExpression("disabledClass", this.disabledClass);
      }

      if (this.enabledClass != null) {
         selectmany.setValueExpression("enabledClass", this.enabledClass);
      }

      if (this.label != null) {
         selectmany.setValueExpression("label", this.label);
      }

      if (this.lang != null) {
         selectmany.setValueExpression("lang", this.lang);
      }

      if (this.onblur != null) {
         selectmany.setValueExpression("onblur", this.onblur);
      }

      if (this.onchange != null) {
         selectmany.setValueExpression("onchange", this.onchange);
      }

      if (this.onclick != null) {
         selectmany.setValueExpression("onclick", this.onclick);
      }

      if (this.ondblclick != null) {
         selectmany.setValueExpression("ondblclick", this.ondblclick);
      }

      if (this.onfocus != null) {
         selectmany.setValueExpression("onfocus", this.onfocus);
      }

      if (this.onkeydown != null) {
         selectmany.setValueExpression("onkeydown", this.onkeydown);
      }

      if (this.onkeypress != null) {
         selectmany.setValueExpression("onkeypress", this.onkeypress);
      }

      if (this.onkeyup != null) {
         selectmany.setValueExpression("onkeyup", this.onkeyup);
      }

      if (this.onmousedown != null) {
         selectmany.setValueExpression("onmousedown", this.onmousedown);
      }

      if (this.onmousemove != null) {
         selectmany.setValueExpression("onmousemove", this.onmousemove);
      }

      if (this.onmouseout != null) {
         selectmany.setValueExpression("onmouseout", this.onmouseout);
      }

      if (this.onmouseover != null) {
         selectmany.setValueExpression("onmouseover", this.onmouseover);
      }

      if (this.onmouseup != null) {
         selectmany.setValueExpression("onmouseup", this.onmouseup);
      }

      if (this.onselect != null) {
         selectmany.setValueExpression("onselect", this.onselect);
      }

      if (this.readonly != null) {
         selectmany.setValueExpression("readonly", this.readonly);
      }

      if (this.style != null) {
         selectmany.setValueExpression("style", this.style);
      }

      if (this.styleClass != null) {
         selectmany.setValueExpression("styleClass", this.styleClass);
      }

      if (this.tabindex != null) {
         selectmany.setValueExpression("tabindex", this.tabindex);
      }

      if (this.title != null) {
         selectmany.setValueExpression("title", this.title);
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
      this.dir = null;
      this.disabled = null;
      this.disabledClass = null;
      this.enabledClass = null;
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
      this.style = null;
      this.styleClass = null;
      this.tabindex = null;
      this.title = null;
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }
}
