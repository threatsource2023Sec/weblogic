package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.aop.config.AopConfigUtils;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;

class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar {
   public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
      AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);
      AnnotationAttributes enableAspectJAutoProxy = AnnotationConfigUtils.attributesFor(importingClassMetadata, (Class)EnableAspectJAutoProxy.class);
      if (enableAspectJAutoProxy != null) {
         if (enableAspectJAutoProxy.getBoolean("proxyTargetClass")) {
            AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
         }

         if (enableAspectJAutoProxy.getBoolean("exposeProxy")) {
            AopConfigUtils.forceAutoProxyCreatorToExposeProxy(registry);
         }
      }

   }
}
