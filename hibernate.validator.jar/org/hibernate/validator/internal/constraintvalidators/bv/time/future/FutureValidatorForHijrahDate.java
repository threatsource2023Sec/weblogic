package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.chrono.HijrahDate;

public class FutureValidatorForHijrahDate extends AbstractFutureJavaTimeValidator {
   protected HijrahDate getReferenceValue(Clock reference) {
      return HijrahDate.now(reference);
   }
}
