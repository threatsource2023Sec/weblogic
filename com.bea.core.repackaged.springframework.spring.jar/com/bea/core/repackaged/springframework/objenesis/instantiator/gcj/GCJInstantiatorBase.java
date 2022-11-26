package com.bea.core.repackaged.springframework.objenesis.instantiator.gcj;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

public abstract class GCJInstantiatorBase implements ObjectInstantiator {
   static Method newObjectMethod = null;
   static ObjectInputStream dummyStream;
   protected final Class type;

   private static void initialize() {
      if (newObjectMethod == null) {
         try {
            newObjectMethod = ObjectInputStream.class.getDeclaredMethod("newObject", Class.class, Class.class);
            newObjectMethod.setAccessible(true);
            dummyStream = new DummyStream();
         } catch (NoSuchMethodException | IOException | RuntimeException var1) {
            throw new ObjenesisException(var1);
         }
      }

   }

   public GCJInstantiatorBase(Class type) {
      this.type = type;
      initialize();
   }

   public abstract Object newInstance();

   private static class DummyStream extends ObjectInputStream {
      public DummyStream() throws IOException {
      }
   }
}
