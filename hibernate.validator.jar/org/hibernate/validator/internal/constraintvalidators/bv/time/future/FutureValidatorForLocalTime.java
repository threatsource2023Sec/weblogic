package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.LocalTime;

public class FutureValidatorForLocalTime extends AbstractFutureJavaTimeValidator {
   protected LocalTime getReferenceValue(Clock reference) {
      return LocalTime.now(reference);
   }
}
