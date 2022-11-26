package kodo.jdbc.conf.descriptor;

public interface TableSchemaFactoryBean extends SchemaFactoryBean {
   String getSchemaColumn();

   void setSchemaColumn(String var1);

   String getTableName();

   void setTableName(String var1);

   String getTable();

   void setTable(String var1);

   String getPrimaryKeyColumn();

   void setPrimaryKeyColumn(String var1);
}
