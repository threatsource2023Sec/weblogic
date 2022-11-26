package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.chrono.JapaneseDate;

public class FutureValidatorForJapaneseDate extends AbstractFutureJavaTimeValidator {
   protected JapaneseDate getReferenceValue(Clock reference) {
      return JapaneseDate.now(reference);
   }
}
