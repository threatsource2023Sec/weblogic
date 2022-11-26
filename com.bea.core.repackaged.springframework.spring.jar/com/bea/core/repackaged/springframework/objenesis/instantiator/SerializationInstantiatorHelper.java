package com.bea.core.repackaged.springframework.objenesis.instantiator;

import java.io.Serializable;

public class SerializationInstantiatorHelper {
   public static Class getNonSerializableSuperClass(Class type) {
      Class result = type;

      do {
         if (!Serializable.class.isAssignableFrom(result)) {
            return result;
         }

         result = result.getSuperclass();
      } while(result != null);

      throw new Error("Bad class hierarchy: No non-serializable parents");
   }
}
