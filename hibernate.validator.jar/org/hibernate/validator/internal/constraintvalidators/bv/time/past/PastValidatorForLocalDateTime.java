package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.LocalDateTime;

public class PastValidatorForLocalDateTime extends AbstractPastJavaTimeValidator {
   protected LocalDateTime getReferenceValue(Clock reference) {
      return LocalDateTime.now(reference);
   }
}
