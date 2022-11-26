package org.hibernate.validator.internal.constraintvalidators.bv;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AssertFalseValidator implements ConstraintValidator {
   public boolean isValid(Boolean bool, ConstraintValidatorContext constraintValidatorContext) {
      return bool == null || !bool;
   }
}
