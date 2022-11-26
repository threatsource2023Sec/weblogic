package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.ZonedDateTime;

public class FutureOrPresentValidatorForZonedDateTime extends AbstractFutureOrPresentJavaTimeValidator {
   protected ZonedDateTime getReferenceValue(Clock reference) {
      return ZonedDateTime.now(reference);
   }
}
