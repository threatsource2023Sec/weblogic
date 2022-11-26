package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.GenericBeanDefinition;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.core.type.MethodMetadata;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReader;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {
   private final AnnotationMetadata metadata;

   public ScannedGenericBeanDefinition(MetadataReader metadataReader) {
      Assert.notNull(metadataReader, (String)"MetadataReader must not be null");
      this.metadata = metadataReader.getAnnotationMetadata();
      this.setBeanClassName(this.metadata.getClassName());
   }

   public final AnnotationMetadata getMetadata() {
      return this.metadata;
   }

   @Nullable
   public MethodMetadata getFactoryMethodMetadata() {
      return null;
   }
}
