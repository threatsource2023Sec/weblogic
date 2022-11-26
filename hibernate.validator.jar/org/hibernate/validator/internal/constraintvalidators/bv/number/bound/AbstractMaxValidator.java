package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Max;

public abstract class AbstractMaxValidator implements ConstraintValidator {
   protected long maxValue;

   public void initialize(Max maxValue) {
      this.maxValue = maxValue.value();
   }

   public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
      if (value == null) {
         return true;
      } else {
         return this.compare(value) <= 0;
      }
   }

   protected abstract int compare(Object var1);
}
