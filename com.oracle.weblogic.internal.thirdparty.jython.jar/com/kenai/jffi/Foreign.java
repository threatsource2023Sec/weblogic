package com.kenai.jffi;

import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;

final class Foreign {
   public static final int VERSION_MAJOR = getVersionField("MAJOR");
   public static final int VERSION_MINOR = getVersionField("MINOR");
   public static final int VERSION_MICRO = getVersionField("MICRO");
   public static final int TYPE_VOID = 0;
   public static final int TYPE_FLOAT = 2;
   public static final int TYPE_DOUBLE = 3;
   public static final int TYPE_LONGDOUBLE = 4;
   public static final int TYPE_UINT8 = 5;
   public static final int TYPE_SINT8 = 6;
   public static final int TYPE_UINT16 = 7;
   public static final int TYPE_SINT16 = 8;
   public static final int TYPE_UINT32 = 9;
   public static final int TYPE_SINT32 = 10;
   public static final int TYPE_UINT64 = 11;
   public static final int TYPE_SINT64 = 12;
   public static final int TYPE_STRUCT = 13;
   public static final int TYPE_POINTER = 14;
   public static final int TYPE_UCHAR = 101;
   public static final int TYPE_SCHAR = 102;
   public static final int TYPE_USHORT = 103;
   public static final int TYPE_SSHORT = 104;
   public static final int TYPE_UINT = 105;
   public static final int TYPE_SINT = 106;
   public static final int TYPE_ULONG = 107;
   public static final int TYPE_SLONG = 108;
   public static final int RTLD_LAZY = 1;
   public static final int RTLD_NOW = 2;
   public static final int RTLD_LOCAL = 4;
   public static final int RTLD_GLOBAL = 8;
   public static final int PROT_READ = 1;
   public static final int PROT_WRITE = 2;
   public static final int PROT_EXEC = 4;
   public static final int PROT_NONE = 0;
   public static final int MAP_SHARED = 1;
   public static final int MAP_PRIVATE = 2;
   public static final int MAP_FIXED = 16;
   public static final int MAP_NORESERVE = 64;
   public static final int MAP_ANON = 256;
   public static final int MAP_ALIGN = 512;
   public static final int MAP_TEXT = 1024;
   public static final int PAGE_NOACCESS = 1;
   public static final int PAGE_READONLY = 2;
   public static final int PAGE_READWRITE = 4;
   public static final int PAGE_WRITECOPY = 8;
   public static final int PAGE_EXECUTE = 16;
   public static final int PAGE_EXECUTE_READ = 32;
   public static final int PAGE_EXECUTE_READWRITE = 64;
   public static final int PAGE_EXECUTE_WRITECOPY = 128;
   public static final int MEM_COMMIT = 4096;
   public static final int MEM_RESERVE = 8192;
   public static final int MEM_DECOMMIT = 16384;
   public static final int MEM_RELEASE = 32768;
   public static final int MEM_FREE = 65536;
   public static final int MEM_PRIVATE = 131072;
   public static final int MEM_MAPPED = 262144;
   public static final int MEM_RESET = 524288;
   public static final int MEM_TOP_DOWN = 1048576;
   public static final int MEM_PHYSICAL = 4194304;
   public static final int MEM_4MB_PAGES = Integer.MIN_VALUE;
   public static final int JNI_OK = 0;
   public static final int JNI_ERR = -1;
   public static final int JNI_EDETACHED = -2;
   public static final int JNI_EVERSION = -3;
   public static final int JNI_ENOMEM = -4;
   public static final int JNI_EEXIST = -5;
   public static final int JNI_EINVAL = -6;
   public static final int F_DEFAULT = 0;
   public static final int F_STDCALL = 1;
   public static final int F_NOERRNO = 2;
   public static final int F_PROTECT = 4;

   private static UnsatisfiedLinkError newLoadError(Throwable cause) {
      UnsatisfiedLinkError error = new UnsatisfiedLinkError(cause.getMessage());
      error.initCause(cause);
      return error;
   }

   public static Foreign getInstance() {
      return Foreign.InstanceHolder.INSTANCE.getForeign();
   }

   private Foreign() {
   }

   private static int getVersionField(String name) {
      try {
         Class c = Class.forName(Foreign.class.getPackage().getName() + ".Version");
         return (Integer)c.getField(name).get(c);
      } catch (Throwable var2) {
         throw new RuntimeException(var2);
      }
   }

   final native int getVersion();

   private native void init();

   private static native boolean isFaultProtectionEnabled();

