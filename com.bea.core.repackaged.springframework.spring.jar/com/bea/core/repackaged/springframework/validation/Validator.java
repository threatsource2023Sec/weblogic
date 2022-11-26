package com.bea.core.repackaged.springframework.validation;

public interface Validator {
   boolean supports(Class var1);

   void validate(Object var1, Errors var2);
}
