package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.YearMonth;

public class PastValidatorForYearMonth extends AbstractPastJavaTimeValidator {
   protected YearMonth getReferenceValue(Clock reference) {
      return YearMonth.now(reference);
   }
}
