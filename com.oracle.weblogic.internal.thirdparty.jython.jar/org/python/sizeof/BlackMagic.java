package org.python.sizeof;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import sun.misc.Unsafe;

public final class BlackMagic {
   public static Unsafe getUnsafe() {
      try {
         Class unsafeClass = Class.forName("sun.misc.Unsafe");
         Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
         unsafeField.setAccessible(true);
         return (Unsafe)unsafeField.get((Object)null);
      } catch (Throwable var2) {
         throw new RuntimeException("Unsafe not available.", var2);
      }
   }

   public static String objectMemoryAsString(Object o) {
      Unsafe unsafe = getUnsafe();
      ByteOrder byteOrder = ByteOrder.nativeOrder();
      StringBuilder b = new StringBuilder();
      int obSize = (int)RamUsageEstimator.shallowSizeOf(o);

      for(int i = 0; i < obSize; i += 2) {
         if ((i & 15) == 0) {
            if (i > 0) {
               b.append("\n");
            }

            b.append(String.format(Locale.ENGLISH, "%#06x", i));
         }

         int shortValue = unsafe.getShort(o, (long)i);
         if (byteOrder == ByteOrder.BIG_ENDIAN) {
            b.append(String.format(Locale.ENGLISH, " %02x", shortValue >>> 8 & 255));
            b.append(String.format(Locale.ENGLISH, " %02x", shortValue & 255));
         } else {
            b.append(String.format(Locale.ENGLISH, " %02x", shortValue & 255));
            b.append(String.format(Locale.ENGLISH, " %02x", shortValue >>> 8 & 255));
         }
      }

      return b.toString();
   }

   public static String fieldsLayoutAsString(Class clazz) {
      Unsafe unsafe = getUnsafe();
      TreeMap fields = new TreeMap();

      int i;
      for(Class c = clazz; c != null; c = c.getSuperclass()) {
         Field[] arr$ = c.getDeclaredFields();
         i = arr$.length;

         for(int i$ = 0; i$ < i; ++i$) {
            Field f = arr$[i$];
            fields.put(unsafe.objectFieldOffset(f), f.getDeclaringClass().getSimpleName() + "." + f.getName());
         }
      }

      fields.put(RamUsageEstimator.shallowSizeOfInstance(clazz), "#shallowSizeOfInstance(" + clazz.getName() + ")");
      StringBuilder b = new StringBuilder();
      Object[] entries = fields.entrySet().toArray();

      for(i = 0; i < entries.length; ++i) {
         Map.Entry e = (Map.Entry)entries[i];
         Map.Entry next = i + 1 < entries.length ? (Map.Entry)entries[i + 1] : null;
         b.append(String.format(Locale.ENGLISH, "@%02d %2s %s\n", e.getKey(), next == null ? "" : (Long)next.getKey() - (Long)e.getKey(), e.getValue()));
      }

      return b.toString();
   }
}
