package org.hibernate.validator.internal.constraintvalidators.bv.time.past;

import java.time.Clock;
import java.time.LocalDate;

public class PastValidatorForLocalDate extends AbstractPastJavaTimeValidator {
   protected LocalDate getReferenceValue(Clock reference) {
      return LocalDate.now(reference);
   }
}
