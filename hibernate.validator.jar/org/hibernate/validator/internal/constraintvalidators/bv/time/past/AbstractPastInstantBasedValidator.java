package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Duration;
import org.hibernate.validator.internal.constraintvalidators.bv.time.AbstractInstantBasedTimeValidator;

public abstract class AbstractPastInstantBasedValidator extends AbstractInstantBasedTimeValidator {
   protected boolean isValid(int result) {
      return result < 0;
   }

   protected Duration getEffectiveTemporalValidationTolerance(Duration absoluteTemporalValidationTolerance) {
      return absoluteTemporalValidationTolerance;
   }
}
