package org.hibernate.validator.internal.metadata.provider;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptions;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ProgrammaticMetaDataProvider implements MetaDataProvider {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Map configuredBeans;
   private final AnnotationProcessingOptions annotationProcessingOptions;

   public ProgrammaticMetaDataProvider(ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, Set constraintMappings) {
      Contracts.assertNotNull(constraintMappings);
      this.configuredBeans = CollectionHelper.toImmutableMap(createBeanConfigurations(constraintMappings, constraintHelper, typeResolutionHelper, valueExtractorManager));
      assertUniquenessOfConfiguredTypes(constraintMappings);
      this.annotationProcessingOptions = mergeAnnotationProcessingOptions(constraintMappings);
   }

   private static void assertUniquenessOfConfiguredTypes(Set mappings) {
      Set allConfiguredTypes = CollectionHelper.newHashSet();
      Iterator var2 = mappings.iterator();

      while(var2.hasNext()) {
         DefaultConstraintMapping constraintMapping = (DefaultConstraintMapping)var2.next();
         Iterator var4 = constraintMapping.getConfiguredTypes().iterator();

         while(var4.hasNext()) {
            Class configuredType = (Class)var4.next();
            if (allConfiguredTypes.contains(configuredType)) {
               throw LOG.getBeanClassHasAlreadyBeConfiguredViaProgrammaticApiException(configuredType);
            }
         }

         allConfiguredTypes.addAll(constraintMapping.getConfiguredTypes());
      }

   }

   private static Map createBeanConfigurations(Set mappings, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
      Map configuredBeans = new HashMap();
      Iterator var5 = mappings.iterator();

      while(var5.hasNext()) {
         DefaultConstraintMapping mapping = (DefaultConstraintMapping)var5.next();
         Set beanConfigurations = mapping.getBeanConfigurations(constraintHelper, typeResolutionHelper, valueExtractorManager);
         Iterator var8 = beanConfigurations.iterator();

         while(var8.hasNext()) {
            BeanConfiguration beanConfiguration = (BeanConfiguration)var8.next();
            configuredBeans.put(beanConfiguration.getBeanClass().getName(), beanConfiguration);
         }
      }

      return configuredBeans;
   }

   private static AnnotationProcessingOptions mergeAnnotationProcessingOptions(Set mappings) {
      if (mappings.size() == 1) {
         return ((DefaultConstraintMapping)mappings.iterator().next()).getAnnotationProcessingOptions();
      } else {
         AnnotationProcessingOptions options = new AnnotationProcessingOptionsImpl();
         Iterator var2 = mappings.iterator();

         while(var2.hasNext()) {
            DefaultConstraintMapping mapping = (DefaultConstraintMapping)var2.next();
            options.merge(mapping.getAnnotationProcessingOptions());
         }

         return options;
      }
   }

   public BeanConfiguration getBeanConfiguration(Class beanClass) {
      return (BeanConfiguration)this.configuredBeans.get(beanClass.getName());
   }

   public AnnotationProcessingOptions getAnnotationProcessingOptions() {
      return this.annotationProcessingOptions;
   }
}
