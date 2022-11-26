package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.chrono.MinguoDate;

public class FutureOrPresentValidatorForMinguoDate extends AbstractFutureOrPresentJavaTimeValidator {
   protected MinguoDate getReferenceValue(Clock reference) {
      return MinguoDate.now(reference);
   }
}
