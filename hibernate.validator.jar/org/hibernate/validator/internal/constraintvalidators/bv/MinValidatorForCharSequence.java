package org.hibernate.validator.internal.constraintvalidators.bv;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Min;

public class MinValidatorForCharSequence implements ConstraintValidator {
   private BigDecimal minValue;

   public void initialize(Min minValue) {
      this.minValue = BigDecimal.valueOf(minValue.value());
   }

   public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
      if (value == null) {
         return true;
      } else {
         try {
            return (new BigDecimal(value.toString())).compareTo(this.minValue) != -1;
         } catch (NumberFormatException var4) {
            return false;
         }
      }
   }
}
