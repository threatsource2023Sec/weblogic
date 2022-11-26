package org.hibernate.validator.internal.cfg.context;

import java.lang.reflect.Method;
import org.hibernate.validator.cfg.context.MethodConstraintMappingContext;

class MethodConstraintMappingContextImpl extends ExecutableConstraintMappingContextImpl implements MethodConstraintMappingContext {
   MethodConstraintMappingContextImpl(TypeConstraintMappingContextImpl typeContext, Method method) {
      super(typeContext, method);
   }

   public MethodConstraintMappingContext ignoreAnnotations(boolean ignoreAnnotations) {
      this.typeContext.mapping.getAnnotationProcessingOptions().ignoreConstraintAnnotationsOnMember(this.executable, ignoreAnnotations);
      return this;
   }
}
