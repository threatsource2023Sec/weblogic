package org.python.netty.buffer;

import java.nio.ByteBuffer;
import org.python.netty.util.internal.PlatformDependent;

class UnpooledUnsafeNoCleanerDirectByteBuf extends UnpooledUnsafeDirectByteBuf {
   UnpooledUnsafeNoCleanerDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
      super(alloc, initialCapacity, maxCapacity);
   }

   protected ByteBuffer allocateDirect(int initialCapacity) {
      return PlatformDependent.allocateDirectNoCleaner(initialCapacity);
   }

   ByteBuffer reallocateDirect(ByteBuffer oldBuffer, int initialCapacity) {
      return PlatformDependent.reallocateDirectNoCleaner(oldBuffer, initialCapacity);
   }

   protected void freeDirect(ByteBuffer buffer) {
      PlatformDependent.freeDirectNoCleaner(buffer);
   }

   public ByteBuf capacity(int newCapacity) {
      this.checkNewCapacity(newCapacity);
      int readerIndex = this.readerIndex();
      int writerIndex = this.writerIndex();
      int oldCapacity = this.capacity();
      ByteBuffer oldBuffer;
      ByteBuffer newBuffer;
      if (newCapacity > oldCapacity) {
         oldBuffer = this.buffer;
         newBuffer = this.reallocateDirect(oldBuffer, newCapacity);
         this.setByteBuffer(newBuffer, false);
      } else if (newCapacity < oldCapacity) {
         oldBuffer = this.buffer;
         newBuffer = this.allocateDirect(newCapacity);
         if (readerIndex < newCapacity) {
            if (writerIndex > newCapacity) {
               writerIndex = newCapacity;
               this.writerIndex(newCapacity);
            }

            oldBuffer.position(readerIndex).limit(writerIndex);
            newBuffer.position(readerIndex).limit(writerIndex);
            newBuffer.put(oldBuffer);
            newBuffer.clear();
         } else {
            this.setIndex(newCapacity, newCapacity);
         }

         this.setByteBuffer(newBuffer, true);
      }

      return this;
   }
}
