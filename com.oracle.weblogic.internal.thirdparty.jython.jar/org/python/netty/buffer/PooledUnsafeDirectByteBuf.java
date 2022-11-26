package org.python.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import org.python.netty.util.Recycler;
import org.python.netty.util.internal.PlatformDependent;

final class PooledUnsafeDirectByteBuf extends PooledByteBuf {
   private static final Recycler RECYCLER = new Recycler() {
      protected PooledUnsafeDirectByteBuf newObject(Recycler.Handle handle) {
         return new PooledUnsafeDirectByteBuf(handle, 0);
      }
   };
   private long memoryAddress;

   static PooledUnsafeDirectByteBuf newInstance(int maxCapacity) {
      PooledUnsafeDirectByteBuf buf = (PooledUnsafeDirectByteBuf)RECYCLER.get();
      buf.reuse(maxCapacity);
      return buf;
   }

   private PooledUnsafeDirectByteBuf(Recycler.Handle recyclerHandle, int maxCapacity) {
      super(recyclerHandle, maxCapacity);
   }

   void init(PoolChunk chunk, long handle, int offset, int length, int maxLength, PoolThreadCache cache) {
      super.init(chunk, handle, offset, length, maxLength, cache);
      this.initMemoryAddress();
   }

   void initUnpooled(PoolChunk chunk, int length) {
      super.initUnpooled(chunk, length);
      this.initMemoryAddress();
   }

   private void initMemoryAddress() {
      this.memoryAddress = PlatformDependent.directBufferAddress((ByteBuffer)this.memory) + (long)this.offset;
   }

   protected ByteBuffer newInternalNioBuffer(ByteBuffer memory) {
      return memory.duplicate();
   }

   public boolean isDirect() {
      return true;
   }

   protected byte _getByte(int index) {
      return UnsafeByteBufUtil.getByte(this.addr(index));
   }

   protected short _getShort(int index) {
      return UnsafeByteBufUtil.getShort(this.addr(index));
   }

   protected short _getShortLE(int index) {
      return UnsafeByteBufUtil.getShortLE(this.addr(index));
   }

   protected int _getUnsignedMedium(int index) {
      return UnsafeByteBufUtil.getUnsignedMedium(this.addr(index));
   }

   protected int _getUnsignedMediumLE(int index) {
      return UnsafeByteBufUtil.getUnsignedMediumLE(this.addr(index));
   }

   protected int _getInt(int index) {
      return UnsafeByteBufUtil.getInt(this.addr(index));
   }

   protected int _getIntLE(int index) {
      return UnsafeByteBufUtil.getIntLE(this.addr(index));
   }

   protected long _getLong(int index) {
      return UnsafeByteBufUtil.getLong(this.addr(index));
   }

