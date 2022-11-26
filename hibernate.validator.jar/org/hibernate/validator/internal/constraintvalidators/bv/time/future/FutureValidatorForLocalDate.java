package org.hibernate.validator.internal.constraintvalidators.bv.time.future;

import java.time.Clock;
import java.time.LocalDate;

public class FutureValidatorForLocalDate extends AbstractFutureJavaTimeValidator {
   protected LocalDate getReferenceValue(Clock reference) {
      return LocalDate.now(reference);
   }
}
