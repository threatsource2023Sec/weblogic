package weblogic.j2ee.descriptor;

public interface PersistencePropertiesBean {
   PersistencePropertyBean[] getProperties();

   PersistencePropertyBean createProperty(String var1);

   PersistencePropertyBean lookupProperty(String var1);

   void destroyProperty(PersistencePropertyBean var1);
}
