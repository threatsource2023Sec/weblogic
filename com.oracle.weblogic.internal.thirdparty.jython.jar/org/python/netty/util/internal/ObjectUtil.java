package org.python.netty.util.internal;

public final class ObjectUtil {
   private ObjectUtil() {
   }

   public static Object checkNotNull(Object arg, String text) {
      if (arg == null) {
         throw new NullPointerException(text);
      } else {
         return arg;
      }
   }

   public static int checkPositive(int i, String name) {
      if (i <= 0) {
         throw new IllegalArgumentException(name + ": " + i + " (expected: > 0)");
      } else {
         return i;
      }
   }

   public static long checkPositive(long i, String name) {
      if (i <= 0L) {
         throw new IllegalArgumentException(name + ": " + i + " (expected: > 0)");
      } else {
         return i;
      }
   }

   public static int checkPositiveOrZero(int i, String name) {
      if (i < 0) {
         throw new IllegalArgumentException(name + ": " + i + " (expected: >= 0)");
      } else {
         return i;
      }
   }

   public static long checkPositiveOrZero(long i, String name) {
      if (i < 0L) {
         throw new IllegalArgumentException(name + ": " + i + " (expected: >= 0)");
      } else {
         return i;
      }
   }

   public static Object[] checkNonEmpty(Object[] array, String name) {
      checkNotNull(array, name);
      checkPositive(array.length, name + ".length");
      return array;
   }

   public static int intValue(Integer wrapper, int defaultValue) {
      return wrapper != null ? wrapper : defaultValue;
   }

   public static long longValue(Long wrapper, long defaultValue) {
      return wrapper != null ? wrapper : defaultValue;
   }
}
