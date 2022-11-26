package org.apache.openjpa.jdbc.schema;

public interface SchemaFactory {
   SchemaGroup readSchema();

   void storeSchema(SchemaGroup var1);
}
