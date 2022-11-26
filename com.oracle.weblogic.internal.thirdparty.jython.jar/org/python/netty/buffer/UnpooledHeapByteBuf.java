package org.python.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;

public class UnpooledHeapByteBuf extends AbstractReferenceCountedByteBuf {
   private final ByteBufAllocator alloc;
   byte[] array;
   private ByteBuffer tmpNioBuf;

   protected UnpooledHeapByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
      super(maxCapacity);
      ObjectUtil.checkNotNull(alloc, "alloc");
      if (initialCapacity > maxCapacity) {
         throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", initialCapacity, maxCapacity));
      } else {
         this.alloc = alloc;
         this.setArray(this.allocateArray(initialCapacity));
         this.setIndex(0, 0);
      }
   }

   protected UnpooledHeapByteBuf(ByteBufAllocator alloc, byte[] initialArray, int maxCapacity) {
      super(maxCapacity);
      ObjectUtil.checkNotNull(alloc, "alloc");
      ObjectUtil.checkNotNull(initialArray, "initialArray");
      if (initialArray.length > maxCapacity) {
         throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", initialArray.length, maxCapacity));
      } else {
         this.alloc = alloc;
         this.setArray(initialArray);
         this.setIndex(0, initialArray.length);
      }
   }

   byte[] allocateArray(int initialCapacity) {
      return new byte[initialCapacity];
   }

   void freeArray(byte[] array) {
   }

   private void setArray(byte[] initialArray) {
      this.array = initialArray;
      this.tmpNioBuf = null;
   }

   public ByteBufAllocator alloc() {
      return this.alloc;
   }

   public ByteOrder order() {
      return ByteOrder.BIG_ENDIAN;
   }

   public boolean isDirect() {
      return false;
   }

   public int capacity() {
      this.ensureAccessible();
      return this.array.length;
   }

   public ByteBuf capacity(int newCapacity) {
      this.checkNewCapacity(newCapacity);
      int oldCapacity = this.array.length;
      byte[] oldArray = this.array;
      byte[] newArray;
      if (newCapacity > oldCapacity) {
         newArray = this.allocateArray(newCapacity);
         System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
         this.setArray(newArray);
         this.freeArray(oldArray);
      } else if (newCapacity < oldCapacity) {
         newArray = this.allocateArray(newCapacity);
         int readerIndex = this.readerIndex();
         if (readerIndex < newCapacity) {
            int writerIndex = this.writerIndex();
            if (writerIndex > newCapacity) {
               writerIndex = newCapacity;
               this.writerIndex(newCapacity);
            }

            System.arraycopy(oldArray, readerIndex, newArray, readerIndex, writerIndex - readerIndex);
         } else {
            this.setIndex(newCapacity, newCapacity);
         }

         this.setArray(newArray);
         this.freeArray(oldArray);
      }

      return this;
   }

   public boolean hasArray() {
      return true;
   }

   public byte[] array() {
      this.ensureAccessible();
      return this.array;
   }

   public int arrayOffset() {
      return 0;
   }

   public boolean hasMemoryAddress() {
      return false;
   }

   public long memoryAddress() {
      throw new UnsupportedOperationException();
   }

   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
      this.checkDstIndex(index, length, dstIndex, dst.capacity());
      if (dst.hasMemoryAddress()) {
         PlatformDependent.copyMemory(this.array, index, dst.memoryAddress() + (long)dstIndex, (long)length);
      } else if (dst.hasArray()) {
         this.getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
      } else {
         dst.setBytes(dstIndex, this.array, index, length);
      }

      return this;
   }

   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
      this.checkDstIndex(index, length, dstIndex, dst.length);
      System.arraycopy(this.array, index, dst, dstIndex, length);
      return this;
   }

   public ByteBuf getBytes(int index, ByteBuffer dst) {
      this.checkIndex(index, dst.remaining());
      dst.put(this.array, index, dst.remaining());
      return this;
   }

   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
      this.ensureAccessible();
      out.write(this.array, index, length);
      return this;
   }

   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
      this.ensureAccessible();
      return this.getBytes(index, out, length, false);
   }

   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
      this.ensureAccessible();
      return this.getBytes(index, out, position, length, false);
   }

   private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
      this.ensureAccessible();
      ByteBuffer tmpBuf;
      if (internal) {
         tmpBuf = this.internalNioBuffer();
      } else {
         tmpBuf = ByteBuffer.wrap(this.array);
      }

      return out.write((ByteBuffer)tmpBuf.clear().position(index).limit(index + length));
   }

   private int getBytes(int index, FileChannel out, long position, int length, boolean internal) throws IOException {
      this.ensureAccessible();
      ByteBuffer tmpBuf = internal ? this.internalNioBuffer() : ByteBuffer.wrap(this.array);
      return out.write((ByteBuffer)tmpBuf.clear().position(index).limit(index + length), position);
   }

   public int readBytes(GatheringByteChannel out, int length) throws IOException {
      this.checkReadableBytes(length);
      int readBytes = this.getBytes(this.readerIndex, out, length, true);
      this.readerIndex += readBytes;
      return readBytes;
   }

   public int readBytes(FileChannel out, long position, int length) throws IOException {
      this.checkReadableBytes(length);
      int readBytes = this.getBytes(this.readerIndex, out, position, length, true);
      this.readerIndex += readBytes;
      return readBytes;
   }

   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
      this.checkSrcIndex(index, length, srcIndex, src.capacity());
      if (src.hasMemoryAddress()) {
         PlatformDependent.copyMemory(src.memoryAddress() + (long)srcIndex, this.array, index, (long)length);
      } else if (src.hasArray()) {
         this.setBytes(index, src.array(), src.arrayOffset() + srcIndex, length);
      } else {
         src.getBytes(srcIndex, this.array, index, length);
      }

      return this;
   }

   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
      this.checkSrcIndex(index, length, srcIndex, src.length);
      System.arraycopy(src, srcIndex, this.array, index, length);
      return this;
   }

   public ByteBuf setBytes(int index, ByteBuffer src) {
      this.ensureAccessible();
      src.get(this.array, index, src.remaining());
      return this;
   }

   public int setBytes(int index, InputStream in, int length) throws IOException {
      this.ensureAccessible();
      return in.read(this.array, index, length);
   }

   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
      this.ensureAccessible();

      try {
         return in.read((ByteBuffer)this.internalNioBuffer().clear().position(index).limit(index + length));
      } catch (ClosedChannelException var5) {
         return -1;
      }
   }

   public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
      this.ensureAccessible();

      try {
         return in.read((ByteBuffer)this.internalNioBuffer().clear().position(index).limit(index + length), position);
      } catch (ClosedChannelException var7) {
         return -1;
      }
   }

   public int nioBufferCount() {
      return 1;
   }

   public ByteBuffer nioBuffer(int index, int length) {
      this.ensureAccessible();
      return ByteBuffer.wrap(this.array, index, length).slice();
   }

   public ByteBuffer[] nioBuffers(int index, int length) {
      return new ByteBuffer[]{this.nioBuffer(index, length)};
   }

   public ByteBuffer internalNioBuffer(int index, int length) {
      this.checkIndex(index, length);
      return (ByteBuffer)this.internalNioBuffer().clear().position(index).limit(index + length);
   }

   public byte getByte(int index) {
      this.ensureAccessible();
      return this._getByte(index);
   }

   protected byte _getByte(int index) {
      return HeapByteBufUtil.getByte(this.array, index);
   }

   public short getShort(int index) {
      this.ensureAccessible();
      return this._getShort(index);
   }

   protected short _getShort(int index) {
      return HeapByteBufUtil.getShort(this.array, index);
   }

   public short getShortLE(int index) {
      this.ensureAccessible();
      return this._getShortLE(index);
   }

   protected short _getShortLE(int index) {
      return HeapByteBufUtil.getShortLE(this.array, index);
   }

   public int getUnsignedMedium(int index) {
      this.ensureAccessible();
      return this._getUnsignedMedium(index);
   }

   protected int _getUnsignedMedium(int index) {
      return HeapByteBufUtil.getUnsignedMedium(this.array, index);
   }

   public int getUnsignedMediumLE(int index) {
      this.ensureAccessible();
      return this._getUnsignedMediumLE(index);
   }

   protected int _getUnsignedMediumLE(int index) {
      return HeapByteBufUtil.getUnsignedMediumLE(this.array, index);
   }

   public int getInt(int index) {
      this.ensureAccessible();
      return this._getInt(index);
   }

   protected int _getInt(int index) {
      return HeapByteBufUtil.getInt(this.array, index);
   }

   public int getIntLE(int index) {
      this.ensureAccessible();
      return this._getIntLE(index);
   }

   protected int _getIntLE(int index) {
      return HeapByteBufUtil.getIntLE(this.array, index);
   }

   public long getLong(int index) {
      this.ensureAccessible();
      return this._getLong(index);
   }

   protected long _getLong(int index) {
      return HeapByteBufUtil.getLong(this.array, index);
   }

   public long getLongLE(int index) {
      this.ensureAccessible();
      return this._getLongLE(index);
   }

   protected long _getLongLE(int index) {
      return HeapByteBufUtil.getLongLE(this.array, index);
   }

   public ByteBuf setByte(int index, int value) {
      this.ensureAccessible();
      this._setByte(index, value);
      return this;
   }

   protected void _setByte(int index, int value) {
      HeapByteBufUtil.setByte(this.array, index, value);
   }

   public ByteBuf setShort(int index, int value) {
      this.ensureAccessible();
      this._setShort(index, value);
      return this;
   }

   protected void _setShort(int index, int value) {
      HeapByteBufUtil.setShort(this.array, index, value);
   }

   public ByteBuf setShortLE(int index, int value) {
      this.ensureAccessible();
      this._setShortLE(index, value);
      return this;
   }

   protected void _setShortLE(int index, int value) {
      HeapByteBufUtil.setShortLE(this.array, index, value);
   }

   public ByteBuf setMedium(int index, int value) {
      this.ensureAccessible();
      this._setMedium(index, value);
      return this;
   }

   protected void _setMedium(int index, int value) {
      HeapByteBufUtil.setMedium(this.array, index, value);
   }

   public ByteBuf setMediumLE(int index, int value) {
      this.ensureAccessible();
      this._setMediumLE(index, value);
      return this;
   }

   protected void _setMediumLE(int index, int value) {
      HeapByteBufUtil.setMediumLE(this.array, index, value);
   }

   public ByteBuf setInt(int index, int value) {
      this.ensureAccessible();
      this._setInt(index, value);
      return this;
   }

   protected void _setInt(int index, int value) {
      HeapByteBufUtil.setInt(this.array, index, value);
   }

   public ByteBuf setIntLE(int index, int value) {
      this.ensureAccessible();
      this._setIntLE(index, value);
      return this;
   }

   protected void _setIntLE(int index, int value) {
      HeapByteBufUtil.setIntLE(this.array, index, value);
   }

   public ByteBuf setLong(int index, long value) {
      this.ensureAccessible();
      this._setLong(index, value);
      return this;
   }

   protected void _setLong(int index, long value) {
      HeapByteBufUtil.setLong(this.array, index, value);
   }

   public ByteBuf setLongLE(int index, long value) {
      this.ensureAccessible();
      this._setLongLE(index, value);
      return this;
   }

   protected void _setLongLE(int index, long value) {
      HeapByteBufUtil.setLongLE(this.array, index, value);
   }

   public ByteBuf copy(int index, int length) {
      this.checkIndex(index, length);
      byte[] copiedArray = new byte[length];
      System.arraycopy(this.array, index, copiedArray, 0, length);
      return new UnpooledHeapByteBuf(this.alloc(), copiedArray, this.maxCapacity());
   }

   private ByteBuffer internalNioBuffer() {
      ByteBuffer tmpNioBuf = this.tmpNioBuf;
      if (tmpNioBuf == null) {
         this.tmpNioBuf = tmpNioBuf = ByteBuffer.wrap(this.array);
      }

      return tmpNioBuf;
   }

   protected void deallocate() {
      this.freeArray(this.array);
      this.array = null;
   }

   public ByteBuf unwrap() {
      return null;
   }
}
