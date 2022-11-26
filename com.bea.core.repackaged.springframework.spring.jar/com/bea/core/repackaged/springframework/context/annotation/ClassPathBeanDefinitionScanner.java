package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionDefaults;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanNameGenerator;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.env.EnvironmentCapable;
import com.bea.core.repackaged.springframework.core.env.StandardEnvironment;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.PatternMatchUtils;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
   private final BeanDefinitionRegistry registry;
   private BeanDefinitionDefaults beanDefinitionDefaults;
   @Nullable
   private String[] autowireCandidatePatterns;
   private BeanNameGenerator beanNameGenerator;
   private ScopeMetadataResolver scopeMetadataResolver;
   private boolean includeAnnotationConfig;

   public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
      this(registry, true);
   }

   public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
      this(registry, useDefaultFilters, getOrCreateEnvironment(registry));
   }

   public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment) {
      this(registry, useDefaultFilters, environment, registry instanceof ResourceLoader ? (ResourceLoader)registry : null);
   }

   public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment, @Nullable ResourceLoader resourceLoader) {
      this.beanDefinitionDefaults = new BeanDefinitionDefaults();
      this.beanNameGenerator = new AnnotationBeanNameGenerator();
      this.scopeMetadataResolver = new AnnotationScopeMetadataResolver();
      this.includeAnnotationConfig = true;
      Assert.notNull(registry, (String)"BeanDefinitionRegistry must not be null");
      this.registry = registry;
      if (useDefaultFilters) {
         this.registerDefaultFilters();
      }

      this.setEnvironment(environment);
      this.setResourceLoader(resourceLoader);
   }

   public final BeanDefinitionRegistry getRegistry() {
      return this.registry;
   }

   public void setBeanDefinitionDefaults(@Nullable BeanDefinitionDefaults beanDefinitionDefaults) {
      this.beanDefinitionDefaults = beanDefinitionDefaults != null ? beanDefinitionDefaults : new BeanDefinitionDefaults();
   }

   public BeanDefinitionDefaults getBeanDefinitionDefaults() {
      return this.beanDefinitionDefaults;
   }

   public void setAutowireCandidatePatterns(@Nullable String... autowireCandidatePatterns) {
      this.autowireCandidatePatterns = autowireCandidatePatterns;
   }

   public void setBeanNameGenerator(@Nullable BeanNameGenerator beanNameGenerator) {
      this.beanNameGenerator = (BeanNameGenerator)(beanNameGenerator != null ? beanNameGenerator : new AnnotationBeanNameGenerator());
   }

   public void setScopeMetadataResolver(@Nullable ScopeMetadataResolver scopeMetadataResolver) {
      this.scopeMetadataResolver = (ScopeMetadataResolver)(scopeMetadataResolver != null ? scopeMetadataResolver : new AnnotationScopeMetadataResolver());
   }

   public void setScopedProxyMode(ScopedProxyMode scopedProxyMode) {
      this.scopeMetadataResolver = new AnnotationScopeMetadataResolver(scopedProxyMode);
   }

   public void setIncludeAnnotationConfig(boolean includeAnnotationConfig) {
      this.includeAnnotationConfig = includeAnnotationConfig;
   }

   public int scan(String... basePackages) {
      int beanCountAtScanStart = this.registry.getBeanDefinitionCount();
      this.doScan(basePackages);
      if (this.includeAnnotationConfig) {
         AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
      }

      return this.registry.getBeanDefinitionCount() - beanCountAtScanStart;
   }

   protected Set doScan(String... basePackages) {
      Assert.notEmpty((Object[])basePackages, (String)"At least one base package must be specified");
      Set beanDefinitions = new LinkedHashSet();
      String[] var3 = basePackages;
      int var4 = basePackages.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String basePackage = var3[var5];
         Set candidates = this.findCandidateComponents(basePackage);
         Iterator var8 = candidates.iterator();

         while(var8.hasNext()) {
            BeanDefinition candidate = (BeanDefinition)var8.next();
            ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
            candidate.setScope(scopeMetadata.getScopeName());
            String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
            if (candidate instanceof AbstractBeanDefinition) {
               this.postProcessBeanDefinition((AbstractBeanDefinition)candidate, beanName);
            }

            if (candidate instanceof AnnotatedBeanDefinition) {
               AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition)candidate);
            }

            if (this.checkCandidate(beanName, candidate)) {
               BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
               definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
               beanDefinitions.add(definitionHolder);
               this.registerBeanDefinition(definitionHolder, this.registry);
            }
         }
      }

      return beanDefinitions;
   }

   protected void postProcessBeanDefinition(AbstractBeanDefinition beanDefinition, String beanName) {
      beanDefinition.applyDefaults(this.beanDefinitionDefaults);
      if (this.autowireCandidatePatterns != null) {
         beanDefinition.setAutowireCandidate(PatternMatchUtils.simpleMatch(this.autowireCandidatePatterns, beanName));
      }

   }

   protected void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry) {
      BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
   }

   protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
      if (!this.registry.containsBeanDefinition(beanName)) {
         return true;
      } else {
         BeanDefinition existingDef = this.registry.getBeanDefinition(beanName);
         BeanDefinition originatingDef = existingDef.getOriginatingBeanDefinition();
         if (originatingDef != null) {
            existingDef = originatingDef;
         }

         if (this.isCompatible(beanDefinition, existingDef)) {
            return false;
         } else {
            throw new ConflictingBeanDefinitionException("Annotation-specified bean name '" + beanName + "' for bean class [" + beanDefinition.getBeanClassName() + "] conflicts with existing, non-compatible bean definition of same name and class [" + existingDef.getBeanClassName() + "]");
         }
      }
   }

   protected boolean isCompatible(BeanDefinition newDefinition, BeanDefinition existingDefinition) {
      return !(existingDefinition instanceof ScannedGenericBeanDefinition) || newDefinition.getSource() != null && newDefinition.getSource().equals(existingDefinition.getSource()) || newDefinition.equals(existingDefinition);
   }

   private static Environment getOrCreateEnvironment(BeanDefinitionRegistry registry) {
      Assert.notNull(registry, (String)"BeanDefinitionRegistry must not be null");
      return (Environment)(registry instanceof EnvironmentCapable ? ((EnvironmentCapable)registry).getEnvironment() : new StandardEnvironment());
   }
}
