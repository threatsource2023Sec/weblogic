package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.MonthDay;

public class FutureOrPresentValidatorForMonthDay extends AbstractFutureOrPresentJavaTimeValidator {
   protected MonthDay getReferenceValue(Clock reference) {
      return MonthDay.now(reference);
   }
}
