package com.bea.core.repackaged.springframework.objenesis.instantiator.sun;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;
import java.lang.reflect.Constructor;

@Instantiator(Typology.STANDARD)
public class SunReflectionFactoryInstantiator implements ObjectInstantiator {
   private final Constructor mungedConstructor;

   public SunReflectionFactoryInstantiator(Class type) {
      Constructor javaLangObjectConstructor = getJavaLangObjectConstructor();
      this.mungedConstructor = SunReflectionFactoryHelper.newConstructorForSerialization(type, javaLangObjectConstructor);
      this.mungedConstructor.setAccessible(true);
   }

   public Object newInstance() {
      try {
         return this.mungedConstructor.newInstance((Object[])null);
      } catch (Exception var2) {
         throw new ObjenesisException(var2);
      }
   }

   private static Constructor getJavaLangObjectConstructor() {
      try {
         return Object.class.getConstructor((Class[])null);
      } catch (NoSuchMethodException var1) {
         throw new ObjenesisException(var1);
      }
   }
}
