package org.python.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import org.python.netty.util.ByteProcessor;
import org.python.netty.util.Recycler;

final class PooledDuplicatedByteBuf extends AbstractPooledDerivedByteBuf {
   private static final Recycler RECYCLER = new Recycler() {
      protected PooledDuplicatedByteBuf newObject(Recycler.Handle handle) {
         return new PooledDuplicatedByteBuf(handle);
      }
   };

   static PooledDuplicatedByteBuf newInstance(AbstractByteBuf unwrapped, ByteBuf wrapped, int readerIndex, int writerIndex) {
      PooledDuplicatedByteBuf duplicate = (PooledDuplicatedByteBuf)RECYCLER.get();
      duplicate.init(unwrapped, wrapped, readerIndex, writerIndex, unwrapped.maxCapacity());
      duplicate.markReaderIndex();
      duplicate.markWriterIndex();
      return duplicate;
   }

   private PooledDuplicatedByteBuf(Recycler.Handle handle) {
      super(handle);
   }

   public int capacity() {
      return this.unwrap().capacity();
   }

   public ByteBuf capacity(int newCapacity) {
      this.unwrap().capacity(newCapacity);
      return this;
   }

   public int arrayOffset() {
      return this.unwrap().arrayOffset();
   }

   public long memoryAddress() {
      return this.unwrap().memoryAddress();
   }

   public ByteBuffer nioBuffer(int index, int length) {
      return this.unwrap().nioBuffer(index, length);
   }

   public ByteBuffer[] nioBuffers(int index, int length) {
      return this.unwrap().nioBuffers(index, length);
   }

   public ByteBuf copy(int index, int length) {
      return this.unwrap().copy(index, length);
   }

   public ByteBuf retainedSlice(int index, int length) {
      return PooledSlicedByteBuf.newInstance(this.unwrap(), this, index, length);
   }

   public ByteBuf duplicate() {
      return this.duplicate0().setIndex(this.readerIndex(), this.writerIndex());
   }

   public ByteBuf retainedDuplicate() {
      return newInstance(this.unwrap(), this, this.readerIndex(), this.writerIndex());
   }

   public byte getByte(int index) {
      return this.unwrap().getByte(index);
   }

   protected byte _getByte(int index) {
      return this.unwrap()._getByte(index);
   }

   public short getShort(int index) {
      return this.unwrap().getShort(index);
   }

   protected short _getShort(int index) {
      return this.unwrap()._getShort(index);
   }

   public short getShortLE(int index) {
      return this.unwrap().getShortLE(index);
   }

   protected short _getShortLE(int index) {
      return this.unwrap()._getShortLE(index);
   }

   public int getUnsignedMedium(int index) {
      return this.unwrap().getUnsignedMedium(index);
   }

   protected int _getUnsignedMedium(int index) {
      return this.unwrap()._getUnsignedMedium(index);
   }

   public int getUnsignedMediumLE(int index) {
      return this.unwrap().getUnsignedMediumLE(index);
   }

   protected int _getUnsignedMediumLE(int index) {
      return this.unwrap()._getUnsignedMediumLE(index);
   }

   public int getInt(int index) {
      return this.unwrap().getInt(index);
   }

   protected int _getInt(int index) {
      return this.unwrap()._getInt(index);
   }

   public int getIntLE(int index) {
      return this.unwrap().getIntLE(index);
   }

   protected int _getIntLE(int index) {
      return this.unwrap()._getIntLE(index);
   }

   public long getLong(int index) {
      return this.unwrap().getLong(index);
   }

   protected long _getLong(int index) {
      return this.unwrap()._getLong(index);
   }

   public long getLongLE(int index) {
      return this.unwrap().getLongLE(index);
   }

   protected long _getLongLE(int index) {
      return this.unwrap()._getLongLE(index);
   }

   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
      this.unwrap().getBytes(index, dst, dstIndex, length);
      return this;
   }

   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
      this.unwrap().getBytes(index, dst, dstIndex, length);
      return this;
   }

   public ByteBuf getBytes(int index, ByteBuffer dst) {
      this.unwrap().getBytes(index, (ByteBuffer)dst);
      return this;
   }

   public ByteBuf setByte(int index, int value) {
      this.unwrap().setByte(index, value);
      return this;
   }

   protected void _setByte(int index, int value) {
      this.unwrap()._setByte(index, value);
   }

   public ByteBuf setShort(int index, int value) {
      this.unwrap().setShort(index, value);
      return this;
   }

   protected void _setShort(int index, int value) {
      this.unwrap()._setShort(index, value);
   }

   public ByteBuf setShortLE(int index, int value) {
      this.unwrap().setShortLE(index, value);
      return this;
   }

   protected void _setShortLE(int index, int value) {
      this.unwrap()._setShortLE(index, value);
   }

   public ByteBuf setMedium(int index, int value) {
      this.unwrap().setMedium(index, value);
      return this;
   }

   protected void _setMedium(int index, int value) {
      this.unwrap()._setMedium(index, value);
   }

   public ByteBuf setMediumLE(int index, int value) {
      this.unwrap().setMediumLE(index, value);
      return this;
   }

   protected void _setMediumLE(int index, int value) {
      this.unwrap()._setMediumLE(index, value);
   }

   public ByteBuf setInt(int index, int value) {
      this.unwrap().setInt(index, value);
      return this;
   }

   protected void _setInt(int index, int value) {
      this.unwrap()._setInt(index, value);
   }

   public ByteBuf setIntLE(int index, int value) {
      this.unwrap().setIntLE(index, value);
      return this;
   }

   protected void _setIntLE(int index, int value) {
      this.unwrap()._setIntLE(index, value);
   }

   public ByteBuf setLong(int index, long value) {
      this.unwrap().setLong(index, value);
      return this;
   }

   protected void _setLong(int index, long value) {
      this.unwrap()._setLong(index, value);
   }

   public ByteBuf setLongLE(int index, long value) {
      this.unwrap().setLongLE(index, value);
      return this;
   }

   protected void _setLongLE(int index, long value) {
      this.unwrap().setLongLE(index, value);
   }

   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
      this.unwrap().setBytes(index, src, srcIndex, length);
      return this;
   }

   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
      this.unwrap().setBytes(index, src, srcIndex, length);
      return this;
   }

   public ByteBuf setBytes(int index, ByteBuffer src) {
      this.unwrap().setBytes(index, (ByteBuffer)src);
      return this;
   }

   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
      this.unwrap().getBytes(index, out, length);
      return this;
   }

   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
      return this.unwrap().getBytes(index, out, length);
   }

   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
      return this.unwrap().getBytes(index, out, position, length);
   }

   public int setBytes(int index, InputStream in, int length) throws IOException {
      return this.unwrap().setBytes(index, in, length);
   }

   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
      return this.unwrap().setBytes(index, in, length);
   }

   public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
      return this.unwrap().setBytes(index, in, position, length);
   }

   public int forEachByte(int index, int length, ByteProcessor processor) {
      return this.unwrap().forEachByte(index, length, processor);
   }

   public int forEachByteDesc(int index, int length, ByteProcessor processor) {
      return this.unwrap().forEachByteDesc(index, length, processor);
   }

   // $FF: synthetic method
   PooledDuplicatedByteBuf(Recycler.Handle x0, Object x1) {
      this(x0);
   }
}
