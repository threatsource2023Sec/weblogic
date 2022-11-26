package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.ZonedDateTime;

public class FutureValidatorForZonedDateTime extends AbstractFutureJavaTimeValidator {
   protected ZonedDateTime getReferenceValue(Clock reference) {
      return ZonedDateTime.now(reference);
   }
}
