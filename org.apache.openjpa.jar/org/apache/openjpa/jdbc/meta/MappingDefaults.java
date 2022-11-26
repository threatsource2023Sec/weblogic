package org.apache.openjpa.jdbc.meta;

import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Index;
import org.apache.openjpa.jdbc.schema.Schema;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.jdbc.schema.Unique;

public interface MappingDefaults {
   boolean defaultMissingInfo();

   boolean useClassCriteria();

   Object getStrategy(ClassMapping var1, boolean var2);

   Object getStrategy(Version var1, boolean var2);

   Object getStrategy(Discriminator var1, boolean var2);

   Object getStrategy(ValueMapping var1, Class var2, boolean var3);

   Object getDiscriminatorValue(Discriminator var1, boolean var2);

   String getTableName(ClassMapping var1, Schema var2);

   String getTableName(FieldMapping var1, Schema var2);

   void populateDataStoreIdColumns(ClassMapping var1, Table var2, Column[] var3);

   void populateColumns(Version var1, Table var2, Column[] var3);

   void populateColumns(Discriminator var1, Table var2, Column[] var3);

   void populateJoinColumn(ClassMapping var1, Table var2, Table var3, Column var4, Object var5, int var6, int var7);

   void populateJoinColumn(FieldMapping var1, Table var2, Table var3, Column var4, Object var5, int var6, int var7);

   void populateForeignKeyColumn(ValueMapping var1, String var2, Table var3, Table var4, Column var5, Object var6, boolean var7, int var8, int var9);

   void populateColumns(ValueMapping var1, String var2, Table var3, Column[] var4);

   boolean populateOrderColumns(FieldMapping var1, Table var2, Column[] var3);

   boolean populateNullIndicatorColumns(ValueMapping var1, String var2, Table var3, Column[] var4);

   ForeignKey getJoinForeignKey(ClassMapping var1, Table var2, Table var3);

   ForeignKey getJoinForeignKey(FieldMapping var1, Table var2, Table var3);

   ForeignKey getForeignKey(ValueMapping var1, String var2, Table var3, Table var4, boolean var5);

   Index getJoinIndex(FieldMapping var1, Table var2, Column[] var3);

   Index getIndex(ValueMapping var1, String var2, Table var3, Column[] var4);

   Index getIndex(Version var1, Table var2, Column[] var3);

   Index getIndex(Discriminator var1, Table var2, Column[] var3);

   Unique getJoinUnique(FieldMapping var1, Table var2, Column[] var3);

   Unique getUnique(ValueMapping var1, String var2, Table var3, Column[] var4);

   String getPrimaryKeyName(ClassMapping var1, Table var2);

   void installPrimaryKey(FieldMapping var1, Table var2);
}
