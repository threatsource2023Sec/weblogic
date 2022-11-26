package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Min;

public abstract class AbstractMinValidator implements ConstraintValidator {
   protected long minValue;

   public void initialize(Min maxValue) {
      this.minValue = maxValue.value();
   }

   public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
      if (value == null) {
         return true;
      } else {
         return this.compare(value) >= 0;
      }
   }

   protected abstract int compare(Object var1);
}
