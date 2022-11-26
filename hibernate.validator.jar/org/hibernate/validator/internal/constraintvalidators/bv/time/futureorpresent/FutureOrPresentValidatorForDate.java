package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Instant;
import java.util.Date;

public class FutureOrPresentValidatorForDate extends AbstractFutureOrPresentInstantBasedValidator {
   protected Instant getInstant(Date value) {
      return value.toInstant();
   }
}
