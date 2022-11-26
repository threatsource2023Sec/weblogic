package org.hibernate.validator.cfg.context;

import org.hibernate.validator.Incubating;

public interface ConstraintDefinitionContext extends ConstraintMappingTarget {
   ConstraintDefinitionContext includeExistingValidators(boolean var1);

   ConstraintDefinitionContext validatedBy(Class var1);

   @Incubating
   ConstraintValidatorDefinitionContext validateType(Class var1);

   @FunctionalInterface
   @Incubating
   public interface ValidationCallable {
      boolean isValid(Object var1);
   }

   @Incubating
   public interface ConstraintValidatorDefinitionContext {
      ConstraintDefinitionContext with(ValidationCallable var1);
   }
}
