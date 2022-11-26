package org.python.netty.channel;

public interface MaxMessagesRecvByteBufAllocator extends RecvByteBufAllocator {
   int maxMessagesPerRead();

   MaxMessagesRecvByteBufAllocator maxMessagesPerRead(int var1);
}
