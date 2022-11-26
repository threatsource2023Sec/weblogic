package jnr.ffi.provider;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;
import jnr.ffi.Runtime;
import jnr.ffi.util.BufferUtil;

public abstract class AbstractArrayMemoryIO extends AbstractMemoryIO {
   private final ArrayIO io;
   protected final byte[] buffer;
   protected final int offset;
   protected final int length;

   protected AbstractArrayMemoryIO(Runtime runtime, byte[] buffer, int offset, int length) {
      super(runtime, 0L, false);
      this.io = AbstractArrayMemoryIO.ArrayIO.getArrayIO(runtime);
      this.buffer = buffer;
      this.offset = offset;
      this.length = length;
   }

   protected AbstractArrayMemoryIO(Runtime runtime, byte[] buffer) {
      this(runtime, buffer, 0, buffer.length);
   }

   protected AbstractArrayMemoryIO(Runtime runtime, int size) {
      this(runtime, new byte[size], 0, size);
   }

   protected final ArrayIO getArrayIO() {
      return this.io;
   }

   public final byte[] array() {
      return this.buffer;
   }

   public final int offset() {
      return this.offset;
   }

   public final int length() {
      return this.length;
   }

   public final int arrayLength() {
      return this.length;
   }

   public final int arrayOffset() {
      return this.offset;
   }

   public final boolean hasArray() {
      return true;
   }

   public final long size() {
      return (long)this.length;
   }

   protected final int index(long off) {
      return this.offset + (int)off;
   }

   protected final int remaining(long offset) {
      return this.length - (int)offset;
   }

   public final boolean isNull() {
      return false;
   }

   public String getString(long offset) {
      return BufferUtil.getString(ByteBuffer.wrap(this.buffer, this.index(offset), this.length - (int)offset), Charset.defaultCharset());
   }

   public String getString(long offset, int maxLength, Charset cs) {
      return BufferUtil.getString(ByteBuffer.wrap(this.buffer, this.index(offset), Math.min(this.length - (int)offset, maxLength)), cs);
   }

   public void putString(long offset, String string, int maxLength, Charset cs) {
      ByteBuffer buf = cs.encode(string);
      int len = Math.min(maxLength - 1, Math.min(buf.remaining(), this.remaining(offset)));
      buf.get(this.buffer, this.index(offset), len);
      this.buffer[this.index(offset) + len] = 0;
   }

   public void putZeroTerminatedByteArray(long offset, byte[] src, int off, int len) {
      System.arraycopy(src, off, this.buffer, this.index(offset), this.length - (int)offset);
      this.buffer[this.index(offset) + len] = 0;
   }

   public final byte getByte(long offset) {
      return (byte)(this.buffer[this.index(offset)] & 255);
   }

   public final short getShort(long offset) {
      return this.io.getInt16(this.buffer, this.index(offset));
   }

   public final int getInt(long offset) {
      return this.io.getInt32(this.buffer, this.index(offset));
   }

   public final long getLongLong(long offset) {
      return this.io.getInt64(this.buffer, this.index(offset));
   }

   public final long getAddress(long offset) {
      return this.io.getAddress(this.buffer, this.index(offset));
   }

   public final float getFloat(long offset) {
      return this.io.getFloat32(this.buffer, this.index(offset));
   }

   public final double getDouble(long offset) {
      return this.io.getFloat64(this.buffer, this.index(offset));
   }

   public final void putByte(long offset, byte value) {
      this.buffer[this.index(offset)] = value;
   }

   public final void putShort(long offset, short value) {
      this.io.putInt16(this.buffer, this.index(offset), value);
   }

   public final void putInt(long offset, int value) {
      this.io.putInt32(this.buffer, this.index(offset), value);
   }

   public final void putLongLong(long offset, long value) {
      this.io.putInt64(this.buffer, this.index(offset), value);
   }

   public final void putAddress(long offset, long value) {
      this.io.putAddress(this.buffer, this.index(offset), value);
   }

   public final void putFloat(long offset, float value) {
      this.io.putFloat32(this.buffer, this.index(offset), value);
   }

   public final void putDouble(long offset, double value) {
      this.io.putFloat64(this.buffer, this.index(offset), value);
   }

   public final void get(long offset, byte[] dst, int off, int len) {
      System.arraycopy(this.buffer, this.index(offset), dst, off, len);
   }

   public final void put(long offset, byte[] src, int off, int len) {
      System.arraycopy(src, off, this.buffer, this.index(offset), len);
   }

