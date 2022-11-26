package org.hibernate.validator.internal.cfg.context;

import java.lang.reflect.Type;
import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.cfg.context.ConstructorConstraintMappingContext;
import org.hibernate.validator.cfg.context.ContainerElementConstraintMappingContext;
import org.hibernate.validator.cfg.context.CrossParameterConstraintMappingContext;
import org.hibernate.validator.cfg.context.MethodConstraintMappingContext;
import org.hibernate.validator.cfg.context.ParameterConstraintMappingContext;
import org.hibernate.validator.cfg.context.ReturnValueConstraintMappingContext;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedParameter;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;

final class ParameterConstraintMappingContextImpl extends CascadableConstraintMappingContextImplBase implements ParameterConstraintMappingContext {
   private final ExecutableConstraintMappingContextImpl executableContext;
   private final int parameterIndex;

   ParameterConstraintMappingContextImpl(ExecutableConstraintMappingContextImpl executableContext, int parameterIndex) {
      super(executableContext.getTypeContext().getConstraintMapping(), executableContext.executable.getGenericParameterTypes()[parameterIndex]);
      this.executableContext = executableContext;
      this.parameterIndex = parameterIndex;
   }

   protected ParameterConstraintMappingContext getThis() {
      return this;
   }

   public ParameterConstraintMappingContext constraint(ConstraintDef definition) {
      super.addConstraint(ConfiguredConstraint.forParameter(definition, this.executableContext.getExecutable(), this.parameterIndex));
      return this;
   }

   public ParameterConstraintMappingContext ignoreAnnotations(boolean ignoreAnnotations) {
      this.mapping.getAnnotationProcessingOptions().ignoreConstraintAnnotationsOnParameter(this.executableContext.getExecutable(), this.parameterIndex, ignoreAnnotations);
      return this;
   }

   public ParameterConstraintMappingContext parameter(int index) {
      return this.executableContext.parameter(index);
   }

   public CrossParameterConstraintMappingContext crossParameter() {
      return this.executableContext.crossParameter();
   }

   public ReturnValueConstraintMappingContext returnValue() {
      return this.executableContext.returnValue();
   }

   public ConstructorConstraintMappingContext constructor(Class... parameterTypes) {
      return this.executableContext.getTypeContext().constructor(parameterTypes);
   }

   public MethodConstraintMappingContext method(String name, Class... parameterTypes) {
      return this.executableContext.getTypeContext().method(name, parameterTypes);
   }

   public ContainerElementConstraintMappingContext containerElementType() {
      return super.containerElement(this, this.executableContext.getTypeContext(), ConstraintLocation.forParameter(this.executableContext.getExecutable(), this.parameterIndex));
   }

   public ContainerElementConstraintMappingContext containerElementType(int index, int... nestedIndexes) {
      return super.containerElement(this, this.executableContext.getTypeContext(), ConstraintLocation.forParameter(this.executableContext.getExecutable(), this.parameterIndex), index, nestedIndexes);
   }

   public ConstrainedParameter build(ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
      Type parameterType = ReflectionHelper.typeOf(this.executableContext.getExecutable(), this.parameterIndex);
      return new ConstrainedParameter(ConfigurationSource.API, this.executableContext.getExecutable(), parameterType, this.parameterIndex, this.getConstraints(constraintHelper, typeResolutionHelper, valueExtractorManager), this.getTypeArgumentConstraints(constraintHelper, typeResolutionHelper, valueExtractorManager), this.getCascadingMetaDataBuilder());
   }

   protected ConstraintDescriptorImpl.ConstraintType getConstraintType() {
      return ConstraintDescriptorImpl.ConstraintType.GENERIC;
   }
}
