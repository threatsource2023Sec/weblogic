package com.bea.core.repackaged.springframework.cglib.transform.impl;

public interface InterceptFieldCallback {
   int writeInt(Object var1, String var2, int var3, int var4);

   char writeChar(Object var1, String var2, char var3, char var4);

   byte writeByte(Object var1, String var2, byte var3, byte var4);

   boolean writeBoolean(Object var1, String var2, boolean var3, boolean var4);

   short writeShort(Object var1, String var2, short var3, short var4);

   float writeFloat(Object var1, String var2, float var3, float var4);

   double writeDouble(Object var1, String var2, double var3, double var5);

   long writeLong(Object var1, String var2, long var3, long var5);

   Object writeObject(Object var1, String var2, Object var3, Object var4);

   int readInt(Object var1, String var2, int var3);

   char readChar(Object var1, String var2, char var3);

   byte readByte(Object var1, String var2, byte var3);

   boolean readBoolean(Object var1, String var2, boolean var3);

   short readShort(Object var1, String var2, short var3);

   float readFloat(Object var1, String var2, float var3);

   double readDouble(Object var1, String var2, double var3);

   long readLong(Object var1, String var2, long var3);

   Object readObject(Object var1, String var2, Object var3);
}
