package org.python.netty.util.internal.shaded.org.jctools.queues;

final class IndexedQueueSizeUtil {
   private IndexedQueueSizeUtil() {
   }

   static int size(IndexedQueue iq) {
      long after = iq.lvConsumerIndex();

      long before;
      long currentProducerIndex;
      do {
         before = after;
         currentProducerIndex = iq.lvProducerIndex();
         after = iq.lvConsumerIndex();
      } while(before != after);

      long size = currentProducerIndex - after;
      return size > 2147483647L ? Integer.MAX_VALUE : (int)size;
   }

   static boolean isEmpty(IndexedQueue iq) {
      return iq.lvConsumerIndex() == iq.lvProducerIndex();
   }

   protected interface IndexedQueue {
      long lvConsumerIndex();

      long lvProducerIndex();
   }
}
