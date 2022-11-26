package org.python.netty.util.internal.shaded.org.jctools.queues;

import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

abstract class MpmcArrayQueueConsumerField extends MpmcArrayQueueL2Pad {
   private static final long C_INDEX_OFFSET;
   private volatile long consumerIndex;

   public MpmcArrayQueueConsumerField(int capacity) {
      super(capacity);
   }

   public final long lvConsumerIndex() {
      return this.consumerIndex;
   }

   protected final boolean casConsumerIndex(long expect, long newValue) {
      return UnsafeAccess.UNSAFE.compareAndSwapLong(this, C_INDEX_OFFSET, expect, newValue);
   }

   static {
      try {
         C_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(MpmcArrayQueueConsumerField.class.getDeclaredField("consumerIndex"));
      } catch (NoSuchFieldException var1) {
         throw new RuntimeException(var1);
      }
   }
}
