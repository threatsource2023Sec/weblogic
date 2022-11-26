package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.OffsetDateTime;

public class PastOrPresentValidatorForOffsetDateTime extends AbstractPastOrPresentJavaTimeValidator {
   protected OffsetDateTime getReferenceValue(Clock reference) {
      return OffsetDateTime.now(reference);
   }
}
