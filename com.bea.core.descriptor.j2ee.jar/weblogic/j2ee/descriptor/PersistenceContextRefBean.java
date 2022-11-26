package weblogic.j2ee.descriptor;

public interface PersistenceContextRefBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   String getPersistenceContextRefName();

   void setPersistenceContextRefName(String var1);

   String getPersistenceUnitName();

   void setPersistenceUnitName(String var1);

   String getPersistenceContextType();

   void setPersistenceContextType(String var1);

   String getSynchronizationType();

   void setSynchronizationType(String var1);

   JavaEEPropertyBean[] getPersistenceProperties();

   JavaEEPropertyBean createPersistenceProperty();

   void destroyPersistenceProperty(JavaEEPropertyBean var1);

   String getMappedName();

   void setMappedName(String var1);

   InjectionTargetBean[] getInjectionTargets();

   InjectionTargetBean createInjectionTarget();

   void destroyInjectionTarget(InjectionTargetBean var1);

   String getId();

   void setId(String var1);
}
