package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.Year;

public class FutureValidatorForYear extends AbstractFutureJavaTimeValidator {
   protected Year getReferenceValue(Clock reference) {
      return Year.now(reference);
   }
}
