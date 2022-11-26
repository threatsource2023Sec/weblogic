package org.python.netty.channel.socket;

import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.channel.ChannelConfig;
import org.python.netty.channel.MessageSizeEstimator;
import org.python.netty.channel.RecvByteBufAllocator;
import org.python.netty.channel.WriteBufferWaterMark;

public interface SocketChannelConfig extends ChannelConfig {
   boolean isTcpNoDelay();

   SocketChannelConfig setTcpNoDelay(boolean var1);

   int getSoLinger();

   SocketChannelConfig setSoLinger(int var1);

   int getSendBufferSize();

   SocketChannelConfig setSendBufferSize(int var1);

   int getReceiveBufferSize();

   SocketChannelConfig setReceiveBufferSize(int var1);

   boolean isKeepAlive();

   SocketChannelConfig setKeepAlive(boolean var1);

   int getTrafficClass();

   SocketChannelConfig setTrafficClass(int var1);

   boolean isReuseAddress();

   SocketChannelConfig setReuseAddress(boolean var1);

   SocketChannelConfig setPerformancePreferences(int var1, int var2, int var3);

   boolean isAllowHalfClosure();

   SocketChannelConfig setAllowHalfClosure(boolean var1);

   SocketChannelConfig setConnectTimeoutMillis(int var1);

   /** @deprecated */
   @Deprecated
   SocketChannelConfig setMaxMessagesPerRead(int var1);

   SocketChannelConfig setWriteSpinCount(int var1);

   SocketChannelConfig setAllocator(ByteBufAllocator var1);

   SocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator var1);

   SocketChannelConfig setAutoRead(boolean var1);

   SocketChannelConfig setAutoClose(boolean var1);

   SocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator var1);

   SocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark var1);
}
