package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface WeblogicApplicationBean extends SettableBean {
   EjbBean getEjb();

   EjbBean createEjb();

   void destroyEjb(EjbBean var1);

   XmlBean getXml();

   XmlBean createXml();

   void destroyXml(XmlBean var1);

   JDBCConnectionPoolBean[] getJDBCConnectionPools();

   JDBCConnectionPoolBean createJDBCConnectionPool();

   void destroyJDBCConnectionPool(JDBCConnectionPoolBean var1);

   SecurityBean getSecurity();

   SecurityBean createSecurity();

   void destroySecurity(SecurityBean var1);

   ApplicationParamBean[] getApplicationParams();

   ApplicationParamBean createApplicationParam();

   void destroyApplicationParam(ApplicationParamBean var1);

   ClassloaderStructureBean getClassloaderStructure();

   ClassloaderStructureBean createClassloaderStructure();

   void destroyClassloaderStructure(ClassloaderStructureBean var1);

   ListenerBean[] getListeners();

   ListenerBean createListener();

   void destroyListener(ListenerBean var1);

   SingletonServiceBean[] getSingletonServices();

   SingletonServiceBean createSingletonService();

   void destroySingletonService(SingletonServiceBean var1);

   StartupBean[] getStartups();

   StartupBean createStartup();

   void destroyStartup(StartupBean var1);

   ShutdownBean[] getShutdowns();

   ShutdownBean createShutdown();

   void destroyShutdown(ShutdownBean var1);

   WeblogicModuleBean[] getModules();

   WeblogicModuleBean createModule();

   void destroyModule(WeblogicModuleBean var1);

   LibraryRefBean[] getLibraryRefs();

   LibraryRefBean createLibraryRef();

   void destroyLibraryRef(LibraryRefBean var1);

   FairShareRequestClassBean[] getFairShareRequests();

   FairShareRequestClassBean createFairShareRequest();

   void destroyFairShareRequest(FairShareRequestClassBean var1);

   ResponseTimeRequestClassBean[] getResponseTimeRequests();

   ResponseTimeRequestClassBean createResponseTimeRequest();

   void destroyResponseTimeRequest(ResponseTimeRequestClassBean var1);

   ContextRequestClassBean[] getContextRequests();

   ContextRequestClassBean createContextRequest();

   void destroyContextRequest(ContextRequestClassBean var1);

   MaxThreadsConstraintBean[] getMaxThreadsConstraints();

   MaxThreadsConstraintBean createMaxThreadsConstraint();

   void destroyMaxThreadsConstraint(MaxThreadsConstraintBean var1);

   MinThreadsConstraintBean[] getMinThreadsConstraints();

   MinThreadsConstraintBean createMinThreadsConstraint();

   void destroyMinThreadsConstraint(MinThreadsConstraintBean var1);

   CapacityBean[] getCapacities();

   CapacityBean createCapacity();

   void destroyCapacity(CapacityBean var1);

   WorkManagerBean[] getWorkManagers();

   WorkManagerBean createWorkManager();

   void destroyWorkManager(WorkManagerBean var1);

   ManagedExecutorServiceBean[] getManagedExecutorServices();

   ManagedExecutorServiceBean createManagedExecutorService();

   void destroyManagedExecutorService(ManagedExecutorServiceBean var1);

   ManagedScheduledExecutorServiceBean[] getManagedScheduledExecutorServices();

   ManagedScheduledExecutorServiceBean createManagedScheduledExecutorService();

   void destroyManagedScheduledExecutorService(ManagedScheduledExecutorServiceBean var1);

   ManagedThreadFactoryBean[] getManagedThreadFactories();

   ManagedThreadFactoryBean createManagedThreadFactory();

   void destroyManagedThreadFactory(ManagedThreadFactoryBean var1);

   String getComponentFactoryClassName();

   void setComponentFactoryClassName(String var1);

   ApplicationAdminModeTriggerBean getApplicationAdminModeTrigger();

   ApplicationAdminModeTriggerBean createApplicationAdminModeTrigger();

   void destroyApplicationAdminModeTrigger();

   SessionDescriptorBean getSessionDescriptor();

   LibraryContextRootOverrideBean[] getLibraryContextRootOverrides();

   LibraryContextRootOverrideBean createLibraryContextRootOverride();

   PreferApplicationPackagesBean getPreferApplicationPackages();

   PreferApplicationPackagesBean createPreferApplicationPackages();

   void destroyPreferApplicationPackages();

   PreferApplicationResourcesBean getPreferApplicationResources();

   PreferApplicationResourcesBean createPreferApplicationResources();

   void destroyPreferApplicationResources();

   FastSwapBean getFastSwap();

   FastSwapBean createFastSwap();

   void destroyFastSwap();

   CoherenceClusterRefBean getCoherenceClusterRef();

   CoherenceClusterRefBean createCoherenceClusterRef();

   void destroyCoherenceClusterRef();

   ResourceDescriptionBean[] getResourceDescriptions();

   ResourceEnvDescriptionBean[] getResourceEnvDescriptions();

   EjbReferenceDescriptionBean[] getEjbReferenceDescriptions();

   ServiceReferenceDescriptionBean[] getServiceReferenceDescriptions();

   MessageDestinationDescriptorBean[] getMessageDestinationDescriptors();

   String getVersion();

   void setVersion(String var1);

   OsgiFrameworkReferenceBean getOsgiFrameworkReference();

   OsgiFrameworkReferenceBean createOsgiFrameworkReference();

   void destroyOsgiFrameworkReference();

   WeblogicEnvironmentBean convertToWeblogicEnvironmentBean();

   ClassLoadingBean getClassLoading();

   String getReadyRegistration();

   CdiDescriptorBean getCdiDescriptor();
}
