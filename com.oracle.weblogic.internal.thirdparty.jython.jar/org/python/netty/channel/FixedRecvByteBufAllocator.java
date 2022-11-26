package org.python.netty.channel;

public class FixedRecvByteBufAllocator extends DefaultMaxMessagesRecvByteBufAllocator {
   private final int bufferSize;

   public FixedRecvByteBufAllocator(int bufferSize) {
      if (bufferSize <= 0) {
         throw new IllegalArgumentException("bufferSize must greater than 0: " + bufferSize);
      } else {
         this.bufferSize = bufferSize;
      }
   }

   public RecvByteBufAllocator.Handle newHandle() {
      return new HandleImpl(this.bufferSize);
   }

   private final class HandleImpl extends DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle {
      private final int bufferSize;

      public HandleImpl(int bufferSize) {
         super();
         this.bufferSize = bufferSize;
      }

      public int guess() {
         return this.bufferSize;
      }
   }
}
