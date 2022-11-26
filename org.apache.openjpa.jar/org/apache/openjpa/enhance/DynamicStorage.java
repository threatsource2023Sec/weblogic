package org.apache.openjpa.enhance;

public interface DynamicStorage {
   int getFieldCount();

   int getObjectCount();

   DynamicStorage newInstance();

   boolean getBoolean(int var1);

   void setBoolean(int var1, boolean var2);

   byte getByte(int var1);

   void setByte(int var1, byte var2);

   char getChar(int var1);

   void setChar(int var1, char var2);

   double getDouble(int var1);

   void setDouble(int var1, double var2);

   float getFloat(int var1);

   void setFloat(int var1, float var2);

   int getInt(int var1);

   void setInt(int var1, int var2);

   long getLong(int var1);

   void setLong(int var1, long var2);

   short getShort(int var1);

   void setShort(int var1, short var2);

   Object getObject(int var1);

   void setObject(int var1, Object var2);

   void initialize();
}
