package weblogic.management.configuration;

public interface ResourceGroupTemplateMBean extends ConfigurationMBean {
   String getName();

   @ExportCustomizeableValues(
      saveDefault = true
   )
   AppDeploymentMBean[] getAppDeployments();

   AppDeploymentMBean lookupAppDeployment(String var1);

   AppDeploymentMBean createAppDeployment(String var1, String var2) throws IllegalArgumentException;

   void destroyAppDeployment(AppDeploymentMBean var1);

   LibraryMBean[] getLibraries();

   LibraryMBean lookupLibrary(String var1);

   LibraryMBean createLibrary(String var1, String var2);

   void destroyLibrary(LibraryMBean var1);

   BasicDeploymentMBean[] getBasicDeployments();

   DeploymentMBean[] getDeployments();

   SystemResourceMBean[] getSystemResources();

   @ExportCustomizeableValues(
      saveDefault = true
   )
   JMSServerMBean[] getJMSServers();

   JMSServerMBean createJMSServer(String var1);

   void destroyJMSServer(JMSServerMBean var1);

   JMSServerMBean lookupJMSServer(String var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   MessagingBridgeMBean[] getMessagingBridges();

   MessagingBridgeMBean createMessagingBridge(String var1);

   void destroyMessagingBridge(MessagingBridgeMBean var1);

   MessagingBridgeMBean lookupMessagingBridge(String var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   PathServiceMBean[] getPathServices();

   PathServiceMBean createPathService(String var1);

   void destroyPathService(PathServiceMBean var1);

   PathServiceMBean lookupPathService(String var1);

   JMSBridgeDestinationMBean createJMSBridgeDestination(String var1);

   void destroyJMSBridgeDestination(JMSBridgeDestinationMBean var1);

   JMSBridgeDestinationMBean lookupJMSBridgeDestination(String var1);

   JMSBridgeDestinationMBean[] getJMSBridgeDestinations();

   @ExportCustomizeableValues(
      saveDefault = true
   )
   MailSessionMBean[] getMailSessions();

   MailSessionMBean createMailSession(String var1);

   void destroyMailSession(MailSessionMBean var1);

   MailSessionMBean lookupMailSession(String var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   FileStoreMBean[] getFileStores();

   FileStoreMBean createFileStore(String var1);

   void destroyFileStore(FileStoreMBean var1);

   FileStoreMBean lookupFileStore(String var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   JDBCStoreMBean[] getJDBCStores();

   JDBCStoreMBean createJDBCStore(String var1);

   void destroyJDBCStore(JDBCStoreMBean var1);

   JDBCStoreMBean lookupJDBCStore(String var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   JMSSystemResourceMBean[] getJMSSystemResources();

   JMSSystemResourceMBean createJMSSystemResource(String var1);

   JMSSystemResourceMBean createJMSSystemResource(String var1, String var2);

   void destroyJMSSystemResource(JMSSystemResourceMBean var1);

   JMSSystemResourceMBean lookupJMSSystemResource(String var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   ForeignJNDIProviderMBean[] getForeignJNDIProviders();

   ForeignJNDIProviderMBean lookupForeignJNDIProvider(String var1);

   ForeignJNDIProviderMBean createForeignJNDIProvider(String var1);

   void destroyForeignJNDIProvider(ForeignJNDIProviderMBean var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   JDBCSystemResourceMBean[] getJDBCSystemResources();

   JDBCSystemResourceMBean createJDBCSystemResource(String var1);

   JDBCSystemResourceMBean createJDBCSystemResource(String var1, String var2);

   JDBCSystemResourceMBean lookupJDBCSystemResource(String var1);

   void destroyJDBCSystemResource(JDBCSystemResourceMBean var1);

   WLDFSystemResourceMBean[] getWLDFSystemResources();

   WLDFSystemResourceMBean createWLDFSystemResource(String var1);

   WLDFSystemResourceMBean createWLDFSystemResource(String var1, String var2);

   WLDFSystemResourceMBean lookupWLDFSystemResource(String var1);

   void destroyWLDFSystemResource(WLDFSystemResourceMBean var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   SAFAgentMBean[] getSAFAgents();

   SAFAgentMBean createSAFAgent(String var1);

   void destroySAFAgent(SAFAgentMBean var1);

   SAFAgentMBean lookupSAFAgent(String var1);

   /** @deprecated */
   @Deprecated
   CoherenceClusterSystemResourceMBean[] getCoherenceClusterSystemResources();

   /** @deprecated */
   @Deprecated
   CoherenceClusterSystemResourceMBean createCoherenceClusterSystemResource(String var1);

   /** @deprecated */
   @Deprecated
   void destroyCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean var1);

   /** @deprecated */
   @Deprecated
   CoherenceClusterSystemResourceMBean lookupCoherenceClusterSystemResource(String var1);

   OsgiFrameworkMBean[] getOsgiFrameworks();

   OsgiFrameworkMBean lookupOsgiFramework(String var1);

   OsgiFrameworkMBean createOsgiFramework(String var1);

   void destroyOsgiFramework(OsgiFrameworkMBean var1);

   void processResources(GeneralResourceProcessor var1);

   void processResources(DiscreteResourceProcessor var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   ManagedExecutorServiceMBean[] getManagedExecutorServices();

   ManagedExecutorServiceMBean createManagedExecutorService(String var1);

   void destroyManagedExecutorService(ManagedExecutorServiceMBean var1);

   ManagedExecutorServiceMBean lookupManagedExecutorService(String var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   ManagedScheduledExecutorServiceMBean[] getManagedScheduledExecutorServices();

   ManagedScheduledExecutorServiceMBean createManagedScheduledExecutorService(String var1);

   void destroyManagedScheduledExecutorService(ManagedScheduledExecutorServiceMBean var1);

   ManagedScheduledExecutorServiceMBean lookupManagedScheduledExecutorService(String var1);

   @ExportCustomizeableValues(
      saveDefault = true
   )
   ManagedThreadFactoryMBean[] getManagedThreadFactories();

   ManagedThreadFactoryMBean createManagedThreadFactory(String var1);

   void destroyManagedThreadFactory(ManagedThreadFactoryMBean var1);

   ManagedThreadFactoryMBean lookupManagedThreadFactory(String var1);

   boolean isImMutable();

   String getResourceGroupTemplateID();

   String getUploadDirectoryName();

   void setUploadDirectoryName(String var1);
}
