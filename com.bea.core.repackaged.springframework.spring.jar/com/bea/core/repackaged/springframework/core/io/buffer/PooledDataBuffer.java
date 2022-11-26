package com.bea.core.repackaged.springframework.core.io.buffer;

public interface PooledDataBuffer extends DataBuffer {
   boolean isAllocated();

   PooledDataBuffer retain();

   boolean release();
}
