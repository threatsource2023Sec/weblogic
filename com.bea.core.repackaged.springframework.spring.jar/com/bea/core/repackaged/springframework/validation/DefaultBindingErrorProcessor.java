package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.beans.PropertyAccessException;
import com.bea.core.repackaged.springframework.context.support.DefaultMessageSourceResolvable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;

public class DefaultBindingErrorProcessor implements BindingErrorProcessor {
   public static final String MISSING_FIELD_ERROR_CODE = "required";

   public void processMissingFieldError(String missingField, BindingResult bindingResult) {
      String fixedField = bindingResult.getNestedPath() + missingField;
      String[] codes = bindingResult.resolveMessageCodes("required", missingField);
      Object[] arguments = this.getArgumentsForBindError(bindingResult.getObjectName(), fixedField);
      FieldError error = new FieldError(bindingResult.getObjectName(), fixedField, "", true, codes, arguments, "Field '" + fixedField + "' is required");
      bindingResult.addError(error);
   }

   public void processPropertyAccessException(PropertyAccessException ex, BindingResult bindingResult) {
      String field = ex.getPropertyName();
      Assert.state(field != null, "No field in exception");
      String[] codes = bindingResult.resolveMessageCodes(ex.getErrorCode(), field);
      Object[] arguments = this.getArgumentsForBindError(bindingResult.getObjectName(), field);
      Object rejectedValue = ex.getValue();
      if (ObjectUtils.isArray(rejectedValue)) {
         rejectedValue = StringUtils.arrayToCommaDelimitedString(ObjectUtils.toObjectArray(rejectedValue));
      }

      FieldError error = new FieldError(bindingResult.getObjectName(), field, rejectedValue, true, codes, arguments, ex.getLocalizedMessage());
      error.wrap(ex);
      bindingResult.addError(error);
   }

   protected Object[] getArgumentsForBindError(String objectName, String field) {
      String[] codes = new String[]{objectName + "." + field, field};
      return new Object[]{new DefaultMessageSourceResolvable(codes, field)};
   }
}
