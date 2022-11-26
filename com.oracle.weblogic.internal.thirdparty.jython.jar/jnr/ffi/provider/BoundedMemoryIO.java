package jnr.ffi.provider;

import java.nio.charset.Charset;
import jnr.ffi.Address;
import jnr.ffi.Pointer;

public final class BoundedMemoryIO extends AbstractMemoryIO implements DelegatingMemoryIO {
   private final long base;
   private final long size;
   private final Pointer io;

   public BoundedMemoryIO(Pointer parent, long offset, long size) {
      super(parent.getRuntime(), parent.address() != 0L ? parent.address() + offset : 0L, parent.isDirect());
      this.io = parent;
      this.base = offset;
      this.size = size;
   }

   public long size() {
      return this.size;
   }

   public final boolean hasArray() {
      return this.io.hasArray();
   }

   public final Object array() {
      return this.io.array();
   }

   public final int arrayOffset() {
      return this.io.arrayOffset() + (int)this.base;
   }

   public final int arrayLength() {
      return (int)this.size;
   }

   public void checkBounds(long offset, long length) {
      checkBounds(this.size, offset, length);
      this.getDelegatedMemoryIO().checkBounds(this.base + offset, length);
   }

   public Pointer getDelegatedMemoryIO() {
      return this.io;
   }

   public int hashCode() {
      return this.getDelegatedMemoryIO().hashCode();
   }

   public boolean equals(Object obj) {
      return obj instanceof BoundedMemoryIO && this.io.equals(((BoundedMemoryIO)obj).io) && ((BoundedMemoryIO)obj).base == this.base && ((BoundedMemoryIO)obj).size == this.size || this.io.equals(obj);
   }

   public byte getByte(long offset) {
      checkBounds(this.size, offset, 1L);
      return this.io.getByte(this.base + offset);
   }

   public short getShort(long offset) {
      checkBounds(this.size, offset, 2L);
      return this.io.getShort(this.base + offset);
   }

   public int getInt(long offset) {
      checkBounds(this.size, offset, 4L);
      return this.io.getInt(this.base + offset);
   }

   public long getLongLong(long offset) {
      checkBounds(this.size, offset, 8L);
      return this.io.getLongLong(this.base + offset);
   }

   public float getFloat(long offset) {
      checkBounds(this.size, offset, 4L);
      return this.io.getFloat(this.base + offset);
   }

   public double getDouble(long offset) {
      checkBounds(this.size, offset, 8L);
      return this.io.getDouble(this.base + offset);
   }

   public Pointer getPointer(long offset) {
      checkBounds(this.size, offset, (long)this.getRuntime().addressSize());
      return this.io.getPointer(this.base + offset);
   }

   public Pointer getPointer(long offset, long size) {
      checkBounds(this.size, this.base + offset, (long)this.getRuntime().addressSize());
      return this.io.getPointer(this.base + offset, size);
   }

   public void putByte(long offset, byte value) {
      checkBounds(this.size, offset, 1L);
      this.io.putByte(this.base + offset, value);
   }

   public void putShort(long offset, short value) {
      checkBounds(this.size, offset, 2L);
      this.io.putShort(this.base + offset, value);
   }

   public void putInt(long offset, int value) {
      checkBounds(this.size, offset, 4L);
      this.io.putInt(this.base + offset, value);
   }

   public void putLongLong(long offset, long value) {
      checkBounds(this.size, offset, 8L);
      this.io.putLongLong(this.base + offset, value);
   }

   public void putFloat(long offset, float value) {
      checkBounds(this.size, offset, 4L);
      this.io.putFloat(this.base + offset, value);
   }

   public void putDouble(long offset, double value) {
      checkBounds(this.size, offset, 8L);
      this.io.putDouble(this.base + offset, value);
   }

   public void putPointer(long offset, Pointer value) {
      checkBounds(this.size, offset, (long)this.getRuntime().addressSize());
      this.io.putPointer(this.base + offset, value);
   }

   public void get(long offset, byte[] dst, int off, int len) {
      checkBounds(this.size, offset, (long)len);
      this.io.get(this.base + offset, dst, off, len);
   }

