package com.bea.core.repackaged.springframework.objenesis;

import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.strategy.InstantiatorStrategy;
import java.util.concurrent.ConcurrentHashMap;

public class ObjenesisBase implements Objenesis {
   protected final InstantiatorStrategy strategy;
   protected ConcurrentHashMap cache;

   public ObjenesisBase(InstantiatorStrategy strategy) {
      this(strategy, true);
   }

   public ObjenesisBase(InstantiatorStrategy strategy, boolean useCache) {
      if (strategy == null) {
         throw new IllegalArgumentException("A strategy can't be null");
      } else {
         this.strategy = strategy;
         this.cache = useCache ? new ConcurrentHashMap() : null;
      }
   }

   public String toString() {
      return this.getClass().getName() + " using " + this.strategy.getClass().getName() + (this.cache == null ? " without" : " with") + " caching";
   }

   public Object newInstance(Class clazz) {
      return this.getInstantiatorOf(clazz).newInstance();
   }

   public ObjectInstantiator getInstantiatorOf(Class clazz) {
      if (clazz.isPrimitive()) {
         throw new IllegalArgumentException("Primitive types can't be instantiated in Java");
      } else if (this.cache == null) {
         return this.strategy.newInstantiatorOf(clazz);
      } else {
         ObjectInstantiator instantiator = (ObjectInstantiator)this.cache.get(clazz.getName());
         if (instantiator == null) {
            ObjectInstantiator newInstantiator = this.strategy.newInstantiatorOf(clazz);
            instantiator = (ObjectInstantiator)this.cache.putIfAbsent(clazz.getName(), newInstantiator);
            if (instantiator == null) {
               instantiator = newInstantiator;
            }
         }

         return instantiator;
      }
   }
}
