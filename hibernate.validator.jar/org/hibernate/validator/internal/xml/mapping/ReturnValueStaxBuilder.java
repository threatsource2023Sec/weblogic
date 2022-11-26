package org.hibernate.validator.internal.xml.mapping;

import java.lang.reflect.Executable;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;

class ReturnValueStaxBuilder extends AbstractConstrainedElementStaxBuilder {
   private static final String RETURN_VALUE_QNAME_LOCAL_PART = "return-value";

   ReturnValueStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, DefaultPackageStaxBuilder defaultPackageStaxBuilder, AnnotationProcessingOptionsImpl annotationProcessingOptions) {
      super(classLoadingHelper, constraintHelper, typeResolutionHelper, valueExtractorManager, defaultPackageStaxBuilder, annotationProcessingOptions);
   }

   Optional getMainAttributeValueQname() {
      return Optional.empty();
   }

   protected String getAcceptableQName() {
      return "return-value";
   }

   CascadingMetaDataBuilder build(Executable executable, Set returnValueConstraints, Set returnValueTypeArgumentConstraints) {
      ConstraintLocation constraintLocation = ConstraintLocation.forReturnValue(executable);
      returnValueConstraints.addAll((Collection)this.constraintTypeStaxBuilders.stream().map((builder) -> {
         return builder.build(constraintLocation, ExecutableHelper.getElementType(executable), ConstraintDescriptorImpl.ConstraintType.GENERIC);
      }).collect(Collectors.toSet()));
      ContainerElementTypeConfigurationBuilder.ContainerElementTypeConfiguration containerElementTypeConfiguration = this.getContainerElementTypeConfiguration(ReflectionHelper.typeOf(executable), constraintLocation);
      returnValueTypeArgumentConstraints.addAll(containerElementTypeConfiguration.getMetaConstraints());
      if (this.ignoreAnnotations.isPresent()) {
         this.annotationProcessingOptions.ignoreConstraintAnnotationsForReturnValue(executable, (Boolean)this.ignoreAnnotations.get());
      }

      return this.getCascadingMetaData(containerElementTypeConfiguration.getTypeParametersCascadingMetaData(), ReflectionHelper.typeOf(executable));
   }
}
