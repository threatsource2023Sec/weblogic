package jnr.ffi;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public abstract class Pointer {
   private final Runtime runtime;
   private final long address;
   private final boolean isDirect;

   public static Pointer wrap(Runtime runtime, long address) {
      return runtime.getMemoryManager().newPointer(address);
   }

   public static Pointer wrap(Runtime runtime, long address, long size) {
      return runtime.getMemoryManager().newPointer(address, size);
   }

   public static Pointer wrap(Runtime runtime, ByteBuffer buffer) {
      return runtime.getMemoryManager().newPointer(buffer);
   }

   public static Pointer newIntPointer(Runtime runtime, long address) {
      return runtime.getMemoryManager().newOpaquePointer(address);
   }

   protected Pointer(Runtime runtime, long address, boolean direct) {
      this.runtime = runtime;
      this.address = address;
      this.isDirect = direct;
   }

   public final boolean isDirect() {
      return this.isDirect;
   }

   public final long address() {
      return this.address;
   }

   public final Runtime getRuntime() {
      return this.runtime;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getClass().getName());
      sb.append(String.format("[address=%#x", this.address()));
      if (this.size() != Long.MAX_VALUE) {
         sb.append(String.format(" size=%d", this.size()));
      }

      sb.append(']');
      return sb.toString();
   }

   public abstract long size();

   public abstract boolean hasArray();

   public abstract Object array();

   public abstract int arrayOffset();

   public abstract int arrayLength();

   public abstract byte getByte(long var1);

   public abstract short getShort(long var1);

   public abstract int getInt(long var1);

   public abstract long getLong(long var1);

   public abstract long getLongLong(long var1);

   public abstract float getFloat(long var1);

   public abstract double getDouble(long var1);

   public abstract long getNativeLong(long var1);

   public abstract long getInt(Type var1, long var2);

   public abstract void putByte(long var1, byte var3);

   public abstract void putShort(long var1, short var3);

   public abstract void putInt(long var1, int var3);

   public abstract void putLong(long var1, long var3);

   public abstract void putLongLong(long var1, long var3);

   public abstract void putFloat(long var1, float var3);

   public abstract void putDouble(long var1, double var3);

   public abstract void putNativeLong(long var1, long var3);

   public abstract void putInt(Type var1, long var2, long var4);

   public abstract long getAddress(long var1);

   public abstract void putAddress(long var1, long var3);

   public abstract void putAddress(long var1, Address var3);

   public abstract void get(long var1, byte[] var3, int var4, int var5);

   public abstract void put(long var1, byte[] var3, int var4, int var5);

   public abstract void get(long var1, short[] var3, int var4, int var5);

   public abstract void put(long var1, short[] var3, int var4, int var5);

   public abstract void get(long var1, int[] var3, int var4, int var5);

   public abstract void put(long var1, int[] var3, int var4, int var5);

   public abstract void get(long var1, long[] var3, int var4, int var5);

   public abstract void put(long var1, long[] var3, int var4, int var5);

   public abstract void get(long var1, float[] var3, int var4, int var5);

   public abstract void put(long var1, float[] var3, int var4, int var5);

   public abstract void get(long var1, double[] var3, int var4, int var5);

   public abstract void put(long var1, double[] var3, int var4, int var5);

   public abstract Pointer getPointer(long var1);

   public abstract Pointer getPointer(long var1, long var3);

   public abstract void putPointer(long var1, Pointer var3);

   public abstract String getString(long var1);

   public abstract String getString(long var1, int var3, Charset var4);

   public abstract void putString(long var1, String var3, int var4, Charset var5);

   public abstract Pointer slice(long var1);

   public abstract Pointer slice(long var1, long var3);

   public abstract void transferTo(long var1, Pointer var3, long var4, long var6);

   public abstract void transferFrom(long var1, Pointer var3, long var4, long var6);

   public abstract void checkBounds(long var1, long var3);

   public abstract void setMemory(long var1, long var3, byte var5);

   public abstract int indexOf(long var1, byte var3);

   public abstract int indexOf(long var1, byte var3, int var4);

   public void get(long offset, Pointer[] dst, int idx, int len) {
      int pointerSize = this.getRuntime().addressSize();

      for(int i = 0; i < len; ++i) {
         dst[idx + i] = this.getPointer(offset + (long)(i * pointerSize));
      }

   }

   public void put(long offset, Pointer[] src, int idx, int len) {
      int pointerSize = this.getRuntime().addressSize();

      for(int i = 0; i < len; ++i) {
         this.putPointer(offset + (long)(i * pointerSize), src[idx + i]);
      }

   }

   public String[] getNullTerminatedStringArray(long offset) {
      Pointer ptr;
      if ((ptr = this.getPointer(offset)) == null) {
         return new String[0];
      } else {
         int pointerSize = this.getRuntime().addressSize();
         List array = new ArrayList();
         array.add(ptr.getString(0L));

         for(int off = pointerSize; (ptr = this.getPointer(offset + (long)off)) != null; off += pointerSize) {
            array.add(ptr.getString(0L));
         }

         return (String[])array.toArray(new String[array.size()]);
      }
   }

   public Pointer[] getNullTerminatedPointerArray(long offset) {
      Pointer ptr;
      if ((ptr = this.getPointer(offset)) == null) {
         return new Pointer[0];
      } else {
         int pointerSize = this.getRuntime().addressSize();
         List array = new ArrayList();
         array.add(ptr);

         for(int off = pointerSize; (ptr = this.getPointer(offset + (long)off)) != null; off += pointerSize) {
            array.add(ptr);
         }

         return (Pointer[])array.toArray(new Pointer[array.size()]);
      }
   }
}
