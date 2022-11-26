package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionCustomizer;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.support.AutowireCandidateQualifier;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanNameGenerator;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.env.EnvironmentCapable;
import com.bea.core.repackaged.springframework.core.env.StandardEnvironment;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.function.Supplier;

public class AnnotatedBeanDefinitionReader {
   private final BeanDefinitionRegistry registry;
   private BeanNameGenerator beanNameGenerator;
   private ScopeMetadataResolver scopeMetadataResolver;
   private ConditionEvaluator conditionEvaluator;

   public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
      this(registry, getOrCreateEnvironment(registry));
   }

   public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, Environment environment) {
      this.beanNameGenerator = new AnnotationBeanNameGenerator();
      this.scopeMetadataResolver = new AnnotationScopeMetadataResolver();
      Assert.notNull(registry, (String)"BeanDefinitionRegistry must not be null");
      Assert.notNull(environment, (String)"Environment must not be null");
      this.registry = registry;
      this.conditionEvaluator = new ConditionEvaluator(registry, environment, (ResourceLoader)null);
      AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
   }

   public final BeanDefinitionRegistry getRegistry() {
      return this.registry;
   }

   public void setEnvironment(Environment environment) {
      this.conditionEvaluator = new ConditionEvaluator(this.registry, environment, (ResourceLoader)null);
   }

   public void setBeanNameGenerator(@Nullable BeanNameGenerator beanNameGenerator) {
      this.beanNameGenerator = (BeanNameGenerator)(beanNameGenerator != null ? beanNameGenerator : new AnnotationBeanNameGenerator());
   }

   public void setScopeMetadataResolver(@Nullable ScopeMetadataResolver scopeMetadataResolver) {
      this.scopeMetadataResolver = (ScopeMetadataResolver)(scopeMetadataResolver != null ? scopeMetadataResolver : new AnnotationScopeMetadataResolver());
   }

   public void register(Class... componentClasses) {
      Class[] var2 = componentClasses;
      int var3 = componentClasses.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class componentClass = var2[var4];
         this.registerBean(componentClass);
      }

   }

   public void registerBean(Class beanClass) {
      this.doRegisterBean(beanClass, (Supplier)null, (String)null, (Class[])null);
   }

   public void registerBean(Class beanClass, @Nullable Supplier instanceSupplier) {
      this.doRegisterBean(beanClass, instanceSupplier, (String)null, (Class[])null);
   }

   public void registerBean(Class beanClass, String name, @Nullable Supplier instanceSupplier) {
      this.doRegisterBean(beanClass, instanceSupplier, name, (Class[])null);
   }

   public void registerBean(Class beanClass, Class... qualifiers) {
      this.doRegisterBean(beanClass, (Supplier)null, (String)null, qualifiers);
   }

   public void registerBean(Class beanClass, String name, Class... qualifiers) {
      this.doRegisterBean(beanClass, (Supplier)null, name, qualifiers);
   }

   void doRegisterBean(Class beanClass, @Nullable Supplier instanceSupplier, @Nullable String name, @Nullable Class[] qualifiers, BeanDefinitionCustomizer... definitionCustomizers) {
      AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);
      if (!this.conditionEvaluator.shouldSkip(abd.getMetadata())) {
         abd.setInstanceSupplier(instanceSupplier);
         ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
         abd.setScope(scopeMetadata.getScopeName());
         String beanName = name != null ? name : this.beanNameGenerator.generateBeanName(abd, this.registry);
         AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);
         int var10;
         int var11;
         if (qualifiers != null) {
            Class[] var9 = qualifiers;
            var10 = qualifiers.length;

            for(var11 = 0; var11 < var10; ++var11) {
               Class qualifier = var9[var11];
               if (Primary.class == qualifier) {
                  abd.setPrimary(true);
               } else if (Lazy.class == qualifier) {
                  abd.setLazyInit(true);
               } else {
                  abd.addQualifier(new AutowireCandidateQualifier(qualifier));
               }
            }
         }

         BeanDefinitionCustomizer[] var13 = definitionCustomizers;
         var10 = definitionCustomizers.length;

         for(var11 = 0; var11 < var10; ++var11) {
            BeanDefinitionCustomizer customizer = var13[var11];
            customizer.customize(abd);
         }

         BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
         definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
         BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, this.registry);
      }
   }

   private static Environment getOrCreateEnvironment(BeanDefinitionRegistry registry) {
      Assert.notNull(registry, (String)"BeanDefinitionRegistry must not be null");
      return (Environment)(registry instanceof EnvironmentCapable ? ((EnvironmentCapable)registry).getEnvironment() : new StandardEnvironment());
   }
}
