package org.hibernate.validator.internal.constraintvalidators.bv.time;

import java.lang.invoke.MethodHandles;
import java.time.Clock;
import java.time.Duration;
import java.time.temporal.TemporalAccessor;
import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public abstract class AbstractJavaTimeValidator implements HibernateConstraintValidator {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   protected Clock referenceClock;

   public void initialize(ConstraintDescriptor constraintDescriptor, HibernateConstraintValidatorInitializationContext initializationContext) {
      try {
         this.referenceClock = Clock.offset(initializationContext.getClockProvider().getClock(), this.getEffectiveTemporalValidationTolerance(initializationContext.getTemporalValidationTolerance()));
      } catch (Exception var4) {
         throw LOG.getUnableToGetCurrentTimeFromClockProvider(var4);
      }
   }

   public boolean isValid(TemporalAccessor value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         int result = ((Comparable)value).compareTo(this.getReferenceValue(this.referenceClock));
         return this.isValid(result);
      }
   }

   protected abstract Duration getEffectiveTemporalValidationTolerance(Duration var1);

   protected abstract TemporalAccessor getReferenceValue(Clock var1);

   protected abstract boolean isValid(int var1);
}
