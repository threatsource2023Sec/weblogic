package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.OffsetDateTime;

public class PastValidatorForOffsetDateTime extends AbstractPastJavaTimeValidator {
   protected OffsetDateTime getReferenceValue(Clock reference) {
      return OffsetDateTime.now(reference);
   }
}
