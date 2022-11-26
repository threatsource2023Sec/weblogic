package org.python.netty.util.internal.shaded.org.jctools.queues;

import java.lang.reflect.Field;
import java.util.Iterator;
import org.python.netty.util.internal.shaded.org.jctools.util.Pow2;
import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;

public abstract class BaseMpscLinkedArrayQueue extends BaseMpscLinkedArrayQueueColdProducerFields implements MessagePassingQueue, QueueProgressIndicators {
   private static final long P_INDEX_OFFSET;
   private static final long C_INDEX_OFFSET;
   private static final long P_LIMIT_OFFSET;
   private static final Object JUMP;

   public BaseMpscLinkedArrayQueue(int initialCapacity) {
      if (initialCapacity < 2) {
         throw new IllegalArgumentException("Initial capacity must be 2 or more");
      } else {
         int p2capacity = Pow2.roundToPowerOfTwo(initialCapacity);
         long mask = (long)(p2capacity - 1 << 1);
         Object[] buffer = CircularArrayOffsetCalculator.allocate(p2capacity + 1);
         this.producerBuffer = buffer;
         this.producerMask = mask;
         this.consumerBuffer = buffer;
         this.consumerMask = mask;
         this.soProducerLimit(mask);
      }
   }

   public final Iterator iterator() {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      return this.getClass().getName();
   }

   public boolean offer(Object e) {
      if (null == e) {
         throw new NullPointerException();
      } else {
         while(true) {
            while(true) {
               long offset = this.lvProducerLimit();
               long pIndex = this.lvProducerIndex();
               if ((pIndex & 1L) != 1L) {
                  long mask = this.producerMask;
                  Object[] buffer = this.producerBuffer;
                  if (offset <= pIndex) {
                     int result = this.offerSlowPath(mask, pIndex, offset);
                     switch (result) {
                        case 0:
                        default:
                           break;
                        case 1:
                           continue;
                        case 2:
                           return false;
                        case 3:
                           this.resize(mask, buffer, pIndex, e);
                           return true;
                     }
                  }

                  if (this.casProducerIndex(pIndex, pIndex + 2L)) {
                     offset = modifiedCalcElementOffset(pIndex, mask);
                     UnsafeRefArrayAccess.soElement(buffer, offset, e);
                     return true;
                  }
               }
            }
         }
      }
   }

   private int offerSlowPath(long mask, long pIndex, long producerLimit) {
      long cIndex = this.lvConsumerIndex();
      long bufferCapacity = this.getCurrentBufferCapacity(mask);
      int result = 0;
      if (cIndex + bufferCapacity > pIndex) {
         if (!this.casProducerLimit(producerLimit, cIndex + bufferCapacity)) {
            result = 1;
         }
      } else if (this.availableInQueue(pIndex, cIndex) <= 0L) {
         result = 2;
      } else if (this.casProducerIndex(pIndex, pIndex + 1L)) {
         result = 3;
      } else {
         result = 1;
      }

      return result;
   }

   protected abstract long availableInQueue(long var1, long var3);

   private static long modifiedCalcElementOffset(long index, long mask) {
      return UnsafeRefArrayAccess.REF_ARRAY_BASE + ((index & mask) << UnsafeRefArrayAccess.REF_ELEMENT_SHIFT - 1);
   }

   public Object poll() {
      Object[] buffer = this.consumerBuffer;
      long index = this.consumerIndex;
      long mask = this.consumerMask;
      long offset = modifiedCalcElementOffset(index, mask);
      Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
      if (e == null) {
         if (index == this.lvProducerIndex()) {
            return null;
         }

         do {
            e = UnsafeRefArrayAccess.lvElement(buffer, offset);
         } while(e == null);
      }

      if (e == JUMP) {
         Object[] nextBuffer = this.getNextBuffer(buffer, mask);
         return this.newBufferPoll(nextBuffer, index);
      } else {
         UnsafeRefArrayAccess.soElement(buffer, offset, (Object)null);
         this.soConsumerIndex(index + 2L);
         return e;
      }
   }

   public Object peek() {
      Object[] buffer = this.consumerBuffer;
      long index = this.consumerIndex;
      long mask = this.consumerMask;
      long offset = modifiedCalcElementOffset(index, mask);
      Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
      if (e == null && index != this.lvProducerIndex()) {
         while(true) {
            if ((e = UnsafeRefArrayAccess.lvElement(buffer, offset)) == null) {
               continue;
            }
         }
      }

      return e == JUMP ? this.newBufferPeek(this.getNextBuffer(buffer, mask), index) : e;
   }

