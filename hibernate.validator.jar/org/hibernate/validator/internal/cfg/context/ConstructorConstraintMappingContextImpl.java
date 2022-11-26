package org.hibernate.validator.internal.cfg.context;

import java.lang.reflect.Constructor;
import org.hibernate.validator.cfg.context.ConstructorConstraintMappingContext;

class ConstructorConstraintMappingContextImpl extends ExecutableConstraintMappingContextImpl implements ConstructorConstraintMappingContext {
   ConstructorConstraintMappingContextImpl(TypeConstraintMappingContextImpl typeContext, Constructor constructor) {
      super(typeContext, constructor);
   }

   public ConstructorConstraintMappingContext ignoreAnnotations(boolean ignoreAnnotations) {
      this.typeContext.mapping.getAnnotationProcessingOptions().ignoreConstraintAnnotationsOnMember(this.executable, ignoreAnnotations);
      return this;
   }
}
