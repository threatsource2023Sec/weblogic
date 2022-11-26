package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import com.bea.core.repackaged.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import com.bea.core.repackaged.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.List;

public abstract class AopConfigUtils {
   public static final String AUTO_PROXY_CREATOR_BEAN_NAME = "com.bea.core.repackaged.springframework.aop.config.internalAutoProxyCreator";
   private static final List APC_PRIORITY_LIST = new ArrayList(3);

   @Nullable
   public static BeanDefinition registerAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry) {
      return registerAutoProxyCreatorIfNecessary(registry, (Object)null);
   }

   @Nullable
   public static BeanDefinition registerAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry, @Nullable Object source) {
      return registerOrEscalateApcAsRequired(InfrastructureAdvisorAutoProxyCreator.class, registry, source);
   }

   @Nullable
   public static BeanDefinition registerAspectJAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry) {
      return registerAspectJAutoProxyCreatorIfNecessary(registry, (Object)null);
   }

   @Nullable
   public static BeanDefinition registerAspectJAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry, @Nullable Object source) {
      return registerOrEscalateApcAsRequired(AspectJAwareAdvisorAutoProxyCreator.class, registry, source);
   }

   @Nullable
   public static BeanDefinition registerAspectJAnnotationAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry) {
      return registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry, (Object)null);
   }

   @Nullable
   public static BeanDefinition registerAspectJAnnotationAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry, @Nullable Object source) {
      return registerOrEscalateApcAsRequired(AnnotationAwareAspectJAutoProxyCreator.class, registry, source);
   }

   public static void forceAutoProxyCreatorToUseClassProxying(BeanDefinitionRegistry registry) {
      if (registry.containsBeanDefinition("com.bea.core.repackaged.springframework.aop.config.internalAutoProxyCreator")) {
         BeanDefinition definition = registry.getBeanDefinition("com.bea.core.repackaged.springframework.aop.config.internalAutoProxyCreator");
         definition.getPropertyValues().add("proxyTargetClass", Boolean.TRUE);
      }

   }

   public static void forceAutoProxyCreatorToExposeProxy(BeanDefinitionRegistry registry) {
      if (registry.containsBeanDefinition("com.bea.core.repackaged.springframework.aop.config.internalAutoProxyCreator")) {
         BeanDefinition definition = registry.getBeanDefinition("com.bea.core.repackaged.springframework.aop.config.internalAutoProxyCreator");
         definition.getPropertyValues().add("exposeProxy", Boolean.TRUE);
      }

   }

   @Nullable
   private static BeanDefinition registerOrEscalateApcAsRequired(Class cls, BeanDefinitionRegistry registry, @Nullable Object source) {
      Assert.notNull(registry, (String)"BeanDefinitionRegistry must not be null");
      if (registry.containsBeanDefinition("com.bea.core.repackaged.springframework.aop.config.internalAutoProxyCreator")) {
         BeanDefinition apcDefinition = registry.getBeanDefinition("com.bea.core.repackaged.springframework.aop.config.internalAutoProxyCreator");
         if (!cls.getName().equals(apcDefinition.getBeanClassName())) {
            int currentPriority = findPriorityForClass(apcDefinition.getBeanClassName());
            int requiredPriority = findPriorityForClass(cls);
            if (currentPriority < requiredPriority) {
               apcDefinition.setBeanClassName(cls.getName());
            }
         }

         return null;
      } else {
         RootBeanDefinition beanDefinition = new RootBeanDefinition(cls);
         beanDefinition.setSource(source);
         beanDefinition.getPropertyValues().add("order", Integer.MIN_VALUE);
         beanDefinition.setRole(2);
         registry.registerBeanDefinition("com.bea.core.repackaged.springframework.aop.config.internalAutoProxyCreator", beanDefinition);
         return beanDefinition;
      }
   }

   private static int findPriorityForClass(Class clazz) {
      return APC_PRIORITY_LIST.indexOf(clazz);
   }

   private static int findPriorityForClass(@Nullable String className) {
      for(int i = 0; i < APC_PRIORITY_LIST.size(); ++i) {
         Class clazz = (Class)APC_PRIORITY_LIST.get(i);
         if (clazz.getName().equals(className)) {
            return i;
         }
      }

      throw new IllegalArgumentException("Class name [" + className + "] is not a known auto-proxy creator class");
   }

   static {
      APC_PRIORITY_LIST.add(InfrastructureAdvisorAutoProxyCreator.class);
      APC_PRIORITY_LIST.add(AspectJAwareAdvisorAutoProxyCreator.class);
      APC_PRIORITY_LIST.add(AnnotationAwareAspectJAutoProxyCreator.class);
   }
}
