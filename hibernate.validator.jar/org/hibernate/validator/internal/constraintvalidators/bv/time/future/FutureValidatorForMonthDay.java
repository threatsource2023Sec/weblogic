package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.MonthDay;

public class FutureValidatorForMonthDay extends AbstractFutureJavaTimeValidator {
   protected MonthDay getReferenceValue(Clock reference) {
      return MonthDay.now(reference);
   }
}
