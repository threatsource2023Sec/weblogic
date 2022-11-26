package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintValidator;
import javax.validation.ValidationException;
import org.hibernate.validator.internal.engine.ValidationContext;
import org.hibernate.validator.internal.engine.ValueContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public abstract class ConstraintTree {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   protected final ConstraintDescriptorImpl descriptor;
   private final Type validatedValueType;
   private volatile ConstraintValidator constraintValidatorForDefaultConstraintValidatorFactoryAndInitializationContext;

   protected ConstraintTree(ConstraintDescriptorImpl descriptor, Type validatedValueType) {
      this.descriptor = descriptor;
      this.validatedValueType = validatedValueType;
   }

   public static ConstraintTree of(ConstraintDescriptorImpl composingDescriptor, Type validatedValueType) {
      return (ConstraintTree)(composingDescriptor.getComposingConstraintImpls().isEmpty() ? new SimpleConstraintTree(composingDescriptor, validatedValueType) : new ComposingConstraintTree(composingDescriptor, validatedValueType));
   }

   public final boolean validateConstraints(ValidationContext executionContext, ValueContext valueContext) {
      Set constraintViolations = CollectionHelper.newHashSet(5);
      this.validateConstraints(executionContext, valueContext, constraintViolations);
      if (!constraintViolations.isEmpty()) {
         executionContext.addConstraintFailures(constraintViolations);
         return false;
      } else {
         return true;
      }
   }

   protected abstract void validateConstraints(ValidationContext var1, ValueContext var2, Set var3);

   public final ConstraintDescriptorImpl getDescriptor() {
      return this.descriptor;
   }

   public final Type getValidatedValueType() {
      return this.validatedValueType;
   }

   private ValidationException getExceptionForNullValidator(Type validatedValueType, String path) {
      if (this.descriptor.getConstraintType() == ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER) {
         return LOG.getValidatorForCrossParameterConstraintMustEitherValidateObjectOrObjectArrayException(this.descriptor.getAnnotationType());
      } else {
         String className = validatedValueType.toString();
         if (validatedValueType instanceof Class) {
            Class clazz = (Class)validatedValueType;
            if (clazz.isArray()) {
               className = clazz.getComponentType().toString() + "[]";
            } else {
               className = clazz.getName();
            }
         }

         return LOG.getNoValidatorFoundForTypeException(this.descriptor.getAnnotationType(), className, path);
      }
   }

   protected final ConstraintValidator getInitializedConstraintValidator(ValidationContext validationContext, ValueContext valueContext) {
      ConstraintValidator validator;
      if (validationContext.getConstraintValidatorFactory() == validationContext.getConstraintValidatorManager().getDefaultConstraintValidatorFactory() && validationContext.getConstraintValidatorInitializationContext() == validationContext.getConstraintValidatorManager().getDefaultConstraintValidatorInitializationContext()) {
         validator = this.constraintValidatorForDefaultConstraintValidatorFactoryAndInitializationContext;
         if (validator == null) {
            synchronized(this) {
               validator = this.constraintValidatorForDefaultConstraintValidatorFactoryAndInitializationContext;
               if (validator == null) {
                  validator = this.getInitializedConstraintValidator(validationContext);
                  this.constraintValidatorForDefaultConstraintValidatorFactoryAndInitializationContext = validator;
               }
            }
         }
      } else {
         validator = this.getInitializedConstraintValidator(validationContext);
      }

      if (validator == ConstraintValidatorManager.DUMMY_CONSTRAINT_VALIDATOR) {
         throw this.getExceptionForNullValidator(this.validatedValueType, valueContext.getPropertyPath().asString());
      } else {
         return validator;
      }
   }

   private ConstraintValidator getInitializedConstraintValidator(ValidationContext validationContext) {
      ConstraintValidator validator = validationContext.getConstraintValidatorManager().getInitializedValidator(this.validatedValueType, this.descriptor, validationContext.getConstraintValidatorFactory(), validationContext.getConstraintValidatorInitializationContext());
      return validator != null ? validator : ConstraintValidatorManager.DUMMY_CONSTRAINT_VALIDATOR;
   }

   protected final Set validateSingleConstraint(ValidationContext executionContext, ValueContext valueContext, ConstraintValidatorContextImpl constraintValidatorContext, ConstraintValidator validator) {
      boolean isValid;
      try {
         Object validatedValue = valueContext.getCurrentValidatedValue();
         isValid = validator.isValid(validatedValue, constraintValidatorContext);
      } catch (RuntimeException var7) {
         if (var7 instanceof ConstraintDeclarationException) {
            throw var7;
         }

         throw LOG.getExceptionDuringIsValidCallException(var7);
      }

      return !isValid ? executionContext.createConstraintViolations(valueContext, constraintValidatorContext) : Collections.emptySet();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("ConstraintTree");
      sb.append("{ descriptor=").append(this.descriptor);
      sb.append('}');
      return sb.toString();
   }
}
