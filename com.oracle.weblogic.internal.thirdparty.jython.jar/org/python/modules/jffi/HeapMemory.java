package org.python.modules.jffi;

import com.kenai.jffi.Platform;
import java.nio.ByteOrder;
import java.util.Arrays;

public final class HeapMemory implements Memory {
   protected static final ArrayIO IO = getArrayIO();
   protected static final int LONG_SIZE = Platform.getPlatform().longSize() / 8;
   protected static final int ADDRESS_SIZE = Platform.getPlatform().addressSize() / 8;
   protected final byte[] buffer;
   protected final int offset;
   protected final int length;

   public HeapMemory(byte[] buffer, int offset, int length) {
      this.buffer = buffer;
      this.offset = offset;
      this.length = length;
   }

   public HeapMemory(int size) {
      this(new byte[size], 0, size);
   }

   private final void checkBounds(long off, long len) {
      Util.checkBounds((long)this.arrayLength(), off, len);
   }

   public final byte[] array() {
      return this.buffer;
   }

   public final int arrayOffset() {
      return this.offset;
   }

   public final int arrayLength() {
      return this.length;
   }

   private static final ArrayIO getArrayIO() {
      if (ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN)) {
         return Platform.getPlatform().addressSize() == 64 ? newBE64ArrayIO() : newBE32ArrayIO();
      } else {
         return Platform.getPlatform().addressSize() == 64 ? newLE64ArrayIO() : newLE32ArrayIO();
      }
   }

   private static final ArrayIO newBE64ArrayIO() {
      return new BE64ArrayIO();
   }

   private static final ArrayIO newBE32ArrayIO() {
      return new BE32ArrayIO();
   }

   private static final ArrayIO newLE64ArrayIO() {
      return new LE64ArrayIO();
   }

   private static final ArrayIO newLE32ArrayIO() {
      return new LE32ArrayIO();
   }

   protected final int index(long off) {
      return this.offset + (int)off;
   }

   public final boolean isNull() {
      return false;
   }

   public final boolean isDirect() {
      return false;
   }

   public HeapMemory slice(long offset) {
      this.checkBounds(offset, 1L);
      return offset == 0L ? this : new HeapMemory(this.array(), this.arrayOffset() + (int)offset, this.arrayLength() - (int)offset);
   }

   public final DirectMemory getMemory(long offset) {
      this.checkBounds(offset, (long)ADDRESS_SIZE);
      long ptr = this.getAddress(offset);
      return ptr != 0L ? new NativeMemory(ptr) : null;
   }

   public final void putAddress(long offset, Memory value) {
      this.checkBounds(offset, (long)ADDRESS_SIZE);
      this.putAddress(offset, ((DirectMemory)value).getAddress());
   }

   public final byte getByte(long offset) {
      this.checkBounds(offset, 1L);
      return (byte)(this.buffer[this.index(offset)] & 255);
   }

   public final short getShort(long offset) {
      this.checkBounds(offset, 2L);
      return IO.getInt16(this.buffer, this.index(offset));
   }

   public final int getInt(long offset) {
      this.checkBounds(offset, 4L);
      return IO.getInt32(this.buffer, this.index(offset));
   }

   public final long getLong(long offset) {
      this.checkBounds(offset, 8L);
      return IO.getInt64(this.buffer, this.index(offset));
   }

   public final long getNativeLong(long offset) {
      return LONG_SIZE == 4 ? (long)this.getInt(offset) : this.getLong(offset);
   }

   public final float getFloat(long offset) {
      this.checkBounds(offset, 4L);
      return IO.getFloat32(this.buffer, this.index(offset));
   }

   public final double getDouble(long offset) {
      this.checkBounds(offset, 8L);
      return IO.getFloat64(this.buffer, this.index(offset));
   }

   public final long getAddress(long offset) {
      this.checkBounds(offset, (long)ADDRESS_SIZE);
      return IO.getAddress(this.buffer, this.index(offset));
   }

   public final byte[] getZeroTerminatedByteArray(long offset) {
      this.checkBounds(offset, 1L);
      int len = this.indexOf(offset, (byte)0);
      byte[] bytes = new byte[len != -1 ? len : this.length - (int)offset];
      System.arraycopy(this.buffer, this.index(offset), bytes, 0, bytes.length);
      return bytes;
   }

   public final void putByte(long offset, byte value) {
      this.checkBounds(offset, 1L);
      this.buffer[this.index(offset)] = value;
   }

   public final void putShort(long offset, short value) {
      this.checkBounds(offset, 2L);
      IO.putInt16(this.buffer, this.index(offset), value);
   }

   public final void putInt(long offset, int value) {
      this.checkBounds(offset, 4L);
      IO.putInt32(this.buffer, this.index(offset), value);
   }

   public final void putLong(long offset, long value) {
      this.checkBounds(offset, 8L);
      IO.putInt64(this.buffer, this.index(offset), value);
   }

   public final void putNativeLong(long offset, long value) {
      if (LONG_SIZE == 4) {
         this.putInt(offset, (int)value);
      } else {
         this.putLong(offset, value);
      }

   }

   public final void putFloat(long offset, float value) {
      this.checkBounds(offset, 4L);
      IO.putFloat32(this.buffer, this.index(offset), value);
   }

   public final void putDouble(long offset, double value) {
      this.checkBounds(offset, 8L);
      IO.putFloat64(this.buffer, this.index(offset), value);
   }

   public final void putAddress(long offset, long value) {
      this.checkBounds(offset, (long)ADDRESS_SIZE);
      IO.putAddress(this.buffer, this.index(offset), value);
   }

   public void putZeroTerminatedByteArray(long offset, byte[] bytes, int off, int len) {
      this.checkBounds(offset, (long)(len + 1));
      System.arraycopy(bytes, off, this.buffer, this.index(offset), len);
      this.buffer[len] = 0;
   }

   public final void get(long offset, byte[] dst, int off, int len) {
      this.checkBounds(offset, (long)len);
      System.arraycopy(this.buffer, this.index(offset), dst, off, len);
   }

   public final void put(long offset, byte[] src, int off, int len) {
      this.checkBounds(offset, (long)len);
      System.arraycopy(src, off, this.buffer, this.index(offset), len);
   }

   public final void get(long offset, short[] dst, int off, int len) {
      this.checkBounds(offset, (long)(len << 1));
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         dst[off + i] = IO.getInt16(this.buffer, begin + (i << 1));
      }

   }

   public final void put(long offset, short[] src, int off, int len) {
      this.checkBounds(offset, (long)(len << 1));
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         IO.putInt16(this.buffer, begin + (i << 1), src[off + i]);
      }

   }

   public final void get(long offset, int[] dst, int off, int len) {
      this.checkBounds(offset, (long)(len << 2));
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         dst[off + i] = IO.getInt32(this.buffer, begin + (i << 2));
      }

   }

   public final void put(long offset, int[] src, int off, int len) {
      this.checkBounds(offset, (long)(len << 2));
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         IO.putInt32(this.buffer, begin + (i << 2), src[off + i]);
      }

   }

   public final void get(long offset, long[] dst, int off, int len) {
      this.checkBounds(offset, (long)(len << 3));
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         dst[off + i] = IO.getInt64(this.buffer, begin + (i << 3));
      }

   }

   public final void put(long offset, long[] src, int off, int len) {
      this.checkBounds(offset, (long)(len << 3));
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         IO.putInt64(this.buffer, begin + (i << 3), src[off + i]);
      }

   }

   public final void get(long offset, float[] dst, int off, int len) {
      this.checkBounds(offset, (long)(len << 2));
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         dst[off + i] = IO.getFloat32(this.buffer, begin + (i << 2));
      }

   }

   public final void put(long offset, float[] src, int off, int len) {
      this.checkBounds(offset, (long)(len << 2));
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         IO.putFloat32(this.buffer, begin + (i << 2), src[off + i]);
      }

   }

   public final void get(long offset, double[] dst, int off, int len) {
      this.checkBounds(offset, (long)(len << 3));
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         dst[off + i] = IO.getFloat64(this.buffer, begin + (i << 3));
      }

   }

   public final void put(long offset, double[] src, int off, int len) {
      this.checkBounds(offset, (long)(len << 3));
      int begin = this.index(offset);

      for(int i = 0; i < len; ++i) {
         IO.putFloat64(this.buffer, begin + (i << 3), src[off + i]);
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
      this.checkBounds(offset, size);
      Arrays.fill(this.buffer, this.index(offset), (int)size, value);
   }

   public final void clear() {
      Arrays.fill(this.buffer, this.offset, this.length, (byte)0);
   }

   private static final class BE64ArrayIO extends BigEndianArrayIO {
      private BE64ArrayIO() {
         super(null);
      }

      public final long getAddress(byte[] buffer, int offset) {
         return this.getInt64(buffer, offset);
      }

      public final void putAddress(byte[] buffer, int offset, long value) {
         this.putInt64(buffer, offset, value);
      }

      // $FF: synthetic method
      BE64ArrayIO(Object x0) {
         this();
      }
   }

   private static final class BE32ArrayIO extends BigEndianArrayIO {
      private BE32ArrayIO() {
         super(null);
      }

      public final long getAddress(byte[] buffer, int offset) {
         return (long)this.getInt32(buffer, offset) & 4294967295L;
      }

      public final void putAddress(byte[] buffer, int offset, long value) {
         this.putInt32(buffer, offset, (int)value);
      }

      // $FF: synthetic method
      BE32ArrayIO(Object x0) {
         this();
      }
   }

   private static final class LE64ArrayIO extends LittleEndianArrayIO {
      private LE64ArrayIO() {
         super(null);
      }

      public final long getAddress(byte[] buffer, int offset) {
         return this.getInt64(buffer, offset);
      }

      public final void putAddress(byte[] buffer, int offset, long value) {
         this.putInt64(buffer, offset, value);
      }

      // $FF: synthetic method
      LE64ArrayIO(Object x0) {
         this();
      }
   }

   private static final class LE32ArrayIO extends LittleEndianArrayIO {
      private LE32ArrayIO() {
         super(null);
      }

      public final long getAddress(byte[] buffer, int offset) {
         return (long)this.getInt32(buffer, offset) & 4294967295L;
      }

      public final void putAddress(byte[] buffer, int offset, long value) {
         this.putInt32(buffer, offset, (int)value);
      }

      // $FF: synthetic method
      LE32ArrayIO(Object x0) {
         this();
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
