package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.chrono.HijrahDate;

public class PastOrPresentValidatorForHijrahDate extends AbstractPastOrPresentJavaTimeValidator {
   protected HijrahDate getReferenceValue(Clock reference) {
      return HijrahDate.now(reference);
   }
}
