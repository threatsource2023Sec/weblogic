package org.hibernate.validator.cfg.context;

public interface TypeConstraintMappingContext extends Constrainable, ConstraintMappingTarget, PropertyTarget, MethodTarget, ConstructorTarget, AnnotationProcessingOptions, AnnotationIgnoreOptions {
   TypeConstraintMappingContext ignoreAllAnnotations();

   TypeConstraintMappingContext defaultGroupSequence(Class... var1);

   TypeConstraintMappingContext defaultGroupSequenceProviderClass(Class var1);
}
