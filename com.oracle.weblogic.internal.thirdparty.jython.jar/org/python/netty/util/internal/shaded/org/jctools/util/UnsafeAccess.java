package org.python.netty.util.internal.shaded.org.jctools.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeAccess {
   public static final boolean SUPPORTS_GET_AND_SET;
   public static final Unsafe UNSAFE;

   static {
      Unsafe instance;
      try {
         Field field = Unsafe.class.getDeclaredField("theUnsafe");
         field.setAccessible(true);
         instance = (Unsafe)field.get((Object)null);
      } catch (Exception var5) {
         try {
            Constructor c = Unsafe.class.getDeclaredConstructor();
            c.setAccessible(true);
            instance = (Unsafe)c.newInstance();
         } catch (Exception var4) {
            SUPPORTS_GET_AND_SET = false;
            throw new RuntimeException(var4);
         }
      }

      boolean getAndSetSupport = false;

      try {
         Unsafe.class.getMethod("getAndSetObject", Object.class, Long.TYPE, Object.class);
         getAndSetSupport = true;
      } catch (Exception var3) {
      }

      UNSAFE = instance;
      SUPPORTS_GET_AND_SET = getAndSetSupport;
   }
}
