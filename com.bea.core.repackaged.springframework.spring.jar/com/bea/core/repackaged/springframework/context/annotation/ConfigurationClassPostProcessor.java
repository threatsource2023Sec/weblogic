package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.framework.autoproxy.AutoProxyUtils;
import com.bea.core.repackaged.springframework.beans.PropertyValues;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import com.bea.core.repackaged.springframework.beans.factory.config.SingletonBeanRegistry;
import com.bea.core.repackaged.springframework.beans.factory.parsing.FailFastProblemReporter;
import com.bea.core.repackaged.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ProblemReporter;
import com.bea.core.repackaged.springframework.beans.factory.parsing.SourceExtractor;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanNameGenerator;
import com.bea.core.repackaged.springframework.context.EnvironmentAware;
import com.bea.core.repackaged.springframework.context.ResourceLoaderAware;
import com.bea.core.repackaged.springframework.core.PriorityOrdered;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.env.StandardEnvironment;
import com.bea.core.repackaged.springframework.core.io.DefaultResourceLoader;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.classreading.CachingMetadataReaderFactory;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReaderFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered, ResourceLoaderAware, BeanClassLoaderAware, EnvironmentAware {
   private static final String IMPORT_REGISTRY_BEAN_NAME = ConfigurationClassPostProcessor.class.getName() + ".importRegistry";
   private final Log logger = LogFactory.getLog(this.getClass());
   private SourceExtractor sourceExtractor = new PassThroughSourceExtractor();
   private ProblemReporter problemReporter = new FailFastProblemReporter();
   @Nullable
   private Environment environment;
   private ResourceLoader resourceLoader = new DefaultResourceLoader();
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
   private boolean setMetadataReaderFactoryCalled = false;
   private final Set registriesPostProcessed = new HashSet();
   private final Set factoriesPostProcessed = new HashSet();
   @Nullable
   private ConfigurationClassBeanDefinitionReader reader;
   private boolean localBeanNameGeneratorSet = false;
   private BeanNameGenerator componentScanBeanNameGenerator = new AnnotationBeanNameGenerator();
   private BeanNameGenerator importBeanNameGenerator = new AnnotationBeanNameGenerator() {
      protected String buildDefaultBeanName(BeanDefinition definition) {
         String beanClassName = definition.getBeanClassName();
         Assert.state(beanClassName != null, "No bean class name set");
         return beanClassName;
      }
   };

   public int getOrder() {
      return Integer.MAX_VALUE;
   }

   public void setSourceExtractor(@Nullable SourceExtractor sourceExtractor) {
      this.sourceExtractor = (SourceExtractor)(sourceExtractor != null ? sourceExtractor : new PassThroughSourceExtractor());
   }

   public void setProblemReporter(@Nullable ProblemReporter problemReporter) {
      this.problemReporter = (ProblemReporter)(problemReporter != null ? problemReporter : new FailFastProblemReporter());
   }

   public void setMetadataReaderFactory(MetadataReaderFactory metadataReaderFactory) {
      Assert.notNull(metadataReaderFactory, (String)"MetadataReaderFactory must not be null");
      this.metadataReaderFactory = metadataReaderFactory;
      this.setMetadataReaderFactoryCalled = true;
   }

   public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
      Assert.notNull(beanNameGenerator, (String)"BeanNameGenerator must not be null");
      this.localBeanNameGeneratorSet = true;
      this.componentScanBeanNameGenerator = beanNameGenerator;
      this.importBeanNameGenerator = beanNameGenerator;
   }

   public void setEnvironment(Environment environment) {
      Assert.notNull(environment, (String)"Environment must not be null");
      this.environment = environment;
   }

   public void setResourceLoader(ResourceLoader resourceLoader) {
      Assert.notNull(resourceLoader, (String)"ResourceLoader must not be null");
      this.resourceLoader = resourceLoader;
      if (!this.setMetadataReaderFactoryCalled) {
         this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
      }

   }

   public void setBeanClassLoader(ClassLoader beanClassLoader) {
      this.beanClassLoader = beanClassLoader;
      if (!this.setMetadataReaderFactoryCalled) {
         this.metadataReaderFactory = new CachingMetadataReaderFactory(beanClassLoader);
      }

   }

   public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
      int registryId = System.identityHashCode(registry);
      if (this.registriesPostProcessed.contains(registryId)) {
         throw new IllegalStateException("postProcessBeanDefinitionRegistry already called on this post-processor against " + registry);
      } else if (this.factoriesPostProcessed.contains(registryId)) {
         throw new IllegalStateException("postProcessBeanFactory already called on this post-processor against " + registry);
      } else {
         this.registriesPostProcessed.add(registryId);
         this.processConfigBeanDefinitions(registry);
      }
   }

   public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
      int factoryId = System.identityHashCode(beanFactory);
      if (this.factoriesPostProcessed.contains(factoryId)) {
         throw new IllegalStateException("postProcessBeanFactory already called on this post-processor against " + beanFactory);
      } else {
         this.factoriesPostProcessed.add(factoryId);
         if (!this.registriesPostProcessed.contains(factoryId)) {
            this.processConfigBeanDefinitions((BeanDefinitionRegistry)beanFactory);
         }

         this.enhanceConfigurationClasses(beanFactory);
         beanFactory.addBeanPostProcessor(new ImportAwareBeanPostProcessor(beanFactory));
      }
   }

   public void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
      List configCandidates = new ArrayList();
      String[] candidateNames = registry.getBeanDefinitionNames();
      String[] var4 = candidateNames;
      int var5 = candidateNames.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String beanName = var4[var6];
         BeanDefinition beanDef = registry.getBeanDefinition(beanName);
         if (!ConfigurationClassUtils.isFullConfigurationClass(beanDef) && !ConfigurationClassUtils.isLiteConfigurationClass(beanDef)) {
            if (ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)) {
               configCandidates.add(new BeanDefinitionHolder(beanDef, beanName));
            }
         } else if (this.logger.isDebugEnabled()) {
            this.logger.debug("Bean definition has already been processed as a configuration class: " + beanDef);
         }
      }

      if (!configCandidates.isEmpty()) {
         configCandidates.sort((bd1, bd2) -> {
            int i1 = ConfigurationClassUtils.getOrder(bd1.getBeanDefinition());
            int i2 = ConfigurationClassUtils.getOrder(bd2.getBeanDefinition());
            return Integer.compare(i1, i2);
         });
         SingletonBeanRegistry sbr = null;
         if (registry instanceof SingletonBeanRegistry) {
            sbr = (SingletonBeanRegistry)registry;
            if (!this.localBeanNameGeneratorSet) {
               BeanNameGenerator generator = (BeanNameGenerator)sbr.getSingleton("com.bea.core.repackaged.springframework.context.annotation.internalConfigurationBeanNameGenerator");
               if (generator != null) {
                  this.componentScanBeanNameGenerator = generator;
                  this.importBeanNameGenerator = generator;
               }
            }
         }

         if (this.environment == null) {
            this.environment = new StandardEnvironment();
         }

         ConfigurationClassParser parser = new ConfigurationClassParser(this.metadataReaderFactory, this.problemReporter, this.environment, this.resourceLoader, this.componentScanBeanNameGenerator, registry);
         Set candidates = new LinkedHashSet(configCandidates);
         Set alreadyParsed = new HashSet(configCandidates.size());

         do {
            parser.parse(candidates);
            parser.validate();
            Set configClasses = new LinkedHashSet(parser.getConfigurationClasses());
            configClasses.removeAll(alreadyParsed);
            if (this.reader == null) {
               this.reader = new ConfigurationClassBeanDefinitionReader(registry, this.sourceExtractor, this.resourceLoader, this.environment, this.importBeanNameGenerator, parser.getImportRegistry());
            }

            this.reader.loadBeanDefinitions(configClasses);
            alreadyParsed.addAll(configClasses);
            candidates.clear();
            if (registry.getBeanDefinitionCount() > candidateNames.length) {
               String[] newCandidateNames = registry.getBeanDefinitionNames();
               Set oldCandidateNames = new HashSet(Arrays.asList(candidateNames));
               Set alreadyParsedClasses = new HashSet();
               Iterator var12 = alreadyParsed.iterator();

               while(var12.hasNext()) {
                  ConfigurationClass configurationClass = (ConfigurationClass)var12.next();
                  alreadyParsedClasses.add(configurationClass.getMetadata().getClassName());
               }

               String[] var23 = newCandidateNames;
               int var24 = newCandidateNames.length;

               for(int var14 = 0; var14 < var24; ++var14) {
                  String candidateName = var23[var14];
                  if (!oldCandidateNames.contains(candidateName)) {
                     BeanDefinition bd = registry.getBeanDefinition(candidateName);
                     if (ConfigurationClassUtils.checkConfigurationClassCandidate(bd, this.metadataReaderFactory) && !alreadyParsedClasses.contains(bd.getBeanClassName())) {
                        candidates.add(new BeanDefinitionHolder(bd, candidateName));
                     }
                  }
               }

               candidateNames = newCandidateNames;
            }
         } while(!candidates.isEmpty());

         if (sbr != null && !sbr.containsSingleton(IMPORT_REGISTRY_BEAN_NAME)) {
            sbr.registerSingleton(IMPORT_REGISTRY_BEAN_NAME, parser.getImportRegistry());
         }

         if (this.metadataReaderFactory instanceof CachingMetadataReaderFactory) {
            ((CachingMetadataReaderFactory)this.metadataReaderFactory).clearCache();
         }

      }
   }

   public void enhanceConfigurationClasses(ConfigurableListableBeanFactory beanFactory) {
      Map configBeanDefs = new LinkedHashMap();
      String[] var3 = beanFactory.getBeanDefinitionNames();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String beanName = var3[var5];
         BeanDefinition beanDef = beanFactory.getBeanDefinition(beanName);
         if (ConfigurationClassUtils.isFullConfigurationClass(beanDef)) {
            if (!(beanDef instanceof AbstractBeanDefinition)) {
               throw new BeanDefinitionStoreException("Cannot enhance @Configuration bean definition '" + beanName + "' since it is not stored in an AbstractBeanDefinition subclass");
            }

            if (this.logger.isInfoEnabled() && beanFactory.containsSingleton(beanName)) {
               this.logger.info("Cannot enhance @Configuration bean definition '" + beanName + "' since its singleton instance has been created too early. The typical cause is a non-static @Bean method with a BeanDefinitionRegistryPostProcessor return type: Consider declaring such methods as 'static'.");
            }

            configBeanDefs.put(beanName, (AbstractBeanDefinition)beanDef);
         }
      }

      if (!configBeanDefs.isEmpty()) {
         ConfigurationClassEnhancer enhancer = new ConfigurationClassEnhancer();
         Iterator var11 = configBeanDefs.entrySet().iterator();

         while(var11.hasNext()) {
            Map.Entry entry = (Map.Entry)var11.next();
            AbstractBeanDefinition beanDef = (AbstractBeanDefinition)entry.getValue();
            beanDef.setAttribute(AutoProxyUtils.PRESERVE_TARGET_CLASS_ATTRIBUTE, Boolean.TRUE);

            try {
               Class configClass = beanDef.resolveBeanClass(this.beanClassLoader);
               if (configClass != null) {
                  Class enhancedClass = enhancer.enhance(configClass, this.beanClassLoader);
                  if (configClass != enhancedClass) {
                     if (this.logger.isTraceEnabled()) {
                        this.logger.trace(String.format("Replacing bean definition '%s' existing class '%s' with enhanced class '%s'", entry.getKey(), configClass.getName(), enhancedClass.getName()));
                     }

                     beanDef.setBeanClass(enhancedClass);
                  }
               }
            } catch (Throwable var9) {
               throw new IllegalStateException("Cannot load configuration class: " + beanDef.getBeanClassName(), var9);
            }
         }

      }
   }

   private static class ImportAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {
      private final BeanFactory beanFactory;

      public ImportAwareBeanPostProcessor(BeanFactory beanFactory) {
         this.beanFactory = beanFactory;
      }

      public PropertyValues postProcessProperties(@Nullable PropertyValues pvs, Object bean, String beanName) {
         if (bean instanceof ConfigurationClassEnhancer.EnhancedConfiguration) {
            ((ConfigurationClassEnhancer.EnhancedConfiguration)bean).setBeanFactory(this.beanFactory);
         }

         return pvs;
      }

      public Object postProcessBeforeInitialization(Object bean, String beanName) {
         if (bean instanceof ImportAware) {
            ImportRegistry ir = (ImportRegistry)this.beanFactory.getBean(ConfigurationClassPostProcessor.IMPORT_REGISTRY_BEAN_NAME, ImportRegistry.class);
            AnnotationMetadata importingClass = ir.getImportingClassFor(bean.getClass().getSuperclass().getName());
            if (importingClass != null) {
               ((ImportAware)bean).setImportMetadata(importingClass);
            }
         }

         return bean;
      }
   }
}
