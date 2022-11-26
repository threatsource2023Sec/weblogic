package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.OffsetTime;

public class PastValidatorForOffsetTime extends AbstractPastJavaTimeValidator {
   protected OffsetTime getReferenceValue(Clock reference) {
      return OffsetTime.now(reference);
   }
}
