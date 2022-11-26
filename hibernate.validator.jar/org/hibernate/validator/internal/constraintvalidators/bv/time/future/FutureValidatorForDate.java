package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Instant;
import java.util.Date;

public class FutureValidatorForDate extends AbstractFutureInstantBasedValidator {
   protected Instant getInstant(Date value) {
      return value.toInstant();
   }
}
