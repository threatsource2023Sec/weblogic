package com.bea.core.repackaged.springframework.objenesis.instantiator.android;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

@Instantiator(Typology.STANDARD)
public class Android10Instantiator implements ObjectInstantiator {
   private final Class type;
   private final Method newStaticMethod;

   public Android10Instantiator(Class type) {
      this.type = type;
      this.newStaticMethod = getNewStaticMethod();
   }

   public Object newInstance() {
      try {
         return this.type.cast(this.newStaticMethod.invoke((Object)null, this.type, Object.class));
      } catch (Exception var2) {
         throw new ObjenesisException(var2);
      }
   }

   private static Method getNewStaticMethod() {
      try {
         Method newStaticMethod = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class);
         newStaticMethod.setAccessible(true);
         return newStaticMethod;
      } catch (NoSuchMethodException | RuntimeException var1) {
         throw new ObjenesisException(var1);
      }
   }
}
