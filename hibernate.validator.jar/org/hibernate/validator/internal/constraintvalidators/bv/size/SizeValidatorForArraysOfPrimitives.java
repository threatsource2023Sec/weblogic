package org.hibernate.validator.internal.constraintvalidators.bv.size;

import java.lang.invoke.MethodHandles;
import javax.validation.constraints.Size;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public abstract class SizeValidatorForArraysOfPrimitives {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   protected int min;
   protected int max;

   public void initialize(Size parameters) {
      this.min = parameters.min();
      this.max = parameters.max();
      this.validateParameters();
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
