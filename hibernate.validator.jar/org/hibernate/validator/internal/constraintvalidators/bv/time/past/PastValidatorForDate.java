package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Instant;
import java.util.Date;

public class PastValidatorForDate extends AbstractPastInstantBasedValidator {
   protected Instant getInstant(Date value) {
      return value.toInstant();
   }
}
