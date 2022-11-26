package org.hibernate.validator.internal.constraintvalidators.hv;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.EAN;

public class EANValidator implements ConstraintValidator {
   private int size;

   public void initialize(EAN constraintAnnotation) {
      switch (constraintAnnotation.type()) {
         case EAN8:
            this.size = 8;
            break;
         case EAN13:
            this.size = 13;
      }

   }

   public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         int length = value.length();
         return length == this.size;
      }
   }
}
