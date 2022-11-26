package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;

@FunctionalInterface
public interface ScopeMetadataResolver {
   ScopeMetadata resolveScopeMetadata(BeanDefinition var1);
}
