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
import java.util.Collections;
import org.python.netty.util.internal.EmptyArrays;
import org.python.netty.util.internal.RecyclableArrayList;

final class FixedCompositeByteBuf extends AbstractReferenceCountedByteBuf {
   private static final ByteBuf[] EMPTY;
   private final int nioBufferCount;
   private final int capacity;
   private final ByteBufAllocator allocator;
   private final ByteOrder order;
   private final Object[] buffers;
   private final boolean direct;

   FixedCompositeByteBuf(ByteBufAllocator allocator, ByteBuf... buffers) {
      super(Integer.MAX_VALUE);
      if (buffers.length == 0) {
         this.buffers = EMPTY;
         this.order = ByteOrder.BIG_ENDIAN;
         this.nioBufferCount = 1;
         this.capacity = 0;
         this.direct = false;
      } else {
         ByteBuf b = buffers[0];
         this.buffers = new Object[buffers.length];
         this.buffers[0] = b;
         boolean direct = true;
         int nioBufferCount = b.nioBufferCount();
         int capacity = b.readableBytes();
         this.order = b.order();

         for(int i = 1; i < buffers.length; ++i) {
            b = buffers[i];
            if (buffers[i].order() != this.order) {
               throw new IllegalArgumentException("All ByteBufs need to have same ByteOrder");
            }

            nioBufferCount += b.nioBufferCount();
            capacity += b.readableBytes();
            if (!b.isDirect()) {
               direct = false;
            }

            this.buffers[i] = b;
         }

         this.nioBufferCount = nioBufferCount;
         this.capacity = capacity;
         this.direct = direct;
      }

      this.setIndex(0, this.capacity());
      this.allocator = allocator;
   }

   public boolean isWritable() {
      return false;
   }

