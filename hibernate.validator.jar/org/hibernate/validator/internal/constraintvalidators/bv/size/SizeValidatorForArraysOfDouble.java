package org.hibernate.validator.internal.constraintvalidators.bv.size;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SizeValidatorForArraysOfDouble extends SizeValidatorForArraysOfPrimitives implements ConstraintValidator {
   public boolean isValid(double[] array, ConstraintValidatorContext constraintValidatorContext) {
      if (array == null) {
         return true;
      } else {
         return array.length >= this.min && array.length <= this.max;
      }
   }
}
