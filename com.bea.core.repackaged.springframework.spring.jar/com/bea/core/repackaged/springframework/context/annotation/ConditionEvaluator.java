package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.context.ConfigurableApplicationContext;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAwareOrderComparator;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.env.EnvironmentCapable;
import com.bea.core.repackaged.springframework.core.env.StandardEnvironment;
import com.bea.core.repackaged.springframework.core.io.DefaultResourceLoader;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.core.type.AnnotatedTypeMetadata;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class ConditionEvaluator {
   private final ConditionContextImpl context;

   public ConditionEvaluator(@Nullable BeanDefinitionRegistry registry, @Nullable Environment environment, @Nullable ResourceLoader resourceLoader) {
      this.context = new ConditionContextImpl(registry, environment, resourceLoader);
   }

   public boolean shouldSkip(AnnotatedTypeMetadata metadata) {
      return this.shouldSkip(metadata, (ConfigurationCondition.ConfigurationPhase)null);
   }

   public boolean shouldSkip(@Nullable AnnotatedTypeMetadata metadata, @Nullable ConfigurationCondition.ConfigurationPhase phase) {
      if (metadata != null && metadata.isAnnotated(Conditional.class.getName())) {
         if (phase == null) {
            return metadata instanceof AnnotationMetadata && ConfigurationClassUtils.isConfigurationCandidate((AnnotationMetadata)metadata) ? this.shouldSkip(metadata, ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION) : this.shouldSkip(metadata, ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
         } else {
            List conditions = new ArrayList();
            Iterator var4 = this.getConditionClasses(metadata).iterator();

            while(var4.hasNext()) {
               String[] conditionClasses = (String[])var4.next();
               String[] var6 = conditionClasses;
               int var7 = conditionClasses.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  String conditionClass = var6[var8];
                  Condition condition = this.getCondition(conditionClass, this.context.getClassLoader());
                  conditions.add(condition);
               }
            }

            AnnotationAwareOrderComparator.sort((List)conditions);
            var4 = conditions.iterator();

            Condition condition;
            ConfigurationCondition.ConfigurationPhase requiredPhase;
            do {
               do {
                  if (!var4.hasNext()) {
                     return false;
                  }

                  condition = (Condition)var4.next();
                  requiredPhase = null;
                  if (condition instanceof ConfigurationCondition) {
                     requiredPhase = ((ConfigurationCondition)condition).getConfigurationPhase();
                  }
               } while(requiredPhase != null && requiredPhase != phase);
            } while(condition.matches(this.context, metadata));

            return true;
         }
      } else {
         return false;
      }
   }

   private List getConditionClasses(AnnotatedTypeMetadata metadata) {
      MultiValueMap attributes = metadata.getAllAnnotationAttributes(Conditional.class.getName(), true);
      Object values = attributes != null ? attributes.get("value") : null;
      return (List)((List)(values != null ? values : Collections.emptyList()));
   }

   private Condition getCondition(String conditionClassName, @Nullable ClassLoader classloader) {
      Class conditionClass = ClassUtils.resolveClassName(conditionClassName, classloader);
      return (Condition)BeanUtils.instantiateClass(conditionClass);
   }

   private static class ConditionContextImpl implements ConditionContext {
      @Nullable
      private final BeanDefinitionRegistry registry;
      @Nullable
      private final ConfigurableListableBeanFactory beanFactory;
      private final Environment environment;
      private final ResourceLoader resourceLoader;
      @Nullable
      private final ClassLoader classLoader;

      public ConditionContextImpl(@Nullable BeanDefinitionRegistry registry, @Nullable Environment environment, @Nullable ResourceLoader resourceLoader) {
         this.registry = registry;
         this.beanFactory = this.deduceBeanFactory(registry);
         this.environment = environment != null ? environment : this.deduceEnvironment(registry);
         this.resourceLoader = resourceLoader != null ? resourceLoader : this.deduceResourceLoader(registry);
         this.classLoader = this.deduceClassLoader(resourceLoader, this.beanFactory);
      }

      @Nullable
      private ConfigurableListableBeanFactory deduceBeanFactory(@Nullable BeanDefinitionRegistry source) {
         if (source instanceof ConfigurableListableBeanFactory) {
            return (ConfigurableListableBeanFactory)source;
         } else {
            return source instanceof ConfigurableApplicationContext ? ((ConfigurableApplicationContext)source).getBeanFactory() : null;
         }
      }

      private Environment deduceEnvironment(@Nullable BeanDefinitionRegistry source) {
         return (Environment)(source instanceof EnvironmentCapable ? ((EnvironmentCapable)source).getEnvironment() : new StandardEnvironment());
      }

      private ResourceLoader deduceResourceLoader(@Nullable BeanDefinitionRegistry source) {
         return (ResourceLoader)(source instanceof ResourceLoader ? (ResourceLoader)source : new DefaultResourceLoader());
      }

      @Nullable
      private ClassLoader deduceClassLoader(@Nullable ResourceLoader resourceLoader, @Nullable ConfigurableListableBeanFactory beanFactory) {
         if (resourceLoader != null) {
            ClassLoader classLoader = resourceLoader.getClassLoader();
            if (classLoader != null) {
               return classLoader;
            }
         }

         return beanFactory != null ? beanFactory.getBeanClassLoader() : ClassUtils.getDefaultClassLoader();
      }

      public BeanDefinitionRegistry getRegistry() {
         Assert.state(this.registry != null, "No BeanDefinitionRegistry available");
         return this.registry;
      }

      @Nullable
      public ConfigurableListableBeanFactory getBeanFactory() {
         return this.beanFactory;
      }

      public Environment getEnvironment() {
         return this.environment;
      }

      public ResourceLoader getResourceLoader() {
         return this.resourceLoader;
      }

      @Nullable
      public ClassLoader getClassLoader() {
         return this.classLoader;
      }
   }
}
