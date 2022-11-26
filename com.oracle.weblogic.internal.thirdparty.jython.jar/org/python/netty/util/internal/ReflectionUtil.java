package org.python.netty.util.internal;

import java.lang.reflect.AccessibleObject;

public final class ReflectionUtil {
   private static final boolean IS_EXPLICIT_TRY_REFLECTION_SET_ACCESSIBLE = explicitTryReflectionSetAccessible0();

   private ReflectionUtil() {
   }

   public static Throwable trySetAccessible(AccessibleObject object) {
      if (!isExplicitTryReflectionSetAccessible()) {
         return new UnsupportedOperationException("Reflective setAccessible(true) disabled");
      } else {
         try {
            object.setAccessible(true);
            return null;
         } catch (SecurityException var2) {
            return var2;
         } catch (RuntimeException var3) {
            return handleInaccessibleObjectException(var3);
         }
      }
   }

   private static RuntimeException handleInaccessibleObjectException(RuntimeException e) {
      if ("java.lang.reflect.InaccessibleObjectException".equals(e.getClass().getName())) {
         return e;
      } else {
         throw e;
      }
   }

   private static boolean explicitTryReflectionSetAccessible0() {
      return SystemPropertyUtil.getBoolean("io.netty.tryReflectionSetAccessible", !isJdk9());
   }

   static boolean isExplicitTryReflectionSetAccessible() {
      return IS_EXPLICIT_TRY_REFLECTION_SET_ACCESSIBLE;
   }

   private static boolean isJdk9() {
      return !System.getProperty("java.version").startsWith("1.");
   }
}
