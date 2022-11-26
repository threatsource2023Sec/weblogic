package org.python.netty.util.internal.shaded.org.jctools.queues;

import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;

public final class CircularArrayOffsetCalculator {
   private CircularArrayOffsetCalculator() {
   }

   public static Object[] allocate(int capacity) {
      return (Object[])(new Object[capacity]);
   }

   public static long calcElementOffset(long index, long mask) {
      return UnsafeRefArrayAccess.REF_ARRAY_BASE + ((index & mask) << UnsafeRefArrayAccess.REF_ELEMENT_SHIFT);
   }
}
