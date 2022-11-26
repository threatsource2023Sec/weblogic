package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Duration;
import org.hibernate.validator.internal.constraintvalidators.bv.time.AbstractEpochBasedTimeValidator;

public abstract class AbstractPastOrPresentEpochBasedValidator extends AbstractEpochBasedTimeValidator {
   protected boolean isValid(int result) {
      return result <= 0;
   }

   protected Duration getEffectiveTemporalValidationTolerance(Duration absoluteTemporalValidationTolerance) {
      return absoluteTemporalValidationTolerance;
   }
}
