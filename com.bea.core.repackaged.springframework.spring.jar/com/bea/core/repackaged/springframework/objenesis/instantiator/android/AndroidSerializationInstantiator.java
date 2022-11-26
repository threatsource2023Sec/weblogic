package com.bea.core.repackaged.springframework.objenesis.instantiator.android;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;
import java.io.ObjectStreamClass;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Instantiator(Typology.SERIALIZATION)
public class AndroidSerializationInstantiator implements ObjectInstantiator {
   private final Class type;
   private final ObjectStreamClass objectStreamClass;
   private final Method newInstanceMethod;

   public AndroidSerializationInstantiator(Class type) {
      this.type = type;
      this.newInstanceMethod = getNewInstanceMethod();

      Method m;
      try {
         m = ObjectStreamClass.class.getMethod("lookupAny", Class.class);
      } catch (NoSuchMethodException var5) {
         throw new ObjenesisException(var5);
      }

      try {
         this.objectStreamClass = (ObjectStreamClass)m.invoke((Object)null, type);
      } catch (InvocationTargetException | IllegalAccessException var4) {
         throw new ObjenesisException(var4);
      }
   }

   public Object newInstance() {
      try {
         return this.type.cast(this.newInstanceMethod.invoke(this.objectStreamClass, this.type));
      } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var2) {
         throw new ObjenesisException(var2);
      }
   }

   private static Method getNewInstanceMethod() {
      try {
         Method newInstanceMethod = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class);
         newInstanceMethod.setAccessible(true);
         return newInstanceMethod;
      } catch (NoSuchMethodException | RuntimeException var1) {
         throw new ObjenesisException(var1);
      }
   }
}
