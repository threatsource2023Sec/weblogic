package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.chrono.MinguoDate;

public class PastValidatorForMinguoDate extends AbstractPastJavaTimeValidator {
   protected MinguoDate getReferenceValue(Clock reference) {
      return MinguoDate.now(reference);
   }
}
