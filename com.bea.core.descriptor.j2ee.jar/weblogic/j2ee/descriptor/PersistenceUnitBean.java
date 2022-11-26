package weblogic.j2ee.descriptor;

public interface PersistenceUnitBean {
   String getName();

   String getDescription();

   void setDescription(String var1);

   String getProvider();

   String getJtaDataSource();

   void setJtaDataSource(String var1);

   String getNonJtaDataSource();

   void setNonJtaDataSource(String var1);

   String[] getMappingFiles();

   String[] getJarFiles();

   String[] getClasses();

   boolean getExcludeUnlistedClasses();

   void setExcludeUnlistedClasses(boolean var1);

   String getSharedCacheMode();

   void setSharedCacheMode(String var1);

   String getValidationMode();

   void setValidationMode(String var1);

   String getTransactionType();

   void setTransactionType(String var1);

   PersistencePropertiesBean getProperties();
}
