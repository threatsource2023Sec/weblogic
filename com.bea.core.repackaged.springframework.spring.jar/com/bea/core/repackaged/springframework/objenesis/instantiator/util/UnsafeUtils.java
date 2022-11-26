package com.bea.core.repackaged.springframework.objenesis.instantiator.util;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public final class UnsafeUtils {
   private static final Unsafe unsafe;

   private UnsafeUtils() {
   }

   public static Unsafe getUnsafe() {
      return unsafe;
   }

   static {
      Field f;
      try {
         f = Unsafe.class.getDeclaredField("theUnsafe");
      } catch (NoSuchFieldException var3) {
         throw new ObjenesisException(var3);
      }

      f.setAccessible(true);

      try {
         unsafe = (Unsafe)f.get((Object)null);
      } catch (IllegalAccessException var2) {
         throw new ObjenesisException(var2);
      }
   }
}
