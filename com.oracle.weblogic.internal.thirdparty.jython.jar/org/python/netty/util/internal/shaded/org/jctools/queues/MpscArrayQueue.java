package org.python.netty.util.internal.shaded.org.jctools.queues;

import org.python.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;

public class MpscArrayQueue extends MpscArrayQueueConsumerField implements QueueProgressIndicators {
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

   public MpscArrayQueue(int capacity) {
      super(capacity);
   }

   public boolean offerIfBelowThreshold(Object e, int threshold) {
      if (null == e) {
         throw new NullPointerException();
      } else {
         long mask = this.mask;
         long capacity = mask + 1L;
         long producerLimit = this.lvProducerLimit();

         long pIndex;
         long offset;
         do {
            pIndex = this.lvProducerIndex();
            offset = producerLimit - pIndex;
            long size = capacity - offset;
            if (size >= (long)threshold) {
               long cIndex = this.lvConsumerIndex();
               size = pIndex - cIndex;
               if (size >= (long)threshold) {
                  return false;
               }

               producerLimit = cIndex + capacity;
               this.soProducerLimit(producerLimit);
            }
         } while(!this.casProducerIndex(pIndex, pIndex + 1L));

         offset = calcElementOffset(pIndex, mask);
         UnsafeRefArrayAccess.soElement(this.buffer, offset, e);
         return true;
      }
   }

   public boolean offer(Object e) {
      if (null == e) {
         throw new NullPointerException();
      } else {
         long mask = this.mask;
         long producerLimit = this.lvProducerLimit();

         long pIndex;
         long offset;
         do {
            pIndex = this.lvProducerIndex();
            if (pIndex >= producerLimit) {
               offset = this.lvConsumerIndex();
               producerLimit = offset + mask + 1L;
               if (pIndex >= producerLimit) {
                  return false;
               }

               this.soProducerLimit(producerLimit);
            }
         } while(!this.casProducerIndex(pIndex, pIndex + 1L));

         offset = calcElementOffset(pIndex, mask);
         UnsafeRefArrayAccess.soElement(this.buffer, offset, e);
         return true;
      }
   }

   public final int failFastOffer(Object e) {
      if (null == e) {
         throw new NullPointerException();
      } else {
         long mask = this.mask;
         long capacity = mask + 1L;
         long pIndex = this.lvProducerIndex();
         long producerLimit = this.lvProducerLimit();
         long offset;
         if (pIndex >= producerLimit) {
            offset = this.lvConsumerIndex();
            producerLimit = offset + capacity;
            if (pIndex >= producerLimit) {
               return 1;
            }

            this.soProducerLimit(producerLimit);
         }

         if (!this.casProducerIndex(pIndex, pIndex + 1L)) {
            return -1;
         } else {
            offset = calcElementOffset(pIndex, mask);
            UnsafeRefArrayAccess.soElement(this.buffer, offset, e);
            return 0;
         }
      }
   }

   public Object poll() {
      long cIndex = this.lpConsumerIndex();
      long offset = this.calcElementOffset(cIndex);
      Object[] buffer = this.buffer;
      Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
      if (null == e) {
         if (cIndex == this.lvProducerIndex()) {
            return null;
         }

         do {
            e = UnsafeRefArrayAccess.lvElement(buffer, offset);
         } while(e == null);
      }

      UnsafeRefArrayAccess.spElement(buffer, offset, (Object)null);
      this.soConsumerIndex(cIndex + 1L);
      return e;
   }

   public Object peek() {
      Object[] buffer = this.buffer;
      long cIndex = this.lpConsumerIndex();
      long offset = this.calcElementOffset(cIndex);
      Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
      if (null == e) {
         if (cIndex == this.lvProducerIndex()) {
            return null;
         }

         do {
            e = UnsafeRefArrayAccess.lvElement(buffer, offset);
         } while(e == null);
      }

      return e;
   }

   public boolean relaxedOffer(Object e) {
      return this.offer(e);
   }

   public Object relaxedPoll() {
      Object[] buffer = this.buffer;
      long cIndex = this.lpConsumerIndex();
      long offset = this.calcElementOffset(cIndex);
      Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
      if (null == e) {
         return null;
      } else {
         UnsafeRefArrayAccess.spElement(buffer, offset, (Object)null);
         this.soConsumerIndex(cIndex + 1L);
         return e;
      }
   }

   public Object relaxedPeek() {
      Object[] buffer = this.buffer;
      long mask = this.mask;
      long cIndex = this.lpConsumerIndex();
      return UnsafeRefArrayAccess.lvElement(buffer, calcElementOffset(cIndex, mask));
   }

   public int drain(MessagePassingQueue.Consumer c) {
      return this.drain(c, this.capacity());
   }

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

   public int drain(MessagePassingQueue.Consumer c, int limit) {
      Object[] buffer = this.buffer;
      long mask = this.mask;
      long cIndex = this.lpConsumerIndex();

      for(int i = 0; i < limit; ++i) {
         long index = cIndex + (long)i;
         long offset = calcElementOffset(index, mask);
         Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
         if (null == e) {
            return i;
         }

         UnsafeRefArrayAccess.spElement(buffer, offset, (Object)null);
         this.soConsumerIndex(index + 1L);
         c.accept(e);
      }

      return limit;
   }

   public int fill(MessagePassingQueue.Supplier s, int limit) {
      long mask = this.mask;
      long capacity = mask + 1L;
      long producerLimit = this.lvProducerLimit();
      int actualLimit = false;

      long pIndex;
      long offset;
      int actualLimit;
      do {
         pIndex = this.lvProducerIndex();
         long available = producerLimit - pIndex;
         if (available <= 0L) {
            offset = this.lvConsumerIndex();
            producerLimit = offset + capacity;
            available = producerLimit - pIndex;
            if (available <= 0L) {
               return 0;
            }

            this.soProducerLimit(producerLimit);
         }

         actualLimit = Math.min((int)available, limit);
      } while(!this.casProducerIndex(pIndex, pIndex + (long)actualLimit));

      Object[] buffer = this.buffer;

      for(int i = 0; i < actualLimit; ++i) {
         offset = calcElementOffset(pIndex + (long)i, mask);
         UnsafeRefArrayAccess.soElement(buffer, offset, s.get());
      }

      return actualLimit;
   }

   public void drain(MessagePassingQueue.Consumer c, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
      Object[] buffer = this.buffer;
      long mask = this.mask;
      long cIndex = this.lpConsumerIndex();
      int counter = 0;

      while(exit.keepRunning()) {
         for(int i = 0; i < 4096; ++i) {
            long offset = calcElementOffset(cIndex, mask);
            Object e = UnsafeRefArrayAccess.lvElement(buffer, offset);
            if (null == e) {
               counter = w.idle(counter);
            } else {
               ++cIndex;
               counter = 0;
               UnsafeRefArrayAccess.spElement(buffer, offset, (Object)null);
               this.soConsumerIndex(cIndex);
               c.accept(e);
            }
         }
      }

   }

   public void fill(MessagePassingQueue.Supplier s, MessagePassingQueue.WaitStrategy w, MessagePassingQueue.ExitCondition exit) {
      int idleCounter = 0;

      while(exit.keepRunning()) {
         if (this.fill(s, MpmcArrayQueue.RECOMENDED_OFFER_BATCH) == 0) {
            idleCounter = w.idle(idleCounter);
         } else {
            idleCounter = 0;
         }
      }

   }
}
