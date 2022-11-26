package org.python.netty.util.internal.shaded.org.jctools.queues;

import org.python.netty.util.internal.shaded.org.jctools.util.JvmInfo;
import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;

public class MpmcArrayQueue extends MpmcArrayQueueConsumerField implements QueueProgressIndicators {
   long p01;
   long p02;
   long p03;
   long p04;
   long p05;
   long p06;
   long p07;
   long p10;
   long p11;
   long p12;
   long p13;
   long p14;
   long p15;
   long p16;
   long p17;
   static final int RECOMENDED_POLL_BATCH;
   static final int RECOMENDED_OFFER_BATCH;

   public MpmcArrayQueue(int capacity) {
      super(validateCapacity(capacity));
   }

   private static int validateCapacity(int capacity) {
      if (capacity < 2) {
         throw new IllegalArgumentException("Minimum size is 2");
      } else {
         return capacity;
      }
   }

   public boolean offer(Object e) {
      if (null == e) {
         throw new NullPointerException();
      } else {
         long mask = this.mask;
         long capacity = mask + 1L;
         long[] sBuffer = this.sequenceBuffer;
         long cIndex = Long.MAX_VALUE;

         long pIndex;
         long seqOffset;
         long seq;
         do {
            pIndex = this.lvProducerIndex();
            seqOffset = calcSequenceOffset(pIndex, mask);
            seq = this.lvSequence(sBuffer, seqOffset);
            if (seq < pIndex) {
               if (pIndex - capacity <= cIndex && pIndex - capacity <= (cIndex = this.lvConsumerIndex())) {
                  return false;
               }

               seq = pIndex + 1L;
            }
         } while(seq > pIndex || !this.casProducerIndex(pIndex, pIndex + 1L));

         assert null == UnsafeRefArrayAccess.lpElement(this.buffer, calcElementOffset(pIndex, mask));

         UnsafeRefArrayAccess.soElement(this.buffer, calcElementOffset(pIndex, mask), e);
         this.soSequence(sBuffer, seqOffset, pIndex + 1L);
         return true;
      }
   }

   public Object poll() {
      long[] sBuffer = this.sequenceBuffer;
      long mask = this.mask;
      long pIndex = -1L;

      long cIndex;
      long seqOffset;
      long seq;
      long expectedSeq;
      do {
         cIndex = this.lvConsumerIndex();
         seqOffset = calcSequenceOffset(cIndex, mask);
         seq = this.lvSequence(sBuffer, seqOffset);
         expectedSeq = cIndex + 1L;
         if (seq < expectedSeq) {
            if (cIndex >= pIndex && cIndex == (pIndex = this.lvProducerIndex())) {
               return null;
            }

            seq = expectedSeq + 1L;
         }
      } while(seq > expectedSeq || !this.casConsumerIndex(cIndex, cIndex + 1L));

      long offset = calcElementOffset(cIndex, mask);
      Object e = UnsafeRefArrayAccess.lpElement(this.buffer, offset);

      assert e != null;

      UnsafeRefArrayAccess.soElement(this.buffer, offset, (Object)null);
      this.soSequence(sBuffer, seqOffset, cIndex + mask + 1L);
      return e;
   }

   public Object peek() {
      long cIndex;
      Object e;
      do {
         cIndex = this.lvConsumerIndex();
         e = UnsafeRefArrayAccess.lpElement(this.buffer, this.calcElementOffset(cIndex));
      } while(e == null && cIndex != this.lvProducerIndex());

      return e;
   }

   public boolean relaxedOffer(Object e) {
      if (null == e) {
         throw new NullPointerException();
      } else {
         long mask = this.mask;
         long[] sBuffer = this.sequenceBuffer;

         long pIndex;
         long seqOffset;
         long seq;
         do {
            pIndex = this.lvProducerIndex();
            seqOffset = calcSequenceOffset(pIndex, mask);
            seq = this.lvSequence(sBuffer, seqOffset);
            if (seq < pIndex) {
               return false;
            }
         } while(seq > pIndex || !this.casProducerIndex(pIndex, pIndex + 1L));

         UnsafeRefArrayAccess.soElement(this.buffer, calcElementOffset(pIndex, mask), e);
         this.soSequence(sBuffer, seqOffset, pIndex + 1L);
         return true;
      }
   }

