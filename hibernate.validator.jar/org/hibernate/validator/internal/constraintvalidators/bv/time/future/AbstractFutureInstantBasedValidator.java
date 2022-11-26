package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Duration;
import org.hibernate.validator.internal.constraintvalidators.bv.time.AbstractInstantBasedTimeValidator;

public abstract class AbstractFutureInstantBasedValidator extends AbstractInstantBasedTimeValidator {
   protected boolean isValid(int result) {
      return result > 0;
   }

   protected Duration getEffectiveTemporalValidationTolerance(Duration absoluteTemporalValidationTolerance) {
      return absoluteTemporalValidationTolerance.negated();
   }
}
