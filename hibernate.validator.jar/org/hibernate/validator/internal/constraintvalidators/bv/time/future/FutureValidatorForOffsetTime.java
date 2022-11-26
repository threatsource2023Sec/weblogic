package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.OffsetTime;

public class FutureValidatorForOffsetTime extends AbstractFutureJavaTimeValidator {
   protected OffsetTime getReferenceValue(Clock reference) {
      return OffsetTime.now(reference);
   }
}
