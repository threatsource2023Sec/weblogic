package com.bea.core.repackaged.springframework.objenesis.strategy;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SingleInstantiatorStrategy implements InstantiatorStrategy {
   private Constructor constructor;

   public SingleInstantiatorStrategy(Class instantiator) {
      try {
         this.constructor = instantiator.getConstructor(Class.class);
      } catch (NoSuchMethodException var3) {
         throw new ObjenesisException(var3);
      }
   }

   public ObjectInstantiator newInstantiatorOf(Class type) {
      try {
         return (ObjectInstantiator)this.constructor.newInstance(type);
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException var3) {
         throw new ObjenesisException(var3);
      }
   }
}
