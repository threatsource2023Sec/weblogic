package org.hibernate.validator.internal.constraintvalidators.bv.money;

import java.math.BigDecimal;
import javax.money.MonetaryAmount;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Max;

public class MaxValidatorForMonetaryAmount implements ConstraintValidator {
   private BigDecimal maxValue;

   public void initialize(Max maxValue) {
      this.maxValue = BigDecimal.valueOf(maxValue.value());
   }

   public boolean isValid(MonetaryAmount value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         return ((BigDecimal)value.getNumber().numberValueExact(BigDecimal.class)).compareTo(this.maxValue) != 1;
      }
   }
}
