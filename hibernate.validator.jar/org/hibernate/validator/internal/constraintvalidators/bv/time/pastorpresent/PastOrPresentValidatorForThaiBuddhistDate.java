package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.chrono.ThaiBuddhistDate;

public class PastOrPresentValidatorForThaiBuddhistDate extends AbstractPastOrPresentJavaTimeValidator {
   protected ThaiBuddhistDate getReferenceValue(Clock reference) {
      return ThaiBuddhistDate.now(reference);
   }
}