   public void put(long offset, byte[] dst, int off, int len) {
      checkBounds(this.size, offset, (long)len);
      this.io.put(this.base + offset, dst, off, len);
   }

   public void get(long offset, short[] dst, int off, int len) {
      checkBounds(this.size, offset, (long)(len * 16 / 8));
      this.io.get(this.base + offset, dst, off, len);
   }

   public void put(long offset, short[] dst, int off, int len) {
      checkBounds(this.size, offset, (long)(len * 16 / 8));
      this.io.put(this.base + offset, dst, off, len);
   }

   public void get(long offset, int[] dst, int off, int len) {
      checkBounds(this.size, offset, (long)(len * 32 / 8));
      this.io.get(this.base + offset, dst, off, len);
   }

   public void put(long offset, int[] src, int off, int len) {
      checkBounds(this.size, offset, (long)(len * 32 / 8));
      this.io.put(this.base + offset, src, off, len);
   }

   public void get(long offset, long[] dst, int off, int len) {
      checkBounds(this.size, offset, (long)(len * 64 / 8));
      this.io.get(this.base + offset, dst, off, len);
   }

   public void put(long offset, long[] src, int off, int len) {
      checkBounds(this.size, offset, (long)(len * 64 / 8));
      this.io.put(this.base + offset, src, off, len);
   }

   public void get(long offset, float[] dst, int off, int len) {
      checkBounds(this.size, offset, (long)(len * 32 / 8));
      this.io.get(this.base + offset, dst, off, len);
   }

   public void put(long offset, float[] src, int off, int len) {
      checkBounds(this.size, offset, (long)(len * 32 / 8));
      this.io.put(this.base + offset, src, off, len);
   }

   public void get(long offset, double[] dst, int off, int len) {
      checkBounds(this.size, offset, (long)(len * 64 / 8));
      this.io.get(this.base + offset, dst, off, len);
   }

   public void put(long offset, double[] src, int off, int len) {
      checkBounds(this.size, offset, (long)(len * 64 / 8));
      this.io.put(this.base + offset, src, off, len);
   }

   public long getAddress(long offset) {
      checkBounds(this.size, offset, (long)this.getRuntime().addressSize());
      return this.io.getAddress(this.base + offset);
   }

   public String getString(long offset, int maxLength, Charset cs) {
      checkBounds(this.size, offset, (long)maxLength);
      return this.io.getString(this.base + offset, maxLength, cs);
   }

   public String getString(long offset) {
      return this.io.getString(this.base + offset, (int)this.size, Charset.defaultCharset());
   }

   public void putAddress(long offset, long value) {
      checkBounds(this.size, offset, (long)this.getRuntime().addressSize());
      this.io.putAddress(this.base + offset, value);
   }

   public void putAddress(long offset, Address value) {
      checkBounds(this.size, offset, (long)this.getRuntime().addressSize());
      this.io.putAddress(this.base + offset, value);
   }

   public void putString(long offset, String string, int maxLength, Charset cs) {
      checkBounds(this.size, offset, (long)maxLength);
      this.io.putString(this.base + offset, string, maxLength, cs);
   }

   public int indexOf(long offset, byte value) {
      return this.io.indexOf(this.base + offset, value, (int)this.size);
   }

   public int indexOf(long offset, byte value, int maxlen) {
      checkBounds(this.size, offset, (long)maxlen);
      return this.io.indexOf(this.base + offset, value, maxlen);
   }

   public void setMemory(long offset, long size, byte value) {
      checkBounds(this.size, this.base + offset, size);
      this.io.setMemory(this.base + offset, size, value);
   }

   public void transferFrom(long offset, Pointer other, long otherOffset, long count) {
      checkBounds(this.size, this.base + offset, count);
      this.getDelegatedMemoryIO().transferFrom(offset, other, otherOffset, count);
   }

   public void transferTo(long offset, Pointer other, long otherOffset, long count) {
      checkBounds(this.size, this.base + offset, count);
      this.getDelegatedMemoryIO().transferTo(offset, other, otherOffset, count);
   }
}
