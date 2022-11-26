package org.apache.openjpa.enhance;

public interface FieldConsumer {
   void storeBooleanField(int var1, boolean var2);

   void storeCharField(int var1, char var2);

   void storeByteField(int var1, byte var2);

   void storeShortField(int var1, short var2);

   void storeIntField(int var1, int var2);

   void storeLongField(int var1, long var2);

   void storeFloatField(int var1, float var2);

   void storeDoubleField(int var1, double var2);

   void storeStringField(int var1, String var2);

   void storeObjectField(int var1, Object var2);
}
