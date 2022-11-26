package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.Instant;

public class PastValidatorForInstant extends AbstractPastJavaTimeValidator {
   protected Instant getReferenceValue(Clock reference) {
      return Instant.now(reference);
   }
}
