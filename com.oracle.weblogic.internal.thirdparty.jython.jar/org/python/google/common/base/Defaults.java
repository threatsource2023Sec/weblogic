package org.python.google.common.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtIncompatible;

@GwtIncompatible
public final class Defaults {
   private static final Map DEFAULTS;

   private Defaults() {
   }

   private static void put(Map map, Class type, Object value) {
      map.put(type, value);
   }

   @Nullable
   public static Object defaultValue(Class type) {
      Object t = DEFAULTS.get(Preconditions.checkNotNull(type));
      return t;
   }

   static {
      Map map = new HashMap();
      put(map, Boolean.TYPE, false);
      put(map, Character.TYPE, '\u0000');
      put(map, Byte.TYPE, (byte)0);
      put(map, Short.TYPE, Short.valueOf((short)0));
      put(map, Integer.TYPE, 0);
      put(map, Long.TYPE, 0L);
      put(map, Float.TYPE, 0.0F);
      put(map, Double.TYPE, 0.0);
      DEFAULTS = Collections.unmodifiableMap(map);
   }
}
