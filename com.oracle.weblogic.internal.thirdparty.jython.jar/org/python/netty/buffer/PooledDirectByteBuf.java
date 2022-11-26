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

final class PooledDirectByteBuf extends PooledByteBuf {
   private static final Recycler RECYCLER = new Recycler() {
      protected PooledDirectByteBuf newObject(Recycler.Handle handle) {
         return new PooledDirectByteBuf(handle, 0);
      }
   };

   static PooledDirectByteBuf newInstance(int maxCapacity) {
      PooledDirectByteBuf buf = (PooledDirectByteBuf)RECYCLER.get();
      buf.reuse(maxCapacity);
      return buf;
   }

   private PooledDirectByteBuf(Recycler.Handle recyclerHandle, int maxCapacity) {
      super(recyclerHandle, maxCapacity);
   }

   protected ByteBuffer newInternalNioBuffer(ByteBuffer memory) {
      return memory.duplicate();
   }

   public boolean isDirect() {
      return true;
   }

   protected byte _getByte(int index) {
      return ((ByteBuffer)this.memory).get(this.idx(index));
   }

   protected short _getShort(int index) {
      return ((ByteBuffer)this.memory).getShort(this.idx(index));
   }

   protected short _getShortLE(int index) {
      return ByteBufUtil.swapShort(this._getShort(index));
   }

   protected int _getUnsignedMedium(int index) {
      index = this.idx(index);
      return (((ByteBuffer)this.memory).get(index) & 255) << 16 | (((ByteBuffer)this.memory).get(index + 1) & 255) << 8 | ((ByteBuffer)this.memory).get(index + 2) & 255;
   }

   protected int _getUnsignedMediumLE(int index) {
      index = this.idx(index);
      return ((ByteBuffer)this.memory).get(index) & 255 | (((ByteBuffer)this.memory).get(index + 1) & 255) << 8 | (((ByteBuffer)this.memory).get(index + 2) & 255) << 16;
   }

   protected int _getInt(int index) {
      return ((ByteBuffer)this.memory).getInt(this.idx(index));
   }

   protected int _getIntLE(int index) {
      return ByteBufUtil.swapInt(this._getInt(index));
   }

   protected long _getLong(int index) {
      return ((ByteBuffer)this.memory).getLong(this.idx(index));
   }

