package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.Year;

public class PastValidatorForYear extends AbstractPastJavaTimeValidator {
   protected Year getReferenceValue(Clock reference) {
      return Year.now(reference);
   }
}
