package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.LocalTime;

public class PastOrPresentValidatorForLocalTime extends AbstractPastOrPresentJavaTimeValidator {
   protected LocalTime getReferenceValue(Clock reference) {
      return LocalTime.now(reference);
   }
}
