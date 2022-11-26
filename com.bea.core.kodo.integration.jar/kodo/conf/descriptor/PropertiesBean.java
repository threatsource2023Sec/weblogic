package kodo.conf.descriptor;

public interface PropertiesBean {
   PersistenceConfigurationPropertyBean[] getProperties();

   PersistenceConfigurationPropertyBean createProperty(String var1);

   PersistenceConfigurationPropertyBean lookupProperty(String var1);

   void destroyProperty(PersistenceConfigurationPropertyBean var1);
}
