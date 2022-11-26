package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.Instant;

public class PastOrPresentValidatorForInstant extends AbstractPastOrPresentJavaTimeValidator {
   protected Instant getReferenceValue(Clock reference) {
      return Instant.now(reference);
   }
}