   private Object[] getNextBuffer(Object[] buffer, long mask) {
      long nextArrayOffset = this.nextArrayOffset(mask);
      Object[] nextBuffer = (Object[])((Object[])UnsafeRefArrayAccess.lvElement(buffer, nextArrayOffset));
      UnsafeRefArrayAccess.soElement(buffer, nextArrayOffset, (Object)null);
      return nextBuffer;
   }

   private long nextArrayOffset(long mask) {
      return modifiedCalcElementOffset(mask + 2L, Long.MAX_VALUE);
   }

   private Object newBufferPoll(Object[] nextBuffer, long index) {
      long offsetInNew = this.newBufferAndOffset(nextBuffer, index);
      Object n = UnsafeRefArrayAccess.lvElement(nextBuffer, offsetInNew);
      if (n == null) {
         throw new IllegalStateException("new buffer must have at least one element");
      } else {
         UnsafeRefArrayAccess.soElement(nextBuffer, offsetInNew, (Object)null);
         this.soConsumerIndex(index + 2L);
         return n;
      }
   }

   private Object newBufferPeek(Object[] nextBuffer, long index) {
      long offsetInNew = this.newBufferAndOffset(nextBuffer, index);
      Object n = UnsafeRefArrayAccess.lvElement(nextBuffer, offsetInNew);
      if (null == n) {
         throw new IllegalStateException("new buffer must have at least one element");
      } else {
         return n;
      }
   }

   private long newBufferAndOffset(Object[] nextBuffer, long index) {
      this.consumerBuffer = nextBuffer;
      this.consumerMask = (long)(nextBuffer.length - 2 << 1);
      long offsetInNew = modifiedCalcElementOffset(index, this.consumerMask);
      return offsetInNew;
   }

   public final int size() {
      long after = this.lvConsumerIndex();

      long before;
      long currentProducerIndex;
      do {
         before = after;
         currentProducerIndex = this.lvProducerIndex();
         after = this.lvConsumerIndex();
      } while(before != after);

      long size = currentProducerIndex - after >> 1;
      return size > 2147483647L ? Integer.MAX_VALUE : (int)size;
   }

   public final boolean isEmpty() {
      return this.lvConsumerIndex() == this.lvProducerIndex();
   }

   private long lvProducerIndex() {
      return UnsafeAccess.UNSAFE.getLongVolatile(this, P_INDEX_OFFSET);
   }

   private long lvConsumerIndex() {
      return UnsafeAccess.UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
   }

