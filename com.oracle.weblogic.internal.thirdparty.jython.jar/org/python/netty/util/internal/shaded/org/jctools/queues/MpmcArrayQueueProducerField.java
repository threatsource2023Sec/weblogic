package org.python.netty.util.internal.shaded.org.jctools.queues;

import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

abstract class MpmcArrayQueueProducerField extends MpmcArrayQueueL1Pad {
   private static final long P_INDEX_OFFSET;
   private volatile long producerIndex;

   public MpmcArrayQueueProducerField(int capacity) {
      super(capacity);
   }

   public final long lvProducerIndex() {
      return this.producerIndex;
   }

   protected final boolean casProducerIndex(long expect, long newValue) {
      return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_INDEX_OFFSET, expect, newValue);
   }

   static {
      try {
         P_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(MpmcArrayQueueProducerField.class.getDeclaredField("producerIndex"));
      } catch (NoSuchFieldException var1) {
         throw new RuntimeException(var1);
      }
   }
}
