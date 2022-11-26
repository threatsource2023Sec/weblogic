package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Instant;
import java.util.Calendar;

public class PastOrPresentValidatorForCalendar extends AbstractPastOrPresentInstantBasedValidator {
   protected Instant getInstant(Calendar value) {
      return value.toInstant();
   }
}
