package com.bea.core.repackaged.springframework.objenesis.instantiator.android;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;
import java.io.ObjectStreamClass;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Instantiator(Typology.STANDARD)
public class Android17Instantiator implements ObjectInstantiator {
   private final Class type;
   private final Method newInstanceMethod;
   private final Integer objectConstructorId;

   public Android17Instantiator(Class type) {
      this.type = type;
      this.newInstanceMethod = getNewInstanceMethod();
      this.objectConstructorId = findConstructorIdForJavaLangObjectConstructor();
   }

   public Object newInstance() {
      try {
         return this.type.cast(this.newInstanceMethod.invoke((Object)null, this.type, this.objectConstructorId));
      } catch (Exception var2) {
         throw new ObjenesisException(var2);
      }
   }

   private static Method getNewInstanceMethod() {
      try {
         Method newInstanceMethod = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class, Integer.TYPE);
         newInstanceMethod.setAccessible(true);
         return newInstanceMethod;
      } catch (NoSuchMethodException | RuntimeException var1) {
         throw new ObjenesisException(var1);
      }
   }

   private static Integer findConstructorIdForJavaLangObjectConstructor() {
      try {
         Method newInstanceMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", Class.class);
         newInstanceMethod.setAccessible(true);
         return (Integer)newInstanceMethod.invoke((Object)null, Object.class);
      } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | RuntimeException var1) {
         throw new ObjenesisException(var1);
      }
   }
}
