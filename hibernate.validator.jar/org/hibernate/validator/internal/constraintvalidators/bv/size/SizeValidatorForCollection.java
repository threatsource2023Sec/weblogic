package org.hibernate.validator.internal.constraintvalidators.bv.size;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Size;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class SizeValidatorForCollection implements ConstraintValidator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private int min;
   private int max;

   public void initialize(Size parameters) {
      this.min = parameters.min();
      this.max = parameters.max();
      this.validateParameters();
   }

   public boolean isValid(Collection collection, ConstraintValidatorContext constraintValidatorContext) {
      if (collection == null) {
         return true;
      } else {
         int length = collection.size();
         return length >= this.min && length <= this.max;
      }
   }

   private void validateParameters() {
      if (this.min < 0) {
         throw LOG.getMinCannotBeNegativeException();
      } else if (this.max < 0) {
         throw LOG.getMaxCannotBeNegativeException();
      } else if (this.max < this.min) {
         throw LOG.getLengthCannotBeNegativeException();
      }
   }
}
