package org.python.netty.channel.socket.oio;

import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.channel.MessageSizeEstimator;
import org.python.netty.channel.RecvByteBufAllocator;
import org.python.netty.channel.WriteBufferWaterMark;
import org.python.netty.channel.socket.SocketChannelConfig;

public interface OioSocketChannelConfig extends SocketChannelConfig {
   OioSocketChannelConfig setSoTimeout(int var1);

   int getSoTimeout();

   OioSocketChannelConfig setTcpNoDelay(boolean var1);

   OioSocketChannelConfig setSoLinger(int var1);

   OioSocketChannelConfig setSendBufferSize(int var1);

   OioSocketChannelConfig setReceiveBufferSize(int var1);

   OioSocketChannelConfig setKeepAlive(boolean var1);

   OioSocketChannelConfig setTrafficClass(int var1);

   OioSocketChannelConfig setReuseAddress(boolean var1);

   OioSocketChannelConfig setPerformancePreferences(int var1, int var2, int var3);

   OioSocketChannelConfig setAllowHalfClosure(boolean var1);

   OioSocketChannelConfig setConnectTimeoutMillis(int var1);

   /** @deprecated */
   @Deprecated
   OioSocketChannelConfig setMaxMessagesPerRead(int var1);

   OioSocketChannelConfig setWriteSpinCount(int var1);

   OioSocketChannelConfig setAllocator(ByteBufAllocator var1);

   OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator var1);

   OioSocketChannelConfig setAutoRead(boolean var1);

   OioSocketChannelConfig setAutoClose(boolean var1);

   OioSocketChannelConfig setWriteBufferHighWaterMark(int var1);

   OioSocketChannelConfig setWriteBufferLowWaterMark(int var1);

   OioSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark var1);

   OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator var1);
}
