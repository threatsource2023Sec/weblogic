package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import org.joda.time.ReadableInstant;

public class FutureValidatorForReadableInstant extends AbstractFutureEpochBasedValidator {
   protected long getEpochMillis(ReadableInstant value, Clock reference) {
      return value.getMillis();
   }
}
