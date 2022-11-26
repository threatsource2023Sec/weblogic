package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.LocalDateTime;

public class FutureValidatorForLocalDateTime extends AbstractFutureJavaTimeValidator {
   protected LocalDateTime getReferenceValue(Clock reference) {
      return LocalDateTime.now(reference);
   }
}
