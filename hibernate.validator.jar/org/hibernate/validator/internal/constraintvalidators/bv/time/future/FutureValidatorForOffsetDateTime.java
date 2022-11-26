package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.OffsetDateTime;

public class FutureValidatorForOffsetDateTime extends AbstractFutureJavaTimeValidator {
   protected OffsetDateTime getReferenceValue(Clock reference) {
      return OffsetDateTime.now(reference);
   }
}
