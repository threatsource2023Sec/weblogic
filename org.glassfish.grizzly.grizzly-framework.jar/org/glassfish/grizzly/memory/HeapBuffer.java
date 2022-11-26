package org.glassfish.grizzly.memory;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.glassfish.grizzly.Buffer;

public class HeapBuffer implements Buffer {
   public static volatile boolean DEBUG_MODE = false;
   protected boolean allowBufferDispose = false;
   protected Exception disposeStackTrace;
   protected byte[] heap;
   protected int offset;
   protected int pos;
   protected int cap;
   protected int lim;
   protected int mark = -1;
   protected boolean readOnly;
   protected ByteOrder order;
   protected boolean bigEndian;
   protected ByteBuffer byteBuffer;

   protected HeapBuffer() {
      this.order = ByteOrder.BIG_ENDIAN;
      this.bigEndian = true;
   }

   protected HeapBuffer(byte[] heap, int offset, int cap) {
      this.order = ByteOrder.BIG_ENDIAN;
      this.bigEndian = true;
      this.heap = heap;
      this.offset = offset;
      this.cap = cap;
      this.lim = this.cap;
   }

   public final boolean isComposite() {
      return false;
   }

   public HeapBuffer prepend(Buffer header) {
      this.checkDispose();
      return this;
   }

   public void trim() {
      this.checkDispose();
      this.flip();
   }

   public void shrink() {
      this.checkDispose();
   }

   public final boolean allowBufferDispose() {
      return this.allowBufferDispose;
   }

   public final void allowBufferDispose(boolean allowBufferDispose) {
      this.allowBufferDispose = allowBufferDispose;
   }

   public final boolean tryDispose() {
      if (this.allowBufferDispose) {
         this.dispose();
         return true;
      } else {
         return false;
      }
   }

   public void dispose() {
      this.prepareDispose();
      this.byteBuffer = null;
      this.heap = null;
      this.pos = 0;
      this.offset = 0;
      this.lim = 0;
      this.cap = 0;
      this.order = ByteOrder.BIG_ENDIAN;
      this.bigEndian = true;
   }

   protected final void prepareDispose() {
      this.checkDispose();
      if (DEBUG_MODE) {
         HeapBuffer.DebugLogic.doDebug(this);
      }

   }

   public ByteBuffer underlying() {
      this.checkDispose();
      return this.toByteBuffer();
   }

   public final int capacity() {
      this.checkDispose();
      return this.cap;
   }

   public final int position() {
      this.checkDispose();
      return this.pos;
   }

   public final HeapBuffer position(int newPosition) {
      this.checkDispose();
      this.pos = newPosition;
      if (this.mark > this.pos) {
         this.mark = -1;
      }

      return this;
   }

   public final int limit() {
      this.checkDispose();
      return this.lim;
   }

   public final HeapBuffer limit(int newLimit) {
      this.checkDispose();
      this.lim = newLimit;
      if (this.mark > this.lim) {
         this.mark = -1;
      }

      return this;
   }

   public final HeapBuffer mark() {
      this.mark = this.pos;
      return this;
   }

   public final HeapBuffer reset() {
      int m = this.mark;
      if (m < 0) {
         throw new InvalidMarkException();
      } else {
         this.pos = m;
         return this;
      }
   }

   public final HeapBuffer clear() {
      this.pos = 0;
      this.lim = this.cap;
      this.mark = -1;
      return this;
   }

   public final HeapBuffer flip() {
      this.lim = this.pos;
      this.pos = 0;
      this.mark = -1;
      return this;
   }

   public final HeapBuffer rewind() {
      this.pos = 0;
      this.mark = -1;
      return this;
   }

   public final int remaining() {
      return this.lim - this.pos;
   }

   public final boolean hasRemaining() {
      return this.pos < this.lim;
   }

   public boolean isReadOnly() {
      return this.readOnly;
   }

   public final boolean isDirect() {
      return false;
   }

