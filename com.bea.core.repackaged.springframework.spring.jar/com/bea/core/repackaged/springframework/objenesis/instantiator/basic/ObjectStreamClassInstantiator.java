package com.bea.core.repackaged.springframework.objenesis.instantiator.basic;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;
import java.io.ObjectStreamClass;
import java.lang.reflect.Method;

@Instantiator(Typology.SERIALIZATION)
public class ObjectStreamClassInstantiator implements ObjectInstantiator {
   private static Method newInstanceMethod;
   private final ObjectStreamClass objStreamClass;

   private static void initialize() {
      if (newInstanceMethod == null) {
         try {
            newInstanceMethod = ObjectStreamClass.class.getDeclaredMethod("newInstance");
            newInstanceMethod.setAccessible(true);
         } catch (NoSuchMethodException | RuntimeException var1) {
            throw new ObjenesisException(var1);
         }
      }

   }

   public ObjectStreamClassInstantiator(Class type) {
      initialize();
      this.objStreamClass = ObjectStreamClass.lookup(type);
   }

   public Object newInstance() {
      try {
         return newInstanceMethod.invoke(this.objStreamClass);
      } catch (Exception var2) {
         throw new ObjenesisException(var2);
      }
   }
}
