package org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent;

import java.time.Clock;
import java.time.LocalDate;

public class PastOrPresentValidatorForLocalDate extends AbstractPastOrPresentJavaTimeValidator {
   protected LocalDate getReferenceValue(Clock reference) {
      return LocalDate.now(reference);
   }
}
