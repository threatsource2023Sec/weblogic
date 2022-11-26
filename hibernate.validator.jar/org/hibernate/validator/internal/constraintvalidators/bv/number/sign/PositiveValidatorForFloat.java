package org.hibernate.validator.internal.constraintvalidators.bv.number.sign;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;

public class PositiveValidatorForFloat implements ConstraintValidator {
   public boolean isValid(Float value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         return NumberSignHelper.signum(value, InfinityNumberComparatorHelper.LESS_THAN) > 0;
      }
   }
}
