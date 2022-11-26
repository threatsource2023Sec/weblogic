package com.kenai.jffi;

import java.nio.Buffer;

public abstract class InvocationBuffer {
   public abstract void putByte(int var1);

   public abstract void putShort(int var1);

   public abstract void putInt(int var1);

   public abstract void putLong(long var1);

   public abstract void putFloat(float var1);

   public abstract void putDouble(double var1);

   public abstract void putAddress(long var1);

   public abstract void putArray(byte[] var1, int var2, int var3, int var4);

   public abstract void putArray(short[] var1, int var2, int var3, int var4);

   public abstract void putArray(int[] var1, int var2, int var3, int var4);

   public abstract void putArray(long[] var1, int var2, int var3, int var4);

   public abstract void putArray(float[] var1, int var2, int var3, int var4);

   public abstract void putArray(double[] var1, int var2, int var3, int var4);

   public abstract void putDirectBuffer(Buffer var1, int var2, int var3);

   public abstract void putStruct(byte[] var1, int var2);

   public abstract void putStruct(long var1);
}