   private void soProducerIndex(long v) {
      UnsafeAccess.UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, v);
   }

   private boolean casProducerIndex(long expect, long newValue) {
      return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_INDEX_OFFSET, expect, newValue);
   }

   private void soConsumerIndex(long v) {
      UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, v);
   }

   private long lvProducerLimit() {
      return this.producerLimit;
   }

   private boolean casProducerLimit(long expect, long newValue) {
      return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_LIMIT_OFFSET, expect, newValue);
   }

   private void soProducerLimit(long v) {
      UnsafeAccess.UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, v);
   }

   public long currentProducerIndex() {
      return this.lvProducerIndex() / 2L;
   }

   public long currentConsumerIndex() {
      return this.lvConsumerIndex() / 2L;
   }

   public abstract int capacity();

   public boolean relaxedOffer(Object e) {
      return this.offer(e);
   }

   public Object relaxedPoll() {
      Object[] buffer = this.consumerBuffer;
      long index = this.consumerIndex;
      long mask = this.consumerMask;
      long offset = modifiedCalcElementOffset(index, mask);
      Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
      if (e == null) {
         return null;
      } else if (e == JUMP) {
         Object[] nextBuffer = this.getNextBuffer(buffer, mask);
         return this.newBufferPoll(nextBuffer, index);
      } else {
         UnsafeRefArrayAccess.soElement(buffer, offset, (Object)null);
         this.soConsumerIndex(index + 2L);
         return e;
      }
   }

   public Object relaxedPeek() {
      Object[] buffer = this.consumerBuffer;
      long index = this.consumerIndex;
      long mask = this.consumerMask;
      long offset = modifiedCalcElementOffset(index, mask);
      Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
      return e == JUMP ? this.newBufferPeek(this.getNextBuffer(buffer, mask), index) : e;
   }

   public int fill(MessagePassingQueue.Supplier s, int batchSize) {
      while(true) {
         long producerLimit = this.lvProducerLimit();
         long pIndex = this.lvProducerIndex();
         if ((pIndex & 1L) != 1L) {
            long mask = this.producerMask;
            Object[] buffer = this.producerBuffer;
            long batchIndex = Math.min(producerLimit, pIndex + (long)(2 * batchSize));
            if (pIndex == producerLimit || producerLimit < batchIndex) {
               int result = this.offerSlowPath(mask, pIndex, producerLimit);
               switch (result) {
                  case 1:
                     continue;
                  case 2:
                     return 0;
                  case 3:
                     this.resize(mask, buffer, pIndex, s.get());
                     return 1;
               }
            }

            if (this.casProducerIndex(pIndex, batchIndex)) {
               int claimedSlots = (int)((batchIndex - pIndex) / 2L);
               boolean var14 = false;

               for(int i = 0; i < claimedSlots; ++i) {
                  long offset = modifiedCalcElementOffset(pIndex + (long)(2 * i), mask);
                  UnsafeRefArrayAccess.soElement(buffer, offset, s.get());
               }

               return claimedSlots;
            }
         }
      }
   }

   private void resize(long oldMask, Object[] oldBuffer, long pIndex, Object e) {
      int newBufferLength = this.getNextBufferSize(oldBuffer);
      Object[] newBuffer = CircularArrayOffsetCalculator.allocate(newBufferLength);
      this.producerBuffer = newBuffer;
      int newMask = newBufferLength - 2 << 1;
      this.producerMask = (long)newMask;
      long offsetInOld = modifiedCalcElementOffset(pIndex, oldMask);
      long offsetInNew = modifiedCalcElementOffset(pIndex, (long)newMask);
      UnsafeRefArrayAccess.soElement(newBuffer, offsetInNew, e);
      UnsafeRefArrayAccess.soElement(oldBuffer, this.nextArrayOffset(oldMask), newBuffer);
      long cIndex = this.lvConsumerIndex();
      long availableInQueue = this.availableInQueue(pIndex, cIndex);
      if (availableInQueue <= 0L) {
         throw new IllegalStateException();
      } else {
         this.soProducerLimit(pIndex + Math.min((long)newMask, availableInQueue));
         this.soProducerIndex(pIndex + 2L);
         UnsafeRefArrayAccess.soElement(oldBuffer, offsetInOld, JUMP);
      }
   }

   protected abstract int getNextBufferSize(Object[] var1);

   protected abstract long getCurrentBufferCapacity(long var1);

   public int fill(MessagePassingQueue.Supplier s) {
      long result = 0L;
      int capacity = this.capacity();

      do {
         int filled = this.fill(s, MpmcArrayQueue.RECOMENDED_OFFER_BATCH);
         if (filled == 0) {
            return (int)result;
         }

         result += (long)filled;
      } while(result <= (long)capacity);

      return (int)result;
   }

   public void fill(MessagePassingQueue.Supplier s, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
      while(exit.keepRunning()) {
         while(this.fill(s, MpmcArrayQueue.RECOMENDED_OFFER_BATCH) != 0 && exit.keepRunning()) {
         }

         for(int idleCounter = 0; exit.keepRunning() && this.fill(s, MpmcArrayQueue.RECOMENDED_OFFER_BATCH) == 0; idleCounter = w.idle(idleCounter)) {
         }
      }

   }

   public void drain(MessagePassingQueue.Consumer c, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
      int idleCounter = 0;

      while(exit.keepRunning()) {
         Object e = this.relaxedPoll();
         if (e == null) {
            idleCounter = w.idle(idleCounter);
         } else {
            idleCounter = 0;
            c.accept(e);
         }
      }

   }

   public int drain(MessagePassingQueue.Consumer c) {
      return this.drain(c, this.capacity());
   }

   public int drain(MessagePassingQueue.Consumer c, int limit) {
      int i;
      Object m;
      for(i = 0; i < limit && (m = this.relaxedPoll()) != null; ++i) {
         c.accept(m);
      }

      return i;
   }

   static {
      Field iField;
      try {
         iField = BaseMpscLinkedArrayQueueProducerFields.class.getDeclaredField("producerIndex");
         P_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(iField);
      } catch (NoSuchFieldException var3) {
         throw new RuntimeException(var3);
      }

      try {
         iField = BaseMpscLinkedArrayQueueConsumerFields.class.getDeclaredField("consumerIndex");
         C_INDEX_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(iField);
      } catch (NoSuchFieldException var2) {
         throw new RuntimeException(var2);
      }

      try {
         iField = BaseMpscLinkedArrayQueueColdProducerFields.class.getDeclaredField("producerLimit");
         P_LIMIT_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(iField);
      } catch (NoSuchFieldException var1) {
         throw new RuntimeException(var1);
      }

      JUMP = new Object();
   }
}
