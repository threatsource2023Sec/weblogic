package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.MonthDay;

public class PastValidatorForMonthDay extends AbstractPastJavaTimeValidator {
   protected MonthDay getReferenceValue(Clock reference) {
      return MonthDay.now(reference);
   }
}
