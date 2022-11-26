package kodo.jdbc.conf.descriptor;

public interface PersistenceConfigurationBean {
   PersistenceUnitConfigurationBean[] getPersistenceUnitConfigurations();

   PersistenceUnitConfigurationBean lookupPersistenceUnitConfiguration(String var1);

   PersistenceUnitConfigurationBean createPersistenceUnitConfiguration(String var1);

   void destroyPersistenceUnitConfiguration(PersistenceUnitConfigurationBean var1);

   String getVersion();

   void setVersion(String var1);
}
