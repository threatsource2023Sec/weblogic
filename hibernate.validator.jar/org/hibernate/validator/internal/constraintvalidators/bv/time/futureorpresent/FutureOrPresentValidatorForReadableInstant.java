package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import org.joda.time.ReadableInstant;

public class FutureOrPresentValidatorForReadableInstant extends AbstractFutureOrPresentEpochBasedValidator {
   protected long getEpochMillis(ReadableInstant value, Clock reference) {
      return value.getMillis();
   }
}
