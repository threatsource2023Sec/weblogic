package org.python.netty.util.internal.shaded.org.jctools.queues;

import java.util.Iterator;
import org.python.netty.util.internal.shaded.org.jctools.util.Pow2;

public abstract class ConcurrentCircularArrayQueue extends ConcurrentCircularArrayQueueL0Pad {
   protected final long mask;
   protected final Object[] buffer;

   public ConcurrentCircularArrayQueue(int capacity) {
      int actualCapacity = Pow2.roundToPowerOfTwo(capacity);
      this.mask = (long)(actualCapacity - 1);
      this.buffer = CircularArrayOffsetCalculator.allocate(actualCapacity);
   }

   protected final long calcElementOffset(long index) {
      return calcElementOffset(index, this.mask);
   }

   protected static long calcElementOffset(long index, long mask) {
      return CircularArrayOffsetCalculator.calcElementOffset(index, mask);
   }

   public Iterator iterator() {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      return this.getClass().getName();
   }

   public void clear() {
      while(this.poll() != null || !this.isEmpty()) {
      }

   }

   public int capacity() {
      return (int)(this.mask + 1L);
   }

   public final int size() {
      return IndexedQueueSizeUtil.size(this);
   }

   public final boolean isEmpty() {
      return IndexedQueueSizeUtil.isEmpty(this);
   }

   public final long currentProducerIndex() {
      return this.lvProducerIndex();
   }

   public final long currentConsumerIndex() {
      return this.lvConsumerIndex();
   }
}
