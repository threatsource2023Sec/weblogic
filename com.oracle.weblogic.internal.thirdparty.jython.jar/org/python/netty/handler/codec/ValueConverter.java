package org.python.netty.handler.codec;

public interface ValueConverter {
   Object convertObject(Object var1);

   Object convertBoolean(boolean var1);

   boolean convertToBoolean(Object var1);

   Object convertByte(byte var1);

   byte convertToByte(Object var1);

   Object convertChar(char var1);

   char convertToChar(Object var1);

   Object convertShort(short var1);

   short convertToShort(Object var1);

   Object convertInt(int var1);

   int convertToInt(Object var1);

   Object convertLong(long var1);

   long convertToLong(Object var1);

   Object convertTimeMillis(long var1);

   long convertToTimeMillis(Object var1);

   Object convertFloat(float var1);

   float convertToFloat(Object var1);

   Object convertDouble(double var1);

   double convertToDouble(Object var1);
}
