package weblogic.j2ee.descriptor;

public interface ApplicationBean {
   String getApplicationName();

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

   String getInitializeInOrder();

   ModuleBean[] getModules();

   ModuleBean createModule();

   void destroyModule(ModuleBean var1);

   SecurityRoleBean[] getSecurityRoles();

   SecurityRoleBean createSecurityRole();

   void destroySecurityRole(SecurityRoleBean var1);

   String getLibraryDirectory();

   void setLibraryDirectory(String var1);

   EnvEntryBean[] getEnvEntries();

   EjbRefBean[] getEjbRefs();

   EjbLocalRefBean[] getEjbLocalRefs();

   ServiceRefBean[] getServiceRefs();

   ResourceRefBean[] getResourceRefs();

   ResourceEnvRefBean[] getResourceEnvRefs();

   MessageDestinationRefBean[] getMessageDestinationRefs();

   PersistenceContextRefBean[] getPersistenceContextRefs();

   PersistenceUnitRefBean[] getPersistenceUnitRefs();

   MessageDestinationBean[] getMessageDestinations();

   DataSourceBean[] getDataSources();

   JmsConnectionFactoryBean[] getJmsConnectionFactories();

   JmsDestinationBean[] getJmsDestinations();

   MailSessionBean[] getMailSessions();

   ConnectionFactoryResourceBean[] getConnectionFactories();

   AdministeredObjectBean[] getAdministeredObjects();

   String getVersion();

   void setVersion(String var1);

   String getId();

   void setId(String var1);

   J2eeEnvironmentBean convertToJ2eeEnvironmentBean();
}
