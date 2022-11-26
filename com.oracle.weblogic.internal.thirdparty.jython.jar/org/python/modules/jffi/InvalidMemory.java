package org.python.modules.jffi;

import java.nio.ByteBuffer;
import org.python.core.Py;
import org.python.core.PyException;

public abstract class InvalidMemory implements Memory {
   private final String message;

   public InvalidMemory(String message) {
      this.message = message;
   }

   protected PyException ex() {
      return Py.MemoryError(this.message);
   }

   public Memory slice(long offset) {
      return this;
   }

   public ByteBuffer asByteBuffer() {
      throw this.ex();
   }

   public final byte getByte(long offset) {
      throw this.ex();
   }

   public final short getShort(long offset) {
      throw this.ex();
   }

   public final int getInt(long offset) {
      throw this.ex();
   }

   public final long getLong(long offset) {
      throw this.ex();
   }

   public final long getNativeLong(long offset) {
      throw this.ex();
   }

   public final float getFloat(long offset) {
      throw this.ex();
   }

   public final double getDouble(long offset) {
      throw this.ex();
   }

   public final DirectMemory getMemory(long offset) {
      throw this.ex();
   }

   public final long getAddress(long offset) {
      throw this.ex();
   }

   public final void putByte(long offset, byte value) {
      throw this.ex();
   }

   public final void putShort(long offset, short value) {
      throw this.ex();
   }

   public final void putInt(long offset, int value) {
      throw this.ex();
   }

   public final void putLong(long offset, long value) {
      throw this.ex();
   }

   public final void putNativeLong(long offset, long value) {
      throw this.ex();
   }

   public final void putFloat(long offset, float value) {
      throw this.ex();
   }

   public final void putDouble(long offset, double value) {
      throw this.ex();
   }

   public final void putMemory(long offset, Memory value) {
      throw this.ex();
   }

   public final void putAddress(long offset, long value) {
      throw this.ex();
   }

   public final void putAddress(long offset, Memory value) {
      throw this.ex();
   }

   public final void get(long offset, byte[] dst, int off, int len) {
      throw this.ex();
   }

   public final void put(long offset, byte[] src, int off, int len) {
      throw this.ex();
   }

   public final void get(long offset, short[] dst, int off, int len) {
      throw this.ex();
   }

   public final void put(long offset, short[] src, int off, int len) {
      throw this.ex();
   }

   public final void get(long offset, int[] dst, int off, int len) {
      throw this.ex();
   }

   public final void put(long offset, int[] src, int off, int len) {
      throw this.ex();
   }

   public final void get(long offset, long[] dst, int off, int len) {
      throw this.ex();
   }

   public final void put(long offset, long[] src, int off, int len) {
      throw this.ex();
   }

   public final void get(long offset, float[] dst, int off, int len) {
      throw this.ex();
   }

   public final void put(long offset, float[] src, int off, int len) {
      throw this.ex();
   }

   public final void get(long offset, double[] dst, int off, int len) {
      throw this.ex();
   }

   public final void put(long offset, double[] src, int off, int len) {
      throw this.ex();
   }

   public final int indexOf(long offset, byte value) {
      throw this.ex();
   }

   public final int indexOf(long offset, byte value, int maxlen) {
      throw this.ex();
   }

   public final void setMemory(long offset, long size, byte value) {
      throw this.ex();
   }

   public final void clear() {
      throw this.ex();
   }

   public byte[] getZeroTerminatedByteArray(long offset) {
      throw this.ex();
   }

   public byte[] getZeroTerminatedByteArray(long offset, int maxlen) {
      throw this.ex();
   }

   public void putZeroTerminatedByteArray(long offset, byte[] bytes, int off, int len) {
      throw this.ex();
   }
}
