package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface SmartValidator extends Validator {
   void validate(Object var1, Errors var2, Object... var3);

   default void validateValue(Class targetType, String fieldName, @Nullable Object value, Errors errors, Object... validationHints) {
      throw new IllegalArgumentException("Cannot validate individual value for " + targetType);
   }
}
