package com.kenai.jffi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import sun.misc.Unsafe;

public abstract class MemoryIO {
   final Foreign foreign;
   private static final long ADDRESS_MASK = Platform.getPlatform().addressMask();

   public static MemoryIO getInstance() {
      return MemoryIO.SingletonHolder.INSTANCE;
   }

   public static MemoryIO getCheckedInstance() {
      return MemoryIO.CheckedMemorySingletonHolder.INSTANCE;
   }

   private MemoryIO() {
      this.foreign = Foreign.getInstance();
   }

   private static MemoryIO newMemoryIO() {
      try {
         if (Boolean.getBoolean("jffi.memory.checked")) {
            return newNativeCheckedImpl();
         } else {
            return !Boolean.getBoolean("jffi.unsafe.disabled") && isUnsafeAvailable() ? newUnsafeImpl() : newNativeImpl();
         }
      } catch (Throwable var1) {
         return newNativeImpl();
      }
   }

   private static MemoryIO newNativeImpl() {
      return Platform.getPlatform().addressSize() == 32 ? newNativeImpl32() : newNativeImpl64();
   }

   private static MemoryIO newNativeCheckedImpl() {
      return (MemoryIO)(Foreign.isMemoryProtectionEnabled() ? new CheckedNativeImpl() : newNativeImpl());
   }

   private static MemoryIO newNativeImpl32() {
      return new NativeImpl32();
   }

   private static MemoryIO newNativeImpl64() {
      return new NativeImpl64();
   }

   private static MemoryIO newUnsafeImpl() {
      return Platform.getPlatform().addressSize() == 32 ? newUnsafeImpl32() : newUnsafeImpl64();
   }

   private static MemoryIO newUnsafeImpl32() {
      return new UnsafeImpl32();
   }

   private static MemoryIO newUnsafeImpl64() {
      return new UnsafeImpl64();
   }

   public abstract byte getByte(long var1);

   public abstract short getShort(long var1);

   public abstract int getInt(long var1);

   public abstract long getLong(long var1);

   public abstract float getFloat(long var1);

   public abstract double getDouble(long var1);

   public abstract long getAddress(long var1);

   public abstract void putByte(long var1, byte var3);

   public abstract void putShort(long var1, short var3);

   public abstract void putInt(long var1, int var3);

   public abstract void putLong(long var1, long var3);

   public abstract void putFloat(long var1, float var3);

   public abstract void putDouble(long var1, double var3);

   public abstract void putAddress(long var1, long var3);

   public final void copyMemory(long src, long dst, long size) {
      if (dst + size > src && src + size > dst) {
         this.memmove(dst, src, size);
      } else {
         this._copyMemory(src, dst, size);
      }

   }

   abstract void _copyMemory(long var1, long var3, long var5);

   public abstract void setMemory(long var1, long var3, byte var5);

   public abstract void memcpy(long var1, long var3, long var5);

   public abstract void memmove(long var1, long var3, long var5);

   public final void memset(long address, int value, long size) {
      this.setMemory(address, size, (byte)value);
   }

   public abstract long memchr(long var1, int var3, long var4);

   public abstract void putByteArray(long var1, byte[] var3, int var4, int var5);

   public abstract void getByteArray(long var1, byte[] var3, int var4, int var5);

   public abstract void putCharArray(long var1, char[] var3, int var4, int var5);

   public abstract void getCharArray(long var1, char[] var3, int var4, int var5);

   public abstract void putShortArray(long var1, short[] var3, int var4, int var5);

   public abstract void getShortArray(long var1, short[] var3, int var4, int var5);

   public abstract void putIntArray(long var1, int[] var3, int var4, int var5);

   public abstract void getIntArray(long var1, int[] var3, int var4, int var5);

   public abstract void putLongArray(long var1, long[] var3, int var4, int var5);

   public abstract void getLongArray(long var1, long[] var3, int var4, int var5);

   public abstract void putFloatArray(long var1, float[] var3, int var4, int var5);

   public abstract void getFloatArray(long var1, float[] var3, int var4, int var5);

   public abstract void putDoubleArray(long var1, double[] var3, int var4, int var5);

   public abstract void getDoubleArray(long var1, double[] var3, int var4, int var5);

   public final long allocateMemory(long size, boolean clear) {
      return Foreign.allocateMemory(size, clear) & ADDRESS_MASK;
   }

   public final void freeMemory(long address) {
      Foreign.freeMemory(address);
   }

   public abstract long getStringLength(long var1);

