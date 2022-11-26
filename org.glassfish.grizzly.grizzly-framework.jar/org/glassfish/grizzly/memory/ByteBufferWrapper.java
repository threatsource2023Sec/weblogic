package org.glassfish.grizzly.memory;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import java.nio.charset.Charset;
import org.glassfish.grizzly.Buffer;

public class ByteBufferWrapper implements Buffer {
   public static volatile boolean DEBUG_MODE = false;
   protected ByteBuffer visible;
   protected int mark;
   protected boolean allowBufferDispose;
   protected Exception disposeStackTrace;

   protected ByteBufferWrapper() {
      this((ByteBuffer)null);
   }

   public ByteBufferWrapper(ByteBuffer underlyingByteBuffer) {
      this.mark = -1;
      this.allowBufferDispose = false;
      this.visible = underlyingByteBuffer;
   }

   public final boolean isComposite() {
      return false;
   }

   public ByteBufferWrapper prepend(Buffer header) {
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

   public boolean isDirect() {
      this.checkDispose();
      return this.visible.isDirect();
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
      this.visible = null;
   }

   protected final void prepareDispose() {
      this.checkDispose();
      if (DEBUG_MODE) {
         ByteBufferWrapper.DebugLogic.doDebug(this);
      }

   }

   public ByteBuffer underlying() {
      this.checkDispose();
      return this.visible;
   }

   public final int capacity() {
      return this.visible.capacity();
   }

   public final int position() {
      this.checkDispose();
      return this.visible.position();
   }

   public final ByteBufferWrapper position(int newPosition) {
      this.checkDispose();
      this.visible.position(newPosition);
      if (this.mark > newPosition) {
         this.mark = -1;
      }

      return this;
   }

   public final int limit() {
      this.checkDispose();
      return this.visible.limit();
   }

   public final ByteBufferWrapper limit(int newLimit) {
      this.checkDispose();
      this.visible.limit(newLimit);
      if (this.mark > newLimit) {
         this.mark = -1;
      }

      return this;
   }

   public final ByteBufferWrapper mark() {
      this.checkDispose();
      this.mark = this.visible.position();
      return this;
   }

   public final ByteBufferWrapper reset() {
      this.checkDispose();
      if (this.mark < 0) {
         throw new InvalidMarkException();
      } else {
         this.visible.position(this.mark);
         return this;
      }
   }

   public final ByteBufferWrapper clear() {
      this.checkDispose();
      this.visible.clear();
      this.mark = -1;
      return this;
   }

   public final ByteBufferWrapper flip() {
      this.checkDispose();
      this.visible.flip();
      this.mark = -1;
      return this;
   }

   public final ByteBufferWrapper rewind() {
      this.checkDispose();
      this.visible.rewind();
      this.mark = -1;
      return this;
   }

   public final int remaining() {
      this.checkDispose();
      return this.visible.remaining();
   }

   public final boolean hasRemaining() {
      this.checkDispose();
      return this.visible.hasRemaining();
   }

   public boolean isReadOnly() {
      this.checkDispose();
      return this.visible.isReadOnly();
   }

   public Buffer split(int splitPosition) {
      this.checkDispose();
      int cap = this.capacity();
      if (splitPosition >= 0 && splitPosition <= cap) {
         if (splitPosition == cap) {
            return Buffers.EMPTY_BUFFER;
         } else {
            if (this.mark >= splitPosition) {
               this.mark = -1;
            }

            int oldPosition = this.position();
            int oldLimit = this.limit();
            Buffers.setPositionLimit((ByteBuffer)this.visible, 0, splitPosition);
            ByteBuffer slice1 = this.visible.slice();
            Buffers.setPositionLimit(this.visible, splitPosition, this.visible.capacity());
            ByteBuffer slice2 = this.visible.slice();
            if (oldPosition < splitPosition) {
               slice1.position(oldPosition);
            } else {
               slice1.position(slice1.capacity());
               slice2.position(oldPosition - splitPosition);
            }

            if (oldLimit < splitPosition) {
               slice1.limit(oldLimit);
               slice2.limit(0);
            } else {
               slice2.limit(oldLimit - splitPosition);
            }

            this.visible = slice1;
            return this.wrapByteBuffer(slice2);
         }
      } else {
         throw new IllegalArgumentException("Invalid splitPosition value, should be 0 <= splitPosition <= capacity");
      }
   }

   public ByteBufferWrapper slice() {
      return this.slice(this.position(), this.limit());
   }

   public ByteBufferWrapper slice(int position, int limit) {
      this.checkDispose();
      int oldPosition = this.position();
      int oldLimit = this.limit();

      ByteBufferWrapper var6;
      try {
         Buffers.setPositionLimit(this.visible, position, limit);
         ByteBuffer slice = this.visible.slice();
         var6 = this.wrapByteBuffer(slice);
      } finally {
         Buffers.setPositionLimit(this.visible, oldPosition, oldLimit);
      }

      return var6;
   }

   public ByteBufferWrapper duplicate() {
      this.checkDispose();
      ByteBuffer duplicate = this.visible.duplicate();
      return this.wrapByteBuffer(duplicate);
   }

   public ByteBufferWrapper asReadOnlyBuffer() {
      this.checkDispose();
      return this.wrapByteBuffer(this.visible.asReadOnlyBuffer());
   }

   public byte get() {
      this.checkDispose();
      return this.visible.get();
   }

   public byte get(int index) {
      this.checkDispose();
      return this.visible.get(index);
   }

   public ByteBufferWrapper put(byte b) {
      this.checkDispose();
      this.visible.put(b);
      return this;
   }

   public ByteBufferWrapper put(int index, byte b) {
      this.checkDispose();
      this.visible.put(index, b);
      return this;
   }

   public ByteBufferWrapper get(byte[] dst) {
      return this.get((byte[])dst, 0, dst.length);
   }

   public ByteBufferWrapper get(byte[] dst, int offset, int length) {
      this.checkDispose();
      Buffers.get(this.visible, dst, offset, length);
      return this;
   }

   public ByteBufferWrapper put(Buffer src) {
      this.put(src, src.position(), src.remaining());
      src.position(src.limit());
      return this;
   }

   public ByteBufferWrapper put(Buffer src, int position, int length) {
      int oldPos = src.position();
      int oldLim = this.limit();
      src.position(position);
      this.limit(this.position() + length);

      try {
         src.get(this.visible);
      } finally {
         src.position(oldPos);
         this.limit(oldLim);
      }

      return this;
   }

   public Buffer get(ByteBuffer dst) {
      this.checkDispose();
      int length = dst.remaining();
      if (this.visible.remaining() < length) {
         throw new BufferUnderflowException();
      } else {
         int srcPos = this.visible.position();
         int oldSrcLim = this.visible.limit();

         try {
            this.visible.limit(srcPos + length);
            dst.put(this.visible);
         } finally {
            this.visible.limit(oldSrcLim);
         }

         return this;
      }
   }

   public Buffer get(ByteBuffer dst, int position, int length) {
      this.checkDispose();
      if (this.visible.remaining() < length) {
         throw new BufferUnderflowException();
      } else {
         int srcPos = this.visible.position();
         int oldSrcLim = this.visible.limit();
         int oldDstPos = dst.position();
         int oldDstLim = dst.limit();
         Buffers.setPositionLimit(dst, position, position + length);

         try {
            this.visible.limit(srcPos + length);
            dst.put(this.visible);
         } finally {
            this.visible.limit(oldSrcLim);
            Buffers.setPositionLimit(dst, oldDstPos, oldDstLim);
         }

         return this;
      }
   }

   public Buffer put(ByteBuffer src) {
      this.checkDispose();
      this.visible.put(src);
      return this;
   }

   public Buffer put(ByteBuffer src, int position, int length) {
      this.checkDispose();
      int oldPos = src.position();
      int oldLim = src.limit();

      try {
         Buffers.setPositionLimit(src, position, position + length);
         this.visible.put(src);
      } finally {
         Buffers.setPositionLimit(src, oldPos, oldLim);
      }

      return this;
   }

   public ByteBufferWrapper put(byte[] src) {
      return this.put((byte[])src, 0, src.length);
   }

   public ByteBufferWrapper put(byte[] src, int offset, int length) {
      this.checkDispose();
      Buffers.put(src, offset, length, this.visible);
      return this;
   }

   public Buffer put8BitString(String s) {
      this.checkDispose();
      int len = s.length();
      if (this.remaining() < len) {
         throw new BufferOverflowException();
      } else {
         for(int i = 0; i < len; ++i) {
            this.visible.put((byte)s.charAt(i));
         }

         return this;
      }
   }

   public ByteBufferWrapper compact() {
      this.checkDispose();
      this.visible.compact();
      return this;
   }

   public ByteOrder order() {
      this.checkDispose();
      return this.visible.order();
   }

   public ByteBufferWrapper order(ByteOrder bo) {
      this.checkDispose();
      this.visible.order(bo);
      return this;
   }

   public char getChar() {
      this.checkDispose();
      return this.visible.getChar();
   }

   public char getChar(int index) {
      this.checkDispose();
      return this.visible.getChar(index);
   }

   public ByteBufferWrapper putChar(char value) {
      this.checkDispose();
      this.visible.putChar(value);
      return this;
   }

   public ByteBufferWrapper putChar(int index, char value) {
      this.checkDispose();
      this.visible.putChar(index, value);
      return this;
   }

   public short getShort() {
      this.checkDispose();
      return this.visible.getShort();
   }

   public short getShort(int index) {
      this.checkDispose();
      return this.visible.getShort(index);
   }

   public ByteBufferWrapper putShort(short value) {
      this.checkDispose();
      this.visible.putShort(value);
      return this;
   }

   public ByteBufferWrapper putShort(int index, short value) {
      this.checkDispose();
      this.visible.putShort(index, value);
      return this;
   }

   public int getInt() {
      this.checkDispose();
      return this.visible.getInt();
   }

   public int getInt(int index) {
      this.checkDispose();
      return this.visible.getInt(index);
   }

   public ByteBufferWrapper putInt(int value) {
      this.checkDispose();
      this.visible.putInt(value);
      return this;
   }

   public ByteBufferWrapper putInt(int index, int value) {
      this.checkDispose();
      this.visible.putInt(index, value);
      return this;
   }

   public long getLong() {
      this.checkDispose();
      return this.visible.getLong();
   }

   public long getLong(int index) {
      this.checkDispose();
      return this.visible.getLong(index);
   }

   public ByteBufferWrapper putLong(long value) {
      this.checkDispose();
      this.visible.putLong(value);
      return this;
   }

   public ByteBufferWrapper putLong(int index, long value) {
      this.checkDispose();
      this.visible.putLong(index, value);
      return this;
   }

   public float getFloat() {
      this.checkDispose();
      return this.visible.getFloat();
   }

   public float getFloat(int index) {
      this.checkDispose();
      return this.visible.getFloat(index);
   }

   public ByteBufferWrapper putFloat(float value) {
      this.checkDispose();
      this.visible.putFloat(value);
      return this;
   }

   public ByteBufferWrapper putFloat(int index, float value) {
      this.checkDispose();
      this.visible.putFloat(index, value);
      return this;
   }

   public double getDouble() {
      this.checkDispose();
      return this.visible.getDouble();
   }

   public double getDouble(int index) {
      this.checkDispose();
      return this.visible.getDouble(index);
   }

   public ByteBufferWrapper putDouble(double value) {
      this.checkDispose();
      this.visible.putDouble(value);
      return this;
   }

   public ByteBufferWrapper putDouble(int index, double value) {
      this.checkDispose();
      this.visible.putDouble(index, value);
      return this;
   }

   public int hashCode() {
      return this.visible.hashCode();
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
      if (this.visible == null) {
         throw new IllegalStateException("BufferWrapper has already been disposed", this.disposeStackTrace);
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("ByteBufferWrapper (" + System.identityHashCode(this) + ") [");
      sb.append("visible=[").append(this.visible).append(']');
      sb.append(']');
      return sb.toString();
   }

   public String toStringContent() {
      return this.toStringContent(Charset.defaultCharset(), this.position(), this.limit());
   }

   public String toStringContent(Charset charset) {
      return this.toStringContent(charset, this.position(), this.limit());
   }

   public String toStringContent(Charset charset, int position, int limit) {
      this.checkDispose();
      return Buffers.toStringContent(this.visible, charset, position, limit);
   }

   public void dumpHex(Appendable appendable) {
      Buffers.dumpBuffer(appendable, this);
   }

   public final ByteBuffer toByteBuffer() {
      this.checkDispose();
      return this.visible;
   }

   public final ByteBuffer toByteBuffer(int position, int limit) {
      this.checkDispose();
      int currentPosition = this.visible.position();
      int currentLimit = this.visible.limit();
      if (position == currentPosition && limit == currentLimit) {
         return this.toByteBuffer();
      } else {
         Buffers.setPositionLimit(this.visible, position, limit);
         ByteBuffer resultBuffer = this.visible.slice();
         Buffers.setPositionLimit(this.visible, currentPosition, currentLimit);
         return resultBuffer;
      }
   }

   public final ByteBufferArray toByteBufferArray() {
      this.checkDispose();
      ByteBufferArray array = ByteBufferArray.create();
      array.add(this.visible);
      return array;
   }

   public final ByteBufferArray toByteBufferArray(int position, int limit) {
      return this.toByteBufferArray(ByteBufferArray.create(), position, limit);
   }

   public final ByteBufferArray toByteBufferArray(ByteBufferArray array) {
      this.checkDispose();
      array.add(this.visible);
      return array;
   }

   public final ByteBufferArray toByteBufferArray(ByteBufferArray array, int position, int limit) {
      this.checkDispose();
      int oldPos = this.visible.position();
      int oldLim = this.visible.limit();
      Buffers.setPositionLimit(this.visible, position, limit);
      array.add(this.visible, oldPos, oldLim);
      return array;
   }

   public final BufferArray toBufferArray() {
      this.checkDispose();
      BufferArray array = BufferArray.create();
      array.add(this);
      return array;
   }

   public final BufferArray toBufferArray(int position, int limit) {
      return this.toBufferArray(BufferArray.create(), position, limit);
   }

   public final BufferArray toBufferArray(BufferArray array) {
      this.checkDispose();
      array.add(this);
      return array;
   }

   public final BufferArray toBufferArray(BufferArray array, int position, int limit) {
      this.checkDispose();
      int oldPos = this.visible.position();
      int oldLim = this.visible.limit();
      Buffers.setPositionLimit(this.visible, position, limit);
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
      return this.visible.hasArray();
   }

   public byte[] array() {
      return this.visible.array();
   }

   public int arrayOffset() {
      return this.visible.arrayOffset();
   }

   protected ByteBufferWrapper wrapByteBuffer(ByteBuffer byteBuffer) {
      return new ByteBufferWrapper(byteBuffer);
   }

   private static class DebugLogic {
      static void doDebug(ByteBufferWrapper wrapper) {
         wrapper.visible.clear();

         while(wrapper.visible.hasRemaining()) {
            wrapper.visible.put((byte)-1);
         }

         wrapper.visible.flip();
         wrapper.disposeStackTrace = new Exception("ByteBufferWrapper was disposed from: ");
      }
   }
}
