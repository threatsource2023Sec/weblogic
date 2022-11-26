package org.hibernate.validator.internal.constraintvalidators.bv.number.sign;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NegativeValidatorForShort implements ConstraintValidator {
   public boolean isValid(Short value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         return NumberSignHelper.signum(value) < 0;
      }
   }
}
