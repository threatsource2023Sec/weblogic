package com.bea.core.repackaged.springframework.objenesis;

import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import java.io.Serializable;

public final class ObjenesisHelper {
   private static final Objenesis OBJENESIS_STD = new ObjenesisStd();
   private static final Objenesis OBJENESIS_SERIALIZER = new ObjenesisSerializer();

   private ObjenesisHelper() {
   }

   public static Object newInstance(Class clazz) {
      return OBJENESIS_STD.newInstance(clazz);
   }

   public static Serializable newSerializableInstance(Class clazz) {
      return (Serializable)OBJENESIS_SERIALIZER.newInstance(clazz);
   }

   public static ObjectInstantiator getInstantiatorOf(Class clazz) {
      return OBJENESIS_STD.getInstantiatorOf(clazz);
   }

   public static ObjectInstantiator getSerializableObjectInstantiatorOf(Class clazz) {
      return OBJENESIS_SERIALIZER.getInstantiatorOf(clazz);
   }
}
