package org.hibernate.validator.internal.constraintvalidators.bv.number.sign;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NegativeValidatorForBigDecimal implements ConstraintValidator {
   public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         return NumberSignHelper.signum(value) < 0;
      }
   }
}
