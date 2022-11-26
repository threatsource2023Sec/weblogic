package org.hibernate.validator.internal.cfg.context;

import java.lang.annotation.ElementType;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.cfg.context.ConstructorConstraintMappingContext;
import org.hibernate.validator.cfg.context.ContainerElementConstraintMappingContext;
import org.hibernate.validator.cfg.context.MethodConstraintMappingContext;
import org.hibernate.validator.cfg.context.PropertyConstraintMappingContext;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedField;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;

final class PropertyConstraintMappingContextImpl extends CascadableConstraintMappingContextImplBase implements PropertyConstraintMappingContext {
   private final TypeConstraintMappingContextImpl typeContext;
   private final Member member;
   private final ConstraintLocation location;

   PropertyConstraintMappingContextImpl(TypeConstraintMappingContextImpl typeContext, Member member) {
      super(typeContext.getConstraintMapping(), ReflectionHelper.typeOf(member));
      this.typeContext = typeContext;
      this.member = member;
      if (member instanceof Field) {
         this.location = ConstraintLocation.forField((Field)member);
      } else {
         this.location = ConstraintLocation.forGetter((Method)member);
      }

   }

   protected PropertyConstraintMappingContextImpl getThis() {
      return this;
   }

   public PropertyConstraintMappingContext constraint(ConstraintDef definition) {
      if (this.member instanceof Field) {
         super.addConstraint(ConfiguredConstraint.forProperty(definition, this.member));
      } else {
         super.addConstraint(ConfiguredConstraint.forExecutable(definition, (Method)this.member));
      }

      return this;
   }

   public PropertyConstraintMappingContext ignoreAnnotations() {
      return this.ignoreAnnotations(true);
   }

   public PropertyConstraintMappingContext ignoreAnnotations(boolean ignoreAnnotations) {
      this.mapping.getAnnotationProcessingOptions().ignoreConstraintAnnotationsOnMember(this.member, ignoreAnnotations);
      return this;
   }

   public PropertyConstraintMappingContext property(String property, ElementType elementType) {
      return this.typeContext.property(property, elementType);
   }

   public ConstructorConstraintMappingContext constructor(Class... parameterTypes) {
      return this.typeContext.constructor(parameterTypes);
   }

   public MethodConstraintMappingContext method(String name, Class... parameterTypes) {
      return this.typeContext.method(name, parameterTypes);
   }

   public ContainerElementConstraintMappingContext containerElementType() {
      return super.containerElement(this, this.typeContext, this.location);
   }

   public ContainerElementConstraintMappingContext containerElementType(int index, int... nestedIndexes) {
      return super.containerElement(this, this.typeContext, this.location, index, nestedIndexes);
   }

   ConstrainedElement build(ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
      return (ConstrainedElement)(this.member instanceof Field ? new ConstrainedField(ConfigurationSource.API, (Field)this.member, this.getConstraints(constraintHelper, typeResolutionHelper, valueExtractorManager), this.getTypeArgumentConstraints(constraintHelper, typeResolutionHelper, valueExtractorManager), this.getCascadingMetaDataBuilder()) : new ConstrainedExecutable(ConfigurationSource.API, (Executable)this.member, this.getConstraints(constraintHelper, typeResolutionHelper, valueExtractorManager), this.getTypeArgumentConstraints(constraintHelper, typeResolutionHelper, valueExtractorManager), this.getCascadingMetaDataBuilder()));
   }

   protected ConstraintDescriptorImpl.ConstraintType getConstraintType() {
      return ConstraintDescriptorImpl.ConstraintType.GENERIC;
   }
}
