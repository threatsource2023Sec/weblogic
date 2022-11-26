package weblogic.j2ee.descriptor;

public interface PersistenceBean {
   PersistenceUnitBean[] getPersistenceUnits();

   PersistenceUnitBean createPersistenceUnit(String var1);

   PersistenceUnitBean lookupPersistenceUnit(String var1);

   void destroyPersistenceUnit(PersistenceUnitBean var1);

   String getVersion();

   void setVersion(String var1);

   String getOriginalVersion();

   void setOriginalVersion(String var1);
}
