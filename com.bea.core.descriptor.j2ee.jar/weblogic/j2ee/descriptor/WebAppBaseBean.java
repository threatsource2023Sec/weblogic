package weblogic.j2ee.descriptor;

public interface WebAppBaseBean extends J2eeEnvironmentBean {
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

   EmptyBean[] getDistributables();

   EmptyBean createDistributable();

   void destroyDistributable(EmptyBean var1);

   ParamValueBean[] getContextParams();

   ParamValueBean createContextParam();

   void destroyContextParam(ParamValueBean var1);

   FilterBean[] getFilters();

   FilterBean createFilter();

   void destroyFilter(FilterBean var1);

   FilterBean lookupFilter(String var1);

   FilterBean createFilter(String var1);

   FilterMappingBean[] getFilterMappings();

   FilterMappingBean createFilterMapping();

   void destroyFilterMapping(FilterMappingBean var1);

   ListenerBean[] getListeners();

   ListenerBean createListener();

   void destroyListener(ListenerBean var1);

   ServletBean[] getServlets();

   ServletBean createServlet();

   void destroyServlet(ServletBean var1);

   ServletBean lookupServlet(String var1);

   ServletBean createServlet(String var1);

   ServletMappingBean[] getServletMappings();

   ServletMappingBean createServletMapping();

   void destroyServletMapping(ServletMappingBean var1);

   SessionConfigBean[] getSessionConfigs();

   SessionConfigBean createSessionConfig();

   void destroySessionConfig(SessionConfigBean var1);

   MimeMappingBean[] getMimeMappings();

   MimeMappingBean createMimeMapping();

   void destroyMimeMapping(MimeMappingBean var1);

   WelcomeFileListBean[] getWelcomeFileLists();

   WelcomeFileListBean createWelcomeFileList();

   void destroyWelcomeFileList(WelcomeFileListBean var1);

   ErrorPageBean[] getErrorPages();

   ErrorPageBean createErrorPage();

   void destroyErrorPage(ErrorPageBean var1);

   JspConfigBean[] getJspConfigs();

   JspConfigBean createJspConfig();

   void destroyJspConfig(JspConfigBean var1);

   SecurityConstraintBean[] getSecurityConstraints();

   SecurityConstraintBean createSecurityConstraint();

   void destroySecurityConstraint(SecurityConstraintBean var1);

   LoginConfigBean[] getLoginConfigs();

   LoginConfigBean createLoginConfig();

   void destroyLoginConfig(LoginConfigBean var1);

   SecurityRoleBean[] getSecurityRoles();

   SecurityRoleBean createSecurityRole();

   void destroySecurityRole(SecurityRoleBean var1);

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

   MessageDestinationBean[] getMessageDestinations();

   MessageDestinationBean createMessageDestination();

   void destroyMessageDestination(MessageDestinationBean var1);

   LocaleEncodingMappingListBean[] getLocaleEncodingMappingLists();

   LocaleEncodingMappingListBean createLocaleEncodingMappingList();

   void destroyLocaleEncodingMappingList(LocaleEncodingMappingListBean var1);

   String getVersion();

   void setVersion(String var1);

   boolean isMetadataComplete();

   void setMetadataComplete(boolean var1);

   String getId();

   void setId(String var1);

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
}
