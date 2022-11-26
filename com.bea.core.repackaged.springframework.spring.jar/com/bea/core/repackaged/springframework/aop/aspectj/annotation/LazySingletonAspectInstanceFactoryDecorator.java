package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public class LazySingletonAspectInstanceFactoryDecorator implements MetadataAwareAspectInstanceFactory, Serializable {
   private final MetadataAwareAspectInstanceFactory maaif;
   @Nullable
   private volatile Object materialized;

   public LazySingletonAspectInstanceFactoryDecorator(MetadataAwareAspectInstanceFactory maaif) {
      Assert.notNull(maaif, (String)"AspectInstanceFactory must not be null");
      this.maaif = maaif;
   }

   public Object getAspectInstance() {
      Object aspectInstance = this.materialized;
      if (aspectInstance == null) {
         Object mutex = this.maaif.getAspectCreationMutex();
         if (mutex == null) {
            aspectInstance = this.maaif.getAspectInstance();
            this.materialized = aspectInstance;
         } else {
            synchronized(mutex) {
               aspectInstance = this.materialized;
               if (aspectInstance == null) {
                  aspectInstance = this.maaif.getAspectInstance();
                  this.materialized = aspectInstance;
               }
            }
         }
      }

      return aspectInstance;
   }

   public boolean isMaterialized() {
      return this.materialized != null;
   }

   @Nullable
   public ClassLoader getAspectClassLoader() {
      return this.maaif.getAspectClassLoader();
   }

   public AspectMetadata getAspectMetadata() {
      return this.maaif.getAspectMetadata();
   }

   @Nullable
   public Object getAspectCreationMutex() {
      return this.maaif.getAspectCreationMutex();
   }

   public int getOrder() {
      return this.maaif.getOrder();
   }

   public String toString() {
      return "LazySingletonAspectInstanceFactoryDecorator: decorating " + this.maaif;
   }
}
