package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Instant;
import java.util.Date;

public class PastOrPresentValidatorForDate extends AbstractPastOrPresentInstantBasedValidator {
   protected Instant getInstant(Date value) {
      return value.toInstant();
   }
}
