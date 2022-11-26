package com.bea.core.repackaged.springframework.beans.factory.annotation;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.MethodMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface AnnotatedBeanDefinition extends BeanDefinition {
   AnnotationMetadata getMetadata();

   @Nullable
   MethodMetadata getFactoryMethodMetadata();
}
