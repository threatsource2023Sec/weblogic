package org.hibernate.validator.internal.engine.constraintvalidation;

import java.lang.reflect.Type;
import java.util.EnumSet;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.constraintvalidation.ValidationTarget;
import org.hibernate.validator.cfg.context.ConstraintDefinitionContext;

class LambdaBasedValidatorDescriptor implements ConstraintValidatorDescriptor {
   private static final long serialVersionUID = 5129757824081595723L;
   private final Type validatedType;
   private final ConstraintDefinitionContext.ValidationCallable lambda;

   public LambdaBasedValidatorDescriptor(Type validatedType, ConstraintDefinitionContext.ValidationCallable lambda) {
      this.validatedType = validatedType;
      this.lambda = lambda;
   }

   public Class getValidatorClass() {
      Class clazz = LambdaExecutor.class;
      return clazz;
   }

   public EnumSet getValidationTargets() {
      return EnumSet.of(ValidationTarget.ANNOTATED_ELEMENT);
   }

   public Type getValidatedType() {
      return this.validatedType;
   }

   public ConstraintValidator newInstance(ConstraintValidatorFactory constraintValidatorFactory) {
      return new LambdaExecutor(this.lambda);
   }

   private static class LambdaExecutor implements ConstraintValidator {
      private final ConstraintDefinitionContext.ValidationCallable lambda;

      public LambdaExecutor(ConstraintDefinitionContext.ValidationCallable lambda) {
         this.lambda = lambda;
      }

      public boolean isValid(Object value, ConstraintValidatorContext context) {
         return this.lambda.isValid(value);
      }
   }
}
