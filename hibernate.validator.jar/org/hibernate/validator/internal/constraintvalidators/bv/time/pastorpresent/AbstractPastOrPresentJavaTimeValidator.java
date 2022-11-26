package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Duration;
import org.hibernate.validator.internal.constraintvalidators.bv.time.AbstractJavaTimeValidator;

public abstract class AbstractPastOrPresentJavaTimeValidator extends AbstractJavaTimeValidator {
   protected boolean isValid(int result) {
      return result <= 0;
   }

   protected Duration getEffectiveTemporalValidationTolerance(Duration absoluteTemporalValidationTolerance) {
      return absoluteTemporalValidationTolerance;
   }
}
