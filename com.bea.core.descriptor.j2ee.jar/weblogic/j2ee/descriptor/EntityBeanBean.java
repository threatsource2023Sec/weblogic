package weblogic.j2ee.descriptor;

public interface EntityBeanBean extends EnterpriseBeanBean {
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

   String getMappedName();

   void setMappedName(String var1);

   String getHome();

   void setHome(String var1);

   String getRemote();

   void setRemote(String var1);

   String getLocalHome();

   void setLocalHome(String var1);

   String getLocal();

   void setLocal(String var1);

   String getEjbClass();

   void setEjbClass(String var1);

   String getPersistenceType();

   void setPersistenceType(String var1);

   String getPrimKeyClass();

   void setPrimKeyClass(String var1);

   boolean isReentrant();

   void setReentrant(boolean var1);

   String getCmpVersion();

   void setCmpVersion(String var1);

   String getAbstractSchemaName();

   void setAbstractSchemaName(String var1);

   CmpFieldBean[] getCmpFields();

   CmpFieldBean createCmpField();

   void destroyCmpField(CmpFieldBean var1);

   String getPrimkeyField();

   void setPrimkeyField(String var1);

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

   LifecycleCallbackBean[] getPostConstructs();

   LifecycleCallbackBean createPostConstruct();

   void destroyPostConstruct(LifecycleCallbackBean var1);

   LifecycleCallbackBean[] getPreDestroys();

   LifecycleCallbackBean createPreDestroy();

   void destroyPreDestroy(LifecycleCallbackBean var1);

   DataSourceBean[] getDataSources();

   DataSourceBean createDataSource();

   void destroyDataSource(DataSourceBean var1);

   JmsConnectionFactoryBean[] getJmsConnectionFactories();

   JmsConnectionFactoryBean createJmsConnectionFactory();

   void destroyJmsConnectionFactory(JmsConnectionFactoryBean var1);

   JmsDestinationBean[] getJmsDestinations();

   JmsDestinationBean createJmsDestination();

   void destroyJmsDestination(JmsDestinationBean var1);

   MailSessionBean[] getMailSessions();

   MailSessionBean createMailSession();

   void destroyMailSession(MailSessionBean var1);

   ConnectionFactoryResourceBean[] getConnectionFactories();

   ConnectionFactoryResourceBean createConnectionFactoryResourceBean();

   void destroyConnectionFactory(ConnectionFactoryResourceBean var1);

   AdministeredObjectBean[] getAdministeredObjects();

   AdministeredObjectBean createAdministeredObjectBean();

   void destroyAdministeredObject(AdministeredObjectBean var1);

   SecurityRoleRefBean[] getSecurityRoleRefs();

   SecurityRoleRefBean createSecurityRoleRef();

   void destroySecurityRoleRef(SecurityRoleRefBean var1);

   SecurityIdentityBean getSecurityIdentity();

   SecurityIdentityBean createSecurityIdentity();

   void destroySecurityIdentity(SecurityIdentityBean var1);

   QueryBean[] getQueries();

   QueryBean createQuery();

   void destroyQuery(QueryBean var1);

   String getId();

   void setId(String var1);
}
