package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.EnumSet;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import org.hibernate.validator.internal.util.TypeHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

class ClassBasedValidatorDescriptor implements ConstraintValidatorDescriptor {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Class validatorClass;
   private final Type validatedType;
   private final EnumSet validationTargets;

   private ClassBasedValidatorDescriptor(Class validatorClass) {
      this.validatorClass = validatorClass;
      this.validatedType = TypeHelper.extractValidatedType(validatorClass);
      this.validationTargets = determineValidationTargets(validatorClass);
   }

   public static ClassBasedValidatorDescriptor of(Class validatorClass, Class registeredConstraintAnnotationType) {
      Type definedConstraintAnnotationType = TypeHelper.extractConstraintType(validatorClass);
      if (!registeredConstraintAnnotationType.equals(definedConstraintAnnotationType)) {
         throw LOG.getConstraintValidatorDefinitionConstraintMismatchException(validatorClass, registeredConstraintAnnotationType, definedConstraintAnnotationType);
      } else {
         return new ClassBasedValidatorDescriptor(validatorClass);
      }
   }

   private static EnumSet determineValidationTargets(Class validatorClass) {
      SupportedValidationTarget supportedTargetAnnotation = (SupportedValidationTarget)validatorClass.getAnnotation(SupportedValidationTarget.class);
      return supportedTargetAnnotation == null ? EnumSet.of(ValidationTarget.ANNOTATED_ELEMENT) : EnumSet.copyOf(Arrays.asList(supportedTargetAnnotation.value()));
   }

   public Class getValidatorClass() {
      return this.validatorClass;
   }

   public ConstraintValidator newInstance(ConstraintValidatorFactory constraintValidatorFactory) {
      ConstraintValidator constraintValidator = constraintValidatorFactory.getInstance(this.validatorClass);
      if (constraintValidator == null) {
         throw LOG.getConstraintValidatorFactoryMustNotReturnNullException(this.validatorClass);
      } else {
         return constraintValidator;
      }
   }

   public Type getValidatedType() {
      return this.validatedType;
   }

   public EnumSet getValidationTargets() {
      return this.validationTargets;
   }
}
