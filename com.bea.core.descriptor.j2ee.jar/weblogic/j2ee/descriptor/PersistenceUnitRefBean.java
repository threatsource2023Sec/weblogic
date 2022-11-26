package weblogic.j2ee.descriptor;

public interface PersistenceUnitRefBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   String getPersistenceUnitRefName();

   void setPersistenceUnitRefName(String var1);

   String getPersistenceUnitName();

   void setPersistenceUnitName(String var1);

   String getMappedName();

   void setMappedName(String var1);

   InjectionTargetBean[] getInjectionTargets();

   InjectionTargetBean createInjectionTarget();

   void destroyInjectionTarget(InjectionTargetBean var1);

   String getId();

   void setId(String var1);
}
