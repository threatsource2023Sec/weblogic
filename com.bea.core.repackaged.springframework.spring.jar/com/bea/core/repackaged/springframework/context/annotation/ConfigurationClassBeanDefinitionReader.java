package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.annotation.Autowire;
import com.bea.core.repackaged.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import com.bea.core.repackaged.springframework.beans.factory.parsing.SourceExtractor;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionReader;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanNameGenerator;
import com.bea.core.repackaged.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.MethodMetadata;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ConfigurationClassBeanDefinitionReader {
   private static final Log logger = LogFactory.getLog(ConfigurationClassBeanDefinitionReader.class);
   private static final ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
   private final BeanDefinitionRegistry registry;
   private final SourceExtractor sourceExtractor;
   private final ResourceLoader resourceLoader;
   private final Environment environment;
   private final BeanNameGenerator importBeanNameGenerator;
   private final ImportRegistry importRegistry;
   private final ConditionEvaluator conditionEvaluator;

   ConfigurationClassBeanDefinitionReader(BeanDefinitionRegistry registry, SourceExtractor sourceExtractor, ResourceLoader resourceLoader, Environment environment, BeanNameGenerator importBeanNameGenerator, ImportRegistry importRegistry) {
      this.registry = registry;
      this.sourceExtractor = sourceExtractor;
      this.resourceLoader = resourceLoader;
      this.environment = environment;
      this.importBeanNameGenerator = importBeanNameGenerator;
      this.importRegistry = importRegistry;
      this.conditionEvaluator = new ConditionEvaluator(registry, environment, resourceLoader);
   }

   public void loadBeanDefinitions(Set configurationModel) {
      TrackedConditionEvaluator trackedConditionEvaluator = new TrackedConditionEvaluator();
      Iterator var3 = configurationModel.iterator();

      while(var3.hasNext()) {
         ConfigurationClass configClass = (ConfigurationClass)var3.next();
         this.loadBeanDefinitionsForConfigurationClass(configClass, trackedConditionEvaluator);
      }

   }

   private void loadBeanDefinitionsForConfigurationClass(ConfigurationClass configClass, TrackedConditionEvaluator trackedConditionEvaluator) {
      if (trackedConditionEvaluator.shouldSkip(configClass)) {
         String beanName = configClass.getBeanName();
         if (StringUtils.hasLength(beanName) && this.registry.containsBeanDefinition(beanName)) {
            this.registry.removeBeanDefinition(beanName);
         }

         this.importRegistry.removeImportingClass(configClass.getMetadata().getClassName());
      } else {
         if (configClass.isImported()) {
            this.registerBeanDefinitionForImportedConfigurationClass(configClass);
         }

         Iterator var3 = configClass.getBeanMethods().iterator();

         while(var3.hasNext()) {
            BeanMethod beanMethod = (BeanMethod)var3.next();
            this.loadBeanDefinitionsForBeanMethod(beanMethod);
         }

         this.loadBeanDefinitionsFromImportedResources(configClass.getImportedResources());
         this.loadBeanDefinitionsFromRegistrars(configClass.getImportBeanDefinitionRegistrars());
      }
   }

   private void registerBeanDefinitionForImportedConfigurationClass(ConfigurationClass configClass) {
      AnnotationMetadata metadata = configClass.getMetadata();
      AnnotatedGenericBeanDefinition configBeanDef = new AnnotatedGenericBeanDefinition(metadata);
      ScopeMetadata scopeMetadata = scopeMetadataResolver.resolveScopeMetadata(configBeanDef);
      configBeanDef.setScope(scopeMetadata.getScopeName());
      String configBeanName = this.importBeanNameGenerator.generateBeanName(configBeanDef, this.registry);
      AnnotationConfigUtils.processCommonDefinitionAnnotations(configBeanDef, metadata);
      BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(configBeanDef, configBeanName);
      definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
      this.registry.registerBeanDefinition(definitionHolder.getBeanName(), definitionHolder.getBeanDefinition());
      configClass.setBeanName(configBeanName);
      if (logger.isTraceEnabled()) {
         logger.trace("Registered bean definition for imported class '" + configBeanName + "'");
      }

   }

   private void loadBeanDefinitionsForBeanMethod(BeanMethod beanMethod) {
      ConfigurationClass configClass = beanMethod.getConfigurationClass();
      MethodMetadata metadata = beanMethod.getMetadata();
      String methodName = metadata.getMethodName();
      if (this.conditionEvaluator.shouldSkip(metadata, ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN)) {
         configClass.skippedBeanMethods.add(methodName);
      } else if (!configClass.skippedBeanMethods.contains(methodName)) {
         AnnotationAttributes bean = AnnotationConfigUtils.attributesFor(metadata, (Class)Bean.class);
         Assert.state(bean != null, "No @Bean annotation attributes");
         List names = new ArrayList(Arrays.asList(bean.getStringArray("name")));
         String beanName = !names.isEmpty() ? (String)names.remove(0) : methodName;
         Iterator var8 = names.iterator();

         while(var8.hasNext()) {
            String alias = (String)var8.next();
            this.registry.registerAlias(beanName, alias);
         }

         if (this.isOverriddenByExistingDefinition(beanMethod, beanName)) {
            if (beanName.equals(beanMethod.getConfigurationClass().getBeanName())) {
               throw new BeanDefinitionStoreException(beanMethod.getConfigurationClass().getResource().getDescription(), beanName, "Bean name derived from @Bean method '" + beanMethod.getMetadata().getMethodName() + "' clashes with bean name for containing configuration class; please make those names unique!");
            }
         } else {
            ConfigurationClassBeanDefinition beanDef = new ConfigurationClassBeanDefinition(configClass, metadata);
            beanDef.setResource(configClass.getResource());
            beanDef.setSource(this.sourceExtractor.extractSource(metadata, configClass.getResource()));
            if (metadata.isStatic()) {
               beanDef.setBeanClassName(configClass.getMetadata().getClassName());
               beanDef.setFactoryMethodName(methodName);
            } else {
               beanDef.setFactoryBeanName(configClass.getBeanName());
               beanDef.setUniqueFactoryMethodName(methodName);
            }

            beanDef.setAutowireMode(3);
            beanDef.setAttribute(RequiredAnnotationBeanPostProcessor.SKIP_REQUIRED_CHECK_ATTRIBUTE, Boolean.TRUE);
            AnnotationConfigUtils.processCommonDefinitionAnnotations(beanDef, metadata);
            Autowire autowire = (Autowire)bean.getEnum("autowire");
            if (autowire.isAutowire()) {
               beanDef.setAutowireMode(autowire.value());
            }

            boolean autowireCandidate = bean.getBoolean("autowireCandidate");
            if (!autowireCandidate) {
               beanDef.setAutowireCandidate(false);
            }

            String initMethodName = bean.getString("initMethod");
            if (StringUtils.hasText(initMethodName)) {
               beanDef.setInitMethodName(initMethodName);
            }

            String destroyMethodName = bean.getString("destroyMethod");
            beanDef.setDestroyMethodName(destroyMethodName);
            ScopedProxyMode proxyMode = ScopedProxyMode.NO;
            AnnotationAttributes attributes = AnnotationConfigUtils.attributesFor(metadata, (Class)Scope.class);
            if (attributes != null) {
               beanDef.setScope(attributes.getString("value"));
               proxyMode = (ScopedProxyMode)attributes.getEnum("proxyMode");
               if (proxyMode == ScopedProxyMode.DEFAULT) {
                  proxyMode = ScopedProxyMode.NO;
               }
            }

            BeanDefinition beanDefToRegister = beanDef;
            if (proxyMode != ScopedProxyMode.NO) {
               BeanDefinitionHolder proxyDef = ScopedProxyCreator.createScopedProxy(new BeanDefinitionHolder(beanDef, beanName), this.registry, proxyMode == ScopedProxyMode.TARGET_CLASS);
               beanDefToRegister = new ConfigurationClassBeanDefinition((RootBeanDefinition)proxyDef.getBeanDefinition(), configClass, metadata);
            }

            if (logger.isTraceEnabled()) {
               logger.trace(String.format("Registering bean definition for @Bean method %s.%s()", configClass.getMetadata().getClassName(), beanName));
            }

            this.registry.registerBeanDefinition(beanName, beanDefToRegister);
         }
      }
   }

   protected boolean isOverriddenByExistingDefinition(BeanMethod beanMethod, String beanName) {
      if (!this.registry.containsBeanDefinition(beanName)) {
         return false;
      } else {
         BeanDefinition existingBeanDef = this.registry.getBeanDefinition(beanName);
         if (existingBeanDef instanceof ConfigurationClassBeanDefinition) {
            ConfigurationClassBeanDefinition ccbd = (ConfigurationClassBeanDefinition)existingBeanDef;
            return ccbd.getMetadata().getClassName().equals(beanMethod.getConfigurationClass().getMetadata().getClassName());
         } else if (existingBeanDef instanceof ScannedGenericBeanDefinition) {
            return false;
         } else if (existingBeanDef.getRole() > 0) {
            return false;
         } else if (this.registry instanceof DefaultListableBeanFactory && !((DefaultListableBeanFactory)this.registry).isAllowBeanDefinitionOverriding()) {
            throw new BeanDefinitionStoreException(beanMethod.getConfigurationClass().getResource().getDescription(), beanName, "@Bean definition illegally overridden by existing bean definition: " + existingBeanDef);
         } else {
            if (logger.isDebugEnabled()) {
               logger.debug(String.format("Skipping bean definition for %s: a definition for bean '%s' already exists. This top-level bean definition is considered as an override.", beanMethod, beanName));
            }

            return true;
         }
      }
   }

   private void loadBeanDefinitionsFromImportedResources(Map importedResources) {
      Map readerInstanceCache = new HashMap();
      importedResources.forEach((resource, readerClass) -> {
         if (BeanDefinitionReader.class == readerClass) {
            if (StringUtils.endsWithIgnoreCase(resource, ".groovy")) {
               readerClass = GroovyBeanDefinitionReader.class;
            } else {
               readerClass = XmlBeanDefinitionReader.class;
            }
         }

         BeanDefinitionReader reader = (BeanDefinitionReader)readerInstanceCache.get(readerClass);
         if (reader == null) {
            try {
               reader = (BeanDefinitionReader)readerClass.getConstructor(BeanDefinitionRegistry.class).newInstance(this.registry);
               if (reader instanceof AbstractBeanDefinitionReader) {
                  AbstractBeanDefinitionReader abdr = (AbstractBeanDefinitionReader)reader;
                  abdr.setResourceLoader(this.resourceLoader);
                  abdr.setEnvironment(this.environment);
               }

               readerInstanceCache.put(readerClass, reader);
            } catch (Throwable var6) {
               throw new IllegalStateException("Could not instantiate BeanDefinitionReader class [" + readerClass.getName() + "]");
            }
         }

         reader.loadBeanDefinitions(resource);
      });
   }

   private void loadBeanDefinitionsFromRegistrars(Map registrars) {
      registrars.forEach((registrar, metadata) -> {
         registrar.registerBeanDefinitions(metadata, this.registry);
      });
   }

   private class TrackedConditionEvaluator {
      private final Map skipped;

      private TrackedConditionEvaluator() {
         this.skipped = new HashMap();
      }

      public boolean shouldSkip(ConfigurationClass configClass) {
         Boolean skip = (Boolean)this.skipped.get(configClass);
         if (skip == null) {
            if (configClass.isImported()) {
               boolean allSkipped = true;
               Iterator var4 = configClass.getImportedBy().iterator();

               while(var4.hasNext()) {
                  ConfigurationClass importedBy = (ConfigurationClass)var4.next();
                  if (!this.shouldSkip(importedBy)) {
                     allSkipped = false;
                     break;
                  }
               }

               if (allSkipped) {
                  skip = true;
               }
            }

            if (skip == null) {
               skip = ConfigurationClassBeanDefinitionReader.this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
            }

            this.skipped.put(configClass, skip);
         }

         return skip;
      }

      // $FF: synthetic method
      TrackedConditionEvaluator(Object x1) {
         this();
      }
   }

   private static class ConfigurationClassBeanDefinition extends RootBeanDefinition implements AnnotatedBeanDefinition {
      private final AnnotationMetadata annotationMetadata;
      private final MethodMetadata factoryMethodMetadata;

      public ConfigurationClassBeanDefinition(ConfigurationClass configClass, MethodMetadata beanMethodMetadata) {
         this.annotationMetadata = configClass.getMetadata();
         this.factoryMethodMetadata = beanMethodMetadata;
         this.setLenientConstructorResolution(false);
      }

      public ConfigurationClassBeanDefinition(RootBeanDefinition original, ConfigurationClass configClass, MethodMetadata beanMethodMetadata) {
         super(original);
         this.annotationMetadata = configClass.getMetadata();
         this.factoryMethodMetadata = beanMethodMetadata;
      }

      private ConfigurationClassBeanDefinition(ConfigurationClassBeanDefinition original) {
         super((RootBeanDefinition)original);
         this.annotationMetadata = original.annotationMetadata;
         this.factoryMethodMetadata = original.factoryMethodMetadata;
      }

      public AnnotationMetadata getMetadata() {
         return this.annotationMetadata;
      }

      public MethodMetadata getFactoryMethodMetadata() {
         return this.factoryMethodMetadata;
      }

      public boolean isFactoryMethod(Method candidate) {
         return super.isFactoryMethod(candidate) && BeanAnnotationHelper.isBeanAnnotated(candidate);
      }

      public ConfigurationClassBeanDefinition cloneBeanDefinition() {
         return new ConfigurationClassBeanDefinition(this);
      }
   }
}
