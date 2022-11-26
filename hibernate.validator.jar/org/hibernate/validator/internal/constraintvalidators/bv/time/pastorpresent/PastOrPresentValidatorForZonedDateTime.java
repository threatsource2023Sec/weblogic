package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.ZonedDateTime;

public class PastOrPresentValidatorForZonedDateTime extends AbstractPastOrPresentJavaTimeValidator {
   protected ZonedDateTime getReferenceValue(Clock reference) {
      return ZonedDateTime.now(reference);
   }
}
