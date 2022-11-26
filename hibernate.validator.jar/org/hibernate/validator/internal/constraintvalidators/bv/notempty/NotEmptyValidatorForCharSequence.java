package org.hibernate.validator.internal.constraintvalidators.bv.notempty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValidatorForCharSequence implements ConstraintValidator {
   public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
      if (charSequence == null) {
         return false;
      } else {
         return charSequence.length() > 0;
      }
   }
}