   public Buffer split(int splitPosition) {
      this.checkDispose();
      if (splitPosition >= 0 && splitPosition <= this.cap) {
         if (this.mark >= splitPosition) {
            this.mark = -1;
         }

         int oldPosition = this.pos;
         int oldLimit = this.lim;
         HeapBuffer ret = this.createHeapBuffer(splitPosition, this.cap - splitPosition);
         this.cap = splitPosition;
         if (oldPosition < splitPosition) {
            this.pos = oldPosition;
         } else {
            this.pos = this.cap;
            ret.position(oldPosition - splitPosition);
         }

         if (oldLimit < splitPosition) {
            this.lim = oldLimit;
            ret.limit(0);
         } else {
            this.lim = this.cap;
            ret.limit(oldLimit - splitPosition);
         }

         return ret;
      } else {
         throw new IllegalArgumentException("Invalid splitPosition value, should be 0 <= splitPosition <= capacity");
      }
   }

   public HeapBuffer slice() {
      return this.slice(this.pos, this.lim);
   }

   public HeapBuffer slice(int position, int limit) {
      this.checkDispose();
      return this.createHeapBuffer(position, limit - position);
   }

   public HeapBuffer duplicate() {
      this.checkDispose();
      HeapBuffer duplicate = this.createHeapBuffer(0, this.cap);
      duplicate.position(this.pos);
      duplicate.limit(this.lim);
      return duplicate;
   }

   public HeapBuffer asReadOnlyBuffer() {
      this.checkDispose();
      this.onShareHeap();
      HeapBuffer b = new ReadOnlyHeapBuffer(this.heap, this.offset, this.cap);
      b.pos = this.pos;
      b.lim = this.lim;
      return b;
   }

   public byte get() {
      if (!this.hasRemaining()) {
         throw new BufferUnderflowException();
      } else {
         return this.heap[this.offset + this.pos++];
      }
   }