   static boolean isMemoryProtectionEnabled() {
      try {
         return isFaultProtectionEnabled();
      } catch (UnsatisfiedLinkError var1) {
         return false;
      }
   }

   static native long dlopen(String var0, int var1);

   static native void dlclose(long var0);

   static native long dlsym(long var0, String var2);

   static native String dlerror();

   static native long allocateMemory(long var0, boolean var2);

   static native void freeMemory(long var0);

   static native long pageSize();

   static native long mmap(long var0, long var2, int var4, int var5, int var6, long var7);

   static native int munmap(long var0, long var2);

   static native int mprotect(long var0, long var2, int var4);

   static native long VirtualAlloc(long var0, int var2, int var3, int var4);

   static native boolean VirtualFree(long var0, int var2, int var3);

   static native boolean VirtualProtect(long var0, int var2, int var3);

   final native long newCallContext(long var1, long[] var3, int var4);

   final native void freeCallContext(long var1);

   final native int getCallContextRawParameterSize(long var1);

   final native boolean isRawParameterPackingEnabled();

   static native int getLastError();

   static native void setLastError(int var0);

   final native long newClosureMagazine(long var1, Method var3, boolean var4);

   final native void freeClosureMagazine(long var1);

   final native long closureMagazineGet(long var1, Object var3);

   final native long lookupBuiltinType(int var1);

   final native int getTypeSize(long var1);

   final native int getTypeAlign(long var1);

   final native int getTypeType(long var1);

   final native long newStruct(long[] var1, boolean var2);

   final native long newArray(long var1, int var3);

   final native void freeAggregate(long var1);

   static native int invokeI0(long var0, long var2);

   static native int invokeI0NoErrno(long var0, long var2);

   static native int invokeI1(long var0, long var2, int var4);

   static native int invokeI1NoErrno(long var0, long var2, int var4);

   static native int invokeI2(long var0, long var2, int var4, int var5);

   static native int invokeI2NoErrno(long var0, long var2, int var4, int var5);

   static native int invokeI3(long var0, long var2, int var4, int var5, int var6);

   static native int invokeI4(long var0, long var2, int var4, int var5, int var6, int var7);

   static native int invokeI5(long var0, long var2, int var4, int var5, int var6, int var7, int var8);

   static native int invokeI6(long var0, long var2, int var4, int var5, int var6, int var7, int var8, int var9);

   static native int invokeI3NoErrno(long var0, long var2, int var4, int var5, int var6);

   static native int invokeI4NoErrno(long var0, long var2, int var4, int var5, int var6, int var7);

   static native int invokeI5NoErrno(long var0, long var2, int var4, int var5, int var6, int var7, int var8);

   static native int invokeI6NoErrno(long var0, long var2, int var4, int var5, int var6, int var7, int var8, int var9);

   static native long invokeL0(long var0, long var2);

   static native long invokeL1(long var0, long var2, long var4);

   static native long invokeL2(long var0, long var2, long var4, long var6);

   static native long invokeL3(long var0, long var2, long var4, long var6, long var8);

   static native long invokeL4(long var0, long var2, long var4, long var6, long var8, long var10);

