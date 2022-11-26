package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.chrono.HijrahDate;

public class PastValidatorForHijrahDate extends AbstractPastJavaTimeValidator {
   protected HijrahDate getReferenceValue(Clock reference) {
      return HijrahDate.now(reference);
   }
}
