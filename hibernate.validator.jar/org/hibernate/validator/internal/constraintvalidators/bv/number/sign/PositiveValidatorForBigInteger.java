package org.hibernate.validator.internal.constraintvalidators.bv.number.sign;

import java.math.BigInteger;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositiveValidatorForBigInteger implements ConstraintValidator {
   public boolean isValid(BigInteger value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         return NumberSignHelper.signum(value) > 0;
      }
   }
}