   public abstract byte[] getZeroTerminatedByteArray(long var1);

   public abstract byte[] getZeroTerminatedByteArray(long var1, int var3);

   /** @deprecated */
   @Deprecated
   public final byte[] getZeroTerminatedByteArray(long address, long maxlen) {
      return this.getZeroTerminatedByteArray(address, (int)maxlen);
   }

   public abstract void putZeroTerminatedByteArray(long var1, byte[] var3, int var4, int var5);

   public final long indexOf(long address, byte value) {
      long location = this.memchr(address, value, 2147483647L);
      return location != 0L ? location - address : -1L;
   }

   public final long indexOf(long address, byte value, int maxlen) {
      long location = this.memchr(address, value, (long)maxlen);
      return location != 0L ? location - address : -1L;
   }

   public final ByteBuffer newDirectByteBuffer(long address, int capacity) {
      return this.foreign.newDirectByteBuffer(address, capacity);
   }

   public final long getDirectBufferAddress(Buffer buffer) {
      return this.foreign.getDirectBufferAddress(buffer);
   }

   private static void verifyAccessor(Class unsafeClass, Class primitive) throws NoSuchMethodException {
      String primitiveName = primitive.getSimpleName();
      String typeName = primitiveName.substring(0, 1).toUpperCase() + primitiveName.substring(1);
      Method get = unsafeClass.getDeclaredMethod("get" + typeName, Long.TYPE);
      if (!get.getReturnType().equals(primitive)) {
         throw new NoSuchMethodException("Incorrect return type for " + get.getName());
      } else {
         unsafeClass.getDeclaredMethod("put" + typeName, Long.TYPE, primitive);
      }
   }

