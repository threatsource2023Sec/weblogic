package org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent;

import java.time.Clock;
import java.time.LocalDate;

public class FutureOrPresentValidatorForLocalDate extends AbstractFutureOrPresentJavaTimeValidator {
   protected LocalDate getReferenceValue(Clock reference) {
      return LocalDate.now(reference);
   }
}
