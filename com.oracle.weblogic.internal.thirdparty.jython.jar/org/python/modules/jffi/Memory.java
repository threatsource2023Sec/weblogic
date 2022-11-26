package org.python.modules.jffi;

public interface Memory {
   boolean isNull();

   boolean isDirect();

   Memory slice(long var1);

   byte getByte(long var1);

   short getShort(long var1);

   int getInt(long var1);

   long getLong(long var1);

   long getNativeLong(long var1);

   float getFloat(long var1);

   double getDouble(long var1);

   long getAddress(long var1);

   DirectMemory getMemory(long var1);

   byte[] getZeroTerminatedByteArray(long var1);

   void putByte(long var1, byte var3);

   void putShort(long var1, short var3);

   void putInt(long var1, int var3);

   void putLong(long var1, long var3);

   void putNativeLong(long var1, long var3);

   void putFloat(long var1, float var3);

   void putDouble(long var1, double var3);

   void putAddress(long var1, Memory var3);

   void putAddress(long var1, long var3);

   void putZeroTerminatedByteArray(long var1, byte[] var3, int var4, int var5);

   void get(long var1, byte[] var3, int var4, int var5);

   void put(long var1, byte[] var3, int var4, int var5);

   void get(long var1, short[] var3, int var4, int var5);

   void put(long var1, short[] var3, int var4, int var5);

   void get(long var1, int[] var3, int var4, int var5);

   void put(long var1, int[] var3, int var4, int var5);

   void get(long var1, long[] var3, int var4, int var5);

   void put(long var1, long[] var3, int var4, int var5);

   void get(long var1, float[] var3, int var4, int var5);

   void put(long var1, float[] var3, int var4, int var5);

   void get(long var1, double[] var3, int var4, int var5);

   void put(long var1, double[] var3, int var4, int var5);

   int indexOf(long var1, byte var3);

   int indexOf(long var1, byte var3, int var4);

   void setMemory(long var1, long var3, byte var5);
}
