package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.chrono.HijrahDate;

public class FutureOrPresentValidatorForHijrahDate extends AbstractFutureOrPresentJavaTimeValidator {
   protected HijrahDate getReferenceValue(Clock reference) {
      return HijrahDate.now(reference);
   }
}
