package kodo.jdbc.conf.descriptor;

public interface LazySchemaFactoryBean extends SchemaFactoryBean {
   boolean getForeignKeys();

   void setForeignKeys(boolean var1);

   boolean getIndexes();

   void setIndexes(boolean var1);

   boolean getPrimaryKeys();

   void setPrimaryKeys(boolean var1);
}
