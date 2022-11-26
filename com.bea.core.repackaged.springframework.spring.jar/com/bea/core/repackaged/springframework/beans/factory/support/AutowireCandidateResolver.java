package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.config.DependencyDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface AutowireCandidateResolver {
   default boolean isAutowireCandidate(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
      return bdHolder.getBeanDefinition().isAutowireCandidate();
   }

   default boolean isRequired(DependencyDescriptor descriptor) {
      return descriptor.isRequired();
   }

   default boolean hasQualifier(DependencyDescriptor descriptor) {
      return false;
   }

   @Nullable
   default Object getSuggestedValue(DependencyDescriptor descriptor) {
      return null;
   }

   @Nullable
   default Object getLazyResolutionProxyIfNecessary(DependencyDescriptor descriptor, @Nullable String beanName) {
      return null;
   }
}
