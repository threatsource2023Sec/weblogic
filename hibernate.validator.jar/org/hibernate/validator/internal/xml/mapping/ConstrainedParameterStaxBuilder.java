package org.hibernate.validator.internal.xml.mapping;

import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Executable;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import javax.xml.namespace.QName;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedParameter;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

class ConstrainedParameterStaxBuilder extends AbstractConstrainedElementStaxBuilder {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final String PARAMETER_QNAME_LOCAL_PART = "parameter";
   private static final QName TYPE_QNAME = new QName("type");

   ConstrainedParameterStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, DefaultPackageStaxBuilder defaultPackageStaxBuilder, AnnotationProcessingOptionsImpl annotationProcessingOptions) {
      super(classLoadingHelper, constraintHelper, typeResolutionHelper, valueExtractorManager, defaultPackageStaxBuilder, annotationProcessingOptions);
   }

   Optional getMainAttributeValueQname() {
      return Optional.of(TYPE_QNAME);
   }

   protected String getAcceptableQName() {
      return "parameter";
   }

   public Class getParameterType(Class beanClass) {
      try {
         return this.classLoadingHelper.loadClass(this.mainAttributeValue, (String)this.defaultPackageStaxBuilder.build().orElse(""));
      } catch (ValidationException var3) {
         throw LOG.getInvalidParameterTypeException(this.mainAttributeValue, beanClass);
      }
   }

   ConstrainedParameter build(Executable executable, int index) {
      ConstraintLocation constraintLocation = ConstraintLocation.forParameter(executable, index);
      Type type = ReflectionHelper.typeOf(executable, index);
      Set metaConstraints = (Set)this.constraintTypeStaxBuilders.stream().map((builder) -> {
         return builder.build(constraintLocation, ElementType.PARAMETER, (ConstraintDescriptorImpl.ConstraintType)null);
      }).collect(Collectors.toSet());
      ContainerElementTypeConfigurationBuilder.ContainerElementTypeConfiguration containerElementTypeConfiguration = this.getContainerElementTypeConfiguration(type, constraintLocation);
      if (this.ignoreAnnotations.isPresent()) {
         this.annotationProcessingOptions.ignoreConstraintAnnotationsOnParameter(executable, index, (Boolean)this.ignoreAnnotations.get());
      }

      ConstrainedParameter constrainedParameter = new ConstrainedParameter(ConfigurationSource.XML, executable, type, index, metaConstraints, containerElementTypeConfiguration.getMetaConstraints(), this.getCascadingMetaData(containerElementTypeConfiguration.getTypeParametersCascadingMetaData(), type));
      return constrainedParameter;
   }
}
