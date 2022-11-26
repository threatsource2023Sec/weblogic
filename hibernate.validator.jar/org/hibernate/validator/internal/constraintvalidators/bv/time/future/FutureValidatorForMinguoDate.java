package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.chrono.MinguoDate;

public class FutureValidatorForMinguoDate extends AbstractFutureJavaTimeValidator {
   protected MinguoDate getReferenceValue(Clock reference) {
      return MinguoDate.now(reference);
   }
}
