package org.hibernate.validator.internal.constraintvalidators.bv.money;

import javax.money.MonetaryAmount;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NegativeValidatorForMonetaryAmount implements ConstraintValidator {
   public boolean isValid(MonetaryAmount value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         return value.signum() < 0;
      }
   }
}
