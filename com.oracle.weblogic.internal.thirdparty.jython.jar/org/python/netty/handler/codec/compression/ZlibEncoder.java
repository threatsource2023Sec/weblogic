package org.python.netty.handler.codec.compression;

import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.handler.codec.MessageToByteEncoder;

public abstract class ZlibEncoder extends MessageToByteEncoder {
   protected ZlibEncoder() {
      super(false);
   }

   public abstract boolean isClosed();

   public abstract ChannelFuture close();

   public abstract ChannelFuture close(ChannelPromise var1);
}
