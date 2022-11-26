package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.YearMonth;

public class FutureOrPresentValidatorForYearMonth extends AbstractFutureOrPresentJavaTimeValidator {
   protected YearMonth getReferenceValue(Clock reference) {
      return YearMonth.now(reference);
   }
}
