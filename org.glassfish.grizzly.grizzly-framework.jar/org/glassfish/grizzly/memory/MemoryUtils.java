package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;

public class MemoryUtils {
   public static ByteBuffer allocateByteBuffer(MemoryManager memoryManager, int size) {
      return memoryManager instanceof ByteBufferAware ? ((ByteBufferAware)memoryManager).allocateByteBuffer(size) : ByteBuffer.allocate(size);
   }

   public static ByteBuffer reallocateByteBuffer(MemoryManager memoryManager, ByteBuffer oldByteBuffer, int size) {
      return memoryManager instanceof ByteBufferAware ? ((ByteBufferAware)memoryManager).reallocateByteBuffer(oldByteBuffer, size) : ByteBuffer.allocate(size);
   }

   public static void releaseByteBuffer(MemoryManager memoryManager, ByteBuffer byteBuffer) {
      if (memoryManager instanceof ByteBufferAware) {
         ((ByteBufferAware)memoryManager).releaseByteBuffer(byteBuffer);
      }

   }
}
