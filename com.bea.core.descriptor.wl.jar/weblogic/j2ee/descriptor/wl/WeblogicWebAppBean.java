package weblogic.j2ee.descriptor.wl;

public interface WeblogicWebAppBean extends WeblogicEnvironmentBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String[] getWeblogicVersions();

   void addWeblogicVersion(String var1);

   void removeWeblogicVersion(String var1);

   void setWeblogicVersions(String[] var1);

   SecurityRoleAssignmentBean[] getSecurityRoleAssignments();

   SecurityRoleAssignmentBean createSecurityRoleAssignment();

   void destroySecurityRoleAssignment(SecurityRoleAssignmentBean var1);

   RunAsRoleAssignmentBean[] getRunAsRoleAssignments();

   RunAsRoleAssignmentBean createRunAsRoleAssignment();

   void destroyRunAsRoleAssignment(RunAsRoleAssignmentBean var1);

   ResourceDescriptionBean[] getResourceDescriptions();

   ResourceDescriptionBean createResourceDescription();

   void destroyResourceDescription(ResourceDescriptionBean var1);

   ResourceEnvDescriptionBean[] getResourceEnvDescriptions();

   ResourceEnvDescriptionBean createResourceEnvDescription();

   void destroyResourceEnvDescription(ResourceEnvDescriptionBean var1);

   EjbReferenceDescriptionBean[] getEjbReferenceDescriptions();

   EjbReferenceDescriptionBean createEjbReferenceDescription();

   void destroyEjbReferenceDescription(EjbReferenceDescriptionBean var1);

   ServiceReferenceDescriptionBean[] getServiceReferenceDescriptions();

   ServiceReferenceDescriptionBean createServiceReferenceDescription();

   void destroyServiceReferenceDescription(ServiceReferenceDescriptionBean var1);

   MessageDestinationDescriptorBean[] getMessageDestinationDescriptors();

   MessageDestinationDescriptorBean createMessageDestinationDescriptor();

   void destroyMessageDestinationDescriptor(MessageDestinationDescriptorBean var1);

   SessionDescriptorBean[] getSessionDescriptors();

   SessionDescriptorBean createSessionDescriptor();

   void destroySessionDescriptor(SessionDescriptorBean var1);

   AsyncDescriptorBean[] getAsyncDescriptors();

   AsyncDescriptorBean createAsyncDescriptor();

   void destroyAsyncDescriptor(AsyncDescriptorBean var1);

   JspDescriptorBean[] getJspDescriptors();

   JspDescriptorBean createJspDescriptor();

   void destroyJspDescriptor(JspDescriptorBean var1);

   ContainerDescriptorBean[] getContainerDescriptors();

   ContainerDescriptorBean createContainerDescriptor();

   void destroyContainerDescriptor(ContainerDescriptorBean var1);

   String[] getAuthFilters();

   void addAuthFilter(String var1);

   void removeAuthFilter(String var1);

   void setAuthFilters(String[] var1);

   CharsetParamsBean[] getCharsetParams();

   CharsetParamsBean createCharsetParams();

   void destroyCharsetParams(CharsetParamsBean var1);

   VirtualDirectoryMappingBean[] getVirtualDirectoryMappings();

   VirtualDirectoryMappingBean createVirtualDirectoryMapping();

   void destroyVirtualDirectoryMapping(VirtualDirectoryMappingBean var1);

   String[] getUrlMatchMaps();

   void addUrlMatchMap(String var1);

   void removeUrlMatchMap(String var1);

   void setUrlMatchMaps(String[] var1);

   SecurityPermissionBean[] getSecurityPermissions();

   SecurityPermissionBean createSecurityPermission();

   void destroySecurityPermission(SecurityPermissionBean var1);

   String[] getContextRoots();

   void addContextRoot(String var1);

   void removeContextRoot(String var1);

   void setContextRoots(String[] var1);

   String[] getWlDispatchPolicies();

   void addWlDispatchPolicy(String var1);

   void removeWlDispatchPolicy(String var1);

   void setWlDispatchPolicies(String[] var1);

   ServletDescriptorBean[] getServletDescriptors();

   ServletDescriptorBean createServletDescriptor();

   ServletDescriptorBean lookupServletDescriptor(String var1);

   void destroyServletDescriptor(ServletDescriptorBean var1);

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

   String[] getComponentFactoryClassName();

   void addComponentFactoryClassName(String var1);

   void removeComponentFactoryClassName(String var1);

   void setComponentFactoryClassName(String[] var1);

   LoggingBean[] getLoggings();

   LoggingBean createLogging();

   void destroyLogging(LoggingBean var1);

   LibraryRefBean[] getLibraryRefs();

   LibraryRefBean createLibraryRef();

   void destroyLibraryRef(LibraryRefBean var1);

   JASPICProviderBean getJASPICProvider();

   FastSwapBean getFastSwap();

   FastSwapBean createFastSwap();

   void destroyFastSwap();

   CoherenceClusterRefBean getCoherenceClusterRef();

   CoherenceClusterRefBean createCoherenceClusterRef();

   void destroyCoherenceClusterRef();

   String getId();

   void setId(String var1);

   String getVersion();

   void setVersion(String var1);

   OsgiFrameworkReferenceBean getOsgiFrameworkReference();

   OsgiFrameworkReferenceBean createOsgiFrameworkReference();

   void destroyOsgiFrameworkReference();

   String getReadyRegistration();

   CdiDescriptorBean getCdiDescriptor();
}
