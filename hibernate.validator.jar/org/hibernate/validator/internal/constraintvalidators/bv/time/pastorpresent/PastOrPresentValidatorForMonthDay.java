package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.MonthDay;

public class PastOrPresentValidatorForMonthDay extends AbstractPastOrPresentJavaTimeValidator {
   protected MonthDay getReferenceValue(Clock reference) {
      return MonthDay.now(reference);
   }
}
