package weblogic.websocket.tyrus.monitoring;

import java.util.concurrent.atomic.LongAdder;

class ConcurrentMessageStatistics implements MessageStatisticsSource {
   private final LongAdder messagesCount = new LongAdder();
   private final LongAdder messagesSize = new LongAdder();
   private final Object minimalMessageSizeLock = new Object();
   private final Object maximalMessageSizeLock = new Object();
   private volatile long minimalMessageSize = Long.MAX_VALUE;
   private volatile long maximalMessageSize = 0L;

   void onMessage(long size) {
      this.messagesCount.increment();
      this.messagesSize.add(size);
      if (this.minimalMessageSize > size) {
         synchronized(this.minimalMessageSizeLock) {
            if (this.minimalMessageSize > size) {
               this.minimalMessageSize = size;
            }
         }
      }

      if (this.maximalMessageSize < size) {
         synchronized(this.maximalMessageSizeLock) {
            if (this.maximalMessageSize < size) {
               this.maximalMessageSize = size;
            }
         }
      }

   }

   public long getMessagesCount() {
      return this.messagesCount.longValue();
   }

   public long getMessagesSize() {
      return this.messagesSize.longValue();
   }

   public long getMinMessageSize() {
      return this.minimalMessageSize == Long.MAX_VALUE ? 0L : this.minimalMessageSize;
   }

   public long getMaxMessageSize() {
      return this.maximalMessageSize;
   }
}
