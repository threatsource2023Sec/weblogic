package org.hibernate.validator.internal.constraintvalidators.bv.notempty;

import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValidatorForCollection implements ConstraintValidator {
   public boolean isValid(Collection collection, ConstraintValidatorContext constraintValidatorContext) {
      if (collection == null) {
         return false;
      } else {
         return collection.size() > 0;
      }
   }
}
