package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.springframework.aop.aspectj.SingletonAspectInstanceFactory;
import com.bea.core.repackaged.springframework.core.annotation.OrderUtils;
import java.io.Serializable;

public class SingletonMetadataAwareAspectInstanceFactory extends SingletonAspectInstanceFactory implements MetadataAwareAspectInstanceFactory, Serializable {
   private final AspectMetadata metadata;

   public SingletonMetadataAwareAspectInstanceFactory(Object aspectInstance, String aspectName) {
      super(aspectInstance);
      this.metadata = new AspectMetadata(aspectInstance.getClass(), aspectName);
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
