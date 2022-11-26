package org.python.netty.util.internal.shaded.org.jctools.queues;

import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

abstract class MpscArrayQueueConsumerField extends MpscArrayQueueL2Pad {
   private static final long C_INDEX_OFFSET;
   protected long consumerIndex;

   public MpscArrayQueueConsumerField(int capacity) {
      super(capacity);
   }

   protected final long lpConsumerIndex() {
      return this.consumerIndex;
   }

   public final long lvConsumerIndex() {
      return UnsafeAccess.UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
   }

   protected void soConsumerIndex(long l) {
      UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, l);
   }

   static {
      try {
         C_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(MpscArrayQueueConsumerField.class.getDeclaredField("consumerIndex"));
      } catch (NoSuchFieldException var1) {
         throw new RuntimeException(var1);
      }
   }
}
