package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.chrono.JapaneseDate;

public class FutureOrPresentValidatorForJapaneseDate extends AbstractFutureOrPresentJavaTimeValidator {
   protected JapaneseDate getReferenceValue(Clock reference) {
      return JapaneseDate.now(reference);
   }
}
