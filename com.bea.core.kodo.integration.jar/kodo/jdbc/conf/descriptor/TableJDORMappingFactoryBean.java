package kodo.jdbc.conf.descriptor;

public interface TableJDORMappingFactoryBean extends MappingFactoryBean {
   boolean getUseSchemaValidation();

   void setUseSchemaValidation(boolean var1);

   String getTypeColumn();

   void setTypeColumn(String var1);

   boolean getConstraintNames();

   void setConstraintNames(boolean var1);

   String getTable();

   void setTable(String var1);

   String getTypes();

   void setTypes(String var1);

   int getStoreMode();

   void setStoreMode(int var1);

   String getMappingColumn();

   void setMappingColumn(String var1);

   boolean getStrict();

   void setStrict(boolean var1);

   String getNameColumn();

   void setNameColumn(String var1);
}
