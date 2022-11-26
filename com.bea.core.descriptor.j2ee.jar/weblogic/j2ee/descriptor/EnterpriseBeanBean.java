package weblogic.j2ee.descriptor;

public interface EnterpriseBeanBean extends J2eeEnvironmentBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String[] getDisplayNames();

   void addDisplayName(String var1);

   void removeDisplayName(String var1);

   void setDisplayNames(String[] var1);

   IconBean[] getIcons();

   IconBean createIcon();

   void destroyIcon(IconBean var1);

   String getEjbName();

   void setEjbName(String var1);

   String getEjbClass();

   void setEjbClass(String var1);

   EnvEntryBean[] getEnvEntries();

   EnvEntryBean createEnvEntry();

   void destroyEnvEntry(EnvEntryBean var1);

   EjbRefBean[] getEjbRefs();

   EjbRefBean createEjbRef();

   void destroyEjbRef(EjbRefBean var1);

   EjbLocalRefBean[] getEjbLocalRefs();

   EjbLocalRefBean createEjbLocalRef();

   void destroyEjbLocalRef(EjbLocalRefBean var1);

   ServiceRefBean[] getServiceRefs();

   ServiceRefBean createServiceRef();

   void destroyServiceRef(ServiceRefBean var1);

   ResourceRefBean[] getResourceRefs();

   DataSourceBean[] getDataSources();

   ResourceRefBean createResourceRef();

   void destroyResourceRef(ResourceRefBean var1);

   ResourceEnvRefBean[] getResourceEnvRefs();

   ResourceEnvRefBean createResourceEnvRef();

   void destroyResourceEnvRef(ResourceEnvRefBean var1);

   MessageDestinationRefBean[] getMessageDestinationRefs();

   MessageDestinationRefBean createMessageDestinationRef();

   void destroyMessageDestinationRef(MessageDestinationRefBean var1);

   PersistenceContextRefBean[] getPersistenceContextRefs();

   PersistenceContextRefBean createPersistenceContextRef();

   void destroyPersistenceContextRef(PersistenceContextRefBean var1);

   PersistenceUnitRefBean[] getPersistenceUnitRefs();

   PersistenceUnitRefBean createPersistenceUnitRef();

   void destroyPersistenceUnitRef(PersistenceUnitRefBean var1);

   SecurityIdentityBean getSecurityIdentity();

   SecurityIdentityBean createSecurityIdentity();

   void destroySecurityIdentity(SecurityIdentityBean var1);
}
