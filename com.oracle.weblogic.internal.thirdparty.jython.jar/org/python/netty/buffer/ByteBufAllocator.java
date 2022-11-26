package org.python.netty.buffer;

public interface ByteBufAllocator {
   ByteBufAllocator DEFAULT = ByteBufUtil.DEFAULT_ALLOCATOR;

   ByteBuf buffer();

   ByteBuf buffer(int var1);

   ByteBuf buffer(int var1, int var2);

   ByteBuf ioBuffer();

   ByteBuf ioBuffer(int var1);

   ByteBuf ioBuffer(int var1, int var2);

   ByteBuf heapBuffer();

   ByteBuf heapBuffer(int var1);

   ByteBuf heapBuffer(int var1, int var2);

   ByteBuf directBuffer();

   ByteBuf directBuffer(int var1);

   ByteBuf directBuffer(int var1, int var2);

   CompositeByteBuf compositeBuffer();

   CompositeByteBuf compositeBuffer(int var1);

   CompositeByteBuf compositeHeapBuffer();

   CompositeByteBuf compositeHeapBuffer(int var1);

   CompositeByteBuf compositeDirectBuffer();

   CompositeByteBuf compositeDirectBuffer(int var1);

   boolean isDirectBufferPooled();

   int calculateNewCapacity(int var1, int var2);
}
