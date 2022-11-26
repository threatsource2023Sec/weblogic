package com.bea.core.repackaged.springframework.objenesis.instantiator.perc;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

@Instantiator(Typology.STANDARD)
public class PercInstantiator implements ObjectInstantiator {
   private final Method newInstanceMethod;
   private final Object[] typeArgs;

   public PercInstantiator(Class type) {
      this.typeArgs = new Object[]{null, Boolean.FALSE};
      this.typeArgs[0] = type;

      try {
         this.newInstanceMethod = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Boolean.TYPE);
         this.newInstanceMethod.setAccessible(true);
      } catch (NoSuchMethodException | RuntimeException var3) {
         throw new ObjenesisException(var3);
      }
   }

   public Object newInstance() {
      try {
         return this.newInstanceMethod.invoke((Object)null, this.typeArgs);
      } catch (Exception var2) {
         throw new ObjenesisException(var2);
      }
   }
}
