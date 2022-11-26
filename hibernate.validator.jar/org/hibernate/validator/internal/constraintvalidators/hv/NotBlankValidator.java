package org.hibernate.validator.internal.constraintvalidators.hv;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankValidator implements ConstraintValidator {
   public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
      if (charSequence == null) {
         return true;
      } else {
         return charSequence.toString().trim().length() > 0;
      }
   }
}
