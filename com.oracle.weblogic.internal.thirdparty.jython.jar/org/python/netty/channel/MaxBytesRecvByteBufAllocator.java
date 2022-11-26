package org.python.netty.channel;

import java.util.Map;

public interface MaxBytesRecvByteBufAllocator extends RecvByteBufAllocator {
   int maxBytesPerRead();

   MaxBytesRecvByteBufAllocator maxBytesPerRead(int var1);

   int maxBytesPerIndividualRead();

   MaxBytesRecvByteBufAllocator maxBytesPerIndividualRead(int var1);

   Map.Entry maxBytesPerReadPair();

   MaxBytesRecvByteBufAllocator maxBytesPerReadPair(int var1, int var2);
}
