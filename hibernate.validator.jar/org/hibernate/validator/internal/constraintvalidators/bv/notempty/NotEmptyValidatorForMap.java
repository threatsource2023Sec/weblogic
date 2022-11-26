package org.hibernate.validator.internal.constraintvalidators.bv.notempty;

import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyValidatorForMap implements ConstraintValidator {
   public boolean isValid(Map map, ConstraintValidatorContext constraintValidatorContext) {
      if (map == null) {
         return false;
      } else {
         return map.size() > 0;
      }
   }
}
