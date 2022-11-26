package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.LocalTime;

public class PastValidatorForLocalTime extends AbstractPastJavaTimeValidator {
   protected LocalTime getReferenceValue(Clock reference) {
      return LocalTime.now(reference);
   }
}
