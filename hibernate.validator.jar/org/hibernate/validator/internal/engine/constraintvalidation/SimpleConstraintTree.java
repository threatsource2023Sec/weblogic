package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.Set;
import javax.validation.ConstraintValidator;
import org.hibernate.validator.internal.engine.ValidationContext;
import org.hibernate.validator.internal.engine.ValueContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

class SimpleConstraintTree extends ConstraintTree {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

   public SimpleConstraintTree(ConstraintDescriptorImpl descriptor, Type validatedValueType) {
      super(descriptor, validatedValueType);
   }

   protected void validateConstraints(ValidationContext validationContext, ValueContext valueContext, Set constraintViolations) {
      if (LOG.isTraceEnabled()) {
         LOG.tracef("Validating value %s against constraint defined by %s.", valueContext.getCurrentValidatedValue(), this.descriptor);
      }

      ConstraintValidator validator = this.getInitializedConstraintValidator(validationContext, valueContext);
      ConstraintValidatorContextImpl constraintValidatorContext = new ConstraintValidatorContextImpl(validationContext.getParameterNames(), validationContext.getClockProvider(), valueContext.getPropertyPath(), this.descriptor, validationContext.getConstraintValidatorPayload());
      constraintViolations.addAll(this.validateSingleConstraint(validationContext, valueContext, constraintValidatorContext, validator));
   }
}