   protected long _getLongLE(int index) {
      return UnsafeByteBufUtil.getLongLE(this.addr(index));
   }

   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
      UnsafeByteBufUtil.getBytes(this, this.addr(index), index, (ByteBuf)dst, dstIndex, length);
      return this;
   }

   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
      UnsafeByteBufUtil.getBytes(this, this.addr(index), index, (byte[])dst, dstIndex, length);
      return this;
   }

   public ByteBuf getBytes(int index, ByteBuffer dst) {
      UnsafeByteBufUtil.getBytes(this, this.addr(index), index, dst);
      return this;
   }

   public ByteBuf readBytes(ByteBuffer dst) {
      int length = dst.remaining();
      this.checkReadableBytes(length);
      this.getBytes(this.readerIndex, dst);
      this.readerIndex += length;
      return this;
   }

   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
      UnsafeByteBufUtil.getBytes(this, this.addr(index), index, out, length);
      return this;
   }

   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
      return this.getBytes(index, out, length, false);
   }

   private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
      this.checkIndex(index, length);
      if (length == 0) {
         return 0;
      } else {
         ByteBuffer tmpBuf;
         if (internal) {
            tmpBuf = this.internalNioBuffer();
         } else {
            tmpBuf = ((ByteBuffer)this.memory).duplicate();
         }

         index = this.idx(index);
         tmpBuf.clear().position(index).limit(index + length);
         return out.write(tmpBuf);
      }
   }

   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
      return this.getBytes(index, out, position, length, false);
   }

   private int getBytes(int index, FileChannel out, long position, int length, boolean internal) throws IOException {
      this.checkIndex(index, length);
      if (length == 0) {
         return 0;
      } else {
         ByteBuffer tmpBuf = internal ? this.internalNioBuffer() : ((ByteBuffer)this.memory).duplicate();
         index = this.idx(index);
         tmpBuf.clear().position(index).limit(index + length);
         return out.write(tmpBuf, position);
      }
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

   protected void _setByte(int index, int value) {
      UnsafeByteBufUtil.setByte(this.addr(index), (byte)value);
   }

   protected void _setShort(int index, int value) {
      UnsafeByteBufUtil.setShort(this.addr(index), value);
   }

   protected void _setShortLE(int index, int value) {
      UnsafeByteBufUtil.setShortLE(this.addr(index), value);
   }

   protected void _setMedium(int index, int value) {
      UnsafeByteBufUtil.setMedium(this.addr(index), value);
   }

   protected void _setMediumLE(int index, int value) {
      UnsafeByteBufUtil.setMediumLE(this.addr(index), value);
   }

   protected void _setInt(int index, int value) {
      UnsafeByteBufUtil.setInt(this.addr(index), value);
   }

   protected void _setIntLE(int index, int value) {
      UnsafeByteBufUtil.setIntLE(this.addr(index), value);
   }

   protected void _setLong(int index, long value) {
      UnsafeByteBufUtil.setLong(this.addr(index), value);
   }

   protected void _setLongLE(int index, long value) {
      UnsafeByteBufUtil.setLongLE(this.addr(index), value);
   }

   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
      UnsafeByteBufUtil.setBytes(this, this.addr(index), index, (ByteBuf)src, srcIndex, length);
      return this;
   }

   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
      UnsafeByteBufUtil.setBytes(this, this.addr(index), index, (byte[])src, srcIndex, length);
      return this;
   }

   public ByteBuf setBytes(int index, ByteBuffer src) {
      UnsafeByteBufUtil.setBytes(this, this.addr(index), index, src);
      return this;
   }

   public int setBytes(int index, InputStream in, int length) throws IOException {
      return UnsafeByteBufUtil.setBytes(this, this.addr(index), index, in, length);
   }

   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
      this.checkIndex(index, length);
      ByteBuffer tmpBuf = this.internalNioBuffer();
      index = this.idx(index);
      tmpBuf.clear().position(index).limit(index + length);

      try {
         return in.read(tmpBuf);
      } catch (ClosedChannelException var6) {
         return -1;
      }
   }

   public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
      this.checkIndex(index, length);
      ByteBuffer tmpBuf = this.internalNioBuffer();
      index = this.idx(index);
      tmpBuf.clear().position(index).limit(index + length);

      try {
         return in.read(tmpBuf, position);
      } catch (ClosedChannelException var8) {
         return -1;
      }
   }

   public ByteBuf copy(int index, int length) {
      return UnsafeByteBufUtil.copy(this, this.addr(index), index, length);
   }

   public int nioBufferCount() {
      return 1;
   }

   public ByteBuffer[] nioBuffers(int index, int length) {
      return new ByteBuffer[]{this.nioBuffer(index, length)};
   }

   public ByteBuffer nioBuffer(int index, int length) {
      this.checkIndex(index, length);
      index = this.idx(index);
      return ((ByteBuffer)((ByteBuffer)this.memory).duplicate().position(index).limit(index + length)).slice();
   }

   public ByteBuffer internalNioBuffer(int index, int length) {
      this.checkIndex(index, length);
      index = this.idx(index);
      return (ByteBuffer)this.internalNioBuffer().clear().position(index).limit(index + length);
   }

   public boolean hasArray() {
      return false;
   }

   public byte[] array() {
      throw new UnsupportedOperationException("direct buffer");
   }

   public int arrayOffset() {
      throw new UnsupportedOperationException("direct buffer");
   }

   public boolean hasMemoryAddress() {
      return true;
   }

   public long memoryAddress() {
      this.ensureAccessible();
      return this.memoryAddress;
   }

   private long addr(int index) {
      return this.memoryAddress + (long)index;
   }

   protected SwappedByteBuf newSwappedByteBuf() {
      return (SwappedByteBuf)(PlatformDependent.isUnaligned() ? new UnsafeDirectSwappedByteBuf(this) : super.newSwappedByteBuf());
   }

   public ByteBuf setZero(int index, int length) {
      UnsafeByteBufUtil.setZero(this, this.addr(index), index, length);
      return this;
   }

   public ByteBuf writeZero(int length) {
      this.ensureWritable(length);
      int wIndex = this.writerIndex;
      this.setZero(wIndex, length);
      this.writerIndex = wIndex + length;
      return this;
   }

   // $FF: synthetic method
   PooledUnsafeDirectByteBuf(Recycler.Handle x0, int x1, Object x2) {
      this(x0, x1);
   }
}
