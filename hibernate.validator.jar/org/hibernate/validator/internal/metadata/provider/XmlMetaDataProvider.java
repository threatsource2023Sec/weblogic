package org.hibernate.validator.internal.metadata.provider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptions;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.xml.mapping.MappingXmlParser;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class XmlMetaDataProvider implements MetaDataProvider {
   private final Map configuredBeans;
   private final AnnotationProcessingOptions annotationProcessingOptions;

   public XmlMetaDataProvider(ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, Set mappingStreams, ClassLoader externalClassLoader) {
      MappingXmlParser mappingParser = new MappingXmlParser(constraintHelper, typeResolutionHelper, valueExtractorManager, externalClassLoader);
      mappingParser.parse(mappingStreams);
      this.configuredBeans = CollectionHelper.toImmutableMap(createBeanConfigurations(mappingParser));
      this.annotationProcessingOptions = mappingParser.getAnnotationProcessingOptions();
   }

   private static Map createBeanConfigurations(MappingXmlParser mappingParser) {
      Map configuredBeans = new HashMap();
      Iterator var2 = mappingParser.getXmlConfiguredClasses().iterator();

      while(var2.hasNext()) {
         Class clazz = (Class)var2.next();
         Set constrainedElements = mappingParser.getConstrainedElementsForClass(clazz);
         BeanConfiguration beanConfiguration = new BeanConfiguration(ConfigurationSource.XML, clazz, constrainedElements, mappingParser.getDefaultSequenceForClass(clazz), (DefaultGroupSequenceProvider)null);
         configuredBeans.put(clazz.getName(), beanConfiguration);
      }

      return configuredBeans;
   }

   public BeanConfiguration getBeanConfiguration(Class beanClass) {
      return (BeanConfiguration)this.configuredBeans.get(beanClass.getName());
   }

   public AnnotationProcessingOptions getAnnotationProcessingOptions() {
      return this.annotationProcessingOptions;
   }
}
