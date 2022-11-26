package com.kenai.jffi;

public abstract class ObjectParameterInvoker {
   public static ObjectParameterInvoker getInstance() {
      return ObjectParameterInvoker.SingletonHolder.INSTANCE;
   }

   static ObjectParameterInvoker newNativeInvoker() {
      return new NativeObjectParameterInvoker(Foreign.getInstance());
   }

   static ObjectParameterInvoker newHeapInvoker() {
      return new HeapObjectParameterInvoker(Foreign.getInstance());
   }

   public abstract boolean isNative();

   public abstract long invokeN1O1rN(Function var1, long var2, Object var4, int var5, int var6, ObjectParameterInfo var7);

   public abstract long invokeN2O1rN(Function var1, long var2, long var4, Object var6, int var7, int var8, ObjectParameterInfo var9);

   public abstract long invokeN2O2rN(Function var1, long var2, long var4, Object var6, int var7, int var8, ObjectParameterInfo var9, Object var10, int var11, int var12, ObjectParameterInfo var13);

   public abstract long invokeN3O1rN(Function var1, long var2, long var4, long var6, Object var8, int var9, int var10, ObjectParameterInfo var11);

   public abstract long invokeN3O2rN(Function var1, long var2, long var4, long var6, Object var8, int var9, int var10, ObjectParameterInfo var11, Object var12, int var13, int var14, ObjectParameterInfo var15);

   public abstract long invokeN3O3rN(Function var1, long var2, long var4, long var6, Object var8, int var9, int var10, ObjectParameterInfo var11, Object var12, int var13, int var14, ObjectParameterInfo var15, Object var16, int var17, int var18, ObjectParameterInfo var19);

   public abstract long invokeN4O1rN(Function var1, long var2, long var4, long var6, long var8, Object var10, int var11, int var12, ObjectParameterInfo var13);

   public abstract long invokeN4O2rN(Function var1, long var2, long var4, long var6, long var8, Object var10, int var11, int var12, ObjectParameterInfo var13, Object var14, int var15, int var16, ObjectParameterInfo var17);

   public abstract long invokeN4O3rN(Function var1, long var2, long var4, long var6, long var8, Object var10, int var11, int var12, ObjectParameterInfo var13, Object var14, int var15, int var16, ObjectParameterInfo var17, Object var18, int var19, int var20, ObjectParameterInfo var21);

   public abstract long invokeN5O1rN(Function var1, long var2, long var4, long var6, long var8, long var10, Object var12, int var13, int var14, ObjectParameterInfo var15);

   public abstract long invokeN5O2rN(Function var1, long var2, long var4, long var6, long var8, long var10, Object var12, int var13, int var14, ObjectParameterInfo var15, Object var16, int var17, int var18, ObjectParameterInfo var19);

   public abstract long invokeN5O3rN(Function var1, long var2, long var4, long var6, long var8, long var10, Object var12, int var13, int var14, ObjectParameterInfo var15, Object var16, int var17, int var18, ObjectParameterInfo var19, Object var20, int var21, int var22, ObjectParameterInfo var23);

   public abstract long invokeN6O1rN(Function var1, long var2, long var4, long var6, long var8, long var10, long var12, Object var14, int var15, int var16, ObjectParameterInfo var17);

   public abstract long invokeN6O2rN(Function var1, long var2, long var4, long var6, long var8, long var10, long var12, Object var14, int var15, int var16, ObjectParameterInfo var17, Object var18, int var19, int var20, ObjectParameterInfo var21);

   public abstract long invokeN6O3rN(Function var1, long var2, long var4, long var6, long var8, long var10, long var12, Object var14, int var15, int var16, ObjectParameterInfo var17, Object var18, int var19, int var20, ObjectParameterInfo var21, Object var22, int var23, int var24, ObjectParameterInfo var25);

   private static final class SingletonHolder {
      static final ObjectParameterInvoker INSTANCE = Foreign.getInstance().getVersion() >= 65546 ? ObjectParameterInvoker.newNativeInvoker() : ObjectParameterInvoker.newHeapInvoker();
   }
}
