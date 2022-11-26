package org.glassfish.tyrus.core;

import java.util.HashMap;

class PrimitivesToWrappers {
   private static final HashMap conversionMap = new HashMap();

   public static Class getPrimitiveWrapper(Class c) {
      if (!c.isPrimitive()) {
         return c;
      } else {
         return conversionMap.containsKey(c) ? (Class)conversionMap.get(c) : c;
      }
   }

   public static boolean isPrimitiveWrapper(Class c) {
      return conversionMap.containsValue(c);
   }

   static {
      conversionMap.put(Integer.TYPE, Integer.class);
      conversionMap.put(Short.TYPE, Short.class);
      conversionMap.put(Long.TYPE, Long.class);
      conversionMap.put(Double.TYPE, Double.class);
      conversionMap.put(Float.TYPE, Float.class);
      conversionMap.put(Boolean.TYPE, Boolean.class);
      conversionMap.put(Byte.TYPE, Byte.class);
      conversionMap.put(Character.TYPE, Character.class);
      conversionMap.put(Void.TYPE, Void.class);
   }
}
