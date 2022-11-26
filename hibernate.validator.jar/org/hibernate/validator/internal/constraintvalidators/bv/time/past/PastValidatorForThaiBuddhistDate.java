package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.chrono.ThaiBuddhistDate;

public class PastValidatorForThaiBuddhistDate extends AbstractPastJavaTimeValidator {
   protected ThaiBuddhistDate getReferenceValue(Clock reference) {
      return ThaiBuddhistDate.now(reference);
   }
}
