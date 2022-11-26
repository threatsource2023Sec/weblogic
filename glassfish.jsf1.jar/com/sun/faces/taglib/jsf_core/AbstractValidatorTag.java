package com.sun.faces.taglib.jsf_core;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.webapp.ValidatorELTag;
import javax.servlet.jsp.JspException;

public class AbstractValidatorTag extends ValidatorELTag {
   private static final Logger LOGGER;
   protected ValueExpression binding = null;
   protected ValueExpression validatorId = null;

   public void setBinding(ValueExpression binding) {
      this.binding = binding;
   }

   public void setValidatorId(ValueExpression validatorId) {
      this.validatorId = validatorId;
   }

   protected Validator createValidator() throws JspException {
      try {
         return createValidator(this.validatorId, this.binding, FacesContext.getCurrentInstance());
      } catch (FacesException var2) {
         throw new JspException(var2.getCause());
      }
   }

   protected static Validator createValidator(ValueExpression validatorId, ValueExpression binding, FacesContext facesContext) {
      ELContext elContext = facesContext.getELContext();
      Validator validator = null;
      if (binding != null) {
         try {
            validator = (Validator)binding.getValue(elContext);
            if (validator != null) {
               return validator;
            }
         } catch (Exception var7) {
            throw new FacesException(var7);
         }
      }

      if (validatorId != null) {
         try {
            String validatorIdVal = (String)validatorId.getValue(elContext);
            validator = facesContext.getApplication().createValidator(validatorIdVal);
            if (validator != null && binding != null) {
               binding.setValue(elContext, validator);
            }
         } catch (Exception var6) {
            throw new FacesException(var6);
         }
      }

      if (validator == null && LOGGER.isLoggable(Level.WARNING)) {
         LOGGER.log(Level.WARNING, MessageUtils.getExceptionMessageString("com.sun.faces.CANNOT_VALIDATE", validatorId != null ? validatorId.getExpressionString() : "", binding != null ? binding.getExpressionString() : ""));
      }

      return validator;
   }

   static {
      LOGGER = FacesLogger.TAGLIB.getLogger();
   }
}
