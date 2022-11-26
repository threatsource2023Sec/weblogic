package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import org.joda.time.ReadableInstant;

public class PastOrPresentValidatorForReadableInstant extends AbstractPastOrPresentEpochBasedValidator {
   protected long getEpochMillis(ReadableInstant value, Clock reference) {
      return value.getMillis();
   }
}
