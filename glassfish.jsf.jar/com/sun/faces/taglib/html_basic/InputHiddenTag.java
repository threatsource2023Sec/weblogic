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

public class InputHiddenTag extends UIComponentELTag {
   private ValueExpression converter;
   private ValueExpression converterMessage;
   private ValueExpression immediate;
   private ValueExpression required;
   private ValueExpression requiredMessage;
   private MethodExpression validator;
   private ValueExpression validatorMessage;
   private ValueExpression value;
   private MethodExpression valueChangeListener;

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

   public String getRendererType() {
      return "javax.faces.Hidden";
   }

   public String getComponentType() {
      return "javax.faces.HtmlInputHidden";
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
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }
}
