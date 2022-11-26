package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.OffsetTime;

public class FutureOrPresentValidatorForOffsetTime extends AbstractFutureOrPresentJavaTimeValidator {
   protected OffsetTime getReferenceValue(Clock reference) {
      return OffsetTime.now(reference);
   }
}
