package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.YearMonth;

public class PastOrPresentValidatorForYearMonth extends AbstractPastOrPresentJavaTimeValidator {
   protected YearMonth getReferenceValue(Clock reference) {
      return YearMonth.now(reference);
   }
}
