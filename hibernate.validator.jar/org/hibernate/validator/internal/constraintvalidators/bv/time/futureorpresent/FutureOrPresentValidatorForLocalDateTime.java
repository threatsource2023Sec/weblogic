package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.LocalDateTime;

public class FutureOrPresentValidatorForLocalDateTime extends AbstractFutureOrPresentJavaTimeValidator {
   protected LocalDateTime getReferenceValue(Clock reference) {
      return LocalDateTime.now(reference);
   }
}
