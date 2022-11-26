package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.chrono.JapaneseDate;

public class PastValidatorForJapaneseDate extends AbstractPastJavaTimeValidator {
   protected JapaneseDate getReferenceValue(Clock reference) {
      return JapaneseDate.now(reference);
   }
}
