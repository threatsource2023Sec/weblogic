package com.sun.faces.taglib.jsf_core;

import com.sun.faces.util.MessageUtils;
import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.jsp.JspException;

public class ValidatorTag extends AbstractValidatorTag {
   private static final long serialVersionUID = -2450754172058855404L;

   protected Validator createValidator() throws JspException {
      return (Validator)(this.validatorId != null && this.validatorId.isLiteralText() ? createValidator(this.validatorId, this.binding, FacesContext.getCurrentInstance()) : new BindingValidator(this.validatorId, this.binding));
   }

   public static class BindingValidator implements Validator, StateHolder {
      private ValueExpression binding;
      private ValueExpression validatorId;
      private Object[] state;

      public BindingValidator() {
      }

      public BindingValidator(ValueExpression validatorId, ValueExpression binding) {
         this.validatorId = validatorId;
         this.binding = binding;
      }

      public Object saveState(FacesContext context) {
         if (context == null) {
            throw new NullPointerException();
         } else {
            if (this.state == null) {
               this.state = new Object[2];
            }

            this.state[0] = this.validatorId;
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
               this.validatorId = (ValueExpression)this.state[0];
               this.binding = (ValueExpression)this.state[1];
            }

         }
      }

      public boolean isTransient() {
         return false;
      }

      public void setTransient(boolean newTransientValue) {
      }

      public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
         Validator instance = AbstractValidatorTag.createValidator(this.validatorId, this.binding, context);
         if (instance != null) {
            instance.validate(context, component, value);
         } else {
            throw new ValidatorException(MessageUtils.getExceptionMessage("com.sun.faces.CANNOT_VALIDATE", this.validatorId != null ? this.validatorId.getExpressionString() : "", this.binding != null ? this.binding.getExpressionString() : ""));
         }
      }
   }
}
