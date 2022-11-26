package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.chrono.ThaiBuddhistDate;

public class FutureOrPresentValidatorForThaiBuddhistDate extends AbstractFutureOrPresentJavaTimeValidator {
   protected ThaiBuddhistDate getReferenceValue(Clock reference) {
      return ThaiBuddhistDate.now(reference);
   }
}
