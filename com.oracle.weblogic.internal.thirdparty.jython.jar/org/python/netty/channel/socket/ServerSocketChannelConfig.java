package org.python.netty.channel.socket;

import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.channel.ChannelConfig;
import org.python.netty.channel.MessageSizeEstimator;
import org.python.netty.channel.RecvByteBufAllocator;
import org.python.netty.channel.WriteBufferWaterMark;

public interface ServerSocketChannelConfig extends ChannelConfig {
   int getBacklog();

   ServerSocketChannelConfig setBacklog(int var1);

   boolean isReuseAddress();

   ServerSocketChannelConfig setReuseAddress(boolean var1);

   int getReceiveBufferSize();

   ServerSocketChannelConfig setReceiveBufferSize(int var1);

   ServerSocketChannelConfig setPerformancePreferences(int var1, int var2, int var3);

   ServerSocketChannelConfig setConnectTimeoutMillis(int var1);

   /** @deprecated */
   @Deprecated
   ServerSocketChannelConfig setMaxMessagesPerRead(int var1);

   ServerSocketChannelConfig setWriteSpinCount(int var1);

   ServerSocketChannelConfig setAllocator(ByteBufAllocator var1);

   ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator var1);

   ServerSocketChannelConfig setAutoRead(boolean var1);

   ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator var1);

   ServerSocketChannelConfig setWriteBufferHighWaterMark(int var1);

   ServerSocketChannelConfig setWriteBufferLowWaterMark(int var1);

   ServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark var1);
}
