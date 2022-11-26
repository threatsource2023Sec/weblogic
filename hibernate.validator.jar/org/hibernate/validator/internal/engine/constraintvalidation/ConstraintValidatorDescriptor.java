package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.reflect.Type;
import java.util.EnumSet;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import org.hibernate.validator.cfg.context.ConstraintDefinitionContext;

public interface ConstraintValidatorDescriptor {
   Class getValidatorClass();

   EnumSet getValidationTargets();

   Type getValidatedType();

   ConstraintValidator newInstance(ConstraintValidatorFactory var1);

   static ConstraintValidatorDescriptor forClass(Class validatorClass, Class constraintAnnotationType) {
      return ClassBasedValidatorDescriptor.of(validatorClass, constraintAnnotationType);
   }

   static ConstraintValidatorDescriptor forLambda(Class annotationType, Type validatedType, ConstraintDefinitionContext.ValidationCallable lambda) {
      return new LambdaBasedValidatorDescriptor(validatedType, lambda);
   }
}
