package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.springframework.aop.aspectj.SimpleAspectInstanceFactory;
import com.bea.core.repackaged.springframework.core.annotation.OrderUtils;

public class SimpleMetadataAwareAspectInstanceFactory extends SimpleAspectInstanceFactory implements MetadataAwareAspectInstanceFactory {
   private final AspectMetadata metadata;

   public SimpleMetadataAwareAspectInstanceFactory(Class aspectClass, String aspectName) {
      super(aspectClass);
      this.metadata = new AspectMetadata(aspectClass, aspectName);
   }

   public final AspectMetadata getAspectMetadata() {
      return this.metadata;
   }

   public Object getAspectCreationMutex() {
      return this;
   }

   protected int getOrderForAspectClass(Class aspectClass) {
      return OrderUtils.getOrder(aspectClass, Integer.MAX_VALUE);
   }
}
