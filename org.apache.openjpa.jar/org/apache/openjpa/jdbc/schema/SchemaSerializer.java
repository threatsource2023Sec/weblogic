package org.apache.openjpa.jdbc.schema;

import org.apache.openjpa.lib.meta.MetaDataSerializer;

public interface SchemaSerializer extends MetaDataSerializer {
   Table[] getTables();

   void addTable(Table var1);

   boolean removeTable(Table var1);

   void addAll(Schema var1);

   void addAll(SchemaGroup var1);

   boolean removeAll(Schema var1);

   boolean removeAll(SchemaGroup var1);

   void clear();
}
