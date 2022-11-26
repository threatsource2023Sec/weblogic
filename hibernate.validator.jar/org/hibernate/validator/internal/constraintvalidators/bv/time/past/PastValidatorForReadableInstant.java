package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import org.joda.time.ReadableInstant;

public class PastValidatorForReadableInstant extends AbstractPastEpochBasedValidator {
   protected long getEpochMillis(ReadableInstant value, Clock reference) {
      return value.getMillis();
   }
}
