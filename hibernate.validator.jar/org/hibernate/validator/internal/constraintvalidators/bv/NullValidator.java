package org.hibernate.validator.internal.constraintvalidators.bv;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullValidator implements ConstraintValidator {
   public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
      return object == null;
   }
}
