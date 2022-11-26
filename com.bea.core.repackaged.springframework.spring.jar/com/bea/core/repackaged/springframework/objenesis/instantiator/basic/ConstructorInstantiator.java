package com.bea.core.repackaged.springframework.objenesis.instantiator.basic;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;
import java.lang.reflect.Constructor;

@Instantiator(Typology.NOT_COMPLIANT)
public class ConstructorInstantiator implements ObjectInstantiator {
   protected Constructor constructor;

   public ConstructorInstantiator(Class type) {
      try {
         this.constructor = type.getDeclaredConstructor((Class[])null);
      } catch (Exception var3) {
         throw new ObjenesisException(var3);
      }
   }

   public Object newInstance() {
      try {
         return this.constructor.newInstance((Object[])null);
      } catch (Exception var2) {
         throw new ObjenesisException(var2);
      }
   }
}
