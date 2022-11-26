package org.python.netty.buffer;

import java.nio.ByteBuffer;
import org.python.netty.util.internal.PlatformDependent;

final class ReadOnlyUnsafeDirectByteBuf extends ReadOnlyByteBufferBuf {
   private final long memoryAddress;

   ReadOnlyUnsafeDirectByteBuf(ByteBufAllocator allocator, ByteBuffer buffer) {
      super(allocator, buffer);
      this.memoryAddress = PlatformDependent.directBufferAddress(buffer);
   }

   protected byte _getByte(int index) {
      return UnsafeByteBufUtil.getByte(this.addr(index));
   }

   protected short _getShort(int index) {
      return UnsafeByteBufUtil.getShort(this.addr(index));
   }

   protected int _getUnsignedMedium(int index) {
      return UnsafeByteBufUtil.getUnsignedMedium(this.addr(index));
   }

   protected int _getInt(int index) {
      return UnsafeByteBufUtil.getInt(this.addr(index));
   }

   protected long _getLong(int index) {
      return UnsafeByteBufUtil.getLong(this.addr(index));
   }

   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
      this.checkIndex(index, length);
      if (dst == null) {
         throw new NullPointerException("dst");
      } else if (dstIndex >= 0 && dstIndex <= dst.capacity() - length) {
         if (dst.hasMemoryAddress()) {
            PlatformDependent.copyMemory(this.addr(index), dst.memoryAddress() + (long)dstIndex, (long)length);
         } else if (dst.hasArray()) {
            PlatformDependent.copyMemory(this.addr(index), dst.array(), dst.arrayOffset() + dstIndex, (long)length);
         } else {
            dst.setBytes(dstIndex, (ByteBuf)this, index, length);
         }

         return this;
      } else {
         throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
      }
   }

   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
      this.checkIndex(index, length);
      if (dst == null) {
         throw new NullPointerException("dst");
      } else if (dstIndex >= 0 && dstIndex <= dst.length - length) {
         if (length != 0) {
            PlatformDependent.copyMemory(this.addr(index), dst, dstIndex, (long)length);
         }

         return this;
      } else {
         throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", dstIndex, length, dst.length));
      }
   }

   public ByteBuf getBytes(int index, ByteBuffer dst) {
      this.checkIndex(index);
      if (dst == null) {
         throw new NullPointerException("dst");
      } else {
         int bytesToCopy = Math.min(this.capacity() - index, dst.remaining());
         ByteBuffer tmpBuf = this.internalNioBuffer();
         tmpBuf.clear().position(index).limit(index + bytesToCopy);
         dst.put(tmpBuf);
         return this;
      }
   }

   public ByteBuf copy(int index, int length) {
      this.checkIndex(index, length);
      ByteBuf copy = this.alloc().directBuffer(length, this.maxCapacity());
      if (length != 0) {
         if (copy.hasMemoryAddress()) {
            PlatformDependent.copyMemory(this.addr(index), copy.memoryAddress(), (long)length);
            copy.setIndex(0, length);
         } else {
            copy.writeBytes((ByteBuf)this, index, length);
         }
      }

      return copy;
   }

   private long addr(int index) {
      return this.memoryAddress + (long)index;
   }
}
