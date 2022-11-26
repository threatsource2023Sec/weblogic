package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import org.joda.time.Instant;
import org.joda.time.ReadablePartial;

public class PastOrPresentValidatorForReadablePartial extends AbstractPastOrPresentEpochBasedValidator {
   protected long getEpochMillis(ReadablePartial value, Clock reference) {
      return value.toDateTime(new Instant(reference.millis())).getMillis();
   }
}