   static native long invokeL5(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

   static native long invokeL6(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14);

   static native long invokeL0NoErrno(long var0, long var2);

   static native long invokeL1NoErrno(long var0, long var2, long var4);

   static native long invokeL2NoErrno(long var0, long var2, long var4, long var6);

   static native long invokeL3NoErrno(long var0, long var2, long var4, long var6, long var8);

   static native long invokeL4NoErrno(long var0, long var2, long var4, long var6, long var8, long var10);

   static native long invokeL5NoErrno(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

   static native long invokeL6NoErrno(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14);

   static native long invokeN0(long var0, long var2);

   static native long invokeN1(long var0, long var2, long var4);

   static native long invokeN2(long var0, long var2, long var4, long var6);

   static native long invokeN3(long var0, long var2, long var4, long var6, long var8);

   static native long invokeN4(long var0, long var2, long var4, long var6, long var8, long var10);

   static native long invokeN5(long var0, long var2, long var4, long var6, long var8, long var10, long var12);

   static native long invokeN6(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14);

   static native long invokeN1O1(long var0, long var2, long var4, Object var6, int var7, int var8, int var9);

   static native long invokeN2O1(long var0, long var2, long var4, long var6, Object var8, int var9, int var10, int var11);

   static native long invokeN2O2(long var0, long var2, long var4, long var6, Object var8, int var9, int var10, int var11, Object var12, int var13, int var14, int var15);

   static native long invokeN3O1(long var0, long var2, long var4, long var6, long var8, Object var10, int var11, int var12, int var13);

   static native long invokeN3O2(long var0, long var2, long var4, long var6, long var8, Object var10, int var11, int var12, int var13, Object var14, int var15, int var16, int var17);

   static native long invokeN3O3(long var0, long var2, long var4, long var6, long var8, Object var10, int var11, int var12, int var13, Object var14, int var15, int var16, int var17, Object var18, int var19, int var20, int var21);

   static native long invokeN4O1(long var0, long var2, long var4, long var6, long var8, long var10, Object var12, int var13, int var14, int var15);

   static native long invokeN4O2(long var0, long var2, long var4, long var6, long var8, long var10, Object var12, int var13, int var14, int var15, Object var16, int var17, int var18, int var19);

   static native long invokeN4O3(long var0, long var2, long var4, long var6, long var8, long var10, Object var12, int var13, int var14, int var15, Object var16, int var17, int var18, int var19, Object var20, int var21, int var22, int var23);

   static native long invokeN4O4(long var0, long var2, long var4, long var6, long var8, long var10, Object var12, int var13, int var14, int var15, Object var16, int var17, int var18, int var19, Object var20, int var21, int var22, int var23, Object var24, int var25, int var26, int var27);

   static native long invokeN5O1(long var0, long var2, long var4, long var6, long var8, long var10, long var12, Object var14, int var15, int var16, int var17);

   static native long invokeN5O2(long var0, long var2, long var4, long var6, long var8, long var10, long var12, Object var14, int var15, int var16, int var17, Object var18, int var19, int var20, int var21);

   static native long invokeN5O3(long var0, long var2, long var4, long var6, long var8, long var10, long var12, Object var14, int var15, int var16, int var17, Object var18, int var19, int var20, int var21, Object var22, int var23, int var24, int var25);

   static native long invokeN5O4(long var0, long var2, long var4, long var6, long var8, long var10, long var12, Object var14, int var15, int var16, int var17, Object var18, int var19, int var20, int var21, Object var22, int var23, int var24, int var25, Object var26, int var27, int var28, int var29);

   static native long invokeN5O5(long var0, long var2, long var4, long var6, long var8, long var10, long var12, Object var14, int var15, int var16, int var17, Object var18, int var19, int var20, int var21, Object var22, int var23, int var24, int var25, Object var26, int var27, int var28, int var29, Object var30, int var31, int var32, int var33);

   static native long invokeN6O1(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, Object var16, int var17, int var18, int var19);

   static native long invokeN6O2(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, Object var16, int var17, int var18, int var19, Object var20, int var21, int var22, int var23);

   static native long invokeN6O3(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, Object var16, int var17, int var18, int var19, Object var20, int var21, int var22, int var23, Object var24, int var25, int var26, int var27);

   static native long invokeN6O4(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, Object var16, int var17, int var18, int var19, Object var20, int var21, int var22, int var23, Object var24, int var25, int var26, int var27, Object var28, int var29, int var30, int var31);

   static native long invokeN6O5(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, Object var16, int var17, int var18, int var19, Object var20, int var21, int var22, int var23, Object var24, int var25, int var26, int var27, Object var28, int var29, int var30, int var31, Object var32, int var33, int var34, int var35);

   static native long invokeN6O6(long var0, long var2, long var4, long var6, long var8, long var10, long var12, long var14, Object var16, int var17, int var18, int var19, Object var20, int var21, int var22, int var23, Object var24, int var25, int var26, int var27, Object var28, int var29, int var30, int var31, Object var32, int var33, int var34, int var35, Object var36, int var37, int var38, int var39);

   static native int invokeArrayReturnInt(long var0, long var2, byte[] var4);

   static native long invokeArrayReturnLong(long var0, long var2, byte[] var4);

   static native float invokeArrayReturnFloat(long var0, long var2, byte[] var4);

   static native double invokeArrayReturnDouble(long var0, long var2, byte[] var4);

   static native void invokeArrayReturnStruct(long var0, long var2, byte[] var4, byte[] var5, int var6);

   static native Object invokeArrayWithObjectsReturnObject(long var0, long var2, byte[] var4, int var5, int[] var6, Object[] var7);

   static native int invokeArrayWithObjectsInt32(long var0, long var2, byte[] var4, int var5, int[] var6, Object[] var7);

   static native long invokeArrayWithObjectsInt64(long var0, long var2, byte[] var4, int var5, int[] var6, Object[] var7);

   static native float invokeArrayWithObjectsFloat(long var0, long var2, byte[] var4, int var5, int[] var6, Object[] var7);

   static native double invokeArrayWithObjectsDouble(long var0, long var2, byte[] var4, int var5, int[] var6, Object[] var7);

   static native void invokeArrayWithObjectsReturnStruct(long var0, long var2, byte[] var4, int var5, int[] var6, Object[] var7, byte[] var8, int var9);

   static native int invokeArrayO1Int32(long var0, long var2, byte[] var4, Object var5, int var6, int var7, int var8);

   static native int invokeArrayO2Int32(long var0, long var2, byte[] var4, Object var5, int var6, int var7, int var8, Object var9, int var10, int var11, int var12);

   static native long invokeArrayO1Int64(long var0, long var2, byte[] var4, Object var5, int var6, int var7, int var8);

   static native long invokeArrayO2Int64(long var0, long var2, byte[] var4, Object var5, int var6, int var7, int var8, Object var9, int var10, int var11, int var12);

   static native void invokePointerParameterArray(long var0, long var2, long var4, long[] var6);

   static native byte getByte(long var0);

   static native short getShort(long var0);

   static native int getInt(long var0);

   static native long getLong(long var0);

   static native float getFloat(long var0);

   static native double getDouble(long var0);

   static native long getAddress(long var0);

   static native void putByte(long var0, byte var2);

   static native void putShort(long var0, short var2);

   static native void putInt(long var0, int var2);

   static native void putLong(long var0, long var2);

   static native void putFloat(long var0, float var2);

   static native void putDouble(long var0, double var2);

   static native void putAddress(long var0, long var2);

   static native void setMemory(long var0, long var2, byte var4);

   static native void copyMemory(long var0, long var2, long var4);

   static native void putByteArray(long var0, byte[] var2, int var3, int var4);

   static native void getByteArray(long var0, byte[] var2, int var3, int var4);

   static native void putCharArray(long var0, char[] var2, int var3, int var4);

   static native void getCharArray(long var0, char[] var2, int var3, int var4);

   static native void putShortArray(long var0, short[] var2, int var3, int var4);

   static native void getShortArray(long var0, short[] var2, int var3, int var4);

   static native void putIntArray(long var0, int[] var2, int var3, int var4);

   static native void getIntArray(long var0, int[] var2, int var3, int var4);

   static native void putLongArray(long var0, long[] var2, int var3, int var4);

   static native void getLongArray(long var0, long[] var2, int var3, int var4);

   static native void putFloatArray(long var0, float[] var2, int var3, int var4);

   static native void getFloatArray(long var0, float[] var2, int var3, int var4);

   static native void putDoubleArray(long var0, double[] var2, int var3, int var4);

   static native void getDoubleArray(long var0, double[] var2, int var3, int var4);

   static native long memchr(long var0, int var2, long var3);

   static native void memmove(long var0, long var2, long var4);

   static native void memcpy(long var0, long var2, long var4);

   static native long strlen(long var0);

   static native byte[] getZeroTerminatedByteArray(long var0);

   static native byte[] getZeroTerminatedByteArray(long var0, int var2);

   static native void putZeroTerminatedByteArray(long var0, byte[] var2, int var3, int var4);

   static native byte getByteChecked(long var0);

   static native short getShortChecked(long var0);

   static native int getIntChecked(long var0);

   static native long getLongChecked(long var0);

   static native float getFloatChecked(long var0);

   static native double getDoubleChecked(long var0);

   static native long getAddressChecked(long var0);

   static native void putByteChecked(long var0, byte var2);

   static native void putShortChecked(long var0, short var2);

   static native void putIntChecked(long var0, int var2);

   static native void putLongChecked(long var0, long var2);

   static native void putFloatChecked(long var0, float var2);

   static native void putDoubleChecked(long var0, double var2);

   static native void putAddressChecked(long var0, long var2);

   static native void setMemoryChecked(long var0, long var2, byte var4);

   static native void copyMemoryChecked(long var0, long var2, long var4);

   static native void putByteArrayChecked(long var0, byte[] var2, int var3, int var4);

   static native void getByteArrayChecked(long var0, byte[] var2, int var3, int var4);

   static native void putCharArrayChecked(long var0, char[] var2, int var3, int var4);

   static native void getCharArrayChecked(long var0, char[] var2, int var3, int var4);

   static native void putShortArrayChecked(long var0, short[] var2, int var3, int var4);

   static native void getShortArrayChecked(long var0, short[] var2, int var3, int var4);

   static native void putIntArrayChecked(long var0, int[] var2, int var3, int var4);

   static native void getIntArrayChecked(long var0, int[] var2, int var3, int var4);

   static native void putLongArrayChecked(long var0, long[] var2, int var3, int var4);

   static native void getLongArrayChecked(long var0, long[] var2, int var3, int var4);

   static native void putFloatArrayChecked(long var0, float[] var2, int var3, int var4);

   static native void getFloatArrayChecked(long var0, float[] var2, int var3, int var4);

   static native void putDoubleArrayChecked(long var0, double[] var2, int var3, int var4);

   static native void getDoubleArrayChecked(long var0, double[] var2, int var3, int var4);

   static native long memchrChecked(long var0, int var2, long var3);

   static native void memmoveChecked(long var0, long var2, long var4);

   static native void memcpyChecked(long var0, long var2, long var4);

   static native long strlenChecked(long var0);

   static native byte[] getZeroTerminatedByteArrayChecked(long var0);

   static native byte[] getZeroTerminatedByteArrayChecked(long var0, int var2);

   static native void putZeroTerminatedByteArrayChecked(long var0, byte[] var2, int var3, int var4);

   final native ByteBuffer newDirectByteBuffer(long var1, int var3);

   final native long getDirectBufferAddress(Buffer var1);

   final native void longDoubleFromDouble(double var1, byte[] var3, int var4, int var5);

   final native double longDoubleToDouble(byte[] var1, int var2, int var3);

   final native void longDoubleFromString(String var1, byte[] var2, int var3, int var4);

   final native String longDoubleToString(byte[] var1, int var2, int var3);

   final native String longDoubleToEngineeringString(byte[] var1, int var2, int var3);

   final native String longDoubleToPlainString(byte[] var1, int var2, int var3);

   final native long newNativeMethod(String var1, String var2, long var3);

   final native void freeNativeMethod(long var1);

   final native long compileNativeMethods(long[] var1);

   final native void freeCompiledMethods(long var1);

   final native boolean registerNativeMethods(Class var1, long var2);

   final native void unregisterNativeMethods(Class var1);

   final native long getSaveErrnoFunction();

   final native void setCallContextErrorFunction(long var1, long var3);

   final native long getSaveErrnoCtxFunction();

   final native int getJNIVersion();

   final native long getJavaVM();

   final native void fatalError(String var1);

   final native Class defineClass(String var1, Object var2, byte[] var3, int var4, int var5);

   final native Class defineClass(String var1, Object var2, ByteBuffer var3);

   final native Object allocObject(Class var1);

   final native int registerNatives(Class var1, long var2, int var4);

   final native int unregisterNatives(Class var1);

   final native String getArch();

   // $FF: synthetic method
   Foreign(Object x0) {
      this();
   }

   private static final class InValidInstanceHolder extends InstanceHolder {
      private final Throwable cause;

      public InValidInstanceHolder(Throwable cause) {
         super(null);
         this.cause = cause;
      }

      final Foreign getForeign() {
         throw Foreign.newLoadError(this.cause);
      }
   }

   private static final class ValidInstanceHolder extends InstanceHolder {
      final Foreign foreign;

      public ValidInstanceHolder(Foreign foreign) {
         super(null);
         this.foreign = foreign;
      }

      final Foreign getForeign() {
         return this.foreign;
      }
   }

   private abstract static class InstanceHolder {
      static final InstanceHolder INSTANCE = getInstanceHolder();

      private InstanceHolder() {
      }

      private static InstanceHolder getInstanceHolder() {
         try {
            Init.load();
            Foreign foreign = new Foreign();
            if ((foreign.getVersion() & 16776960) != (Foreign.VERSION_MAJOR << 16 | Foreign.VERSION_MINOR << 8)) {
               String msg = String.format("incorrect native library version %d.%d, expected %d.%d", foreign.getVersion() >> 16 & 255, foreign.getVersion() >> 8 & 255, Foreign.VERSION_MAJOR, Foreign.VERSION_MINOR);
               return new InValidInstanceHolder(new UnsatisfiedLinkError(msg));
            } else {
               foreign.init();
               return new ValidInstanceHolder(foreign);
            }
         } catch (Throwable var2) {
            return new InValidInstanceHolder(var2);
         }
      }

      abstract Foreign getForeign();

      // $FF: synthetic method
      InstanceHolder(Object x0) {
         this();
      }
   }
}
