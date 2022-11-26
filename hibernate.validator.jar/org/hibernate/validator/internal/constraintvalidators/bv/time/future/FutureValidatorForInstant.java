package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.Instant;

public class FutureValidatorForInstant extends AbstractFutureJavaTimeValidator {
   protected Instant getReferenceValue(Clock reference) {
      return Instant.now(reference);
   }
}
