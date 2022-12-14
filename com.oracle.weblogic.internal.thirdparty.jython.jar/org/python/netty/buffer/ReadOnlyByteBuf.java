package org.python.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import org.python.netty.util.ByteProcessor;

/** @deprecated */
@Deprecated
public class ReadOnlyByteBuf extends AbstractDerivedByteBuf {
   private final ByteBuf buffer;

   public ReadOnlyByteBuf(ByteBuf buffer) {
      super(buffer.maxCapacity());
      if (!(buffer instanceof ReadOnlyByteBuf) && !(buffer instanceof DuplicatedByteBuf)) {
         this.buffer = buffer;
      } else {
         this.buffer = buffer.unwrap();
      }

      this.setIndex(buffer.readerIndex(), buffer.writerIndex());
   }

   public boolean isReadOnly() {
      return true;
   }

   public boolean isWritable() {
      return false;
   }

   public boolean isWritable(int numBytes) {
      return false;
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

   public boolean hasArray() {
      return false;
   }

   public byte[] array() {
      throw new ReadOnlyBufferException();
   }

   public int arrayOffset() {
      throw new ReadOnlyBufferException();
   }

   public boolean hasMemoryAddress() {
      return false;
   }

   public long memoryAddress() {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf discardReadBytes() {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setBytes(int index, ByteBuffer src) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setByte(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   protected void _setByte(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setShort(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   protected void _setShort(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setShortLE(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   protected void _setShortLE(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setMedium(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   protected void _setMedium(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setMediumLE(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   protected void _setMediumLE(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setInt(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   protected void _setInt(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setIntLE(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   protected void _setIntLE(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setLong(int index, long value) {
      throw new ReadOnlyBufferException();
   }

   protected void _setLong(int index, long value) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setLongLE(int index, long value) {
      throw new ReadOnlyBufferException();
   }

   protected void _setLongLE(int index, long value) {
      throw new ReadOnlyBufferException();
   }

   public int setBytes(int index, InputStream in, int length) {
      throw new ReadOnlyBufferException();
   }

   public int setBytes(int index, ScatteringByteChannel in, int length) {
      throw new ReadOnlyBufferException();
   }

   public int setBytes(int index, FileChannel in, long position, int length) {
      throw new ReadOnlyBufferException();
   }

   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
      return this.unwrap().getBytes(index, out, length);
   }

   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
      return this.unwrap().getBytes(index, out, position, length);
   }

   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
      this.unwrap().getBytes(index, out, length);
      return this;
   }

   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
      this.unwrap().getBytes(index, dst, dstIndex, length);
      return this;
   }

   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
      this.unwrap().getBytes(index, dst, dstIndex, length);
      return this;
   }

   public ByteBuf getBytes(int index, ByteBuffer dst) {
      this.unwrap().getBytes(index, dst);
      return this;
   }

   public ByteBuf duplicate() {
      return new ReadOnlyByteBuf(this);
   }

   public ByteBuf copy(int index, int length) {
      return this.unwrap().copy(index, length);
   }

   public ByteBuf slice(int index, int length) {
      return Unpooled.unmodifiableBuffer(this.unwrap().slice(index, length));
   }

   public byte getByte(int index) {
      return this.unwrap().getByte(index);
   }

   protected byte _getByte(int index) {
      return this.unwrap().getByte(index);
   }

   public short getShort(int index) {
      return this.unwrap().getShort(index);
   }

   protected short _getShort(int index) {
      return this.unwrap().getShort(index);
   }

   public short getShortLE(int index) {
      return this.unwrap().getShortLE(index);
   }

   protected short _getShortLE(int index) {
      return this.unwrap().getShortLE(index);
   }

   public int getUnsignedMedium(int index) {
      return this.unwrap().getUnsignedMedium(index);
   }

   protected int _getUnsignedMedium(int index) {
      return this.unwrap().getUnsignedMedium(index);
   }

   public int getUnsignedMediumLE(int index) {
      return this.unwrap().getUnsignedMediumLE(index);
   }

   protected int _getUnsignedMediumLE(int index) {
      return this.unwrap().getUnsignedMediumLE(index);
   }

   public int getInt(int index) {
      return this.unwrap().getInt(index);
   }

   protected int _getInt(int index) {
      return this.unwrap().getInt(index);
   }

   public int getIntLE(int index) {
      return this.unwrap().getIntLE(index);
   }

   protected int _getIntLE(int index) {
      return this.unwrap().getIntLE(index);
   }

   public long getLong(int index) {
      return this.unwrap().getLong(index);
   }

   protected long _getLong(int index) {
      return this.unwrap().getLong(index);
   }

   public long getLongLE(int index) {
      return this.unwrap().getLongLE(index);
   }

   protected long _getLongLE(int index) {
      return this.unwrap().getLongLE(index);
   }

   public int nioBufferCount() {
      return this.unwrap().nioBufferCount();
   }

   public ByteBuffer nioBuffer(int index, int length) {
      return this.unwrap().nioBuffer(index, length).asReadOnlyBuffer();
   }

   public ByteBuffer[] nioBuffers(int index, int length) {
      return this.unwrap().nioBuffers(index, length);
   }

   public int forEachByte(int index, int length, ByteProcessor processor) {
      return this.unwrap().forEachByte(index, length, processor);
   }

   public int forEachByteDesc(int index, int length, ByteProcessor processor) {
      return this.unwrap().forEachByteDesc(index, length, processor);
   }

   public int capacity() {
      return this.unwrap().capacity();
   }

   public ByteBuf capacity(int newCapacity) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf asReadOnly() {
      return this;
   }
}
