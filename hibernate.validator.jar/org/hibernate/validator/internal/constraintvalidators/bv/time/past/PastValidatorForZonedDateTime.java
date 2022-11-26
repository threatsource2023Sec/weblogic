package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.ZonedDateTime;

public class PastValidatorForZonedDateTime extends AbstractPastJavaTimeValidator {
   protected ZonedDateTime getReferenceValue(Clock reference) {
      return ZonedDateTime.now(reference);
   }
}
