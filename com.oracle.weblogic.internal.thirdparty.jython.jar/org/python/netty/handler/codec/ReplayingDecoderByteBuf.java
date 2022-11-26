package org.python.netty.handler.codec;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufAllocator;
import org.python.netty.buffer.SwappedByteBuf;
import org.python.netty.buffer.Unpooled;
import org.python.netty.util.ByteProcessor;
import org.python.netty.util.Signal;
import org.python.netty.util.internal.StringUtil;

final class ReplayingDecoderByteBuf extends ByteBuf {
   private static final Signal REPLAY;
   private ByteBuf buffer;
   private boolean terminated;
   private SwappedByteBuf swapped;
   static final ReplayingDecoderByteBuf EMPTY_BUFFER;

   ReplayingDecoderByteBuf() {
   }

   ReplayingDecoderByteBuf(ByteBuf buffer) {
      this.setCumulation(buffer);
   }

   void setCumulation(ByteBuf buffer) {
      this.buffer = buffer;
   }

   void terminate() {
      this.terminated = true;
   }

   public int capacity() {
      return this.terminated ? this.buffer.capacity() : Integer.MAX_VALUE;
   }

   public ByteBuf capacity(int newCapacity) {
      throw reject();
   }

   public int maxCapacity() {
      return this.capacity();
   }

   public ByteBufAllocator alloc() {
      return this.buffer.alloc();
   }

   public boolean isReadOnly() {
      return false;
   }

   public ByteBuf asReadOnly() {
      return Unpooled.unmodifiableBuffer((ByteBuf)this);
   }

   public boolean isDirect() {
      return this.buffer.isDirect();
   }

   public boolean hasArray() {
      return false;
   }

   public byte[] array() {
      throw new UnsupportedOperationException();
   }

   public int arrayOffset() {
      throw new UnsupportedOperationException();
   }

   public boolean hasMemoryAddress() {
      return false;
   }

   public long memoryAddress() {
      throw new UnsupportedOperationException();
   }

   public ByteBuf clear() {
      throw reject();
   }

   public boolean equals(Object obj) {
      return this == obj;
   }

   public int compareTo(ByteBuf buffer) {
      throw reject();
   }

   public ByteBuf copy() {
      throw reject();
   }

   public ByteBuf copy(int index, int length) {
      this.checkIndex(index, length);
      return this.buffer.copy(index, length);
   }

   public ByteBuf discardReadBytes() {
      throw reject();
   }

   public ByteBuf ensureWritable(int writableBytes) {
      throw reject();
   }

   public int ensureWritable(int minWritableBytes, boolean force) {
      throw reject();
   }

   public ByteBuf duplicate() {
      throw reject();
   }

   public ByteBuf retainedDuplicate() {
      throw reject();
   }

   public boolean getBoolean(int index) {
      this.checkIndex(index, 1);
      return this.buffer.getBoolean(index);
   }

   public byte getByte(int index) {
      this.checkIndex(index, 1);
      return this.buffer.getByte(index);
   }

