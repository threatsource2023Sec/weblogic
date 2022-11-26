package org.python.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import org.python.netty.util.ByteProcessor;
import org.python.netty.util.CharsetUtil;
import org.python.netty.util.internal.MathUtil;

abstract class AbstractUnpooledSlicedByteBuf extends AbstractDerivedByteBuf {
   private final ByteBuf buffer;
   private final int adjustment;

   AbstractUnpooledSlicedByteBuf(ByteBuf buffer, int index, int length) {
      super(length);
      checkSliceOutOfBounds(index, length, buffer);
      if (buffer instanceof AbstractUnpooledSlicedByteBuf) {
         this.buffer = ((AbstractUnpooledSlicedByteBuf)buffer).buffer;
         this.adjustment = ((AbstractUnpooledSlicedByteBuf)buffer).adjustment + index;
      } else if (buffer instanceof DuplicatedByteBuf) {
         this.buffer = buffer.unwrap();
         this.adjustment = index;
      } else {
         this.buffer = buffer;
         this.adjustment = index;
      }

      this.initLength(length);
      this.writerIndex(length);
   }

   void initLength(int length) {
   }

   int length() {
      return this.capacity();
   }

   public ByteBuf unwrap() {
      return this.buffer;
   }

   public ByteBufAllocator alloc() {
      return this.unwrap().alloc();
   }

   /** @deprecated */
   @Deprecated
   public ByteOrder order() {
      return this.unwrap().order();
   }

   public boolean isDirect() {
      return this.unwrap().isDirect();
   }

   public ByteBuf capacity(int newCapacity) {
      throw new UnsupportedOperationException("sliced buffer");
   }

   public boolean hasArray() {
      return this.unwrap().hasArray();
   }

   public byte[] array() {
      return this.unwrap().array();
   }

   public int arrayOffset() {
      return this.idx(this.unwrap().arrayOffset());
   }

   public boolean hasMemoryAddress() {
      return this.unwrap().hasMemoryAddress();
   }

   public long memoryAddress() {
      return this.unwrap().memoryAddress() + (long)this.adjustment;
   }

   public byte getByte(int index) {
      this.checkIndex0(index, 1);
      return this.unwrap().getByte(this.idx(index));
   }

   protected byte _getByte(int index) {
      return this.unwrap().getByte(this.idx(index));
   }

   public short getShort(int index) {
      this.checkIndex0(index, 2);
      return this.unwrap().getShort(this.idx(index));
   }

   protected short _getShort(int index) {
      return this.unwrap().getShort(this.idx(index));
   }

   public short getShortLE(int index) {
      this.checkIndex0(index, 2);
      return this.unwrap().getShortLE(this.idx(index));
   }

   protected short _getShortLE(int index) {
      return this.unwrap().getShortLE(this.idx(index));
   }

   public int getUnsignedMedium(int index) {
      this.checkIndex0(index, 3);
      return this.unwrap().getUnsignedMedium(this.idx(index));
   }

   protected int _getUnsignedMedium(int index) {
      return this.unwrap().getUnsignedMedium(this.idx(index));
   }

   public int getUnsignedMediumLE(int index) {
      this.checkIndex0(index, 3);
      return this.unwrap().getUnsignedMediumLE(this.idx(index));
   }

   protected int _getUnsignedMediumLE(int index) {
      return this.unwrap().getUnsignedMediumLE(this.idx(index));
   }

   public int getInt(int index) {
      this.checkIndex0(index, 4);
      return this.unwrap().getInt(this.idx(index));
   }

   protected int _getInt(int index) {
      return this.unwrap().getInt(this.idx(index));
   }

   public int getIntLE(int index) {
      this.checkIndex0(index, 4);
      return this.unwrap().getIntLE(this.idx(index));
   }

   protected int _getIntLE(int index) {
      return this.unwrap().getIntLE(this.idx(index));
   }

   public long getLong(int index) {
      this.checkIndex0(index, 8);
      return this.unwrap().getLong(this.idx(index));
   }

   protected long _getLong(int index) {
      return this.unwrap().getLong(this.idx(index));
   }

   public long getLongLE(int index) {
      this.checkIndex0(index, 8);
      return this.unwrap().getLongLE(this.idx(index));
   }

   protected long _getLongLE(int index) {
      return this.unwrap().getLongLE(this.idx(index));
   }

   public ByteBuf duplicate() {
      return this.unwrap().duplicate().setIndex(this.idx(this.readerIndex()), this.idx(this.writerIndex()));
   }

   public ByteBuf copy(int index, int length) {
      this.checkIndex0(index, length);
      return this.unwrap().copy(this.idx(index), length);
   }

