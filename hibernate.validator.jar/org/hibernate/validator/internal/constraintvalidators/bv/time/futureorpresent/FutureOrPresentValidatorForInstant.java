package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.Instant;

public class FutureOrPresentValidatorForInstant extends AbstractFutureOrPresentJavaTimeValidator {
   protected Instant getReferenceValue(Clock reference) {
      return Instant.now(reference);
   }
}
