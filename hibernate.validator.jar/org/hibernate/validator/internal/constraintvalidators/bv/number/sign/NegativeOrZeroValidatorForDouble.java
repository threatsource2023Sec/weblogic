package org.hibernate.validator.internal.constraintvalidators.bv.number.sign;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;

public class NegativeOrZeroValidatorForDouble implements ConstraintValidator {
   public boolean isValid(Double value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         return NumberSignHelper.signum(value, InfinityNumberComparatorHelper.GREATER_THAN) <= 0;
      }
   }
}
