package org.python.netty.util.internal.shaded.org.jctools.util;

public final class UnsafeRefArrayAccess {
   public static final long REF_ARRAY_BASE;
   public static final int REF_ELEMENT_SHIFT;

   private UnsafeRefArrayAccess() {
   }

   public static void spElement(Object[] buffer, long offset, Object e) {
      UnsafeAccess.UNSAFE.putObject(buffer, offset, e);
   }

   public static void soElement(Object[] buffer, long offset, Object e) {
      UnsafeAccess.UNSAFE.putOrderedObject(buffer, offset, e);
   }

   public static Object lpElement(Object[] buffer, long offset) {
      return UnsafeAccess.UNSAFE.getObject(buffer, offset);
   }

   public static Object lvElement(Object[] buffer, long offset) {
      return UnsafeAccess.UNSAFE.getObjectVolatile(buffer, offset);
   }

   public static long calcElementOffset(long index) {
      return REF_ARRAY_BASE + (index << REF_ELEMENT_SHIFT);
   }

   static {
      int scale = UnsafeAccess.UNSAFE.arrayIndexScale(Object[].class);
      if (4 == scale) {
         REF_ELEMENT_SHIFT = 2;
      } else {
         if (8 != scale) {
            throw new IllegalStateException("Unknown pointer size");
         }

         REF_ELEMENT_SHIFT = 3;
      }

      REF_ARRAY_BASE = (long)UnsafeAccess.UNSAFE.arrayBaseOffset(Object[].class);
   }
}
