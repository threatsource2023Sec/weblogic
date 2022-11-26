package com.bea.core.repackaged.springframework.validation;

import com.bea.core.repackaged.springframework.beans.PropertyAccessException;

public interface BindingErrorProcessor {
   void processMissingFieldError(String var1, BindingResult var2);

   void processPropertyAccessException(PropertyAccessException var1, BindingResult var2);
}
