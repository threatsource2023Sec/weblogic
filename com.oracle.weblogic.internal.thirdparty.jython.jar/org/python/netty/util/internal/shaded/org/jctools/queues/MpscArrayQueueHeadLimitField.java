package org.python.netty.util.internal.shaded.org.jctools.queues;

import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

abstract class MpscArrayQueueHeadLimitField extends MpscArrayQueueMidPad {
   private static final long P_LIMIT_OFFSET;
   private volatile long producerLimit;

   public MpscArrayQueueHeadLimitField(int capacity) {
      super(capacity);
      this.producerLimit = (long)capacity;
   }

   protected final long lvProducerLimit() {
      return this.producerLimit;
   }

   protected final void soProducerLimit(long v) {
      UnsafeAccess.UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, v);
   }

   static {
      try {
         P_LIMIT_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(MpscArrayQueueHeadLimitField.class.getDeclaredField("producerLimit"));
      } catch (NoSuchFieldException var1) {
         throw new RuntimeException(var1);
      }
   }
}
