package com.sun.faces.taglib.jsf_core;

import com.sun.faces.util.MessageUtils;
import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.servlet.jsp.JspException;

public class ConverterTag extends AbstractConverterTag {
   private static final long serialVersionUID = -7044710401705704550L;

   protected Converter createConverter() throws JspException {
      return (Converter)(this.converterId != null && this.converterId.isLiteralText() ? createConverter(this.converterId, this.binding, FacesContext.getCurrentInstance()) : new BindingConverter(this.converterId, this.binding));
   }

   public static class BindingConverter implements Converter, StateHolder {
      ValueExpression converterId;
      ValueExpression binding;
      private Object[] state;

      public BindingConverter() {
      }

      public BindingConverter(ValueExpression converterId, ValueExpression binding) {
         this.converterId = converterId;
         this.binding = binding;
      }

      public Object getAsObject(FacesContext context, UIComponent component, String value) {
         Converter delegate = this.getDelegate(context);
         if (delegate != null) {
            return delegate.getAsObject(context, component, value);
         } else {
            throw new ConverterException(MessageUtils.getExceptionMessage("com.sun.faces.CANNOT_CONVERT", this.converterId != null ? this.converterId.getExpressionString() : "", this.binding != null ? this.binding.getExpressionString() : ""));
         }
      }

      public String getAsString(FacesContext context, UIComponent component, Object value) {
         Converter delegate = this.getDelegate(context);
         if (delegate != null) {
            return delegate.getAsString(context, component, value);
         } else {
            throw new ConverterException(MessageUtils.getExceptionMessage("com.sun.faces.CANNOT_CONVERT", this.converterId != null ? this.converterId.getExpressionString() : "", this.binding != null ? this.binding.getExpressionString() : ""));
         }
      }

      public Object saveState(FacesContext context) {
         if (context == null) {
            throw new NullPointerException();
         } else {
            if (this.state == null) {
               this.state = new Object[2];
            }

            this.state[0] = this.converterId;
            this.state[1] = this.binding;
            return this.state;
         }
      }

      public void restoreState(FacesContext context, Object state) {
         if (context == null) {
            throw new NullPointerException();
         } else {
            this.state = (Object[])((Object[])state);
            if (this.state != null) {
               this.converterId = (ValueExpression)this.state[0];
               this.binding = (ValueExpression)this.state[1];
            }

         }
      }

      public boolean isTransient() {
         return false;
      }

      public void setTransient(boolean newTransientValue) {
      }

      private Converter getDelegate(FacesContext context) {
         return AbstractConverterTag.createConverter(this.converterId, this.binding, context);
      }
   }
}
