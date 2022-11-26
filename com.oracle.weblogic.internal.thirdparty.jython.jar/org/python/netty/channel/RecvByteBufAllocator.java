package org.python.netty.channel;

import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.util.UncheckedBooleanSupplier;
import org.python.netty.util.internal.ObjectUtil;

public interface RecvByteBufAllocator {
   Handle newHandle();

   public static class DelegatingHandle implements Handle {
      private final Handle delegate;

      public DelegatingHandle(Handle delegate) {
         this.delegate = (Handle)ObjectUtil.checkNotNull(delegate, "delegate");
      }

      protected final Handle delegate() {
         return this.delegate;
      }

      public ByteBuf allocate(ByteBufAllocator alloc) {
         return this.delegate.allocate(alloc);
      }

      public int guess() {
         return this.delegate.guess();
      }

      public void reset(ChannelConfig config) {
         this.delegate.reset(config);
      }

      public void incMessagesRead(int numMessages) {
         this.delegate.incMessagesRead(numMessages);
      }

      public void lastBytesRead(int bytes) {
         this.delegate.lastBytesRead(bytes);
      }

      public int lastBytesRead() {
         return this.delegate.lastBytesRead();
      }

      public boolean continueReading() {
         return this.delegate.continueReading();
      }

      public int attemptedBytesRead() {
         return this.delegate.attemptedBytesRead();
      }

      public void attemptedBytesRead(int bytes) {
         this.delegate.attemptedBytesRead(bytes);
      }

      public void readComplete() {
         this.delegate.readComplete();
      }
   }

   public interface ExtendedHandle extends Handle {
      boolean continueReading(UncheckedBooleanSupplier var1);
   }

   /** @deprecated */
   @Deprecated
   public interface Handle {
      ByteBuf allocate(ByteBufAllocator var1);

      int guess();

      void reset(ChannelConfig var1);

      void incMessagesRead(int var1);

      void lastBytesRead(int var1);

      int lastBytesRead();

      void attemptedBytesRead(int var1);

      int attemptedBytesRead();

      boolean continueReading();

      void readComplete();
   }
}
