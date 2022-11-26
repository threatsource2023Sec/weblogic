package org.python.netty.channel.socket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.channel.ChannelConfig;
import org.python.netty.channel.MessageSizeEstimator;
import org.python.netty.channel.RecvByteBufAllocator;
import org.python.netty.channel.WriteBufferWaterMark;

public interface DatagramChannelConfig extends ChannelConfig {
   int getSendBufferSize();

   DatagramChannelConfig setSendBufferSize(int var1);

   int getReceiveBufferSize();

   DatagramChannelConfig setReceiveBufferSize(int var1);

   int getTrafficClass();

   DatagramChannelConfig setTrafficClass(int var1);

   boolean isReuseAddress();

   DatagramChannelConfig setReuseAddress(boolean var1);

   boolean isBroadcast();

   DatagramChannelConfig setBroadcast(boolean var1);

   boolean isLoopbackModeDisabled();

   DatagramChannelConfig setLoopbackModeDisabled(boolean var1);

   int getTimeToLive();

   DatagramChannelConfig setTimeToLive(int var1);

   InetAddress getInterface();

   DatagramChannelConfig setInterface(InetAddress var1);

   NetworkInterface getNetworkInterface();

   DatagramChannelConfig setNetworkInterface(NetworkInterface var1);

   /** @deprecated */
   @Deprecated
   DatagramChannelConfig setMaxMessagesPerRead(int var1);

   DatagramChannelConfig setWriteSpinCount(int var1);

   DatagramChannelConfig setConnectTimeoutMillis(int var1);

   DatagramChannelConfig setAllocator(ByteBufAllocator var1);

   DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator var1);

   DatagramChannelConfig setAutoRead(boolean var1);

   DatagramChannelConfig setAutoClose(boolean var1);

   DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator var1);

   DatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark var1);
}
