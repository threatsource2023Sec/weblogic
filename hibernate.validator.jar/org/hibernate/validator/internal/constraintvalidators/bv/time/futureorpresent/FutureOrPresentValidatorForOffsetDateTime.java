package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.OffsetDateTime;

public class FutureOrPresentValidatorForOffsetDateTime extends AbstractFutureOrPresentJavaTimeValidator {
   protected OffsetDateTime getReferenceValue(Clock reference) {
      return OffsetDateTime.now(reference);
   }
}
