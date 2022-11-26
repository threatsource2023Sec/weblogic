package org.python.modules.jffi;

import com.kenai.jffi.MemoryIO;
import com.kenai.jffi.Platform;

class NativeMemory implements Memory, DirectMemory {
   protected static final MemoryIO IO = MemoryIO.getInstance();
   final NativeMemory parent;
   final long address;

   NativeMemory(long address) {
      this.address = address;
      this.parent = null;
   }

   private NativeMemory(NativeMemory parent, long offset) {
      this.parent = parent;
      this.address = parent.address + offset;
   }

   public final long getAddress() {
      return this.address;
   }

   public NativeMemory slice(long offset) {
      return offset == 0L ? this : new NativeMemory(this, offset);
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
      return IO.getByte(this.address + offset);
   }

   public final short getShort(long offset) {
      return IO.getShort(this.address + offset);
   }

   public final int getInt(long offset) {
      return IO.getInt(this.address + offset);
   }

   public final long getLong(long offset) {
      return IO.getLong(this.address + offset);
   }

   public final long getNativeLong(long offset) {
      return Platform.getPlatform().longSize() == 32 ? (long)IO.getInt(this.address + offset) : IO.getLong(this.address + offset);
   }

   public final float getFloat(long offset) {
      return IO.getFloat(this.address + offset);
   }

   public final double getDouble(long offset) {
      return IO.getDouble(this.address + offset);
   }

   public final long getAddress(long offset) {
      return IO.getAddress(this.address + offset);
   }

   public final DirectMemory getMemory(long offset) {
      long ptr = IO.getAddress(this.address + offset);
      return ptr != 0L ? new NativeMemory(ptr) : null;
   }

   public final byte[] getZeroTerminatedByteArray(long offset) {
      return IO.getZeroTerminatedByteArray(this.address + offset);
   }

   public void putZeroTerminatedByteArray(long offset, byte[] bytes, int off, int len) {
      IO.putZeroTerminatedByteArray(this.address + offset, bytes, off, len);
   }

   public final void putByte(long offset, byte value) {
      IO.putByte(this.address + offset, value);
   }

   public final void putShort(long offset, short value) {
      IO.putShort(this.address + offset, value);
   }

   public final void putInt(long offset, int value) {
      IO.putInt(this.address + offset, value);
   }

   public final void putLong(long offset, long value) {
      IO.putLong(this.address + offset, value);
   }

   public final void putNativeLong(long offset, long value) {
      if (Platform.getPlatform().longSize() == 32) {
         IO.putInt(this.address + offset, (int)value);
      } else {
         IO.putLong(this.address + offset, value);
      }

   }

   public final void putAddress(long offset, long value) {
      IO.putAddress(this.address + offset, value);
   }

   public final void putFloat(long offset, float value) {
      IO.putFloat(this.address + offset, value);
   }

   public final void putDouble(long offset, double value) {
      IO.putDouble(this.address + offset, value);
   }

   public final void putAddress(long offset, Memory value) {
      IO.putAddress(this.address + offset, ((DirectMemory)value).getAddress());
   }

   public final void get(long offset, byte[] dst, int off, int len) {
      IO.getByteArray(this.address + offset, dst, off, len);
   }

   public final void put(long offset, byte[] src, int off, int len) {
      IO.putByteArray(this.address + offset, src, off, len);
   }

   public final void get(long offset, short[] dst, int off, int len) {
      IO.getShortArray(this.address + offset, dst, off, len);
   }

   public final void put(long offset, short[] src, int off, int len) {
      IO.putShortArray(this.address + offset, src, off, len);
   }

   public final void get(long offset, int[] dst, int off, int len) {
      IO.getIntArray(this.address + offset, dst, off, len);
   }

   public final void put(long offset, int[] src, int off, int len) {
      IO.putIntArray(this.address + offset, src, off, len);
   }

   public final void get(long offset, long[] dst, int off, int len) {
      IO.getLongArray(this.address + offset, dst, off, len);
   }

   public final void put(long offset, long[] src, int off, int len) {
      IO.putLongArray(this.address + offset, src, off, len);
   }

   public final void get(long offset, float[] dst, int off, int len) {
      IO.getFloatArray(this.address + offset, dst, off, len);
   }

   public final void put(long offset, float[] src, int off, int len) {
      IO.putFloatArray(this.address + offset, src, off, len);
   }

   public final void get(long offset, double[] dst, int off, int len) {
      IO.getDoubleArray(this.address + offset, dst, off, len);
   }

   public final void put(long offset, double[] src, int off, int len) {
      IO.putDoubleArray(this.address + offset, src, off, len);
   }

   public final int indexOf(long offset, byte value) {
      return value == 0 ? (int)IO.getStringLength(this.address + offset) : (int)IO.indexOf(this.address + offset, value);
   }

   public final int indexOf(long offset, byte value, int maxlen) {
      return (int)IO.indexOf(this.address, value, maxlen);
   }

   public final void setMemory(long offset, long size, byte value) {
      IO.setMemory(this.address + offset, size, value);
   }
}
