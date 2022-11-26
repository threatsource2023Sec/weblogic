package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Instant;
import java.util.Calendar;

public class FutureOrPresentValidatorForCalendar extends AbstractFutureOrPresentInstantBasedValidator {
   protected Instant getInstant(Calendar value) {
      return value.toInstant();
   }
}
