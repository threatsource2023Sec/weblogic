package org.hibernate.validator.internal.cfg.context;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.cfg.context.ConstructorConstraintMappingContext;
import org.hibernate.validator.cfg.context.CrossParameterConstraintMappingContext;
import org.hibernate.validator.cfg.context.MethodConstraintMappingContext;
import org.hibernate.validator.cfg.context.ParameterConstraintMappingContext;
import org.hibernate.validator.cfg.context.ReturnValueConstraintMappingContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;

final class CrossParameterConstraintMappingContextImpl extends ConstraintMappingContextImplBase implements CrossParameterConstraintMappingContext {
   private final ExecutableConstraintMappingContextImpl executableContext;

   CrossParameterConstraintMappingContextImpl(ExecutableConstraintMappingContextImpl executableContext) {
      super(executableContext.getTypeContext().getConstraintMapping());
      this.executableContext = executableContext;
   }

   public CrossParameterConstraintMappingContext constraint(ConstraintDef definition) {
      super.addConstraint(ConfiguredConstraint.forCrossParameter(definition, this.executableContext.getExecutable()));
      return this;
   }

   public CrossParameterConstraintMappingContext ignoreAnnotations(boolean ignoreAnnotations) {
      this.mapping.getAnnotationProcessingOptions().ignoreConstraintAnnotationsForCrossParameterConstraint(this.executableContext.getExecutable(), ignoreAnnotations);
      return this;
   }

   public ParameterConstraintMappingContext parameter(int index) {
      return this.executableContext.parameter(index);
   }

   public MethodConstraintMappingContext method(String name, Class... parameterTypes) {
      return this.executableContext.getTypeContext().method(name, parameterTypes);
   }

   public ConstructorConstraintMappingContext constructor(Class... parameterTypes) {
      return this.executableContext.getTypeContext().constructor(parameterTypes);
   }

   public ReturnValueConstraintMappingContext returnValue() {
      return this.executableContext.returnValue();
   }

   protected ConstraintDescriptorImpl.ConstraintType getConstraintType() {
      return ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER;
   }
}
