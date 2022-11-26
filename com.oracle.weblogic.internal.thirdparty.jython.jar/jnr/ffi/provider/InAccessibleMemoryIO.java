package jnr.ffi.provider;

import java.nio.charset.Charset;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

public abstract class InAccessibleMemoryIO extends AbstractMemoryIO {
   private static final String msg = "attempted access to inaccessible memory";

   protected InAccessibleMemoryIO(Runtime runtime, long address, boolean isDirect) {
      super(runtime, address, isDirect);
   }

   protected RuntimeException error() {
      return new IndexOutOfBoundsException("attempted access to inaccessible memory");
   }

   public boolean hasArray() {
      return false;
   }

   public Object array() {
      return null;
   }

   public int arrayOffset() {
      return 0;
   }

   public int arrayLength() {
      return 0;
   }

   public final byte getByte(long offset) {
      throw this.error();
   }

   public final short getShort(long offset) {
      throw this.error();
   }

   public final int getInt(long offset) {
      throw this.error();
   }

   public final long getLong(long offset) {
      throw this.error();
   }

   public final long getLongLong(long offset) {
      throw this.error();
   }

   public final float getFloat(long offset) {
      throw this.error();
   }

   public final double getDouble(long offset) {
      throw this.error();
   }

   public final void putByte(long offset, byte value) {
      throw this.error();
   }

   public final void putShort(long offset, short value) {
      throw this.error();
   }

   public final void putInt(long offset, int value) {
      throw this.error();
   }

   public final void putLong(long offset, long value) {
      throw this.error();
   }

   public final void putLongLong(long offset, long value) {
      throw this.error();
   }

   public final void putFloat(long offset, float value) {
      throw this.error();
   }

   public final void putDouble(long offset, double value) {
      throw this.error();
   }

   public final void get(long offset, byte[] dst, int off, int len) {
      throw this.error();
   }

   public final void put(long offset, byte[] dst, int off, int len) {
      throw this.error();
   }

   public final void get(long offset, short[] dst, int off, int len) {
      throw this.error();
   }

   public final void put(long offset, short[] dst, int off, int len) {
      throw this.error();
   }

   public final void get(long offset, int[] dst, int off, int len) {
      throw this.error();
   }

   public final void put(long offset, int[] src, int off, int len) {
      throw this.error();
   }

   public final void get(long offset, long[] dst, int off, int len) {
      throw this.error();
   }

   public final void put(long offset, long[] src, int off, int len) {
      throw this.error();
   }

   public final void get(long offset, float[] dst, int off, int len) {
      throw this.error();
   }

   public final void put(long offset, float[] src, int off, int len) {
      throw this.error();
   }

   public final void get(long offset, double[] dst, int off, int len) {
      throw this.error();
   }

   public final void put(long offset, double[] src, int off, int len) {
      throw this.error();
   }

   public final Pointer getPointer(long offset, long size) {
      throw this.error();
   }

   public final Pointer getPointer(long offset) {
      throw this.error();
   }

   public final void putPointer(long offset, Pointer value) {
      throw this.error();
   }

   public String getString(long offset) {
      throw this.error();
   }

   public String getString(long offset, int maxLength, Charset cs) {
      throw this.error();
   }

   public void putString(long offset, String string, int maxLength, Charset cs) {
      throw this.error();
   }

   public final int indexOf(long offset, byte value, int maxlen) {
      throw this.error();
   }

   public final void setMemory(long offset, long size, byte value) {
      throw this.error();
   }
}
