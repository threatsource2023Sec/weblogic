package org.hibernate.validator.internal.cfg.context;

import org.hibernate.validator.cfg.context.ConstraintDefinitionContext;
import org.hibernate.validator.cfg.context.TypeConstraintMappingContext;

abstract class ConstraintContextImplBase {
   protected final DefaultConstraintMapping mapping;

   public ConstraintContextImplBase(DefaultConstraintMapping mapping) {
      this.mapping = mapping;
   }

   public TypeConstraintMappingContext type(Class type) {
      return this.mapping.type(type);
   }

   public ConstraintDefinitionContext constraintDefinition(Class annotationClass) {
      return this.mapping.constraintDefinition(annotationClass);
   }
}
