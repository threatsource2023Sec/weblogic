package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;

public interface ImportBeanDefinitionRegistrar {
   void registerBeanDefinitions(AnnotationMetadata var1, BeanDefinitionRegistry var2);
}
