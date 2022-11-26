package org.hibernate.validator.internal.metadata;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import org.hibernate.validator.internal.engine.MethodValidationConfiguration;
import org.hibernate.validator.internal.engine.groups.ValidationOrderGenerator;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaData;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaDataImpl;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptions;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.provider.AnnotationMetaDataProvider;
import org.hibernate.validator.internal.metadata.provider.MetaDataProvider;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ConcurrentReferenceHashMap;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.classhierarchy.ClassHierarchyHelper;
import org.hibernate.validator.internal.util.logging.Messages;

public class BeanMetaDataManager {
   private static final int DEFAULT_INITIAL_CAPACITY = 16;
   private static final float DEFAULT_LOAD_FACTOR = 0.75F;
   private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
   private final List metaDataProviders;
   private final ConstraintHelper constraintHelper;
   private final TypeResolutionHelper typeResolutionHelper;
   private final ValueExtractorManager valueExtractorManager;
   private final ExecutableParameterNameProvider parameterNameProvider;
   private final ConcurrentReferenceHashMap beanMetaDataCache;
   private final ExecutableHelper executableHelper;
   private final ValidationOrderGenerator validationOrderGenerator;
   private final MethodValidationConfiguration methodValidationConfiguration;

   public BeanMetaDataManager(ConstraintHelper constraintHelper, ExecutableHelper executableHelper, TypeResolutionHelper typeResolutionHelper, ExecutableParameterNameProvider parameterNameProvider, ValueExtractorManager valueExtractorManager, ValidationOrderGenerator validationOrderGenerator, List optionalMetaDataProviders, MethodValidationConfiguration methodValidationConfiguration) {
      this.constraintHelper = constraintHelper;
      this.executableHelper = executableHelper;
      this.typeResolutionHelper = typeResolutionHelper;
      this.valueExtractorManager = valueExtractorManager;
      this.parameterNameProvider = parameterNameProvider;
      this.validationOrderGenerator = validationOrderGenerator;
      this.metaDataProviders = CollectionHelper.newArrayList();
      this.metaDataProviders.addAll(optionalMetaDataProviders);
      this.methodValidationConfiguration = methodValidationConfiguration;
      this.beanMetaDataCache = new ConcurrentReferenceHashMap(16, 0.75F, 16, ConcurrentReferenceHashMap.ReferenceType.SOFT, ConcurrentReferenceHashMap.ReferenceType.SOFT, EnumSet.of(ConcurrentReferenceHashMap.Option.IDENTITY_COMPARISONS));
      AnnotationProcessingOptions annotationProcessingOptions = this.getAnnotationProcessingOptionsFromNonDefaultProviders();
      AnnotationMetaDataProvider defaultProvider = new AnnotationMetaDataProvider(constraintHelper, typeResolutionHelper, valueExtractorManager, annotationProcessingOptions);
      this.metaDataProviders.add(defaultProvider);
   }

   public BeanMetaData getBeanMetaData(Class beanClass) {
      Contracts.assertNotNull(beanClass, Messages.MESSAGES.beanTypeCannotBeNull());
      BeanMetaData beanMetaData = (BeanMetaData)this.beanMetaDataCache.get(beanClass);
      if (beanMetaData != null) {
         return beanMetaData;
      } else {
         BeanMetaData beanMetaData = this.createBeanMetaData(beanClass);
         BeanMetaData previousBeanMetaData = (BeanMetaData)this.beanMetaDataCache.putIfAbsent(beanClass, beanMetaData);
         return (BeanMetaData)(previousBeanMetaData != null ? previousBeanMetaData : beanMetaData);
      }
   }

   public void clear() {
      this.beanMetaDataCache.clear();
   }

   public int numberOfCachedBeanMetaDataInstances() {
      return this.beanMetaDataCache.size();
   }

   private BeanMetaDataImpl createBeanMetaData(Class clazz) {
      BeanMetaDataImpl.BeanMetaDataBuilder builder = BeanMetaDataImpl.BeanMetaDataBuilder.getInstance(this.constraintHelper, this.executableHelper, this.typeResolutionHelper, this.valueExtractorManager, this.parameterNameProvider, this.validationOrderGenerator, clazz, this.methodValidationConfiguration);
      Iterator var3 = this.metaDataProviders.iterator();

      while(var3.hasNext()) {
         MetaDataProvider provider = (MetaDataProvider)var3.next();
         Iterator var5 = this.getBeanConfigurationForHierarchy(provider, clazz).iterator();

         while(var5.hasNext()) {
            BeanConfiguration beanConfiguration = (BeanConfiguration)var5.next();
            builder.add(beanConfiguration);
         }
      }

      return builder.build();
   }

   private AnnotationProcessingOptions getAnnotationProcessingOptionsFromNonDefaultProviders() {
      AnnotationProcessingOptions options = new AnnotationProcessingOptionsImpl();
      Iterator var2 = this.metaDataProviders.iterator();

      while(var2.hasNext()) {
         MetaDataProvider metaDataProvider = (MetaDataProvider)var2.next();
         options.merge(metaDataProvider.getAnnotationProcessingOptions());
      }

      return options;
   }

   private List getBeanConfigurationForHierarchy(MetaDataProvider provider, Class beanClass) {
      List configurations = CollectionHelper.newArrayList();
      Iterator var4 = ClassHierarchyHelper.getHierarchy(beanClass).iterator();

      while(var4.hasNext()) {
         Class clazz = (Class)var4.next();
         BeanConfiguration configuration = provider.getBeanConfiguration(clazz);
         if (configuration != null) {
            configurations.add(configuration);
         }
      }

      return configurations;
   }
}
