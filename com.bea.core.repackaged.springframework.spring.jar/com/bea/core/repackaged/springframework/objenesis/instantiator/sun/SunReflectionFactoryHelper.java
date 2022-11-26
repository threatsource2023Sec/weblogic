package com.bea.core.repackaged.springframework.objenesis.instantiator.sun;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class SunReflectionFactoryHelper {
   public static Constructor newConstructorForSerialization(Class type, Constructor constructor) {
      Class reflectionFactoryClass = getReflectionFactoryClass();
      Object reflectionFactory = createReflectionFactory(reflectionFactoryClass);
      Method newConstructorForSerializationMethod = getNewConstructorForSerializationMethod(reflectionFactoryClass);

      try {
         return (Constructor)newConstructorForSerializationMethod.invoke(reflectionFactory, type, constructor);
      } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException var6) {
         throw new ObjenesisException(var6);
      }
   }

   private static Class getReflectionFactoryClass() {
      try {
         return Class.forName("sun.reflect.ReflectionFactory");
      } catch (ClassNotFoundException var1) {
         throw new ObjenesisException(var1);
      }
   }

   private static Object createReflectionFactory(Class reflectionFactoryClass) {
      try {
         Method method = reflectionFactoryClass.getDeclaredMethod("getReflectionFactory");
         return method.invoke((Object)null);
      } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException | NoSuchMethodException var2) {
         throw new ObjenesisException(var2);
      }
   }

   private static Method getNewConstructorForSerializationMethod(Class reflectionFactoryClass) {
      try {
         return reflectionFactoryClass.getDeclaredMethod("newConstructorForSerialization", Class.class, Constructor.class);
      } catch (NoSuchMethodException var2) {
         throw new ObjenesisException(var2);
      }
   }
}
