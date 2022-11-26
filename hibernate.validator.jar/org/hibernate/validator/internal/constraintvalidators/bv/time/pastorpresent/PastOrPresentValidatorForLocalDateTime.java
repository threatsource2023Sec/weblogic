package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.LocalDateTime;

public class PastOrPresentValidatorForLocalDateTime extends AbstractPastOrPresentJavaTimeValidator {
   protected LocalDateTime getReferenceValue(Clock reference) {
      return LocalDateTime.now(reference);
   }
}
