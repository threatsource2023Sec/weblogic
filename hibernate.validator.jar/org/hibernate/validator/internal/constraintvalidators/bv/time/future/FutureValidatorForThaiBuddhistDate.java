package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.chrono.ThaiBuddhistDate;

public class FutureValidatorForThaiBuddhistDate extends AbstractFutureJavaTimeValidator {
   protected ThaiBuddhistDate getReferenceValue(Clock reference) {
      return ThaiBuddhistDate.now(reference);
   }
}
