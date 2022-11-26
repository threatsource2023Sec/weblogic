package org.hibernate.validator.internal.constraintvalidators.hv.time;

import java.time.Duration;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.time.DurationMin;

public class DurationMinValidator implements ConstraintValidator {
   private Duration minDuration;
   private boolean inclusive;

   public void initialize(DurationMin constraintAnnotation) {
      this.minDuration = Duration.ofNanos(constraintAnnotation.nanos()).plusMillis(constraintAnnotation.millis()).plusSeconds(constraintAnnotation.seconds()).plusMinutes(constraintAnnotation.minutes()).plusHours(constraintAnnotation.hours()).plusDays(constraintAnnotation.days());
      this.inclusive = constraintAnnotation.inclusive();
   }

   public boolean isValid(Duration value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         int comparisonResult = this.minDuration.compareTo(value);
         return this.inclusive ? comparisonResult <= 0 : comparisonResult < 0;
      }
   }
}
