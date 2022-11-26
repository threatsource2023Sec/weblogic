package org.hibernate.validator.internal.constraintvalidators.bv;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Max;

public class MaxValidatorForCharSequence implements ConstraintValidator {
   private BigDecimal maxValue;

   public void initialize(Max maxValue) {
      this.maxValue = BigDecimal.valueOf(maxValue.value());
   }

   public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
      if (value == null) {
         return true;
      } else {
         try {
            return (new BigDecimal(value.toString())).compareTo(this.maxValue) != 1;
         } catch (NumberFormatException var4) {
            return false;
         }
      }
   }
}
