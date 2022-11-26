package org.python.netty.util.internal.shaded.org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.python.netty.util.internal.shaded.org.jctools.queues.QueueProgressIndicators;

public final class MpscAtomicArrayQueue extends AtomicReferenceArrayQueue implements QueueProgressIndicators {
   private final AtomicLong consumerIndex = new AtomicLong();
   private final AtomicLong producerIndex = new AtomicLong();
   private volatile long headCache;

   public MpscAtomicArrayQueue(int capacity) {
      super(capacity);
   }

   public boolean offer(Object e) {
      if (null == e) {
         throw new NullPointerException();
      } else {
         int mask = this.mask;
         long capacity = (long)(mask + 1);
         long consumerIndexCache = this.lvConsumerIndexCache();

         long currentProducerIndex;
         do {
            currentProducerIndex = this.lvProducerIndex();
            long wrapPoint = currentProducerIndex - capacity;
            if (consumerIndexCache <= wrapPoint) {
               long currHead = this.lvConsumerIndex();
               if (currHead <= wrapPoint) {
                  return false;
               }

               this.svConsumerIndexCache(currHead);
               consumerIndexCache = currHead;
            }
         } while(!this.casProducerIndex(currentProducerIndex, currentProducerIndex + 1L));

         int offset = this.calcElementOffset(currentProducerIndex, mask);
         this.soElement(offset, e);
         return true;
      }
   }

   public final int weakOffer(Object e) {
      if (null == e) {
         throw new NullPointerException("Null is not a valid element");
      } else {
         int mask = this.mask;
         long capacity = (long)(mask + 1);
         long currentTail = this.lvProducerIndex();
         long consumerIndexCache = this.lvConsumerIndexCache();
         long wrapPoint = currentTail - capacity;
         if (consumerIndexCache <= wrapPoint) {
            long currHead = this.lvConsumerIndex();
            if (currHead <= wrapPoint) {
               return 1;
            }

            this.svConsumerIndexCache(currHead);
         }

         if (!this.casProducerIndex(currentTail, currentTail + 1L)) {
            return -1;
         } else {
            int offset = this.calcElementOffset(currentTail, mask);
            this.soElement(offset, e);
            return 0;
         }
      }
   }

   public Object poll() {
      long consumerIndex = this.lvConsumerIndex();
      int offset = this.calcElementOffset(consumerIndex);
      AtomicReferenceArray buffer = this.buffer;
      Object e = this.lvElement(buffer, offset);
      if (null == e) {
         if (consumerIndex == this.lvProducerIndex()) {
            return null;
         }

         do {
            e = this.lvElement(buffer, offset);
         } while(e == null);
      }

      this.spElement(buffer, offset, (Object)null);
      this.soConsumerIndex(consumerIndex + 1L);
      return e;
   }

   public Object peek() {
      AtomicReferenceArray buffer = this.buffer;
      long consumerIndex = this.lvConsumerIndex();
      int offset = this.calcElementOffset(consumerIndex);
      Object e = this.lvElement(buffer, offset);
      if (null == e) {
         if (consumerIndex == this.lvProducerIndex()) {
            return null;
         }

         do {
            e = this.lvElement(buffer, offset);
         } while(e == null);
      }

      return e;
   }

   public int size() {
      long after = this.lvConsumerIndex();

      long before;
      long currentProducerIndex;
      do {
         before = after;
         currentProducerIndex = this.lvProducerIndex();
         after = this.lvConsumerIndex();
      } while(before != after);

      return (int)(currentProducerIndex - after);
   }

   public boolean isEmpty() {
      return this.lvConsumerIndex() == this.lvProducerIndex();
   }

   public long currentProducerIndex() {
      return this.lvProducerIndex();
   }

   public long currentConsumerIndex() {
      return this.lvConsumerIndex();
   }

   private long lvConsumerIndex() {
      return this.consumerIndex.get();
   }

   private long lvProducerIndex() {
      return this.producerIndex.get();
   }

   protected final long lvConsumerIndexCache() {
      return this.headCache;
   }

   protected final void svConsumerIndexCache(long v) {
      this.headCache = v;
   }

   protected final boolean casProducerIndex(long expect, long newValue) {
      return this.producerIndex.compareAndSet(expect, newValue);
   }

   protected void soConsumerIndex(long l) {
      this.consumerIndex.lazySet(l);
   }
}
