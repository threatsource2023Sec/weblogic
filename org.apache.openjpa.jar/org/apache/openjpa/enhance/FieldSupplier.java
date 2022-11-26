package org.apache.openjpa.enhance;

public interface FieldSupplier {
   boolean fetchBooleanField(int var1);

   char fetchCharField(int var1);

   byte fetchByteField(int var1);

   short fetchShortField(int var1);

   int fetchIntField(int var1);

   long fetchLongField(int var1);

   float fetchFloatField(int var1);

   double fetchDoubleField(int var1);

   String fetchStringField(int var1);

   Object fetchObjectField(int var1);
}
