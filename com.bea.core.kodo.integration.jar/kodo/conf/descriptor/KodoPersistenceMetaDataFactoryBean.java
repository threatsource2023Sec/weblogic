package kodo.conf.descriptor;

public interface KodoPersistenceMetaDataFactoryBean extends MetaDataFactoryBean {
   String getURLs();

   void setURLs(String var1);

   String getFiles();

   void setFiles(String var1);

   String getClasspathScan();

   void setClasspathScan(String var1);

   String getDefaultAccessType();

   void setDefaultAccessType(String var1);

   boolean getFieldOverride();

   void setFieldOverride(boolean var1);

   String getTypes();

   void setTypes(String var1);

   int getStoreMode();

   void setStoreMode(int var1);

   boolean getStrict();

   void setStrict(boolean var1);

   String getResources();

   void setResources(String var1);
}
