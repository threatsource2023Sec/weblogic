package org.python.netty.buffer;

import java.nio.ByteBuffer;
import org.python.netty.util.internal.PlatformDependent;

final class WrappedUnpooledUnsafeDirectByteBuf extends UnpooledUnsafeDirectByteBuf {
   WrappedUnpooledUnsafeDirectByteBuf(ByteBufAllocator alloc, long memoryAddress, int size, boolean doFree) {
      super(alloc, PlatformDependent.directBuffer(memoryAddress, size), size, doFree);
   }

   protected void freeDirect(ByteBuffer buffer) {
      PlatformDependent.freeMemory(this.memoryAddress);
   }
}
