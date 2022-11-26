package kodo.jdbc.conf.descriptor;

public interface TableDeprecatedJDOMappingFactoryBean extends MappingFactoryBean {
   String getTableName();

   void setTableName(String var1);

   String getURLs();

   void setURLs(String var1);

   String getClasspathScan();

   void setClasspathScan(String var1);

   String getTypes();

   void setTypes(String var1);

   String getMappingColumn();

   void setMappingColumn(String var1);

   int getStoreMode();

   void setStoreMode(int var1);

   boolean getStrict();

   void setStrict(boolean var1);

   String getNameColumn();

   void setNameColumn(String var1);

   boolean getUseSchemaValidation();

   void setUseSchemaValidation(boolean var1);

   boolean getSingleRow();

   void setSingleRow(boolean var1);

   String getFiles();

   void setFiles(String var1);

   boolean getScanTopDown();

   void setScanTopDown(boolean var1);

   String getResources();

   void setResources(String var1);
}
