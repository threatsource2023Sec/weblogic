package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;

public abstract class ValidationUtils {
   private static final Log logger = LogFactory.getLog(ValidationUtils.class);

   public static void invokeValidator(Validator validator, Object target, Errors errors) {
      invokeValidator(validator, target, errors, (Object[])null);
   }

   public static void invokeValidator(Validator validator, Object target, Errors errors, @Nullable Object... validationHints) {
      Assert.notNull(validator, (String)"Validator must not be null");
      Assert.notNull(target, "Target object must not be null");
      Assert.notNull(errors, (String)"Errors object must not be null");
      if (logger.isDebugEnabled()) {
         logger.debug("Invoking validator [" + validator + "]");
      }

      if (!validator.supports(target.getClass())) {
         throw new IllegalArgumentException("Validator [" + validator.getClass() + "] does not support [" + target.getClass() + "]");
      } else {
         if (!ObjectUtils.isEmpty(validationHints) && validator instanceof SmartValidator) {
            ((SmartValidator)validator).validate(target, errors, validationHints);
         } else {
            validator.validate(target, errors);
         }

         if (logger.isDebugEnabled()) {
            if (errors.hasErrors()) {
               logger.debug("Validator found " + errors.getErrorCount() + " errors");
            } else {
               logger.debug("Validator found no errors");
            }
         }

      }
   }

   public static void rejectIfEmpty(Errors errors, String field, String errorCode) {
      rejectIfEmpty(errors, field, errorCode, (Object[])null, (String)null);
   }

   public static void rejectIfEmpty(Errors errors, String field, String errorCode, String defaultMessage) {
      rejectIfEmpty(errors, field, errorCode, (Object[])null, defaultMessage);
   }

   public static void rejectIfEmpty(Errors errors, String field, String errorCode, Object[] errorArgs) {
      rejectIfEmpty(errors, field, errorCode, errorArgs, (String)null);
   }

   public static void rejectIfEmpty(Errors errors, String field, String errorCode, @Nullable Object[] errorArgs, @Nullable String defaultMessage) {
      Assert.notNull(errors, (String)"Errors object must not be null");
      Object value = errors.getFieldValue(field);
      if (value == null || !StringUtils.hasLength(value.toString())) {
         errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
      }

   }

   public static void rejectIfEmptyOrWhitespace(Errors errors, String field, String errorCode) {
      rejectIfEmptyOrWhitespace(errors, field, errorCode, (Object[])null, (String)null);
   }

   public static void rejectIfEmptyOrWhitespace(Errors errors, String field, String errorCode, String defaultMessage) {
      rejectIfEmptyOrWhitespace(errors, field, errorCode, (Object[])null, defaultMessage);
   }

   public static void rejectIfEmptyOrWhitespace(Errors errors, String field, String errorCode, @Nullable Object[] errorArgs) {
      rejectIfEmptyOrWhitespace(errors, field, errorCode, errorArgs, (String)null);
   }

   public static void rejectIfEmptyOrWhitespace(Errors errors, String field, String errorCode, @Nullable Object[] errorArgs, @Nullable String defaultMessage) {
      Assert.notNull(errors, (String)"Errors object must not be null");
      Object value = errors.getFieldValue(field);
      if (value == null || !StringUtils.hasText(value.toString())) {
         errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
      }

   }
}
