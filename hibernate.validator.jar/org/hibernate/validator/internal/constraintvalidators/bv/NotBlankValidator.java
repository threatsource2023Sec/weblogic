package org.hibernate.validator.internal.constraintvalidators.bv;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankValidator implements ConstraintValidator {
   public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
      if (charSequence == null) {
         return false;
      } else {
         return charSequence.toString().trim().length() > 0;
      }
   }
}
