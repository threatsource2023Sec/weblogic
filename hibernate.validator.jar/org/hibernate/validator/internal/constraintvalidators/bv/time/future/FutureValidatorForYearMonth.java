package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.YearMonth;

public class FutureValidatorForYearMonth extends AbstractFutureJavaTimeValidator {
   protected YearMonth getReferenceValue(Clock reference) {
      return YearMonth.now(reference);
   }
}
