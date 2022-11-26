package com.bea.core.repackaged.springframework.beans.factory.annotation;

import com.bea.core.repackaged.springframework.beans.factory.support.GenericBeanDefinition;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.MethodMetadata;
import com.bea.core.repackaged.springframework.core.type.StandardAnnotationMetadata;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class AnnotatedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {
   private final AnnotationMetadata metadata;
   @Nullable
   private MethodMetadata factoryMethodMetadata;

   public AnnotatedGenericBeanDefinition(Class beanClass) {
      this.setBeanClass(beanClass);
      this.metadata = new StandardAnnotationMetadata(beanClass, true);
   }

   public AnnotatedGenericBeanDefinition(AnnotationMetadata metadata) {
      Assert.notNull(metadata, (String)"AnnotationMetadata must not be null");
      if (metadata instanceof StandardAnnotationMetadata) {
         this.setBeanClass(((StandardAnnotationMetadata)metadata).getIntrospectedClass());
      } else {
         this.setBeanClassName(metadata.getClassName());
      }

      this.metadata = metadata;
   }

   public AnnotatedGenericBeanDefinition(AnnotationMetadata metadata, MethodMetadata factoryMethodMetadata) {
      this(metadata);
      Assert.notNull(factoryMethodMetadata, (String)"MethodMetadata must not be null");
      this.setFactoryMethodName(factoryMethodMetadata.getMethodName());
      this.factoryMethodMetadata = factoryMethodMetadata;
   }

   public final AnnotationMetadata getMetadata() {
      return this.metadata;
   }

   @Nullable
   public final MethodMetadata getFactoryMethodMetadata() {
      return this.factoryMethodMetadata;
   }
}
