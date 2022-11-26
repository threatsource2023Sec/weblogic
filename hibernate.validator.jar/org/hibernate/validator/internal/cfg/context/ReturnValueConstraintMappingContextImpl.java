package org.hibernate.validator.internal.cfg.context;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.cfg.context.ConstructorConstraintMappingContext;
import org.hibernate.validator.cfg.context.ContainerElementConstraintMappingContext;
import org.hibernate.validator.cfg.context.CrossParameterConstraintMappingContext;
import org.hibernate.validator.cfg.context.MethodConstraintMappingContext;
import org.hibernate.validator.cfg.context.ParameterConstraintMappingContext;
import org.hibernate.validator.cfg.context.ReturnValueConstraintMappingContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.util.ReflectionHelper;

final class ReturnValueConstraintMappingContextImpl extends CascadableConstraintMappingContextImplBase implements ReturnValueConstraintMappingContext {
   private final ExecutableConstraintMappingContextImpl executableContext;

   ReturnValueConstraintMappingContextImpl(ExecutableConstraintMappingContextImpl executableContext) {
      super(executableContext.getTypeContext().getConstraintMapping(), ReflectionHelper.typeOf(executableContext.getExecutable()));
      this.executableContext = executableContext;
   }

   protected ReturnValueConstraintMappingContext getThis() {
      return this;
   }

   public ReturnValueConstraintMappingContext constraint(ConstraintDef definition) {
      super.addConstraint(ConfiguredConstraint.forExecutable(definition, this.executableContext.getExecutable()));
      return this;
   }

   public ReturnValueConstraintMappingContext ignoreAnnotations(boolean ignoreAnnotations) {
      this.mapping.getAnnotationProcessingOptions().ignoreConstraintAnnotationsForReturnValue(this.executableContext.getExecutable(), ignoreAnnotations);
      return this;
   }

   public ParameterConstraintMappingContext parameter(int index) {
      return this.executableContext.parameter(index);
   }

   public CrossParameterConstraintMappingContext crossParameter() {
      return this.executableContext.crossParameter();
   }

   public MethodConstraintMappingContext method(String name, Class... parameterTypes) {
      return this.executableContext.getTypeContext().method(name, parameterTypes);
   }

   public ConstructorConstraintMappingContext constructor(Class... parameterTypes) {
      return this.executableContext.getTypeContext().constructor(parameterTypes);
   }

   public ContainerElementConstraintMappingContext containerElementType() {
      return super.containerElement(this, this.executableContext.getTypeContext(), ConstraintLocation.forReturnValue(this.executableContext.getExecutable()));
   }

   public ContainerElementConstraintMappingContext containerElementType(int index, int... nestedIndexes) {
      return super.containerElement(this, this.executableContext.getTypeContext(), ConstraintLocation.forReturnValue(this.executableContext.getExecutable()), index, nestedIndexes);
   }

   protected ConstraintDescriptorImpl.ConstraintType getConstraintType() {
      return ConstraintDescriptorImpl.ConstraintType.GENERIC;
   }
}