   public byte get(int index) {
      if (index >= 0 && index < this.lim) {
         return this.heap[this.offset + index];
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public HeapBuffer put(byte b) {
      if (!this.hasRemaining()) {
         throw new BufferOverflowException();
      } else {
         this.heap[this.offset + this.pos++] = b;
         return this;
      }
   }

   public HeapBuffer put(int index, byte b) {
      if (index >= 0 && index < this.lim) {
         this.heap[this.offset + index] = b;
         return this;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public HeapBuffer get(byte[] dst) {
      return this.get((byte[])dst, 0, dst.length);
   }

   public HeapBuffer get(byte[] dst, int offset, int length) {
      if (this.remaining() < length) {
         throw new BufferUnderflowException();
      } else {
         System.arraycopy(this.heap, this.offset + this.pos, dst, offset, length);
         this.pos += length;
         return this;
      }
   }

   public HeapBuffer put(Buffer src) {
      this.put(src, src.position(), src.remaining());
      src.position(src.limit());
      return this;
   }

   public HeapBuffer put(Buffer src, int position, int length) {
      if (this.remaining() < length) {
         throw new BufferOverflowException();
      } else {
         int oldPos = src.position();
         int oldLim = src.limit();
         int thisPos = this.pos;
         Buffers.setPositionLimit(src, position, position + length);
         src.get(this.heap, this.offset + thisPos, length);
         Buffers.setPositionLimit(src, oldPos, oldLim);
         this.pos = thisPos + length;
         return this;
      }
   }

   public Buffer get(ByteBuffer dst) {
      int length = dst.remaining();
      dst.put(this.heap, this.offset + this.pos, length);
      this.pos += length;
      return this;
   }

   public Buffer get(ByteBuffer dst, int position, int length) {
      int oldPos = dst.position();
      int oldLim = dst.limit();

      try {
         Buffers.setPositionLimit(dst, position, position + length);
         dst.put(this.heap, this.offset + this.pos, length);
         this.pos += length;
      } finally {
         Buffers.setPositionLimit(dst, oldPos, oldLim);
      }

      return this;
   }

   public Buffer put(ByteBuffer src) {
      int length = src.remaining();
      src.get(this.heap, this.offset + this.pos, length);
      this.pos += length;
      return this;
   }

   public Buffer put(ByteBuffer src, int position, int length) {
      int oldPos = src.position();
      int oldLim = src.limit();

      try {
         Buffers.setPositionLimit(src, position, position + length);
         src.get(this.heap, this.offset + this.pos, length);
         this.pos += length;
      } finally {
         Buffers.setPositionLimit(src, oldPos, oldLim);
      }

      return this;
   }

   public static HeapBuffer wrap(byte[] heap) {
      return wrap(heap, 0, heap.length);
   }

   public static HeapBuffer wrap(byte[] heap, int off, int len) {
      return new HeapBuffer(heap, off, len);
   }

   public HeapBuffer put(byte[] src) {
      return this.put((byte[])src, 0, src.length);
   }

   public HeapBuffer put(byte[] src, int offset, int length) {
      if (this.remaining() < length) {
         throw new BufferOverflowException();
      } else {
         System.arraycopy(src, offset, this.heap, this.offset + this.pos, length);
         this.pos += length;
         return this;
      }
   }

   public HeapBuffer put8BitString(String s) {
      int len = s.length();
      if (this.remaining() < len) {
         throw new BufferOverflowException();
      } else {
         s.getBytes(0, len, this.heap, this.offset + this.pos);
         this.pos += len;
         return this;
      }
   }

   public HeapBuffer compact() {
      int length = this.remaining();
      System.arraycopy(this.heap, this.offset + this.pos, this.heap, this.offset, length);
      this.pos = length;
      this.lim = this.cap;
      return this;
   }

   public ByteOrder order() {
      return this.order;
   }

   public HeapBuffer order(ByteOrder bo) {
      this.order = bo;
      this.bigEndian = this.order == ByteOrder.BIG_ENDIAN;
      return this;
   }

   public char getChar() {
      if (this.remaining() < 2) {
         throw new BufferUnderflowException();
      } else {
         char c = Bits.getChar(this.heap, this.offset + this.pos, this.bigEndian);
         this.pos += 2;
         return c;
      }
   }

   public char getChar(int index) {
      if (index >= 0 && index < this.lim - 1) {
         return Bits.getChar(this.heap, this.offset + index, this.bigEndian);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public HeapBuffer putChar(char value) {
      if (this.remaining() < 2) {
         throw new BufferUnderflowException();
      } else {
         Bits.putChar(this.heap, this.offset + this.pos, value, this.bigEndian);
         this.pos += 2;
         return this;
      }
   }

   public HeapBuffer putChar(int index, char value) {
      if (index >= 0 && index < this.lim - 1) {
         Bits.putChar(this.heap, this.offset + index, value, this.bigEndian);
         return this;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public short getShort() {
      if (this.remaining() < 2) {
         throw new BufferUnderflowException();
      } else {
         short s = Bits.getShort(this.heap, this.offset + this.pos, this.bigEndian);
         this.pos += 2;
         return s;
      }
   }

   public short getShort(int index) {
      if (index >= 0 && index < this.lim - 1) {
         return Bits.getShort(this.heap, this.offset + index, this.bigEndian);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public HeapBuffer putShort(short value) {
      if (this.remaining() < 2) {
         throw new BufferUnderflowException();
      } else {
         Bits.putShort(this.heap, this.offset + this.pos, value, this.bigEndian);
         this.pos += 2;
         return this;
      }
   }

   public HeapBuffer putShort(int index, short value) {
      if (index >= 0 && index < this.lim - 1) {
         Bits.putShort(this.heap, this.offset + index, value, this.bigEndian);
         return this;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public int getInt() {
      if (this.remaining() < 4) {
         throw new BufferUnderflowException();
      } else {
         int i = Bits.getInt(this.heap, this.offset + this.pos, this.bigEndian);
         this.pos += 4;
         return i;
      }
   }

   public int getInt(int index) {
      if (index >= 0 && index < this.lim - 3) {
         return Bits.getInt(this.heap, this.offset + index, this.bigEndian);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public HeapBuffer putInt(int value) {
      if (this.remaining() < 4) {
         throw new BufferUnderflowException();
      } else {
         Bits.putInt(this.heap, this.offset + this.pos, value, this.bigEndian);
         this.pos += 4;
         return this;
      }
   }

   public HeapBuffer putInt(int index, int value) {
      if (index >= 0 && index < this.lim - 3) {
         Bits.putInt(this.heap, this.offset + index, value, this.bigEndian);
         return this;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public long getLong() {
      if (this.remaining() < 8) {
         throw new BufferUnderflowException();
      } else {
         long l = Bits.getLong(this.heap, this.offset + this.pos, this.bigEndian);
         this.pos += 8;
         return l;
      }
   }

   public long getLong(int index) {
      if (index >= 0 && index < this.lim - 7) {
         return Bits.getLong(this.heap, this.offset + index, this.bigEndian);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public HeapBuffer putLong(long value) {
      if (this.remaining() < 8) {
         throw new BufferUnderflowException();
      } else {
         Bits.putLong(this.heap, this.offset + this.pos, value, this.bigEndian);
         this.pos += 8;
         return this;
      }
   }

   public HeapBuffer putLong(int index, long value) {
      if (index >= 0 && index < this.lim - 7) {
         Bits.putLong(this.heap, this.offset + index, value, this.bigEndian);
         return this;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public float getFloat() {
      if (this.remaining() < 4) {
         throw new BufferUnderflowException();
      } else {
         float f = Bits.getFloat(this.heap, this.offset + this.pos, this.bigEndian);
         this.pos += 4;
         return f;
      }
   }

   public float getFloat(int index) {
      if (index >= 0 && index < this.lim - 3) {
         return Bits.getFloat(this.heap, this.offset + index, this.bigEndian);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public HeapBuffer putFloat(float value) {
      if (this.remaining() < 4) {
         throw new BufferUnderflowException();
      } else {
         Bits.putFloat(this.heap, this.offset + this.pos, value, this.bigEndian);
         this.pos += 4;
         return this;
      }
   }

   public HeapBuffer putFloat(int index, float value) {
      if (index >= 0 && index < this.lim - 3) {
         Bits.putFloat(this.heap, this.offset + index, value, this.bigEndian);
         return this;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public double getDouble() {
      if (this.remaining() < 8) {
         throw new BufferUnderflowException();
      } else {
         double d = Bits.getDouble(this.heap, this.offset + this.pos, this.bigEndian);
         this.pos += 8;
         return d;
      }
   }

   public double getDouble(int index) {
      if (index >= 0 && index < this.lim - 7) {
         return Bits.getDouble(this.heap, this.offset + index, this.bigEndian);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public HeapBuffer putDouble(double value) {
      if (this.remaining() < 8) {
         throw new BufferUnderflowException();
      } else {
         Bits.putDouble(this.heap, this.offset + this.pos, value, this.bigEndian);
         this.pos += 8;
         return this;
      }
   }

   public HeapBuffer putDouble(int index, double value) {
      if (index >= 0 && index < this.lim - 7) {
         Bits.putDouble(this.heap, this.offset + index, value, this.bigEndian);
         return this;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public int hashCode() {
      int result = this.allowBufferDispose ? 1 : 0;
      result = 31 * result + (this.disposeStackTrace != null ? this.disposeStackTrace.hashCode() : 0);
      result = 31 * result + (this.heap != null ? Arrays.hashCode(this.heap) : 0);
      result = 31 * result + this.offset;
      result = 31 * result + this.pos;
      result = 31 * result + this.cap;
      result = 31 * result + this.lim;
      result = 31 * result + this.mark;
      result = 31 * result + (this.readOnly ? 1 : 0);
      result = 31 * result + (this.order != null ? this.order.hashCode() : 0);
      result = 31 * result + (this.bigEndian ? 1 : 0);
      return result;
   }

   public boolean equals(Object obj) {
      if (obj instanceof Buffer) {
         Buffer that = (Buffer)obj;
         if (this.remaining() != that.remaining()) {
            return false;
         } else {
            int p = this.position();
            int i = this.limit() - 1;

            for(int j = that.limit() - 1; i >= p; --j) {
               byte v1 = this.get(i);
               byte v2 = that.get(j);
               if (v1 != v2) {
                  return false;
               }

               --i;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public int compareTo(Buffer o) {
      int n = this.position() + Math.min(this.remaining(), o.remaining());
      int i = this.position();

      for(int j = o.position(); i < n; ++j) {
         byte v1 = this.get(i);
         byte v2 = o.get(j);
         if (v1 != v2) {
            if (v1 < v2) {
               return -1;
            }

            return 1;
         }

         ++i;
      }

      return this.remaining() - o.remaining();
   }

   protected void checkDispose() {
      if (this.heap == null) {
         throw new IllegalStateException("HeapBuffer has already been disposed", this.disposeStackTrace);
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("HeapBuffer (" + System.identityHashCode(this) + ") ");
      sb.append("[pos=");
      sb.append(this.pos);
      sb.append(" lim=");
      sb.append(this.lim);
      sb.append(" cap=");
      sb.append(this.cap);
      sb.append(']');
      return sb.toString();
   }

   public String toStringContent() {
      return this.toStringContent(Charset.defaultCharset(), this.pos, this.lim);
   }

   public String toStringContent(Charset charset) {
      return this.toStringContent(charset, this.pos, this.lim);
   }

   public String toStringContent(Charset charset, int position, int limit) {
      this.checkDispose();
      if (charset == null) {
         charset = Charset.defaultCharset();
      }

      boolean isRestoreByteBuffer = this.byteBuffer != null;
      int oldPosition = 0;
      int oldLimit = 0;
      if (isRestoreByteBuffer) {
         oldPosition = this.byteBuffer.position();
         oldLimit = this.byteBuffer.limit();
      }

      ByteBuffer bb = this.toByteBuffer0(position, limit, false);

      String var8;
      try {
         var8 = charset.decode(bb).toString();
      } finally {
         if (isRestoreByteBuffer) {
            Buffers.setPositionLimit(this.byteBuffer, oldPosition, oldLimit);
         }

      }

      return var8;
   }

   public void dumpHex(Appendable appendable) {
      Buffers.dumpBuffer(appendable, this);
   }

   public ByteBuffer toByteBuffer() {
      return this.toByteBuffer(this.pos, this.lim);
   }

   public ByteBuffer toByteBuffer(int position, int limit) {
      return this.toByteBuffer0(position, limit, false);
   }

   public final ByteBufferArray toByteBufferArray() {
      ByteBufferArray array = ByteBufferArray.create();
      array.add(this.toByteBuffer());
      return array;
   }

   public final ByteBufferArray toByteBufferArray(int position, int limit) {
      return this.toByteBufferArray(ByteBufferArray.create(), position, limit);
   }

   public final ByteBufferArray toByteBufferArray(ByteBufferArray array) {
      array.add(this.toByteBuffer());
      return array;
   }

   public final ByteBufferArray toByteBufferArray(ByteBufferArray array, int position, int limit) {
      array.add(this.toByteBuffer(position, limit));
      return array;
   }

   public final BufferArray toBufferArray() {
      BufferArray array = BufferArray.create();
      array.add(this);
      return array;
   }

   public final BufferArray toBufferArray(int position, int limit) {
      return this.toBufferArray(BufferArray.create(), position, limit);
   }

   public final BufferArray toBufferArray(BufferArray array) {
      array.add(this);
      return array;
   }

   public final BufferArray toBufferArray(BufferArray array, int position, int limit) {
      int oldPos = this.pos;
      int oldLim = this.lim;
      this.pos = position;
      this.lim = limit;
      array.add(this, oldPos, oldLim);
      return array;
   }

   public boolean release() {
      return this.tryDispose();
   }

   public boolean isExternal() {
      return false;
   }

   public boolean hasArray() {
      return true;
   }

   public int arrayOffset() {
      return this.offset;
   }

   public byte[] array() {
      return this.heap;
   }

   protected void onShareHeap() {
   }

   protected HeapBuffer createHeapBuffer(int offs, int capacity) {
      this.onShareHeap();
      return new HeapBuffer(this.heap, offs + this.offset, capacity);
   }

   protected ByteBuffer toByteBuffer0(int pos, int lim, boolean slice) {
      if (this.byteBuffer == null) {
         this.byteBuffer = ByteBuffer.wrap(this.heap);
      }

      Buffers.setPositionLimit(this.byteBuffer, this.offset + pos, this.offset + lim);
      return slice ? this.byteBuffer.slice() : this.byteBuffer;
   }

   private static class DebugLogic {
      static void doDebug(HeapBuffer heapBuffer) {
         heapBuffer.clear();

         while(heapBuffer.hasRemaining()) {
            heapBuffer.put((byte)-1);
         }

         heapBuffer.flip();
         heapBuffer.disposeStackTrace = new Exception("HeapBuffer was disposed from: ");
      }
   }
}
