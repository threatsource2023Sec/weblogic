package org.python.netty.buffer;

public interface ByteBufAllocatorMetric {
   long usedHeapMemory();

   long usedDirectMemory();
}
