package org.hibernate.validator.internal.constraintvalidators.bv.notempty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValidatorForArray implements ConstraintValidator {
   public boolean isValid(Object[] array, ConstraintValidatorContext constraintValidatorContext) {
      if (array == null) {
         return false;
      } else {
         return array.length > 0;
      }
   }
}