   public final void get(long offset, short[] dst, int off, int len) {
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         dst[off + i] = this.io.getInt16(this.buffer, begin + (i << 1));
      }

   }

   public final void put(long offset, short[] src, int off, int len) {
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         this.io.putInt16(this.buffer, begin + (i << 1), src[off + i]);
      }

   }

   public final void get(long offset, int[] dst, int off, int len) {
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         dst[off + i] = this.io.getInt32(this.buffer, begin + (i << 2));
      }

   }

   public final void put(long offset, int[] src, int off, int len) {
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         this.io.putInt32(this.buffer, begin + (i << 2), src[off + i]);
      }

   }

   public final void get(long offset, long[] dst, int off, int len) {
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         dst[off + i] = this.io.getInt64(this.buffer, begin + (i << 3));
      }

   }

   public final void put(long offset, long[] src, int off, int len) {
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         this.io.putInt64(this.buffer, begin + (i << 3), src[off + i]);
      }

   }

   public final void get(long offset, float[] dst, int off, int len) {
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         dst[off + i] = this.io.getFloat32(this.buffer, begin + (i << 2));
      }

   }

   public final void put(long offset, float[] src, int off, int len) {
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         this.io.putFloat32(this.buffer, begin + (i << 2), src[off + i]);
      }

   }

   public final void get(long offset, double[] dst, int off, int len) {
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         dst[off + i] = this.io.getFloat64(this.buffer, begin + (i << 3));
      }

   }

   public final void put(long offset, double[] src, int off, int len) {
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         this.io.putFloat64(this.buffer, begin + (i << 3), src[off + i]);
      }

   }

   public final int indexOf(long offset, byte value) {
      int off = this.index(offset);

      for(int i = 0; i < this.length; ++i) {
         if (this.buffer[off + i] == value) {
            return i;
         }
      }

      return -1;
   }

   public final int indexOf(long offset, byte value, int maxlen) {
      int off = this.index(offset);

      for(int i = 0; i < Math.min(this.length, maxlen); ++i) {
         if (this.buffer[off + i] == value) {
            return i;
         }
      }

      return -1;
   }

   public final void setMemory(long offset, long size, byte value) {
      Arrays.fill(this.buffer, this.index(offset), (int)size, value);
   }

   public final void clear() {
      Arrays.fill(this.buffer, this.offset, this.length, (byte)0);
   }

   private static final class BE64ArrayIO extends BigEndianArrayIO {
      public static final ArrayIO INSTANCE = new BE64ArrayIO();

      private BE64ArrayIO() {
         super(null);
      }

      public final long getAddress(byte[] buffer, int offset) {
         return this.getInt64(buffer, offset);
      }

      public final void putAddress(byte[] buffer, int offset, long value) {
         this.putInt64(buffer, offset, value);
      }
   }

   private static final class BE32ArrayIO extends BigEndianArrayIO {
      public static final ArrayIO INSTANCE = new BE32ArrayIO();

      private BE32ArrayIO() {
         super(null);
      }

      public final long getAddress(byte[] buffer, int offset) {
         return (long)this.getInt32(buffer, offset) & 4294967295L;
      }

      public final void putAddress(byte[] buffer, int offset, long value) {
         this.putInt32(buffer, offset, (int)value);
      }
   }

   private static final class LE64ArrayIO extends LittleEndianArrayIO {
      public static final ArrayIO INSTANCE = new LE64ArrayIO();

      private LE64ArrayIO() {
         super(null);
      }

      public final long getAddress(byte[] buffer, int offset) {
         return this.getInt64(buffer, offset);
      }

      public final void putAddress(byte[] buffer, int offset, long value) {
         this.putInt64(buffer, offset, value);
      }
   }

   private static final class LE32ArrayIO extends LittleEndianArrayIO {
      public static final ArrayIO INSTANCE = new LE32ArrayIO();

      private LE32ArrayIO() {
         super(null);
      }

      public final long getAddress(byte[] buffer, int offset) {
         return (long)this.getInt32(buffer, offset) & 4294967295L;
      }

      public final void putAddress(byte[] buffer, int offset, long value) {
         this.putInt32(buffer, offset, (int)value);
      }
   }

   private abstract static class BigEndianArrayIO extends ArrayIO {
      private BigEndianArrayIO() {
      }

      public short getInt16(byte[] array, int offset) {
         return (short)((array[offset + 0] & 255) << 8 | array[offset + 1] & 255);
      }

      public int getInt32(byte[] array, int offset) {
         return (array[offset + 0] & 255) << 24 | (array[offset + 1] & 255) << 16 | (array[offset + 2] & 255) << 8 | (array[offset + 3] & 255) << 0;
      }

      public long getInt64(byte[] array, int offset) {
         return ((long)array[offset + 0] & 255L) << 56 | ((long)array[offset + 1] & 255L) << 48 | ((long)array[offset + 2] & 255L) << 40 | ((long)array[offset + 3] & 255L) << 32 | ((long)array[offset + 4] & 255L) << 24 | ((long)array[offset + 5] & 255L) << 16 | ((long)array[offset + 6] & 255L) << 8 | ((long)array[offset + 7] & 255L) << 0;
      }

      public final void putInt16(byte[] buffer, int offset, int value) {
         buffer[offset + 0] = (byte)(value >> 8);
         buffer[offset + 1] = (byte)(value >> 0);
      }

      public final void putInt32(byte[] buffer, int offset, int value) {
         buffer[offset + 0] = (byte)(value >> 24);
         buffer[offset + 1] = (byte)(value >> 16);
         buffer[offset + 2] = (byte)(value >> 8);
         buffer[offset + 3] = (byte)(value >> 0);
      }

      public final void putInt64(byte[] buffer, int offset, long value) {
         buffer[offset + 0] = (byte)((int)(value >> 56));
         buffer[offset + 1] = (byte)((int)(value >> 48));
         buffer[offset + 2] = (byte)((int)(value >> 40));
         buffer[offset + 3] = (byte)((int)(value >> 32));
         buffer[offset + 4] = (byte)((int)(value >> 24));
         buffer[offset + 5] = (byte)((int)(value >> 16));
         buffer[offset + 6] = (byte)((int)(value >> 8));
         buffer[offset + 7] = (byte)((int)(value >> 0));
      }

      // $FF: synthetic method
      BigEndianArrayIO(Object x0) {
         this();
      }
   }

   private abstract static class LittleEndianArrayIO extends ArrayIO {
      private LittleEndianArrayIO() {
      }

      public final short getInt16(byte[] array, int offset) {
         return (short)(array[offset] & 255 | (array[offset + 1] & 255) << 8);
      }

      public final int getInt32(byte[] array, int offset) {
         return (array[offset + 0] & 255) << 0 | (array[offset + 1] & 255) << 8 | (array[offset + 2] & 255) << 16 | (array[offset + 3] & 255) << 24;
      }

      public final long getInt64(byte[] array, int offset) {
         return ((long)array[offset + 0] & 255L) << 0 | ((long)array[offset + 1] & 255L) << 8 | ((long)array[offset + 2] & 255L) << 16 | ((long)array[offset + 3] & 255L) << 24 | ((long)array[offset + 4] & 255L) << 32 | ((long)array[offset + 5] & 255L) << 40 | ((long)array[offset + 6] & 255L) << 48 | ((long)array[offset + 7] & 255L) << 56;
      }

      public final void putInt16(byte[] buffer, int offset, int value) {
         buffer[offset + 0] = (byte)(value >> 0);
         buffer[offset + 1] = (byte)(value >> 8);
      }

      public final void putInt32(byte[] buffer, int offset, int value) {
         buffer[offset + 0] = (byte)(value >> 0);
         buffer[offset + 1] = (byte)(value >> 8);
         buffer[offset + 2] = (byte)(value >> 16);
         buffer[offset + 3] = (byte)(value >> 24);
      }

      public final void putInt64(byte[] buffer, int offset, long value) {
         buffer[offset + 0] = (byte)((int)(value >> 0));
         buffer[offset + 1] = (byte)((int)(value >> 8));
         buffer[offset + 2] = (byte)((int)(value >> 16));
         buffer[offset + 3] = (byte)((int)(value >> 24));
         buffer[offset + 4] = (byte)((int)(value >> 32));
         buffer[offset + 5] = (byte)((int)(value >> 40));
         buffer[offset + 6] = (byte)((int)(value >> 48));
         buffer[offset + 7] = (byte)((int)(value >> 56));
      }

      // $FF: synthetic method
      LittleEndianArrayIO(Object x0) {
         this();
      }
   }

   protected abstract static class ArrayIO {
      public static ArrayIO getArrayIO(Runtime runtime) {
         if (runtime.byteOrder().equals(ByteOrder.BIG_ENDIAN)) {
            return runtime.addressSize() == 8 ? AbstractArrayMemoryIO.BE64ArrayIO.INSTANCE : AbstractArrayMemoryIO.BE32ArrayIO.INSTANCE;
         } else {
            return runtime.addressSize() == 8 ? AbstractArrayMemoryIO.LE64ArrayIO.INSTANCE : AbstractArrayMemoryIO.LE32ArrayIO.INSTANCE;
         }
      }

      public abstract short getInt16(byte[] var1, int var2);

      public abstract int getInt32(byte[] var1, int var2);

      public abstract long getInt64(byte[] var1, int var2);

      public abstract long getAddress(byte[] var1, int var2);

      public abstract void putInt16(byte[] var1, int var2, int var3);

      public abstract void putInt32(byte[] var1, int var2, int var3);

      public abstract void putInt64(byte[] var1, int var2, long var3);

      public abstract void putAddress(byte[] var1, int var2, long var3);

      public final float getFloat32(byte[] buffer, int offset) {
         return Float.intBitsToFloat(this.getInt32(buffer, offset));
      }

      public final void putFloat32(byte[] buffer, int offset, float value) {
         this.putInt32(buffer, offset, Float.floatToRawIntBits(value));
      }

      public final double getFloat64(byte[] buffer, int offset) {
         return Double.longBitsToDouble(this.getInt64(buffer, offset));
      }

      public final void putFloat64(byte[] buffer, int offset, double value) {
         this.putInt64(buffer, offset, Double.doubleToRawLongBits(value));
      }
   }
}
