package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.aop.scope.ScopedProxyUtils;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;

final class ScopedProxyCreator {
   private ScopedProxyCreator() {
   }

   public static BeanDefinitionHolder createScopedProxy(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry, boolean proxyTargetClass) {
      return ScopedProxyUtils.createScopedProxy(definitionHolder, registry, proxyTargetClass);
   }

   public static String getTargetBeanName(String originalBeanName) {
      return ScopedProxyUtils.getTargetBeanName(originalBeanName);
   }
}
