package org.apache.openjpa.jdbc.meta;

import java.util.Properties;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.lib.util.Closeable;

public interface ReverseCustomizer extends Closeable {
   void setConfiguration(Properties var1);

   void setTool(ReverseMappingTool var1);

   int getTableType(Table var1, int var2);

   String getClassName(Table var1, String var2);

   void customize(ClassMapping var1);

   String getClassCode(ClassMapping var1);

   String getFieldName(ClassMapping var1, Column[] var2, ForeignKey var3, String var4);

   void customize(FieldMapping var1);

   String getInitialValue(FieldMapping var1);

   String getDeclaration(FieldMapping var1);

   String getFieldCode(FieldMapping var1);

   boolean unmappedTable(Table var1);

   void close();
}
