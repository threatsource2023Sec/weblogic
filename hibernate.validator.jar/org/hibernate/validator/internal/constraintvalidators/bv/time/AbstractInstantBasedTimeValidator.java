package org.hibernate.validator.internal.constraintvalidators.bv.time;

import java.lang.invoke.MethodHandles;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public abstract class AbstractInstantBasedTimeValidator implements HibernateConstraintValidator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   protected Clock referenceClock;

   public void initialize(ConstraintDescriptor constraintDescriptor, HibernateConstraintValidatorInitializationContext initializationContext) {
      try {
         this.referenceClock = Clock.offset(initializationContext.getClockProvider().getClock(), this.getEffectiveTemporalValidationTolerance(initializationContext.getTemporalValidationTolerance()));
      } catch (Exception var4) {
         throw LOG.getUnableToGetCurrentTimeFromClockProvider(var4);
      }
   }

   public boolean isValid(Object value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         int result = this.getInstant(value).compareTo(this.referenceClock.instant());
         return this.isValid(result);
      }
   }

   protected abstract Duration getEffectiveTemporalValidationTolerance(Duration var1);

   protected abstract Instant getInstant(Object var1);

   protected abstract boolean isValid(int var1);
}
