package org.hibernate.validator.internal.xml.mapping;

import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.namespace.QName;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetMethodFromPropertyName;

class ConstrainedGetterStaxBuilder extends AbstractConstrainedElementStaxBuilder {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final QName NAME_QNAME = new QName("name");
   private static final String GETTER_QNAME_LOCAL_PART = "getter";

   ConstrainedGetterStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, DefaultPackageStaxBuilder defaultPackageStaxBuilder, AnnotationProcessingOptionsImpl annotationProcessingOptions) {
      super(classLoadingHelper, constraintHelper, typeResolutionHelper, valueExtractorManager, defaultPackageStaxBuilder, annotationProcessingOptions);
   }

   Optional getMainAttributeValueQname() {
      return Optional.of(NAME_QNAME);
   }

   protected String getAcceptableQName() {
      return "getter";
   }

   ConstrainedExecutable build(Class beanClass, List alreadyProcessedGetterNames) {
      if (alreadyProcessedGetterNames.contains(this.mainAttributeValue)) {
         throw LOG.getIsDefinedTwiceInMappingXmlForBeanException(this.mainAttributeValue, beanClass);
      } else {
         alreadyProcessedGetterNames.add(this.mainAttributeValue);
         Method getter = findGetter(beanClass, this.mainAttributeValue);
         ConstraintLocation constraintLocation = ConstraintLocation.forGetter(beanClass, getter);
         Set metaConstraints = (Set)this.constraintTypeStaxBuilders.stream().map((builder) -> {
            return builder.build(constraintLocation, ElementType.METHOD, (ConstraintDescriptorImpl.ConstraintType)null);
         }).collect(Collectors.toSet());
         ContainerElementTypeConfigurationBuilder.ContainerElementTypeConfiguration containerElementTypeConfiguration = this.getContainerElementTypeConfiguration(ReflectionHelper.typeOf(getter), constraintLocation);
         ConstrainedExecutable constrainedGetter = new ConstrainedExecutable(ConfigurationSource.XML, getter, Collections.emptyList(), Collections.emptySet(), metaConstraints, containerElementTypeConfiguration.getMetaConstraints(), this.getCascadingMetaData(containerElementTypeConfiguration.getTypeParametersCascadingMetaData(), ReflectionHelper.typeOf(getter)));
         if (this.ignoreAnnotations.isPresent()) {
            this.annotationProcessingOptions.ignoreConstraintAnnotationsOnMember(getter, (Boolean)this.ignoreAnnotations.get());
         }

         return constrainedGetter;
      }
   }

   private static Method findGetter(Class beanClass, String getterName) {
      Method method = (Method)run(GetMethodFromPropertyName.action(beanClass, getterName));
      if (method == null) {
         throw LOG.getBeanDoesNotContainThePropertyException(beanClass, getterName);
      } else {
         return method;
      }
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
