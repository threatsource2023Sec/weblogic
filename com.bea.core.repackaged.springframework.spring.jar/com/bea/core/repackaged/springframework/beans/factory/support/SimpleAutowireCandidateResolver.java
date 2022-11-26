package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.config.DependencyDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class SimpleAutowireCandidateResolver implements AutowireCandidateResolver {
   public boolean isAutowireCandidate(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
      return bdHolder.getBeanDefinition().isAutowireCandidate();
   }

   public boolean isRequired(DependencyDescriptor descriptor) {
      return descriptor.isRequired();
   }

   @Nullable
   public Object getSuggestedValue(DependencyDescriptor descriptor) {
      return null;
   }

   @Nullable
   public Object getLazyResolutionProxyIfNecessary(DependencyDescriptor descriptor, @Nullable String beanName) {
      return null;
   }
}
