package org.python.modules.jffi;

import com.kenai.jffi.MemoryIO;
import com.kenai.jffi.Platform;

class BoundedNativeMemory implements Memory, DirectMemory {
   protected static final MemoryIO IO = MemoryIO.getInstance();
   protected static final int LONG_SIZE = Platform.getPlatform().longSize();
   protected static final int ADDRESS_SIZE = Platform.getPlatform().addressSize();
   final long address;
   final long size;
   final BoundedNativeMemory parent;

   BoundedNativeMemory(long address, int size) {
      this.address = address;
      this.size = (long)size;
      this.parent = null;
   }

   private BoundedNativeMemory(BoundedNativeMemory parent, long offset) {
      this.address = parent.address + offset;
      this.size = parent.size - offset;
      this.parent = parent;
   }

   private final void checkBounds(long off, long len) {
      Util.checkBounds(this.size, off, len);
   }

   public final long getAddress() {
      return this.address;
   }

   public BoundedNativeMemory slice(long offset) {
      this.checkBounds(offset, 1L);
      return offset == 0L ? this : new BoundedNativeMemory(this, offset);
   }

   public final boolean equals(Object obj) {
      return obj instanceof DirectMemory && ((DirectMemory)obj).getAddress() == this.address;
   }

   public final int hashCode() {
      int hash = 5;
      hash = 53 * hash + (int)(this.address ^ this.address >>> 32);
      return hash;
   }

   public final boolean isNull() {
      return this.address == 0L;
   }

   public final boolean isDirect() {
      return true;
   }

   public final byte getByte(long offset) {
      this.checkBounds(offset, 1L);
      return IO.getByte(this.address + offset);
   }

   public final short getShort(long offset) {
      this.checkBounds(offset, 2L);
      return IO.getShort(this.address + offset);
   }

   public final int getInt(long offset) {
      this.checkBounds(offset, 4L);
      return IO.getInt(this.address + offset);
   }

   public final long getLong(long offset) {
      this.checkBounds(offset, 8L);
      return IO.getLong(this.address + offset);
   }

   public final long getNativeLong(long offset) {
      return LONG_SIZE == 32 ? (long)this.getInt(offset) : this.getLong(offset);
   }

   public final float getFloat(long offset) {
      this.checkBounds(offset, 4L);
      return IO.getFloat(this.address + offset);
   }

   public final double getDouble(long offset) {
      this.checkBounds(offset, 8L);
      return IO.getDouble(this.address + offset);
   }

   public final long getAddress(long offset) {
      this.checkBounds(offset, (long)(ADDRESS_SIZE >> 3));
      return IO.getAddress(this.address + offset);
   }

   public final DirectMemory getMemory(long offset) {
      this.checkBounds(offset, (long)(ADDRESS_SIZE >> 3));
      long ptr = IO.getAddress(this.address + offset);
      return ptr != 0L ? new NativeMemory(ptr) : null;
   }

   public final byte[] getZeroTerminatedByteArray(long offset) {
      this.checkBounds(offset, 1L);
      return IO.getZeroTerminatedByteArray(this.address + offset, (int)(this.size - offset));
   }

   public void putZeroTerminatedByteArray(long offset, byte[] bytes, int off, int len) {
      this.checkBounds(offset, (long)(len + 1));
      IO.putZeroTerminatedByteArray(this.address + offset, bytes, off, len);
   }

   public final void putByte(long offset, byte value) {
      this.checkBounds(offset, 1L);
      IO.putByte(this.address + offset, value);
   }

   public final void putShort(long offset, short value) {
      this.checkBounds(offset, 2L);
      IO.putShort(this.address + offset, value);
   }

   public final void putInt(long offset, int value) {
      this.checkBounds(offset, 4L);
      IO.putInt(this.address + offset, value);
   }

   public final void putLong(long offset, long value) {
      this.checkBounds(offset, 8L);
      IO.putLong(this.address + offset, value);
   }

   public final void putNativeLong(long offset, long value) {
      if (LONG_SIZE == 32) {
         this.putInt(offset, (int)value);
      } else {
         this.putLong(offset, value);
      }

   }

   public final void putAddress(long offset, long value) {
      this.checkBounds(offset, (long)(ADDRESS_SIZE >> 3));
      IO.putAddress(this.address + offset, value);
   }

   public final void putFloat(long offset, float value) {
      this.checkBounds(offset, 4L);
      IO.putFloat(this.address + offset, value);
   }

   public final void putDouble(long offset, double value) {
      this.checkBounds(offset, 8L);
      IO.putDouble(this.address + offset, value);
   }

   public final void putAddress(long offset, Memory value) {
      this.checkBounds(offset, (long)(ADDRESS_SIZE >> 3));
      IO.putAddress(this.address + offset, ((DirectMemory)value).getAddress());
   }

   public final void get(long offset, byte[] dst, int off, int len) {
      this.checkBounds(offset, (long)len);
      IO.getByteArray(this.address + offset, dst, off, len);
   }

   public final void put(long offset, byte[] src, int off, int len) {
      this.checkBounds(offset, (long)len);
      IO.putByteArray(this.address + offset, src, off, len);
   }

   public final void get(long offset, short[] dst, int off, int len) {
      this.checkBounds(offset, (long)(len << 1));
      IO.getShortArray(this.address + offset, dst, off, len);
   }

   public final void put(long offset, short[] src, int off, int len) {
      this.checkBounds(offset, (long)(len << 1));
      IO.putShortArray(this.address + offset, src, off, len);
   }

   public final void get(long offset, int[] dst, int off, int len) {
      this.checkBounds(offset, (long)(len << 2));
      IO.getIntArray(this.address + offset, dst, off, len);
   }

   public final void put(long offset, int[] src, int off, int len) {
      this.checkBounds(offset, (long)(len << 2));
      IO.putIntArray(this.address + offset, src, off, len);
   }

   public final void get(long offset, long[] dst, int off, int len) {
      this.checkBounds(offset, (long)(len << 3));
      IO.getLongArray(this.address + offset, dst, off, len);
   }

   public final void put(long offset, long[] src, int off, int len) {
      this.checkBounds(offset, (long)(len << 3));
      IO.putLongArray(this.address + offset, src, off, len);
   }

   public final void get(long offset, float[] dst, int off, int len) {
      this.checkBounds(offset, (long)(len << 2));
      IO.getFloatArray(this.address + offset, dst, off, len);
   }

   public final void put(long offset, float[] src, int off, int len) {
      this.checkBounds(offset, (long)(len << 2));
      IO.putFloatArray(this.address + offset, src, off, len);
   }

   public final void get(long offset, double[] dst, int off, int len) {
      this.checkBounds(offset, (long)(len << 3));
      IO.getDoubleArray(this.address + offset, dst, off, len);
   }

   public final void put(long offset, double[] src, int off, int len) {
      this.checkBounds(offset, (long)(len << 3));
      IO.putDoubleArray(this.address + offset, src, off, len);
   }

   public final int indexOf(long offset, byte value) {
      return value == 0 ? (int)IO.getStringLength(this.address + offset) : (int)IO.indexOf(this.address + offset, value);
   }

   public final int indexOf(long offset, byte value, int maxlen) {
      return (int)IO.indexOf(this.address, value, maxlen);
   }

   public final void setMemory(long offset, long size, byte value) {
      this.checkBounds(offset, size);
      IO.setMemory(this.address + offset, size, value);
   }
}