   static boolean isUnsafeAvailable() {
      try {
         Class sunClass = Class.forName("sun.misc.Unsafe");
         Class[] primitiveTypes = new Class[]{Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE};
         Class[] var2 = primitiveTypes;
         int var3 = primitiveTypes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class type = var2[var4];
            verifyAccessor(sunClass, type);
         }

         sunClass.getDeclaredMethod("getAddress", Long.TYPE);
         sunClass.getDeclaredMethod("putAddress", Long.TYPE, Long.TYPE);
         sunClass.getDeclaredMethod("allocateMemory", Long.TYPE);
         sunClass.getDeclaredMethod("freeMemory", Long.TYPE);
         return true;
      } catch (Throwable var6) {
         return false;
      }
   }

   // $FF: synthetic method
   MemoryIO(Object x0) {
      this();
   }

   private static class UnsafeImpl64 extends UnsafeImpl {
      private UnsafeImpl64() {
         super(null);
      }

      public final long getAddress(long address) {
         return unsafe.getLong(address);
      }

      public final void putAddress(long address, long value) {
         unsafe.putLong(address, value);
      }

      // $FF: synthetic method
      UnsafeImpl64(Object x0) {
         this();
      }
   }

   private static class UnsafeImpl32 extends UnsafeImpl {
      private UnsafeImpl32() {
         super(null);
      }

      public final long getAddress(long address) {
         return (long)unsafe.getInt(address) & MemoryIO.ADDRESS_MASK;
      }

      public final void putAddress(long address, long value) {
         unsafe.putInt(address, (int)value);
      }

      // $FF: synthetic method
      UnsafeImpl32(Object x0) {
         this();
      }
   }

   private abstract static class UnsafeImpl extends MemoryIO {
      protected static Unsafe unsafe = (Unsafe)Unsafe.class.cast(getUnsafe());

      private UnsafeImpl() {
         super(null);
      }

      private static Object getUnsafe() {
         try {
            Class sunUnsafe = Class.forName("sun.misc.Unsafe");
            Field f = sunUnsafe.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return f.get(sunUnsafe);
         } catch (Exception var2) {
            throw new RuntimeException(var2);
         }
      }

      public final byte getByte(long address) {
         return unsafe.getByte(address);
      }

      public final short getShort(long address) {
         return unsafe.getShort(address);
      }

      public final int getInt(long address) {
         return unsafe.getInt(address);
      }

      public final long getLong(long address) {
         return unsafe.getLong(address);
      }

      public final float getFloat(long address) {
         return unsafe.getFloat(address);
      }

      public final double getDouble(long address) {
         return unsafe.getDouble(address);
      }

      public final void putByte(long address, byte value) {
         unsafe.putByte(address, value);
      }

      public final void putShort(long address, short value) {
         unsafe.putShort(address, value);
      }

      public final void putInt(long address, int value) {
         unsafe.putInt(address, value);
      }

      public final void putLong(long address, long value) {
         unsafe.putLong(address, value);
      }

      public final void putFloat(long address, float value) {
         unsafe.putFloat(address, value);
      }

      public final void putDouble(long address, double value) {
         unsafe.putDouble(address, value);
      }

      public final void _copyMemory(long src, long dst, long size) {
         unsafe.copyMemory(src, dst, size);
      }

      public final void setMemory(long src, long size, byte value) {
         unsafe.setMemory(src, size, value);
      }

      public final void memcpy(long dst, long src, long size) {
         Foreign.memcpy(dst, src, size);
      }

      public final void memmove(long dst, long src, long size) {
         Foreign.memmove(dst, src, size);
      }

      public final long memchr(long address, int value, long size) {
         return Foreign.memchr(address, value, size);
      }

      public final void putByteArray(long address, byte[] data, int offset, int length) {
         Foreign.putByteArray(address, data, offset, length);
      }

      public final void getByteArray(long address, byte[] data, int offset, int length) {
         Foreign.getByteArray(address, data, offset, length);
      }

      public final void putCharArray(long address, char[] data, int offset, int length) {
         Foreign.putCharArray(address, data, offset, length);
      }

      public final void getCharArray(long address, char[] data, int offset, int length) {
         Foreign.getCharArray(address, data, offset, length);
      }

      public final void putShortArray(long address, short[] data, int offset, int length) {
         Foreign.putShortArray(address, data, offset, length);
      }

      public final void getShortArray(long address, short[] data, int offset, int length) {
         Foreign.getShortArray(address, data, offset, length);
      }

      public final void putIntArray(long address, int[] data, int offset, int length) {
         Foreign.putIntArray(address, data, offset, length);
      }

      public final void getIntArray(long address, int[] data, int offset, int length) {
         Foreign.getIntArray(address, data, offset, length);
      }

      public final void putLongArray(long address, long[] data, int offset, int length) {
         Foreign.putLongArray(address, data, offset, length);
      }

      public final void getLongArray(long address, long[] data, int offset, int length) {
         Foreign.getLongArray(address, data, offset, length);
      }

      public final void putFloatArray(long address, float[] data, int offset, int length) {
         Foreign.putFloatArray(address, data, offset, length);
      }

      public final void getFloatArray(long address, float[] data, int offset, int length) {
         Foreign.getFloatArray(address, data, offset, length);
      }

      public final void putDoubleArray(long address, double[] data, int offset, int length) {
         Foreign.putDoubleArray(address, data, offset, length);
      }

      public final void getDoubleArray(long address, double[] data, int offset, int length) {
         Foreign.getDoubleArray(address, data, offset, length);
      }

      public final long getStringLength(long address) {
         return Foreign.strlen(address);
      }

      public final byte[] getZeroTerminatedByteArray(long address) {
         return Foreign.getZeroTerminatedByteArray(address);
      }

      public final byte[] getZeroTerminatedByteArray(long address, int maxlen) {
         return Foreign.getZeroTerminatedByteArray(address, maxlen);
      }

      public final void putZeroTerminatedByteArray(long address, byte[] data, int offset, int length) {
         Foreign.putZeroTerminatedByteArray(address, data, offset, length);
      }

      // $FF: synthetic method
      UnsafeImpl(Object x0) {
         this();
      }
   }

   private static final class NativeImpl64 extends NativeImpl {
      private NativeImpl64() {
         super(null);
      }

      public final long getAddress(long address) {
         return Foreign.getLong(address);
      }

      public final void putAddress(long address, long value) {
         Foreign.putLong(address, value);
      }

      // $FF: synthetic method
      NativeImpl64(Object x0) {
         this();
      }
   }

   private static final class CheckedNativeImpl extends MemoryIO {
      private CheckedNativeImpl() {
         super(null);
      }

      public final byte getByte(long address) {
         return Foreign.getByteChecked(address);
      }

      public final short getShort(long address) {
         return Foreign.getShortChecked(address);
      }

      public final int getInt(long address) {
         return Foreign.getIntChecked(address);
      }

      public final long getLong(long address) {
         return Foreign.getLongChecked(address);
      }

      public final float getFloat(long address) {
         return Foreign.getFloatChecked(address);
      }

      public final double getDouble(long address) {
         return Foreign.getDoubleChecked(address);
      }

      public final void putByte(long address, byte value) {
         Foreign.putByteChecked(address, value);
      }

      public final void putShort(long address, short value) {
         Foreign.putShortChecked(address, value);
      }

      public final void putInt(long address, int value) {
         Foreign.putIntChecked(address, value);
      }

      public final void putLong(long address, long value) {
         Foreign.putLongChecked(address, value);
      }

      public final void putFloat(long address, float value) {
         Foreign.putFloatChecked(address, value);
      }

      public final void putDouble(long address, double value) {
         Foreign.putDoubleChecked(address, value);
      }

      public final void setMemory(long address, long size, byte value) {
         Foreign.setMemoryChecked(address, size, value);
      }

      public final void _copyMemory(long src, long dst, long size) {
         Foreign.copyMemoryChecked(src, dst, size);
      }

      public final long getAddress(long address) {
         return Foreign.getAddressChecked(address) & MemoryIO.ADDRESS_MASK;
      }

      public final void putAddress(long address, long value) {
         Foreign.putAddressChecked(address, value);
      }

      public final void memcpy(long dst, long src, long size) {
         Foreign.memcpyChecked(dst, src, size);
      }

      public final void memmove(long dst, long src, long size) {
         Foreign.memmoveChecked(dst, src, size);
      }

      public final long memchr(long address, int value, long size) {
         return Foreign.memchrChecked(address, value, size);
      }

      public final void putByteArray(long address, byte[] data, int offset, int length) {
         Foreign.putByteArrayChecked(address, data, offset, length);
      }

      public final void getByteArray(long address, byte[] data, int offset, int length) {
         Foreign.getByteArrayChecked(address, data, offset, length);
      }

      public final void putCharArray(long address, char[] data, int offset, int length) {
         Foreign.putCharArrayChecked(address, data, offset, length);
      }

      public final void getCharArray(long address, char[] data, int offset, int length) {
         Foreign.getCharArrayChecked(address, data, offset, length);
      }

      public final void putShortArray(long address, short[] data, int offset, int length) {
         Foreign.putShortArrayChecked(address, data, offset, length);
      }

      public final void getShortArray(long address, short[] data, int offset, int length) {
         Foreign.getShortArrayChecked(address, data, offset, length);
      }

      public final void putIntArray(long address, int[] data, int offset, int length) {
         Foreign.putIntArrayChecked(address, data, offset, length);
      }

      public final void getIntArray(long address, int[] data, int offset, int length) {
         Foreign.getIntArrayChecked(address, data, offset, length);
      }

      public final void putLongArray(long address, long[] data, int offset, int length) {
         Foreign.putLongArrayChecked(address, data, offset, length);
      }

      public final void getLongArray(long address, long[] data, int offset, int length) {
         Foreign.getLongArrayChecked(address, data, offset, length);
      }

      public final void putFloatArray(long address, float[] data, int offset, int length) {
         Foreign.putFloatArrayChecked(address, data, offset, length);
      }

      public final void getFloatArray(long address, float[] data, int offset, int length) {
         Foreign.getFloatArrayChecked(address, data, offset, length);
      }

      public final void putDoubleArray(long address, double[] data, int offset, int length) {
         Foreign.putDoubleArrayChecked(address, data, offset, length);
      }

      public final void getDoubleArray(long address, double[] data, int offset, int length) {
         Foreign.getDoubleArrayChecked(address, data, offset, length);
      }

      public final long getStringLength(long address) {
         return Foreign.strlenChecked(address);
      }

      public final byte[] getZeroTerminatedByteArray(long address) {
         return Foreign.getZeroTerminatedByteArrayChecked(address);
      }

      public final byte[] getZeroTerminatedByteArray(long address, int maxlen) {
         return Foreign.getZeroTerminatedByteArrayChecked(address, maxlen);
      }

      public final void putZeroTerminatedByteArray(long address, byte[] data, int offset, int length) {
         Foreign.putZeroTerminatedByteArrayChecked(address, data, offset, length);
      }

      // $FF: synthetic method
      CheckedNativeImpl(Object x0) {
         this();
      }
   }

   private static final class NativeImpl32 extends NativeImpl {
      private NativeImpl32() {
         super(null);
      }

      public final long getAddress(long address) {
         return (long)Foreign.getInt(address) & MemoryIO.ADDRESS_MASK;
      }

      public final void putAddress(long address, long value) {
         Foreign.putInt(address, (int)value);
      }

      // $FF: synthetic method
      NativeImpl32(Object x0) {
         this();
      }
   }

   private abstract static class NativeImpl extends MemoryIO {
      private NativeImpl() {
         super(null);
      }

      public final byte getByte(long address) {
         return Foreign.getByte(address);
      }

      public final short getShort(long address) {
         return Foreign.getShort(address);
      }

      public final int getInt(long address) {
         return Foreign.getInt(address);
      }

      public final long getLong(long address) {
         return Foreign.getLong(address);
      }

      public final float getFloat(long address) {
         return Foreign.getFloat(address);
      }

      public final double getDouble(long address) {
         return Foreign.getDouble(address);
      }

      public final void putByte(long address, byte value) {
         Foreign.putByte(address, value);
      }

      public final void putShort(long address, short value) {
         Foreign.putShort(address, value);
      }

      public final void putInt(long address, int value) {
         Foreign.putInt(address, value);
      }

      public final void putLong(long address, long value) {
         Foreign.putLong(address, value);
      }

      public final void putFloat(long address, float value) {
         Foreign.putFloat(address, value);
      }

      public final void putDouble(long address, double value) {
         Foreign.putDouble(address, value);
      }

      public final void setMemory(long address, long size, byte value) {
         Foreign.setMemory(address, size, value);
      }

      public final void _copyMemory(long src, long dst, long size) {
         Foreign.copyMemory(src, dst, size);
      }

      public final void memcpy(long dst, long src, long size) {
         Foreign.memcpy(dst, src, size);
      }

      public final void memmove(long dst, long src, long size) {
         Foreign.memmove(dst, src, size);
      }

      public final long memchr(long address, int value, long size) {
         return Foreign.memchr(address, value, size);
      }

      public final void putByteArray(long address, byte[] data, int offset, int length) {
         Foreign.putByteArray(address, data, offset, length);
      }

      public final void getByteArray(long address, byte[] data, int offset, int length) {
         Foreign.getByteArray(address, data, offset, length);
      }

      public final void putCharArray(long address, char[] data, int offset, int length) {
         Foreign.putCharArray(address, data, offset, length);
      }

      public final void getCharArray(long address, char[] data, int offset, int length) {
         Foreign.getCharArray(address, data, offset, length);
      }

      public final void putShortArray(long address, short[] data, int offset, int length) {
         Foreign.putShortArray(address, data, offset, length);
      }

      public final void getShortArray(long address, short[] data, int offset, int length) {
         Foreign.getShortArray(address, data, offset, length);
      }

      public final void putIntArray(long address, int[] data, int offset, int length) {
         Foreign.putIntArray(address, data, offset, length);
      }

      public final void getIntArray(long address, int[] data, int offset, int length) {
         Foreign.getIntArray(address, data, offset, length);
      }

      public final void putLongArray(long address, long[] data, int offset, int length) {
         Foreign.putLongArray(address, data, offset, length);
      }

      public final void getLongArray(long address, long[] data, int offset, int length) {
         Foreign.getLongArray(address, data, offset, length);
      }

      public final void putFloatArray(long address, float[] data, int offset, int length) {
         Foreign.putFloatArray(address, data, offset, length);
      }

      public final void getFloatArray(long address, float[] data, int offset, int length) {
         Foreign.getFloatArray(address, data, offset, length);
      }

      public final void putDoubleArray(long address, double[] data, int offset, int length) {
         Foreign.putDoubleArray(address, data, offset, length);
      }

      public final void getDoubleArray(long address, double[] data, int offset, int length) {
         Foreign.getDoubleArray(address, data, offset, length);
      }

      public final long getStringLength(long address) {
         return Foreign.strlen(address);
      }

      public final byte[] getZeroTerminatedByteArray(long address) {
         return Foreign.getZeroTerminatedByteArray(address);
      }

      public final byte[] getZeroTerminatedByteArray(long address, int maxlen) {
         return Foreign.getZeroTerminatedByteArray(address, maxlen);
      }

      public final void putZeroTerminatedByteArray(long address, byte[] data, int offset, int length) {
         Foreign.putZeroTerminatedByteArray(address, data, offset, length);
      }

      // $FF: synthetic method
      NativeImpl(Object x0) {
         this();
      }
   }

   private static final class CheckedMemorySingletonHolder {
      private static final MemoryIO INSTANCE = MemoryIO.newNativeCheckedImpl();
   }

   private static final class SingletonHolder {
      private static final MemoryIO INSTANCE = MemoryIO.newMemoryIO();
   }
}