   public Object relaxedPoll() {
      long[] sBuffer = this.sequenceBuffer;
      long mask = this.mask;

      long cIndex;
      long seqOffset;
      long seq;
      long expectedSeq;
      do {
         cIndex = this.lvConsumerIndex();
         seqOffset = calcSequenceOffset(cIndex, mask);
         seq = this.lvSequence(sBuffer, seqOffset);
         expectedSeq = cIndex + 1L;
         if (seq < expectedSeq) {
            return null;
         }
      } while(seq > expectedSeq || !this.casConsumerIndex(cIndex, cIndex + 1L));

      long offset = calcElementOffset(cIndex, mask);
      Object e = UnsafeRefArrayAccess.lpElement(this.buffer, offset);
      UnsafeRefArrayAccess.soElement(this.buffer, offset, (Object)null);
      this.soSequence(sBuffer, seqOffset, cIndex + mask + 1L);
      return e;
   }

   public Object relaxedPeek() {
      long currConsumerIndex = this.lvConsumerIndex();
      return UnsafeRefArrayAccess.lpElement(this.buffer, this.calcElementOffset(currConsumerIndex));
   }

   public int drain(MessagePassingQueue.Consumer c) {
      int capacity = this.capacity();

      int sum;
      int drained;
      for(sum = 0; sum < capacity; sum += drained) {
         int drained = false;
         if ((drained = this.drain(c, RECOMENDED_POLL_BATCH)) == 0) {
            break;
         }
      }

      return sum;
   }

   public int fill(MessagePassingQueue.Supplier s) {
      long result = 0L;
      int capacity = this.capacity();

      do {
         int filled = this.fill(s, RECOMENDED_OFFER_BATCH);
         if (filled == 0) {
            return (int)result;
         }

         result += (long)filled;
      } while(result <= (long)capacity);

      return (int)result;
   }

   public int drain(MessagePassingQueue.Consumer c, int limit) {
      long[] sBuffer = this.sequenceBuffer;
      long mask = this.mask;
      Object[] buffer = this.buffer;

      for(int i = 0; i < limit; ++i) {
         long cIndex;
         long seqOffset;
         long seq;
         long expectedSeq;
         do {
            cIndex = this.lvConsumerIndex();
            seqOffset = calcSequenceOffset(cIndex, mask);
            seq = this.lvSequence(sBuffer, seqOffset);
            expectedSeq = cIndex + 1L;
            if (seq < expectedSeq) {
               return i;
            }
         } while(seq > expectedSeq || !this.casConsumerIndex(cIndex, cIndex + 1L));

         long offset = calcElementOffset(cIndex, mask);
         Object e = UnsafeRefArrayAccess.lpElement(buffer, offset);
         UnsafeRefArrayAccess.soElement(buffer, offset, (Object)null);
         this.soSequence(sBuffer, seqOffset, cIndex + mask + 1L);
         c.accept(e);
      }

      return limit;
   }

   public int fill(MessagePassingQueue.Supplier s, int limit) {
      long[] sBuffer = this.sequenceBuffer;
      long mask = this.mask;
      Object[] buffer = this.buffer;

      for(int i = 0; i < limit; ++i) {
         long pIndex;
         long seqOffset;
         long seq;
         do {
            pIndex = this.lvProducerIndex();
            seqOffset = calcSequenceOffset(pIndex, mask);
            seq = this.lvSequence(sBuffer, seqOffset);
            if (seq < pIndex) {
               return i;
            }
         } while(seq > pIndex || !this.casProducerIndex(pIndex, pIndex + 1L));

         UnsafeRefArrayAccess.soElement(buffer, calcElementOffset(pIndex, mask), s.get());
         this.soSequence(sBuffer, seqOffset, pIndex + 1L);
      }

      return limit;
   }

   public void drain(MessagePassingQueue.Consumer c, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
      int idleCounter = 0;

      while(exit.keepRunning()) {
         if (this.drain(c, RECOMENDED_POLL_BATCH) == 0) {
            idleCounter = w.idle(idleCounter);
         } else {
            idleCounter = 0;
         }
      }

   }

   public void fill(MessagePassingQueue.Supplier s, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
      int idleCounter = 0;

      while(exit.keepRunning()) {
         if (this.fill(s, RECOMENDED_OFFER_BATCH) == 0) {
            idleCounter = w.idle(idleCounter);
         } else {
            idleCounter = 0;
         }
      }

   }

   static {
      RECOMENDED_POLL_BATCH = JvmInfo.CPUs * 4;
      RECOMENDED_OFFER_BATCH = JvmInfo.CPUs * 4;
   }
}