   public boolean isWritable(int size) {
      return false;
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

   protected void _setShortLE(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setMedium(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   protected void _setMedium(int index, int value) {
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

   protected void _setIntLE(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   public ByteBuf setLong(int index, long value) {
      throw new ReadOnlyBufferException();
   }

   protected void _setLong(int index, long value) {
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

   public int capacity() {
      return this.capacity;
   }

   public int maxCapacity() {
      return this.capacity;
   }

   public ByteBuf capacity(int newCapacity) {
      throw new ReadOnlyBufferException();
   }

   public ByteBufAllocator alloc() {
      return this.allocator;
   }

   public ByteOrder order() {
      return this.order;
   }

   public ByteBuf unwrap() {
      return null;
   }

   public boolean isDirect() {
      return this.direct;
   }

   private Component findComponent(int index) {
      int readable = 0;

      for(int i = 0; i < this.buffers.length; ++i) {
         Component comp = null;
         Object obj = this.buffers[i];
         ByteBuf b;
         boolean isBuffer;
         if (obj instanceof ByteBuf) {
            b = (ByteBuf)obj;
            isBuffer = true;
         } else {
            comp = (Component)obj;
            b = comp.buf;
            isBuffer = false;
         }

         readable += b.readableBytes();
         if (index < readable) {
            if (isBuffer) {
               comp = new Component(i, readable - b.readableBytes(), b);
               this.buffers[i] = comp;
            }

            return comp;
         }
      }

      throw new IllegalStateException();
   }

   private ByteBuf buffer(int i) {
      Object obj = this.buffers[i];
      return obj instanceof ByteBuf ? (ByteBuf)obj : ((Component)obj).buf;
   }

   public byte getByte(int index) {
      return this._getByte(index);
   }

   protected byte _getByte(int index) {
      Component c = this.findComponent(index);
      return c.buf.getByte(index - c.offset);
   }

   protected short _getShort(int index) {
      Component c = this.findComponent(index);
      if (index + 2 <= c.endOffset) {
         return c.buf.getShort(index - c.offset);
      } else {
         return this.order() == ByteOrder.BIG_ENDIAN ? (short)((this._getByte(index) & 255) << 8 | this._getByte(index + 1) & 255) : (short)(this._getByte(index) & 255 | (this._getByte(index + 1) & 255) << 8);
      }
   }

   protected short _getShortLE(int index) {
      Component c = this.findComponent(index);
      if (index + 2 <= c.endOffset) {
         return c.buf.getShortLE(index - c.offset);
      } else {
         return this.order() == ByteOrder.BIG_ENDIAN ? (short)(this._getByte(index) & 255 | (this._getByte(index + 1) & 255) << 8) : (short)((this._getByte(index) & 255) << 8 | this._getByte(index + 1) & 255);
      }
   }

   protected int _getUnsignedMedium(int index) {
      Component c = this.findComponent(index);
      if (index + 3 <= c.endOffset) {
         return c.buf.getUnsignedMedium(index - c.offset);
      } else {
         return this.order() == ByteOrder.BIG_ENDIAN ? (this._getShort(index) & '\uffff') << 8 | this._getByte(index + 2) & 255 : this._getShort(index) & '\uffff' | (this._getByte(index + 2) & 255) << 16;
      }
   }

   protected int _getUnsignedMediumLE(int index) {
      Component c = this.findComponent(index);
      if (index + 3 <= c.endOffset) {
         return c.buf.getUnsignedMediumLE(index - c.offset);
      } else {
         return this.order() == ByteOrder.BIG_ENDIAN ? this._getShortLE(index) & '\uffff' | (this._getByte(index + 2) & 255) << 16 : (this._getShortLE(index) & '\uffff') << 8 | this._getByte(index + 2) & 255;
      }
   }

   protected int _getInt(int index) {
      Component c = this.findComponent(index);
      if (index + 4 <= c.endOffset) {
         return c.buf.getInt(index - c.offset);
      } else {
         return this.order() == ByteOrder.BIG_ENDIAN ? (this._getShort(index) & '\uffff') << 16 | this._getShort(index + 2) & '\uffff' : this._getShort(index) & '\uffff' | (this._getShort(index + 2) & '\uffff') << 16;
      }
   }

   protected int _getIntLE(int index) {
      Component c = this.findComponent(index);
      if (index + 4 <= c.endOffset) {
         return c.buf.getIntLE(index - c.offset);
      } else {
         return this.order() == ByteOrder.BIG_ENDIAN ? this._getShortLE(index) & '\uffff' | (this._getShortLE(index + 2) & '\uffff') << 16 : (this._getShortLE(index) & '\uffff') << 16 | this._getShortLE(index + 2) & '\uffff';
      }
   }

   protected long _getLong(int index) {
      Component c = this.findComponent(index);
      if (index + 8 <= c.endOffset) {
         return c.buf.getLong(index - c.offset);
      } else {
         return this.order() == ByteOrder.BIG_ENDIAN ? ((long)this._getInt(index) & 4294967295L) << 32 | (long)this._getInt(index + 4) & 4294967295L : (long)this._getInt(index) & 4294967295L | ((long)this._getInt(index + 4) & 4294967295L) << 32;
      }
   }

   protected long _getLongLE(int index) {
      Component c = this.findComponent(index);
      if (index + 8 <= c.endOffset) {
         return c.buf.getLongLE(index - c.offset);
      } else {
         return this.order() == ByteOrder.BIG_ENDIAN ? (long)this._getIntLE(index) & 4294967295L | ((long)this._getIntLE(index + 4) & 4294967295L) << 32 : ((long)this._getIntLE(index) & 4294967295L) << 32 | (long)this._getIntLE(index + 4) & 4294967295L;
      }
   }

   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
      this.checkDstIndex(index, length, dstIndex, dst.length);
      if (length == 0) {
         return this;
      } else {
         Component c = this.findComponent(index);
         int i = c.index;
         int adjustment = c.offset;
         ByteBuf s = c.buf;

         while(true) {
            int localLength = Math.min(length, s.readableBytes() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
            length -= localLength;
            adjustment += s.readableBytes();
            if (length <= 0) {
               return this;
            }

            ++i;
            s = this.buffer(i);
         }
      }
   }

   public ByteBuf getBytes(int index, ByteBuffer dst) {
      int limit = dst.limit();
      int length = dst.remaining();
      this.checkIndex(index, length);
      if (length == 0) {
         return this;
      } else {
         try {
            Component c = this.findComponent(index);
            int i = c.index;
            int adjustment = c.offset;
            ByteBuf s = c.buf;

            while(true) {
               int localLength = Math.min(length, s.readableBytes() - (index - adjustment));
               dst.limit(dst.position() + localLength);
               s.getBytes(index - adjustment, dst);
               index += localLength;
               length -= localLength;
               adjustment += s.readableBytes();
               if (length <= 0) {
                  return this;
               }

               ++i;
               s = this.buffer(i);
            }
         } finally {
            dst.limit(limit);
         }
      }
   }

   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
      this.checkDstIndex(index, length, dstIndex, dst.capacity());
      if (length == 0) {
         return this;
      } else {
         Component c = this.findComponent(index);
         int i = c.index;
         int adjustment = c.offset;
         ByteBuf s = c.buf;

         while(true) {
            int localLength = Math.min(length, s.readableBytes() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
            length -= localLength;
            adjustment += s.readableBytes();
            if (length <= 0) {
               return this;
            }

            ++i;
            s = this.buffer(i);
         }
      }
   }

   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
      int count = this.nioBufferCount();
      if (count == 1) {
         return out.write(this.internalNioBuffer(index, length));
      } else {
         long writtenBytes = out.write(this.nioBuffers(index, length));
         return writtenBytes > 2147483647L ? Integer.MAX_VALUE : (int)writtenBytes;
      }
   }

   public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
      int count = this.nioBufferCount();
      if (count == 1) {
         return out.write(this.internalNioBuffer(index, length), position);
      } else {
         long writtenBytes = 0L;
         ByteBuffer[] var9 = this.nioBuffers(index, length);
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            ByteBuffer buf = var9[var11];
            writtenBytes += (long)out.write(buf, position + writtenBytes);
         }

         return writtenBytes > 2147483647L ? Integer.MAX_VALUE : (int)writtenBytes;
      }
   }

   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
      this.checkIndex(index, length);
      if (length == 0) {
         return this;
      } else {
         Component c = this.findComponent(index);
         int i = c.index;
         int adjustment = c.offset;
         ByteBuf s = c.buf;

         while(true) {
            int localLength = Math.min(length, s.readableBytes() - (index - adjustment));
            s.getBytes(index - adjustment, out, localLength);
            index += localLength;
            length -= localLength;
            adjustment += s.readableBytes();
            if (length <= 0) {
               return this;
            }

            ++i;
            s = this.buffer(i);
         }
      }
   }

   public ByteBuf copy(int index, int length) {
      this.checkIndex(index, length);
      boolean release = true;
      ByteBuf buf = this.alloc().buffer(length);

      ByteBuf var5;
      try {
         buf.writeBytes((ByteBuf)this, index, length);
         release = false;
         var5 = buf;
      } finally {
         if (release) {
            buf.release();
         }

      }

      return var5;
   }

   public int nioBufferCount() {
      return this.nioBufferCount;
   }

   public ByteBuffer nioBuffer(int index, int length) {
      this.checkIndex(index, length);
      if (this.buffers.length == 1) {
         ByteBuf buf = this.buffer(0);
         if (buf.nioBufferCount() == 1) {
            return buf.nioBuffer(index, length);
         }
      }

      ByteBuffer merged = ByteBuffer.allocate(length).order(this.order());
      ByteBuffer[] buffers = this.nioBuffers(index, length);

      for(int i = 0; i < buffers.length; ++i) {
         merged.put(buffers[i]);
      }

      merged.flip();
      return merged;
   }

   public ByteBuffer internalNioBuffer(int index, int length) {
      if (this.buffers.length == 1) {
         return this.buffer(0).internalNioBuffer(index, length);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public ByteBuffer[] nioBuffers(int index, int length) {
      this.checkIndex(index, length);
      if (length == 0) {
         return EmptyArrays.EMPTY_BYTE_BUFFERS;
      } else {
         RecyclableArrayList array = RecyclableArrayList.newInstance(this.buffers.length);

         try {
            Component c = this.findComponent(index);
            int i = c.index;
            int adjustment = c.offset;
            ByteBuf s = c.buf;

            while(true) {
               int localLength = Math.min(length, s.readableBytes() - (index - adjustment));
               switch (s.nioBufferCount()) {
                  case 0:
                     throw new UnsupportedOperationException();
                  case 1:
                     array.add(s.nioBuffer(index - adjustment, localLength));
                     break;
                  default:
                     Collections.addAll(array, s.nioBuffers(index - adjustment, localLength));
               }

               index += localLength;
               length -= localLength;
               adjustment += s.readableBytes();
               if (length <= 0) {
                  ByteBuffer[] var12 = (ByteBuffer[])array.toArray(new ByteBuffer[array.size()]);
                  return var12;
               }

               ++i;
               s = this.buffer(i);
            }
         } finally {
            array.recycle();
         }
      }
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

   protected void deallocate() {
      for(int i = 0; i < this.buffers.length; ++i) {
         this.buffer(i).release();
      }

   }

   public String toString() {
      String result = super.toString();
      result = result.substring(0, result.length() - 1);
      return result + ", components=" + this.buffers.length + ')';
   }

   static {
      EMPTY = new ByteBuf[]{Unpooled.EMPTY_BUFFER};
   }

   private static final class Component {
      private final int index;
      private final int offset;
      private final ByteBuf buf;
      private final int endOffset;

      Component(int index, int offset, ByteBuf buf) {
         this.index = index;
         this.offset = offset;
         this.endOffset = offset + buf.readableBytes();
         this.buf = buf;
      }
   }
}