   public ByteBuf slice(int index, int length) {
      this.checkIndex0(index, length);
      return this.unwrap().slice(this.idx(index), length);
   }

   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
      this.checkIndex0(index, length);
      this.unwrap().getBytes(this.idx(index), dst, dstIndex, length);
      return this;
   }

   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
      this.checkIndex0(index, length);
      this.unwrap().getBytes(this.idx(index), dst, dstIndex, length);
      return this;
   }

   public ByteBuf getBytes(int index, ByteBuffer dst) {
      this.checkIndex0(index, dst.remaining());
      this.unwrap().getBytes(this.idx(index), dst);
      return this;
   }

   public ByteBuf setByte(int index, int value) {
      this.checkIndex0(index, 1);
      this.unwrap().setByte(this.idx(index), value);
      return this;
   }

   public CharSequence getCharSequence(int index, int length, Charset charset) {
      this.checkIndex0(index, length);
      return this.buffer.getCharSequence(this.idx(index), length, charset);
   }

   protected void _setByte(int index, int value) {
      this.unwrap().setByte(this.idx(index), value);
   }

   public ByteBuf setShort(int index, int value) {
      this.checkIndex0(index, 2);
      this.unwrap().setShort(this.idx(index), value);
      return this;
   }

   protected void _setShort(int index, int value) {
      this.unwrap().setShort(this.idx(index), value);
   }

   public ByteBuf setShortLE(int index, int value) {
      this.checkIndex0(index, 2);
      this.unwrap().setShortLE(this.idx(index), value);
      return this;
   }

   protected void _setShortLE(int index, int value) {
      this.unwrap().setShortLE(this.idx(index), value);
   }

   public ByteBuf setMedium(int index, int value) {
      this.checkIndex0(index, 3);
      this.unwrap().setMedium(this.idx(index), value);
      return this;
   }

   protected void _setMedium(int index, int value) {
      this.unwrap().setMedium(this.idx(index), value);
   }

   public ByteBuf setMediumLE(int index, int value) {
      this.checkIndex0(index, 3);
      this.unwrap().setMediumLE(this.idx(index), value);
      return this;
   }

   protected void _setMediumLE(int index, int value) {
      this.unwrap().setMediumLE(this.idx(index), value);
   }

   public ByteBuf setInt(int index, int value) {
      this.checkIndex0(index, 4);
      this.unwrap().setInt(this.idx(index), value);
      return this;
   }

   protected void _setInt(int index, int value) {
      this.unwrap().setInt(this.idx(index), value);
   }

   public ByteBuf setIntLE(int index, int value) {
      this.checkIndex0(index, 4);
      this.unwrap().setIntLE(this.idx(index), value);
      return this;
   }

   protected void _setIntLE(int index, int value) {
      this.unwrap().setIntLE(this.idx(index), value);
   }

   public ByteBuf setLong(int index, long value) {
      this.checkIndex0(index, 8);
      this.unwrap().setLong(this.idx(index), value);
      return this;
   }

   protected void _setLong(int index, long value) {
      this.unwrap().setLong(this.idx(index), value);
   }

   public ByteBuf setLongLE(int index, long value) {
      this.checkIndex0(index, 8);
      this.unwrap().setLongLE(this.idx(index), value);
      return this;
   }

   protected void _setLongLE(int index, long value) {
      this.unwrap().setLongLE(this.idx(index), value);
   }

   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
      this.checkIndex0(index, length);
      this.unwrap().setBytes(this.idx(index), src, srcIndex, length);
      return this;
   }

   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
      this.checkIndex0(index, length);
      this.unwrap().setBytes(this.idx(index), src, srcIndex, length);
      return this;
   }

   public ByteBuf setBytes(int index, ByteBuffer src) {
      this.checkIndex0(index, src.remaining());
      this.unwrap().setBytes(this.idx(index), src);
      return this;
   }

   public int setCharSequence(int index, CharSequence sequence, Charset charset) {
      if (charset.equals(CharsetUtil.UTF_8)) {
         this.checkIndex0(index, ByteBufUtil.utf8MaxBytes(sequence));
         return ByteBufUtil.writeUtf8(this, this.idx(index), sequence, sequence.length());
      } else if (charset.equals(CharsetUtil.US_ASCII)) {
         int len = sequence.length();
         this.checkIndex0(index, len);
         return ByteBufUtil.writeAscii(this, this.idx(index), sequence, len);
      } else {
         byte[] bytes = sequence.toString().getBytes(charset);
         this.checkIndex0(index, bytes.length);
         this.buffer.setBytes(this.idx(index), bytes);
         return bytes.length;
      }
   }

   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
      this.checkIndex0(index, length);
      this.unwrap().getBytes(this.idx(index), out, length);
      return this;
   }

   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
      this.checkIndex0(index, length);
      return this.unwrap().getBytes(this.idx(index), out, length);
   }

   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
      this.checkIndex0(index, length);
      return this.unwrap().getBytes(this.idx(index), out, position, length);
   }

   public int setBytes(int index, InputStream in, int length) throws IOException {
      this.checkIndex0(index, length);
      return this.unwrap().setBytes(this.idx(index), in, length);
   }

   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
      this.checkIndex0(index, length);
      return this.unwrap().setBytes(this.idx(index), in, length);
   }

   public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
      this.checkIndex0(index, length);
      return this.unwrap().setBytes(this.idx(index), in, position, length);
   }

   public int nioBufferCount() {
      return this.unwrap().nioBufferCount();
   }

   public ByteBuffer nioBuffer(int index, int length) {
      this.checkIndex0(index, length);
      return this.unwrap().nioBuffer(this.idx(index), length);
   }

   public ByteBuffer[] nioBuffers(int index, int length) {
      this.checkIndex0(index, length);
      return this.unwrap().nioBuffers(this.idx(index), length);
   }

   public int forEachByte(int index, int length, ByteProcessor processor) {
      this.checkIndex0(index, length);
      int ret = this.unwrap().forEachByte(this.idx(index), length, processor);
      return ret >= this.adjustment ? ret - this.adjustment : -1;
   }

   public int forEachByteDesc(int index, int length, ByteProcessor processor) {
      this.checkIndex0(index, length);
      int ret = this.unwrap().forEachByteDesc(this.idx(index), length, processor);
      return ret >= this.adjustment ? ret - this.adjustment : -1;
   }

   final int idx(int index) {
      return index + this.adjustment;
   }

   static void checkSliceOutOfBounds(int index, int length, ByteBuf buffer) {
      if (MathUtil.isOutOfBounds(index, length, buffer.capacity())) {
         throw new IndexOutOfBoundsException(buffer + ".slice(" + index + ", " + length + ')');
      }
   }
}