   protected long _getLongLE(int index) {
      return ByteBufUtil.swapLong(this._getLong(index));
   }

   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
      this.checkDstIndex(index, length, dstIndex, dst.capacity());
      if (dst.hasArray()) {
         this.getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
      } else if (dst.nioBufferCount() > 0) {
         ByteBuffer[] var5 = dst.nioBuffers(dstIndex, length);
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ByteBuffer bb = var5[var7];
            int bbLen = bb.remaining();
            this.getBytes(index, bb);
            index += bbLen;
         }
      } else {
         dst.setBytes(dstIndex, (ByteBuf)this, index, length);
      }

      return this;
   }

   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
      this.getBytes(index, dst, dstIndex, length, false);
      return this;
   }

   private void getBytes(int index, byte[] dst, int dstIndex, int length, boolean internal) {
      this.checkDstIndex(index, length, dstIndex, dst.length);
      ByteBuffer tmpBuf;
      if (internal) {
         tmpBuf = this.internalNioBuffer();
      } else {
         tmpBuf = ((ByteBuffer)this.memory).duplicate();
      }

      index = this.idx(index);
      tmpBuf.clear().position(index).limit(index + length);
      tmpBuf.get(dst, dstIndex, length);
   }

   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
      this.checkReadableBytes(length);
      this.getBytes(this.readerIndex, dst, dstIndex, length, true);
      this.readerIndex += length;
      return this;
   }

   public ByteBuf getBytes(int index, ByteBuffer dst) {
      this.getBytes(index, dst, false);
      return this;
   }

   private void getBytes(int index, ByteBuffer dst, boolean internal) {
      this.checkIndex(index, dst.remaining());
      ByteBuffer tmpBuf;
      if (internal) {
         tmpBuf = this.internalNioBuffer();
      } else {
         tmpBuf = ((ByteBuffer)this.memory).duplicate();
      }

      index = this.idx(index);
      tmpBuf.clear().position(index).limit(index + dst.remaining());
      dst.put(tmpBuf);
   }

   public ByteBuf readBytes(ByteBuffer dst) {
      int length = dst.remaining();
      this.checkReadableBytes(length);
      this.getBytes(this.readerIndex, dst, true);
      this.readerIndex += length;
      return this;
   }

   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
      this.getBytes(index, out, length, false);
      return this;
   }

   private void getBytes(int index, OutputStream out, int length, boolean internal) throws IOException {
      this.checkIndex(index, length);
      if (length != 0) {
         byte[] tmp = new byte[length];
         ByteBuffer tmpBuf;
         if (internal) {
            tmpBuf = this.internalNioBuffer();
         } else {
            tmpBuf = ((ByteBuffer)this.memory).duplicate();
         }

         tmpBuf.clear().position(this.idx(index));
         tmpBuf.get(tmp);
         out.write(tmp);
      }
   }

   public ByteBuf readBytes(OutputStream out, int length) throws IOException {
      this.checkReadableBytes(length);
      this.getBytes(this.readerIndex, out, length, true);
      this.readerIndex += length;
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
      ((ByteBuffer)this.memory).put(this.idx(index), (byte)value);
   }

   protected void _setShort(int index, int value) {
      ((ByteBuffer)this.memory).putShort(this.idx(index), (short)value);
   }

   protected void _setShortLE(int index, int value) {
      this._setShort(index, ByteBufUtil.swapShort((short)value));
   }

   protected void _setMedium(int index, int value) {
      index = this.idx(index);
      ((ByteBuffer)this.memory).put(index, (byte)(value >>> 16));
      ((ByteBuffer)this.memory).put(index + 1, (byte)(value >>> 8));
      ((ByteBuffer)this.memory).put(index + 2, (byte)value);
   }

   protected void _setMediumLE(int index, int value) {
      index = this.idx(index);
      ((ByteBuffer)this.memory).put(index, (byte)value);
      ((ByteBuffer)this.memory).put(index + 1, (byte)(value >>> 8));
      ((ByteBuffer)this.memory).put(index + 2, (byte)(value >>> 16));
   }

   protected void _setInt(int index, int value) {
      ((ByteBuffer)this.memory).putInt(this.idx(index), value);
   }

   protected void _setIntLE(int index, int value) {
      this._setInt(index, ByteBufUtil.swapInt(value));
   }

   protected void _setLong(int index, long value) {
      ((ByteBuffer)this.memory).putLong(this.idx(index), value);
   }

   protected void _setLongLE(int index, long value) {
      this._setLong(index, ByteBufUtil.swapLong(value));
   }

   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
      this.checkSrcIndex(index, length, srcIndex, src.capacity());
      if (src.hasArray()) {
         this.setBytes(index, src.array(), src.arrayOffset() + srcIndex, length);
      } else if (src.nioBufferCount() > 0) {
         ByteBuffer[] var5 = src.nioBuffers(srcIndex, length);
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ByteBuffer bb = var5[var7];
            int bbLen = bb.remaining();
            this.setBytes(index, bb);
            index += bbLen;
         }
      } else {
         src.getBytes(srcIndex, (ByteBuf)this, index, length);
      }

      return this;
   }

   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
      this.checkSrcIndex(index, length, srcIndex, src.length);
      ByteBuffer tmpBuf = this.internalNioBuffer();
      index = this.idx(index);
      tmpBuf.clear().position(index).limit(index + length);
      tmpBuf.put(src, srcIndex, length);
      return this;
   }

   public ByteBuf setBytes(int index, ByteBuffer src) {
      this.checkIndex(index, src.remaining());
      ByteBuffer tmpBuf = this.internalNioBuffer();
      if (src == tmpBuf) {
         src = src.duplicate();
      }

      index = this.idx(index);
      tmpBuf.clear().position(index).limit(index + src.remaining());
      tmpBuf.put(src);
      return this;
   }

   public int setBytes(int index, InputStream in, int length) throws IOException {
      this.checkIndex(index, length);
      byte[] tmp = new byte[length];
      int readBytes = in.read(tmp);
      if (readBytes <= 0) {
         return readBytes;
      } else {
         ByteBuffer tmpBuf = this.internalNioBuffer();
         tmpBuf.clear().position(this.idx(index));
         tmpBuf.put(tmp, 0, readBytes);
         return readBytes;
      }
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
      this.checkIndex(index, length);
      ByteBuf copy = this.alloc().directBuffer(length, this.maxCapacity());
      copy.writeBytes((ByteBuf)this, index, length);
      return copy;
   }

   public int nioBufferCount() {
      return 1;
   }

   public ByteBuffer nioBuffer(int index, int length) {
      this.checkIndex(index, length);
      index = this.idx(index);
      return ((ByteBuffer)((ByteBuffer)this.memory).duplicate().position(index).limit(index + length)).slice();
   }

   public ByteBuffer[] nioBuffers(int index, int length) {
      return new ByteBuffer[]{this.nioBuffer(index, length)};
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
      return false;
   }

   public long memoryAddress() {
      throw new UnsupportedOperationException();
   }

   // $FF: synthetic method
   PooledDirectByteBuf(Recycler.Handle x0, int x1, Object x2) {
      this(x0, x1);
   }
}