   public short getUnsignedByte(int index) {
      this.checkIndex(index, 1);
      return this.buffer.getUnsignedByte(index);
   }

   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
      this.checkIndex(index, length);
      this.buffer.getBytes(index, dst, dstIndex, length);
      return this;
   }

   public ByteBuf getBytes(int index, byte[] dst) {
      this.checkIndex(index, dst.length);
      this.buffer.getBytes(index, dst);
      return this;
   }

   public ByteBuf getBytes(int index, ByteBuffer dst) {
      throw reject();
   }

   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
      this.checkIndex(index, length);
      this.buffer.getBytes(index, dst, dstIndex, length);
      return this;
   }

   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
      throw reject();
   }

   public ByteBuf getBytes(int index, ByteBuf dst) {
      throw reject();
   }

   public int getBytes(int index, GatheringByteChannel out, int length) {
      throw reject();
   }

   public int getBytes(int index, FileChannel out, long position, int length) {
      throw reject();
   }

   public ByteBuf getBytes(int index, OutputStream out, int length) {
      throw reject();
   }

   public int getInt(int index) {
      this.checkIndex(index, 4);
      return this.buffer.getInt(index);
   }

   public int getIntLE(int index) {
      this.checkIndex(index, 4);
      return this.buffer.getIntLE(index);
   }

   public long getUnsignedInt(int index) {
      this.checkIndex(index, 4);
      return this.buffer.getUnsignedInt(index);
   }

   public long getUnsignedIntLE(int index) {
      this.checkIndex(index, 4);
      return this.buffer.getUnsignedIntLE(index);
   }

   public long getLong(int index) {
      this.checkIndex(index, 8);
      return this.buffer.getLong(index);
   }

   public long getLongLE(int index) {
      this.checkIndex(index, 8);
      return this.buffer.getLongLE(index);
   }

   public int getMedium(int index) {
      this.checkIndex(index, 3);
      return this.buffer.getMedium(index);
   }

   public int getMediumLE(int index) {
      this.checkIndex(index, 3);
      return this.buffer.getMediumLE(index);
   }

   public int getUnsignedMedium(int index) {
      this.checkIndex(index, 3);
      return this.buffer.getUnsignedMedium(index);
   }

   public int getUnsignedMediumLE(int index) {
      this.checkIndex(index, 3);
      return this.buffer.getUnsignedMediumLE(index);
   }

   public short getShort(int index) {
      this.checkIndex(index, 2);
      return this.buffer.getShort(index);
   }

   public short getShortLE(int index) {
      this.checkIndex(index, 2);
      return this.buffer.getShortLE(index);
   }

   public int getUnsignedShort(int index) {
      this.checkIndex(index, 2);
      return this.buffer.getUnsignedShort(index);
   }

   public int getUnsignedShortLE(int index) {
      this.checkIndex(index, 2);
      return this.buffer.getUnsignedShortLE(index);
   }

   public char getChar(int index) {
      this.checkIndex(index, 2);
      return this.buffer.getChar(index);
   }

   public float getFloat(int index) {
      this.checkIndex(index, 4);
      return this.buffer.getFloat(index);
   }

   public double getDouble(int index) {
      this.checkIndex(index, 8);
      return this.buffer.getDouble(index);
   }

   public CharSequence getCharSequence(int index, int length, Charset charset) {
      this.checkIndex(index, length);
      return this.buffer.getCharSequence(index, length, charset);
   }

   public int hashCode() {
      throw reject();
   }

   public int indexOf(int fromIndex, int toIndex, byte value) {
      if (fromIndex == toIndex) {
         return -1;
      } else if (Math.max(fromIndex, toIndex) > this.buffer.writerIndex()) {
         throw REPLAY;
      } else {
         return this.buffer.indexOf(fromIndex, toIndex, value);
      }
   }

   public int bytesBefore(byte value) {
      int bytes = this.buffer.bytesBefore(value);
      if (bytes < 0) {
         throw REPLAY;
      } else {
         return bytes;
      }
   }

   public int bytesBefore(int length, byte value) {
      return this.bytesBefore(this.buffer.readerIndex(), length, value);
   }

   public int bytesBefore(int index, int length, byte value) {
      int writerIndex = this.buffer.writerIndex();
      if (index >= writerIndex) {
         throw REPLAY;
      } else if (index <= writerIndex - length) {
         return this.buffer.bytesBefore(index, length, value);
      } else {
         int res = this.buffer.bytesBefore(index, writerIndex - index, value);
         if (res < 0) {
            throw REPLAY;
         } else {
            return res;
         }
      }
   }

   public int forEachByte(ByteProcessor processor) {
      int ret = this.buffer.forEachByte(processor);
      if (ret < 0) {
         throw REPLAY;
      } else {
         return ret;
      }
   }

   public int forEachByte(int index, int length, ByteProcessor processor) {
      int writerIndex = this.buffer.writerIndex();
      if (index >= writerIndex) {
         throw REPLAY;
      } else if (index <= writerIndex - length) {
         return this.buffer.forEachByte(index, length, processor);
      } else {
         int ret = this.buffer.forEachByte(index, writerIndex - index, processor);
         if (ret < 0) {
            throw REPLAY;
         } else {
            return ret;
         }
      }
   }

   public int forEachByteDesc(ByteProcessor processor) {
      if (this.terminated) {
         return this.buffer.forEachByteDesc(processor);
      } else {
         throw reject();
      }
   }

   public int forEachByteDesc(int index, int length, ByteProcessor processor) {
      if (index + length > this.buffer.writerIndex()) {
         throw REPLAY;
      } else {
         return this.buffer.forEachByteDesc(index, length, processor);
      }
   }

   public ByteBuf markReaderIndex() {
      this.buffer.markReaderIndex();
      return this;
   }

   public ByteBuf markWriterIndex() {
      throw reject();
   }

   public ByteOrder order() {
      return this.buffer.order();
   }

   public ByteBuf order(ByteOrder endianness) {
      if (endianness == null) {
         throw new NullPointerException("endianness");
      } else if (endianness == this.order()) {
         return this;
      } else {
         SwappedByteBuf swapped = this.swapped;
         if (swapped == null) {
            this.swapped = swapped = new SwappedByteBuf(this);
         }

         return swapped;
      }
   }

   public boolean isReadable() {
      return this.terminated ? this.buffer.isReadable() : true;
   }

   public boolean isReadable(int size) {
      return this.terminated ? this.buffer.isReadable(size) : true;
   }

   public int readableBytes() {
      return this.terminated ? this.buffer.readableBytes() : Integer.MAX_VALUE - this.buffer.readerIndex();
   }

   public boolean readBoolean() {
      this.checkReadableBytes(1);
      return this.buffer.readBoolean();
   }

   public byte readByte() {
      this.checkReadableBytes(1);
      return this.buffer.readByte();
   }

   public short readUnsignedByte() {
      this.checkReadableBytes(1);
      return this.buffer.readUnsignedByte();
   }

   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
      this.checkReadableBytes(length);
      this.buffer.readBytes(dst, dstIndex, length);
      return this;
   }

   public ByteBuf readBytes(byte[] dst) {
      this.checkReadableBytes(dst.length);
      this.buffer.readBytes(dst);
      return this;
   }

   public ByteBuf readBytes(ByteBuffer dst) {
      throw reject();
   }

   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
      this.checkReadableBytes(length);
      this.buffer.readBytes(dst, dstIndex, length);
      return this;
   }

   public ByteBuf readBytes(ByteBuf dst, int length) {
      throw reject();
   }

   public ByteBuf readBytes(ByteBuf dst) {
      this.checkReadableBytes(dst.writableBytes());
      this.buffer.readBytes(dst);
      return this;
   }

   public int readBytes(GatheringByteChannel out, int length) {
      throw reject();
   }

   public int readBytes(FileChannel out, long position, int length) {
      throw reject();
   }

   public ByteBuf readBytes(int length) {
      this.checkReadableBytes(length);
      return this.buffer.readBytes(length);
   }

   public ByteBuf readSlice(int length) {
      this.checkReadableBytes(length);
      return this.buffer.readSlice(length);
   }

   public ByteBuf readRetainedSlice(int length) {
      this.checkReadableBytes(length);
      return this.buffer.readRetainedSlice(length);
   }

   public ByteBuf readBytes(OutputStream out, int length) {
      throw reject();
   }

   public int readerIndex() {
      return this.buffer.readerIndex();
   }

   public ByteBuf readerIndex(int readerIndex) {
      this.buffer.readerIndex(readerIndex);
      return this;
   }

   public int readInt() {
      this.checkReadableBytes(4);
      return this.buffer.readInt();
   }

   public int readIntLE() {
      this.checkReadableBytes(4);
      return this.buffer.readIntLE();
   }

   public long readUnsignedInt() {
      this.checkReadableBytes(4);
      return this.buffer.readUnsignedInt();
   }

   public long readUnsignedIntLE() {
      this.checkReadableBytes(4);
      return this.buffer.readUnsignedIntLE();
   }

   public long readLong() {
      this.checkReadableBytes(8);
      return this.buffer.readLong();
   }

   public long readLongLE() {
      this.checkReadableBytes(8);
      return this.buffer.readLongLE();
   }

   public int readMedium() {
      this.checkReadableBytes(3);
      return this.buffer.readMedium();
   }

   public int readMediumLE() {
      this.checkReadableBytes(3);
      return this.buffer.readMediumLE();
   }

   public int readUnsignedMedium() {
      this.checkReadableBytes(3);
      return this.buffer.readUnsignedMedium();
   }

   public int readUnsignedMediumLE() {
      this.checkReadableBytes(3);
      return this.buffer.readUnsignedMediumLE();
   }

   public short readShort() {
      this.checkReadableBytes(2);
      return this.buffer.readShort();
   }

   public short readShortLE() {
      this.checkReadableBytes(2);
      return this.buffer.readShortLE();
   }

   public int readUnsignedShort() {
      this.checkReadableBytes(2);
      return this.buffer.readUnsignedShort();
   }

   public int readUnsignedShortLE() {
      this.checkReadableBytes(2);
      return this.buffer.readUnsignedShortLE();
   }

   public char readChar() {
      this.checkReadableBytes(2);
      return this.buffer.readChar();
   }

   public float readFloat() {
      this.checkReadableBytes(4);
      return this.buffer.readFloat();
   }

   public double readDouble() {
      this.checkReadableBytes(8);
      return this.buffer.readDouble();
   }

   public CharSequence readCharSequence(int length, Charset charset) {
      this.checkReadableBytes(length);
      return this.buffer.readCharSequence(length, charset);
   }

   public ByteBuf resetReaderIndex() {
      this.buffer.resetReaderIndex();
      return this;
   }

   public ByteBuf resetWriterIndex() {
      throw reject();
   }

   public ByteBuf setBoolean(int index, boolean value) {
      throw reject();
   }

   public ByteBuf setByte(int index, int value) {
      throw reject();
   }

   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
      throw reject();
   }

   public ByteBuf setBytes(int index, byte[] src) {
      throw reject();
   }

   public ByteBuf setBytes(int index, ByteBuffer src) {
      throw reject();
   }

   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
      throw reject();
   }

   public ByteBuf setBytes(int index, ByteBuf src, int length) {
      throw reject();
   }

   public ByteBuf setBytes(int index, ByteBuf src) {
      throw reject();
   }

   public int setBytes(int index, InputStream in, int length) {
      throw reject();
   }

   public ByteBuf setZero(int index, int length) {
      throw reject();
   }

   public int setBytes(int index, ScatteringByteChannel in, int length) {
      throw reject();
   }

   public int setBytes(int index, FileChannel in, long position, int length) {
      throw reject();
   }

   public ByteBuf setIndex(int readerIndex, int writerIndex) {
      throw reject();
   }

   public ByteBuf setInt(int index, int value) {
      throw reject();
   }

   public ByteBuf setIntLE(int index, int value) {
      throw reject();
   }

   public ByteBuf setLong(int index, long value) {
      throw reject();
   }

   public ByteBuf setLongLE(int index, long value) {
      throw reject();
   }

   public ByteBuf setMedium(int index, int value) {
      throw reject();
   }

   public ByteBuf setMediumLE(int index, int value) {
      throw reject();
   }

   public ByteBuf setShort(int index, int value) {
      throw reject();
   }

   public ByteBuf setShortLE(int index, int value) {
      throw reject();
   }

   public ByteBuf setChar(int index, int value) {
      throw reject();
   }

   public ByteBuf setFloat(int index, float value) {
      throw reject();
   }

   public ByteBuf setDouble(int index, double value) {
      throw reject();
   }

   public ByteBuf skipBytes(int length) {
      this.checkReadableBytes(length);
      this.buffer.skipBytes(length);
      return this;
   }

   public ByteBuf slice() {
      throw reject();
   }

   public ByteBuf retainedSlice() {
      throw reject();
   }

   public ByteBuf slice(int index, int length) {
      this.checkIndex(index, length);
      return this.buffer.slice(index, length);
   }

   public ByteBuf retainedSlice(int index, int length) {
      this.checkIndex(index, length);
      return this.buffer.slice(index, length);
   }

   public int nioBufferCount() {
      return this.buffer.nioBufferCount();
   }

   public ByteBuffer nioBuffer() {
      throw reject();
   }

   public ByteBuffer nioBuffer(int index, int length) {
      this.checkIndex(index, length);
      return this.buffer.nioBuffer(index, length);
   }

   public ByteBuffer[] nioBuffers() {
      throw reject();
   }

   public ByteBuffer[] nioBuffers(int index, int length) {
      this.checkIndex(index, length);
      return this.buffer.nioBuffers(index, length);
   }

   public ByteBuffer internalNioBuffer(int index, int length) {
      this.checkIndex(index, length);
      return this.buffer.internalNioBuffer(index, length);
   }

   public String toString(int index, int length, Charset charset) {
      this.checkIndex(index, length);
      return this.buffer.toString(index, length, charset);
   }

   public String toString(Charset charsetName) {
      throw reject();
   }

   public String toString() {
      return StringUtil.simpleClassName((Object)this) + '(' + "ridx=" + this.readerIndex() + ", widx=" + this.writerIndex() + ')';
   }

   public boolean isWritable() {
      return false;
   }

   public boolean isWritable(int size) {
      return false;
   }

   public int writableBytes() {
      return 0;
   }

   public int maxWritableBytes() {
      return 0;
   }

   public ByteBuf writeBoolean(boolean value) {
      throw reject();
   }

   public ByteBuf writeByte(int value) {
      throw reject();
   }

   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
      throw reject();
   }

   public ByteBuf writeBytes(byte[] src) {
      throw reject();
   }

   public ByteBuf writeBytes(ByteBuffer src) {
      throw reject();
   }

   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
      throw reject();
   }

   public ByteBuf writeBytes(ByteBuf src, int length) {
      throw reject();
   }

   public ByteBuf writeBytes(ByteBuf src) {
      throw reject();
   }

   public int writeBytes(InputStream in, int length) {
      throw reject();
   }

   public int writeBytes(ScatteringByteChannel in, int length) {
      throw reject();
   }

   public int writeBytes(FileChannel in, long position, int length) {
      throw reject();
   }

   public ByteBuf writeInt(int value) {
      throw reject();
   }

   public ByteBuf writeIntLE(int value) {
      throw reject();
   }

   public ByteBuf writeLong(long value) {
      throw reject();
   }

   public ByteBuf writeLongLE(long value) {
      throw reject();
   }

   public ByteBuf writeMedium(int value) {
      throw reject();
   }

   public ByteBuf writeMediumLE(int value) {
      throw reject();
   }

   public ByteBuf writeZero(int length) {
      throw reject();
   }

   public int writerIndex() {
      return this.buffer.writerIndex();
   }

   public ByteBuf writerIndex(int writerIndex) {
      throw reject();
   }

   public ByteBuf writeShort(int value) {
      throw reject();
   }

   public ByteBuf writeShortLE(int value) {
      throw reject();
   }

   public ByteBuf writeChar(int value) {
      throw reject();
   }

   public ByteBuf writeFloat(float value) {
      throw reject();
   }

   public ByteBuf writeDouble(double value) {
      throw reject();
   }

   public int setCharSequence(int index, CharSequence sequence, Charset charset) {
      throw reject();
   }

   public int writeCharSequence(CharSequence sequence, Charset charset) {
      throw reject();
   }

   private void checkIndex(int index, int length) {
      if (index + length > this.buffer.writerIndex()) {
         throw REPLAY;
      }
   }

   private void checkReadableBytes(int readableBytes) {
      if (this.buffer.readableBytes() < readableBytes) {
         throw REPLAY;
      }
   }

   public ByteBuf discardSomeReadBytes() {
      throw reject();
   }

   public int refCnt() {
      return this.buffer.refCnt();
   }

   public ByteBuf retain() {
      throw reject();
   }

   public ByteBuf retain(int increment) {
      throw reject();
   }

   public ByteBuf touch() {
      this.buffer.touch();
      return this;
   }

   public ByteBuf touch(Object hint) {
      this.buffer.touch(hint);
      return this;
   }

   public boolean release() {
      throw reject();
   }

   public boolean release(int decrement) {
      throw reject();
   }

   public ByteBuf unwrap() {
      throw reject();
   }

   private static UnsupportedOperationException reject() {
      return new UnsupportedOperationException("not a replayable operation");
   }

   static {
      REPLAY = ReplayingDecoder.REPLAY;
      EMPTY_BUFFER = new ReplayingDecoderByteBuf(Unpooled.EMPTY_BUFFER);
      EMPTY_BUFFER.terminate();
   }
}
