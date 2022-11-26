package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Instant;
import java.util.Calendar;

public class FutureValidatorForCalendar extends AbstractFutureInstantBasedValidator {
   protected Instant getInstant(Calendar value) {
      return value.toInstant();
   }
}
