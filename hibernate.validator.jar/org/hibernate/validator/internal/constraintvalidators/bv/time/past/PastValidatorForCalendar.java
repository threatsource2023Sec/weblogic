package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Instant;
import java.util.Calendar;

public class PastValidatorForCalendar extends AbstractPastInstantBasedValidator {
   protected Instant getInstant(Calendar value) {
      return value.toInstant();
   }
}
