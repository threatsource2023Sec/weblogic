package org.jboss.weld.util;

import java.util.Map;
import org.jboss.weld.util.collections.ImmutableMap;

public final class Defaults {
   private static final Map JLS_PRIMITIVE_DEFAULT_VALUES;

   private Defaults() {
   }

   public static Object getJlsDefaultValue(Class type) {
      return !type.isPrimitive() ? null : JLS_PRIMITIVE_DEFAULT_VALUES.get(type);
   }

   static {
      ImmutableMap.Builder builder = ImmutableMap.builder();
      builder.put(Boolean.TYPE, false);
      builder.put(Character.TYPE, '\u0000');
      builder.put(Byte.TYPE, (byte)0);
      builder.put(Short.TYPE, Short.valueOf((short)0));
      builder.put(Integer.TYPE, 0);
      builder.put(Long.TYPE, 0L);
      builder.put(Float.TYPE, 0.0F);
      builder.put(Double.TYPE, 0.0);
      JLS_PRIMITIVE_DEFAULT_VALUES = builder.build();
   }
}
