package com.bea.core.repackaged.springframework.objenesis;

import com.bea.core.repackaged.springframework.core.SpringProperties;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.strategy.InstantiatorStrategy;
import com.bea.core.repackaged.springframework.objenesis.strategy.StdInstantiatorStrategy;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;

public class SpringObjenesis implements Objenesis {
   public static final String IGNORE_OBJENESIS_PROPERTY_NAME = "spring.objenesis.ignore";
   private final InstantiatorStrategy strategy;
   private final ConcurrentReferenceHashMap cache;
   private volatile Boolean worthTrying;

   public SpringObjenesis() {
      this((InstantiatorStrategy)null);
   }

   public SpringObjenesis(InstantiatorStrategy strategy) {
      this.cache = new ConcurrentReferenceHashMap();
      this.strategy = (InstantiatorStrategy)(strategy != null ? strategy : new StdInstantiatorStrategy());
      if (SpringProperties.getFlag("spring.objenesis.ignore")) {
         this.worthTrying = Boolean.FALSE;
      }

   }

   public boolean isWorthTrying() {
      return this.worthTrying != Boolean.FALSE;
   }

   public Object newInstance(Class clazz, boolean useCache) {
      return !useCache ? this.newInstantiatorOf(clazz).newInstance() : this.getInstantiatorOf(clazz).newInstance();
   }

   public Object newInstance(Class clazz) {
      return this.getInstantiatorOf(clazz).newInstance();
   }

   public ObjectInstantiator getInstantiatorOf(Class clazz) {
      ObjectInstantiator instantiator = (ObjectInstantiator)this.cache.get(clazz);
      if (instantiator == null) {
         ObjectInstantiator newInstantiator = this.newInstantiatorOf(clazz);
         instantiator = (ObjectInstantiator)this.cache.putIfAbsent(clazz, newInstantiator);
         if (instantiator == null) {
            instantiator = newInstantiator;
         }
      }

      return instantiator;
   }

   protected ObjectInstantiator newInstantiatorOf(Class clazz) {
      Boolean currentWorthTrying = this.worthTrying;

      try {
         ObjectInstantiator instantiator = this.strategy.newInstantiatorOf(clazz);
         if (currentWorthTrying == null) {
            this.worthTrying = Boolean.TRUE;
         }

         return instantiator;
      } catch (ObjenesisException var5) {
         if (currentWorthTrying == null) {
            Throwable cause = var5.getCause();
            if (cause instanceof ClassNotFoundException || cause instanceof IllegalAccessException) {
               this.worthTrying = Boolean.FALSE;
            }
         }

         throw var5;
      } catch (NoClassDefFoundError var6) {
         if (currentWorthTrying == null) {
            this.worthTrying = Boolean.FALSE;
         }

         throw new ObjenesisException(var6);
      }
   }
}
