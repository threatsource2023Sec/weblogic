package jnr.ffi.provider.jffi;

import com.kenai.jffi.MemoryIO;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.provider.AbstractMemoryIO;

class DirectMemoryIO extends AbstractMemoryIO {
   static final MemoryIO IO = MemoryIO.getInstance();

   DirectMemoryIO(Runtime runtime, long address) {
      super(runtime, address, true);
   }

   DirectMemoryIO(Runtime runtime, int address) {
      super(runtime, (long)address & 4294967295L, true);
   }

   public long size() {
      return Long.MAX_VALUE;
   }

   public boolean hasArray() {
      return false;
   }

   public Object array() {
      throw new UnsupportedOperationException("no array");
   }

   public int arrayOffset() {
      throw new UnsupportedOperationException("no array");
   }

   public int arrayLength() {
      throw new UnsupportedOperationException("no array");
   }

   public int hashCode() {
      return (int)(this.address() << 32 ^ this.address());
   }

   public boolean equals(Object obj) {
      return obj instanceof Pointer && ((Pointer)obj).address() == this.address() && ((Pointer)obj).getRuntime().isCompatible(this.getRuntime());
   }

   public final byte getByte(long offset) {
      return IO.getByte(this.address() + offset);
   }

   public final short getShort(long offset) {
      return IO.getShort(this.address() + offset);
   }

   public final int getInt(long offset) {
      return IO.getInt(this.address() + offset);
   }

   public final long getLongLong(long offset) {
      return IO.getLong(this.address() + offset);
   }

   public final float getFloat(long offset) {
      return IO.getFloat(this.address() + offset);
   }

   public final double getDouble(long offset) {
      return IO.getDouble(this.address() + offset);
   }

   public final void putByte(long offset, byte value) {
      IO.putByte(this.address() + offset, value);
   }

   public final void putShort(long offset, short value) {
      IO.putShort(this.address() + offset, value);
   }

   public final void putInt(long offset, int value) {
      IO.putInt(this.address() + offset, value);
   }

   public final void putLongLong(long offset, long value) {
      IO.putLong(this.address() + offset, value);
   }

   public final void putFloat(long offset, float value) {
      IO.putFloat(this.address() + offset, value);
   }

   public final void putDouble(long offset, double value) {
      IO.putDouble(this.address() + offset, value);
   }

   public final void get(long offset, byte[] dst, int off, int len) {
      IO.getByteArray(this.address() + offset, dst, off, len);
   }

   public final void put(long offset, byte[] src, int off, int len) {
      IO.putByteArray(this.address() + offset, src, off, len);
   }

   public final void get(long offset, short[] dst, int off, int len) {
      IO.getShortArray(this.address() + offset, dst, off, len);
   }

   public final void put(long offset, short[] src, int off, int len) {
      IO.putShortArray(this.address() + offset, src, off, len);
   }

   public final void get(long offset, int[] dst, int off, int len) {
      IO.getIntArray(this.address() + offset, dst, off, len);
   }

   public final void put(long offset, int[] src, int off, int len) {
      IO.putIntArray(this.address() + offset, src, off, len);
   }

   public final void get(long offset, long[] dst, int off, int len) {
      IO.getLongArray(this.address() + offset, dst, off, len);
   }

   public final void put(long offset, long[] src, int off, int len) {
      IO.putLongArray(this.address() + offset, src, off, len);
   }

   public final void get(long offset, float[] dst, int off, int len) {
      IO.getFloatArray(this.address() + offset, dst, off, len);
   }

   public final void put(long offset, float[] src, int off, int len) {
      IO.putFloatArray(this.address() + offset, src, off, len);
   }

   public final void get(long offset, double[] dst, int off, int len) {
      IO.getDoubleArray(this.address() + offset, dst, off, len);
   }

   public final void put(long offset, double[] src, int off, int len) {
      IO.putDoubleArray(this.address() + offset, src, off, len);
   }

   public Pointer getPointer(long offset) {
      return MemoryUtil.newPointer(this.getRuntime(), IO.getAddress(this.address() + offset));
   }

   public Pointer getPointer(long offset, long size) {
      return MemoryUtil.newPointer(this.getRuntime(), IO.getAddress(this.address() + offset), size);
   }

   public void putPointer(long offset, Pointer value) {
      IO.putAddress(this.address() + offset, value != null ? value.address() : 0L);
   }

   public String getString(long offset) {
      return Charset.defaultCharset().decode(ByteBuffer.wrap(IO.getZeroTerminatedByteArray(this.address() + offset))).toString();
   }

   public String getString(long offset, int maxLength, Charset cs) {
      byte[] bytes = IO.getZeroTerminatedByteArray(this.address() + offset, maxLength);
      return cs.decode(ByteBuffer.wrap(bytes)).toString();
   }

   public void putString(long offset, String string, int maxLength, Charset cs) {
      ByteBuffer buf = cs.encode(string);
      int len = Math.min(maxLength, buf.remaining());
      IO.putZeroTerminatedByteArray(this.address() + offset, buf.array(), buf.arrayOffset() + buf.position(), len);
   }

   public void putZeroTerminatedByteArray(long offset, byte[] src, int off, int len) {
      IO.putZeroTerminatedByteArray(this.address() + offset, src, off, len);
   }

   public int indexOf(long offset, byte value, int maxlen) {
      return (int)IO.indexOf(this.address() + offset, value, maxlen);
   }

   public final void setMemory(long offset, long size, byte value) {
      IO.setMemory(this.address() + offset, size, value);
   }
}
