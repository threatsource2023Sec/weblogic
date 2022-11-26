package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.OffsetTime;

public class PastOrPresentValidatorForOffsetTime extends AbstractPastOrPresentJavaTimeValidator {
   protected OffsetTime getReferenceValue(Clock reference) {
      return OffsetTime.now(reference);
   }
}
