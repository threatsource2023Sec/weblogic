package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.Year;

public class FutureOrPresentValidatorForYear extends AbstractFutureOrPresentJavaTimeValidator {
   protected Year getReferenceValue(Clock reference) {
      return Year.now(reference);
   }
}
