package org.python.netty.channel;

import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.util.UncheckedBooleanSupplier;

public abstract class DefaultMaxMessagesRecvByteBufAllocator implements MaxMessagesRecvByteBufAllocator {
   private volatile int maxMessagesPerRead;

   public DefaultMaxMessagesRecvByteBufAllocator() {
      this(1);
   }

   public DefaultMaxMessagesRecvByteBufAllocator(int maxMessagesPerRead) {
      this.maxMessagesPerRead(maxMessagesPerRead);
   }

   public int maxMessagesPerRead() {
      return this.maxMessagesPerRead;
   }

   public MaxMessagesRecvByteBufAllocator maxMessagesPerRead(int maxMessagesPerRead) {
      if (maxMessagesPerRead <= 0) {
         throw new IllegalArgumentException("maxMessagesPerRead: " + maxMessagesPerRead + " (expected: > 0)");
      } else {
         this.maxMessagesPerRead = maxMessagesPerRead;
         return this;
      }
   }

   public abstract class MaxMessageHandle implements RecvByteBufAllocator.ExtendedHandle {
      private ChannelConfig config;
      private int maxMessagePerRead;
      private int totalMessages;
      private int totalBytesRead;
      private int attemptedBytesRead;
      private int lastBytesRead;
      private final UncheckedBooleanSupplier defaultMaybeMoreSupplier = new UncheckedBooleanSupplier() {
         public boolean get() {
            return MaxMessageHandle.this.attemptedBytesRead == MaxMessageHandle.this.lastBytesRead;
         }
      };

      public void reset(ChannelConfig config) {
         this.config = config;
         this.maxMessagePerRead = DefaultMaxMessagesRecvByteBufAllocator.this.maxMessagesPerRead();
         this.totalMessages = this.totalBytesRead = 0;
      }

      public ByteBuf allocate(ByteBufAllocator alloc) {
         return alloc.ioBuffer(this.guess());
      }

      public final void incMessagesRead(int amt) {
         this.totalMessages += amt;
      }

      public final void lastBytesRead(int bytes) {
         this.lastBytesRead = bytes;
         if (bytes > 0) {
            this.totalBytesRead += bytes;
         }

      }

      public final int lastBytesRead() {
         return this.lastBytesRead;
      }

      public boolean continueReading() {
         return this.continueReading(this.defaultMaybeMoreSupplier);
      }

      public boolean continueReading(UncheckedBooleanSupplier maybeMoreDataSupplier) {
         return this.config.isAutoRead() && maybeMoreDataSupplier.get() && this.totalMessages < this.maxMessagePerRead && this.totalBytesRead > 0;
      }

      public void readComplete() {
      }

      public int attemptedBytesRead() {
         return this.attemptedBytesRead;
      }

      public void attemptedBytesRead(int bytes) {
         this.attemptedBytesRead = bytes;
      }

      protected final int totalBytesRead() {
         return this.totalBytesRead < 0 ? Integer.MAX_VALUE : this.totalBytesRead;
      }
   }
}
