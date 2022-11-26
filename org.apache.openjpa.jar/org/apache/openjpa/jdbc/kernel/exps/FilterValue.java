package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.meta.XMLMetaData;

public interface FilterValue {
   Class getType();

   int length();

   void appendTo(SQLBuffer var1);

   void appendTo(SQLBuffer var1, int var2);

   String getColumnAlias(Column var1);

   String getColumnAlias(String var1, Table var2);

   Object toDataStoreValue(Object var1);

   boolean isConstant();

   Object getValue();

   Object getSQLValue();

   boolean isPath();

   ClassMapping getClassMapping();

   FieldMapping getFieldMapping();

   PCPath getXPath();

   XMLMetaData getXmlMapping();
}
