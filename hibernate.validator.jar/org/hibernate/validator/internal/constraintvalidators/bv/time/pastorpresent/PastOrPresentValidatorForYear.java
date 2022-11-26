package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.Year;

public class PastOrPresentValidatorForYear extends AbstractPastOrPresentJavaTimeValidator {
   protected Year getReferenceValue(Clock reference) {
      return Year.now(reference);
   }
}
