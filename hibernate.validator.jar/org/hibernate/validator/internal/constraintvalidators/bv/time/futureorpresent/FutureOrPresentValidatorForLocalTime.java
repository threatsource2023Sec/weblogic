package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.LocalTime;

public class FutureOrPresentValidatorForLocalTime extends AbstractFutureOrPresentJavaTimeValidator {
   protected LocalTime getReferenceValue(Clock reference) {
      return LocalTime.now(reference);
   }
}
