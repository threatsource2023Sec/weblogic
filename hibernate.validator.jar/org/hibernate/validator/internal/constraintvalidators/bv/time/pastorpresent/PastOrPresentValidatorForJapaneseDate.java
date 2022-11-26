package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.chrono.JapaneseDate;

public class PastOrPresentValidatorForJapaneseDate extends AbstractPastOrPresentJavaTimeValidator {
   protected JapaneseDate getReferenceValue(Clock reference) {
      return JapaneseDate.now(reference);
   }
}
