package org.python.netty.channel.local;

import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.buffer.CompositeByteBuf;

final class PreferHeapByteBufAllocator implements ByteBufAllocator {
   private final ByteBufAllocator allocator;

   PreferHeapByteBufAllocator(ByteBufAllocator allocator) {
      this.allocator = allocator;
   }

   public ByteBuf buffer() {
      return this.allocator.heapBuffer();
   }

   public ByteBuf buffer(int initialCapacity) {
      return this.allocator.heapBuffer(initialCapacity);
   }

   public ByteBuf buffer(int initialCapacity, int maxCapacity) {
      return this.allocator.heapBuffer(initialCapacity, maxCapacity);
   }

   public ByteBuf ioBuffer() {
      return this.allocator.heapBuffer();
   }

   public ByteBuf ioBuffer(int initialCapacity) {
      return this.allocator.heapBuffer(initialCapacity);
   }

   public ByteBuf ioBuffer(int initialCapacity, int maxCapacity) {
      return this.allocator.heapBuffer(initialCapacity, maxCapacity);
   }

   public ByteBuf heapBuffer() {
      return this.allocator.heapBuffer();
   }

   public ByteBuf heapBuffer(int initialCapacity) {
      return this.allocator.heapBuffer(initialCapacity);
   }

   public ByteBuf heapBuffer(int initialCapacity, int maxCapacity) {
      return this.allocator.heapBuffer(initialCapacity, maxCapacity);
   }

   public ByteBuf directBuffer() {
      return this.allocator.directBuffer();
   }

   public ByteBuf directBuffer(int initialCapacity) {
      return this.allocator.directBuffer(initialCapacity);
   }

   public ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
      return this.allocator.directBuffer(initialCapacity, maxCapacity);
   }

   public CompositeByteBuf compositeBuffer() {
      return this.allocator.compositeHeapBuffer();
   }

   public CompositeByteBuf compositeBuffer(int maxNumComponents) {
      return this.allocator.compositeHeapBuffer(maxNumComponents);
   }

   public CompositeByteBuf compositeHeapBuffer() {
      return this.allocator.compositeHeapBuffer();
   }

   public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
      return this.allocator.compositeHeapBuffer(maxNumComponents);
   }

   public CompositeByteBuf compositeDirectBuffer() {
      return this.allocator.compositeDirectBuffer();
   }

   public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
      return this.allocator.compositeDirectBuffer(maxNumComponents);
   }

   public boolean isDirectBufferPooled() {
      return this.allocator.isDirectBufferPooled();
   }

   public int calculateNewCapacity(int minNewCapacity, int maxCapacity) {
      return this.allocator.calculateNewCapacity(minNewCapacity, maxCapacity);
   }
}
