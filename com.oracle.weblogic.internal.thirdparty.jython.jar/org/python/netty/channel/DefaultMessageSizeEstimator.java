package org.python.netty.channel;

import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufHolder;

public final class DefaultMessageSizeEstimator implements MessageSizeEstimator {
   public static final MessageSizeEstimator DEFAULT = new DefaultMessageSizeEstimator(8);
   private final MessageSizeEstimator.Handle handle;

   public DefaultMessageSizeEstimator(int unknownSize) {
      if (unknownSize < 0) {
         throw new IllegalArgumentException("unknownSize: " + unknownSize + " (expected: >= 0)");
      } else {
         this.handle = new HandleImpl(unknownSize);
      }
   }

   public MessageSizeEstimator.Handle newHandle() {
      return this.handle;
   }

   private static final class HandleImpl implements MessageSizeEstimator.Handle {
      private final int unknownSize;

      private HandleImpl(int unknownSize) {
         this.unknownSize = unknownSize;
      }

      public int size(Object msg) {
         if (msg instanceof ByteBuf) {
            return ((ByteBuf)msg).readableBytes();
         } else if (msg instanceof ByteBufHolder) {
            return ((ByteBufHolder)msg).content().readableBytes();
         } else {
            return msg instanceof FileRegion ? 0 : this.unknownSize;
         }
      }

      // $FF: synthetic method
      HandleImpl(int x0, Object x1) {
         this(x0);
      }
   }
}
