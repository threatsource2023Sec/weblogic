package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.chrono.MinguoDate;

public class PastOrPresentValidatorForMinguoDate extends AbstractPastOrPresentJavaTimeValidator {
   protected MinguoDate getReferenceValue(Clock reference) {
      return MinguoDate.now(reference);
   }
}
