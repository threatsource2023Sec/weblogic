package weblogic.management.configuration;

import java.util.HashMap;
import javax.management.InvalidAttributeValueException;

public interface DomainMBean extends ConfigurationPropertiesMBean {
   String UPLOAD_DEFAULT = "upload";
   String CONFIG_CHANGE_LOG = "log";
   String CONFIG_CHANGE_AUDIT = "audit";
   String CONFIG_CHANGE_LOG_AND_AUDIT = "logaudit";
   String CONFIG_CHANGE_NONE = "none";

   String getDomainVersion();

   void setDomainVersion(String var1);

   long getLastModificationTime();

   /** @deprecated */
   @Deprecated
   boolean isActive();

   SecurityConfigurationMBean getSecurityConfiguration();

   JTAMBean getJTA();

   JPAMBean getJPA();

   DeploymentConfigurationMBean getDeploymentConfiguration();

   WTCServerMBean[] getWTCServers();

   WTCServerMBean createWTCServer(String var1);

   void destroyWTCServer(WTCServerMBean var1);

   WTCServerMBean lookupWTCServer(String var1);

   LogMBean getLog();

   SNMPAgentMBean getSNMPAgent();

   SNMPAgentDeploymentMBean[] getSNMPAgentDeployments();

   SNMPAgentDeploymentMBean createSNMPAgentDeployment(String var1);

   void destroySNMPAgentDeployment(SNMPAgentDeploymentMBean var1);

   SNMPAgentDeploymentMBean lookupSNMPAgentDeployment(String var1);

   String getRootDirectory();

   void discoverManagedServers();

   boolean discoverManagedServer(String var1);

   /** @deprecated */
   @Deprecated
   Object[] getDisconnectedManagedServers();

   boolean isConsoleEnabled();

   void setConsoleEnabled(boolean var1);

   boolean isJavaServiceConsoleEnabled();

   void setJavaServiceConsoleEnabled(boolean var1);

   String getConsoleContextPath();

   void setConsoleContextPath(String var1);

   String getConsoleExtensionDirectory();

   void setConsoleExtensionDirectory(String var1);

   /** @deprecated */
   @Deprecated
   boolean isAutoConfigurationSaveEnabled();

   void setAutoConfigurationSaveEnabled(boolean var1);

   ServerMBean[] getServers();

   ServerMBean createServer(String var1);

   void destroyServer(ServerMBean var1);

   ServerMBean lookupServer(String var1);

   ServerTemplateMBean[] getServerTemplates();

   ServerTemplateMBean createServerTemplate(String var1);

   void destroyServerTemplate(ServerTemplateMBean var1);

   ServerTemplateMBean lookupServerTemplate(String var1);

   CoherenceServerMBean[] getCoherenceServers();

   CoherenceServerMBean createCoherenceServer(String var1);

   void destroyCoherenceServer(CoherenceServerMBean var1);

   CoherenceServerMBean lookupCoherenceServer(String var1);

   ClusterMBean[] getClusters();

   ClusterMBean createCluster(String var1);

   void destroyCluster(ClusterMBean var1);

   ClusterMBean lookupCluster(String var1);

   DeploymentMBean[] getDeployments();

   /** @deprecated */
   @Deprecated
   FileT3MBean[] getFileT3s();

   /** @deprecated */
   @Deprecated
   FileT3MBean createFileT3(String var1);

   /** @deprecated */
   @Deprecated
   void destroyFileT3(FileT3MBean var1);

   /** @deprecated */
   @Deprecated
   FileT3MBean lookupFileT3(String var1);

   MessagingBridgeMBean[] getMessagingBridges();

   MessagingBridgeMBean createMessagingBridge(String var1);

   void destroyMessagingBridge(MessagingBridgeMBean var1);

   MessagingBridgeMBean lookupMessagingBridge(String var1);

   /** @deprecated */
   @Deprecated
   void addMessagingBridge(MessagingBridgeMBean var1);

   /** @deprecated */
   @Deprecated
   HashMap start();

   /** @deprecated */
   @Deprecated
   HashMap kill();

   void setProductionModeEnabled(boolean var1);

   boolean isProductionModeEnabled();

   EmbeddedLDAPMBean getEmbeddedLDAP();

   boolean isAdministrationPortEnabled();

   void setAdministrationPortEnabled(boolean var1) throws InvalidAttributeValueException;

   int getAdministrationPort();

   void setExalogicOptimizationsEnabled(boolean var1);

   boolean isExalogicOptimizationsEnabled();

   void setJavaServiceEnabled(boolean var1);

   boolean isJavaServiceEnabled();

   void setAdministrationPort(int var1) throws InvalidAttributeValueException;

   int getArchiveConfigurationCount();

   void setArchiveConfigurationCount(int var1) throws InvalidAttributeValueException;

   boolean isConfigBackupEnabled();

   void setConfigBackupEnabled(boolean var1);

   String getConfigurationVersion();

   void setConfigurationVersion(String var1);

   /** @deprecated */
   @Deprecated
   boolean isAdministrationMBeanAuditingEnabled();

   /** @deprecated */
   @Deprecated
   void setAdministrationMBeanAuditingEnabled(boolean var1);

   String getConfigurationAuditType();

   void setConfigurationAuditType(String var1) throws InvalidAttributeValueException;

   boolean isClusterConstraintsEnabled();

   void setClusterConstraintsEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   ApplicationMBean[] getApplications();

   /** @deprecated */
   @Deprecated
   ApplicationMBean createApplication(String var1);

   /** @deprecated */
   @Deprecated
   void destroyApplication(ApplicationMBean var1);

   /** @deprecated */
   @Deprecated
   ApplicationMBean lookupApplication(String var1);

   AppDeploymentMBean[] getAppDeployments();

   AppDeploymentMBean lookupAppDeployment(String var1);

   AppDeploymentMBean createAppDeployment(String var1, String var2) throws IllegalArgumentException;

   void destroyAppDeployment(AppDeploymentMBean var1);

   /** @deprecated */
   @Deprecated
   void addAppDeployment(AppDeploymentMBean var1);

   AppDeploymentMBean[] getInternalAppDeployments();

   AppDeploymentMBean lookupInternalAppDeployment(String var1);

   void setInternalAppDeployments(AppDeploymentMBean[] var1);

   AppDeploymentMBean createInternalAppDeployment(String var1, String var2) throws IllegalArgumentException;

   void addInternalAppDeployment(AppDeploymentMBean var1);

   void destroyInternalAppDeployment(AppDeploymentMBean var1);

   LibraryMBean[] getLibraries();

   LibraryMBean lookupLibrary(String var1);

   LibraryMBean createLibrary(String var1, String var2);

   void destroyLibrary(LibraryMBean var1);

   void addLibrary(LibraryMBean var1);

   DomainLibraryMBean[] getDomainLibraries();

   DomainLibraryMBean lookupDomainLibrary(String var1);

   DomainLibraryMBean createDomainLibrary(String var1, String var2);

   void destroyDomainLibrary(DomainLibraryMBean var1);

   LibraryMBean[] getInternalLibraries();

   LibraryMBean lookupInternalLibrary(String var1);

   LibraryMBean createInternalLibrary(String var1, String var2);

   void addInternalLibrary(LibraryMBean var1);

   void destroyInternalLibrary(LibraryMBean var1);

   void setInternalLibraries(LibraryMBean[] var1);

   BasicDeploymentMBean[] getBasicDeployments();

   WSReliableDeliveryPolicyMBean[] getWSReliableDeliveryPolicies();

   WSReliableDeliveryPolicyMBean lookupWSReliableDeliveryPolicy(String var1);

   WSReliableDeliveryPolicyMBean createWSReliableDeliveryPolicy(String var1);

   void destroyWSReliableDeliveryPolicy(WSReliableDeliveryPolicyMBean var1);

   MachineMBean[] getMachines();

   MachineMBean createMachine(String var1);

   UnixMachineMBean createUnixMachine(String var1);

   void destroyMachine(MachineMBean var1);

   MachineMBean lookupMachine(String var1);

   XMLEntityCacheMBean[] getXMLEntityCaches();

   XMLEntityCacheMBean createXMLEntityCache(String var1);

   XMLEntityCacheMBean lookupXMLEntityCache(String var1);

   void destroyXMLEntityCache(XMLEntityCacheMBean var1);

   XMLRegistryMBean[] getXMLRegistries();

   XMLRegistryMBean createXMLRegistry(String var1);

   void destroyXMLRegistry(XMLRegistryMBean var1);

   XMLRegistryMBean lookupXMLRegistry(String var1);

   TargetMBean[] getTargets();

   TargetMBean lookupTarget(String var1) throws IllegalArgumentException;

   JMSServerMBean[] getJMSServers();

   JMSServerMBean createJMSServer(String var1);

   void destroyJMSServer(JMSServerMBean var1);

   JMSServerMBean lookupJMSServer(String var1);

   /** @deprecated */
   @Deprecated
   void addJMSServer(JMSServerMBean var1);

   /** @deprecated */
   @Deprecated
   JMSStoreMBean[] getJMSStores();

   /** @deprecated */
   @Deprecated
   JMSStoreMBean lookupJMSStore(String var1);

   /** @deprecated */
   @Deprecated
   JMSJDBCStoreMBean[] getJMSJDBCStores();

   /** @deprecated */
   @Deprecated
   JMSJDBCStoreMBean createJMSJDBCStore(String var1);

   /** @deprecated */
   @Deprecated
   void destroyJMSJDBCStore(JMSJDBCStoreMBean var1);

   /** @deprecated */
   @Deprecated
   JMSJDBCStoreMBean lookupJMSJDBCStore(String var1);

   /** @deprecated */
   @Deprecated
   JMSFileStoreMBean[] getJMSFileStores();

   /** @deprecated */
   @Deprecated
   JMSFileStoreMBean createJMSFileStore(String var1);

   /** @deprecated */
   @Deprecated
   void destroyJMSFileStore(JMSFileStoreMBean var1);

   /** @deprecated */
   @Deprecated
   JMSFileStoreMBean lookupJMSFileStore(String var1);

   /** @deprecated */
   @Deprecated
   JMSDestinationMBean[] getJMSDestinations();

   JMSDestinationMBean lookupJMSDestination(String var1);

   /** @deprecated */
   @Deprecated
   JMSQueueMBean[] getJMSQueues();

   /** @deprecated */
   @Deprecated
   JMSQueueMBean createJMSQueue(String var1);

   /** @deprecated */
   @Deprecated
   void destroyJMSQueue(JMSQueueMBean var1);

   /** @deprecated */
   @Deprecated
   JMSQueueMBean lookupJMSQueue(String var1);

   /** @deprecated */
   @Deprecated
   JMSTopicMBean[] getJMSTopics();

   /** @deprecated */
   @Deprecated
   JMSTopicMBean createJMSTopic(String var1);

   /** @deprecated */
   @Deprecated
   void destroyJMSTopic(JMSTopicMBean var1);

   /** @deprecated */
   @Deprecated
   JMSTopicMBean lookupJMSTopic(String var1);

   /** @deprecated */
   @Deprecated
   JMSDistributedQueueMBean[] getJMSDistributedQueues();

   /** @deprecated */
   @Deprecated
   JMSDistributedQueueMBean createJMSDistributedQueue(String var1);

   /** @deprecated */
   @Deprecated
   void destroyJMSDistributedQueue(JMSDistributedQueueMBean var1);

   /** @deprecated */
   @Deprecated
   JMSDistributedQueueMBean lookupJMSDistributedQueue(String var1);

   /** @deprecated */
   @Deprecated
   JMSDistributedTopicMBean[] getJMSDistributedTopics();

   /** @deprecated */
   @Deprecated
   JMSDistributedTopicMBean createJMSDistributedTopic(String var1);

   /** @deprecated */
   @Deprecated
   void destroyJMSDistributedTopic(JMSDistributedTopicMBean var1);

   /** @deprecated */
   @Deprecated
   JMSDistributedTopicMBean lookupJMSDistributedTopic(String var1);

   /** @deprecated */
   @Deprecated
   JMSTemplateMBean[] getJMSTemplates();

   /** @deprecated */
   @Deprecated
   JMSTemplateMBean createJMSTemplate(String var1);

   /** @deprecated */
   @Deprecated
   void destroyJMSTemplate(JMSTemplateMBean var1);

   /** @deprecated */
   @Deprecated
   JMSTemplateMBean lookupJMSTemplate(String var1);

   /** @deprecated */
   @Deprecated
   NetworkChannelMBean[] getNetworkChannels();

   /** @deprecated */
   @Deprecated
   NetworkChannelMBean createNetworkChannel(String var1);

   /** @deprecated */
   @Deprecated
   void destroyNetworkChannel(NetworkChannelMBean var1);

   /** @deprecated */
   @Deprecated
   NetworkChannelMBean lookupNetworkChannel(String var1);

   VirtualHostMBean[] getVirtualHosts();

   VirtualHostMBean createVirtualHost(String var1);

   void destroyVirtualHost(VirtualHostMBean var1);

   VirtualHostMBean lookupVirtualHost(String var1);

   VirtualTargetMBean[] getVirtualTargets();

   VirtualTargetMBean createVirtualTarget(String var1);

   void destroyVirtualTarget(VirtualTargetMBean var1);

   VirtualTargetMBean lookupVirtualTarget(String var1);

   MigratableTargetMBean[] getMigratableTargets();

   MigratableTargetMBean createMigratableTarget(String var1);

   void destroyMigratableTarget(MigratableTargetMBean var1);

   MigratableTargetMBean lookupMigratableTarget(String var1);

   EJBContainerMBean getEJBContainer();

   EJBContainerMBean createEJBContainer();

   void destroyEJBContainer();

   WebAppContainerMBean getWebAppContainer();

   CdiContainerMBean getCdiContainer();

   JMXMBean getJMX();

   SelfTuningMBean getSelfTuning();

   ResourceManagementMBean getResourceManagement();

   PathServiceMBean[] getPathServices();

   PathServiceMBean createPathService(String var1);

   void destroyPathService(PathServiceMBean var1);

   PathServiceMBean lookupPathService(String var1);

   /** @deprecated */
   @Deprecated
   void addPathService(PathServiceMBean var1);

   /** @deprecated */
   @Deprecated
   JMSDestinationKeyMBean[] getJMSDestinationKeys();

   /** @deprecated */
   @Deprecated
   JMSDestinationKeyMBean createJMSDestinationKey(String var1);

   /** @deprecated */
   @Deprecated
   void destroyJMSDestinationKey(JMSDestinationKeyMBean var1);

   /** @deprecated */
   @Deprecated
   JMSDestinationKeyMBean lookupJMSDestinationKey(String var1);

   /** @deprecated */
   @Deprecated
   JMSConnectionFactoryMBean[] getJMSConnectionFactories();

   /** @deprecated */
   @Deprecated
   JMSConnectionFactoryMBean createJMSConnectionFactory(String var1);

   /** @deprecated */
   @Deprecated
   void destroyJMSConnectionFactory(JMSConnectionFactoryMBean var1);

   /** @deprecated */
   @Deprecated
   JMSConnectionFactoryMBean lookupJMSConnectionFactory(String var1);

   /** @deprecated */
   @Deprecated
   JMSSessionPoolMBean[] getJMSSessionPools();

   /** @deprecated */
   @Deprecated
   JMSSessionPoolMBean createJMSSessionPool(String var1);

   /** @deprecated */
   @Deprecated
   JMSSessionPoolMBean createJMSSessionPool(String var1, JMSSessionPoolMBean var2);

   /** @deprecated */
   @Deprecated
   void destroyJMSSessionPool(JMSSessionPoolMBean var1);

   /** @deprecated */
   @Deprecated
   JMSSessionPoolMBean lookupJMSSessionPool(String var1);

   JMSBridgeDestinationMBean createJMSBridgeDestination(String var1);

   void destroyJMSBridgeDestination(JMSBridgeDestinationMBean var1);

   JMSBridgeDestinationMBean lookupJMSBridgeDestination(String var1);

   JMSBridgeDestinationMBean[] getJMSBridgeDestinations();

   /** @deprecated */
   @Deprecated
   void addJMSBridgeDestination(JMSBridgeDestinationMBean var1);

   BridgeDestinationMBean createBridgeDestination(String var1);

   void destroyBridgeDestination(BridgeDestinationMBean var1);

   BridgeDestinationMBean lookupBridgeDestination(String var1);

   /** @deprecated */
   @Deprecated
   BridgeDestinationMBean[] getBridgeDestinations();

   /** @deprecated */
   @Deprecated
   ForeignJMSServerMBean[] getForeignJMSServers();

   /** @deprecated */
   @Deprecated
   ForeignJMSServerMBean createForeignJMSServer(String var1);

   /** @deprecated */
   @Deprecated
   void destroyForeignJMSServer(ForeignJMSServerMBean var1);

   /** @deprecated */
   @Deprecated
   ForeignJMSServerMBean lookupForeignJMSServer(String var1);

   ShutdownClassMBean[] getShutdownClasses();

   ShutdownClassMBean createShutdownClass(String var1);

   void destroyShutdownClass(ShutdownClassMBean var1);

   ShutdownClassMBean lookupShutdownClass(String var1);

   StartupClassMBean[] getStartupClasses();

   StartupClassMBean createStartupClass(String var1);

   void destroyStartupClass(StartupClassMBean var1);

   StartupClassMBean lookupStartupClass(String var1);

   SingletonServiceMBean[] getSingletonServices();

   SingletonServiceMBean createSingletonService(String var1);

   void destroySingletonService(SingletonServiceMBean var1);

   SingletonServiceMBean lookupSingletonService(String var1);

   MailSessionMBean[] getMailSessions();

   MailSessionMBean createMailSession(String var1);

   void destroyMailSession(MailSessionMBean var1);

   MailSessionMBean lookupMailSession(String var1);

   /** @deprecated */
   @Deprecated
   void addMailSession(MailSessionMBean var1);

   JoltConnectionPoolMBean[] getJoltConnectionPools();

   JoltConnectionPoolMBean createJoltConnectionPool(String var1);

   void destroyJoltConnectionPool(JoltConnectionPoolMBean var1);

   JoltConnectionPoolMBean lookupJoltConnectionPool(String var1);

   LogFilterMBean[] getLogFilters();

   LogFilterMBean createLogFilter(String var1);

   void destroyLogFilter(LogFilterMBean var1);

   LogFilterMBean lookupLogFilter(String var1);

   FileStoreMBean[] getFileStores();

   FileStoreMBean createFileStore(String var1);

   void destroyFileStore(FileStoreMBean var1);

   FileStoreMBean lookupFileStore(String var1);

   /** @deprecated */
   @Deprecated
   void addFileStore(FileStoreMBean var1);

   /** @deprecated */
   @Deprecated
   ReplicatedStoreMBean[] getReplicatedStores();

   /** @deprecated */
   @Deprecated
   ReplicatedStoreMBean createReplicatedStore(String var1);

   /** @deprecated */
   @Deprecated
   void destroyReplicatedStore(ReplicatedStoreMBean var1);

   /** @deprecated */
   @Deprecated
   ReplicatedStoreMBean lookupReplicatedStore(String var1);

   JDBCStoreMBean[] getJDBCStores();

   JDBCStoreMBean createJDBCStore(String var1);

   void destroyJDBCStore(JDBCStoreMBean var1);

   JDBCStoreMBean lookupJDBCStore(String var1);

   /** @deprecated */
   @Deprecated
   void addJDBCStore(JDBCStoreMBean var1);

   JMSSystemResourceMBean[] getJMSSystemResources();

   JMSSystemResourceMBean createJMSSystemResource(String var1);

   JMSSystemResourceMBean createJMSSystemResource(String var1, String var2);

   void destroyJMSSystemResource(JMSSystemResourceMBean var1);

   JMSSystemResourceMBean lookupJMSSystemResource(String var1);

   /** @deprecated */
   @Deprecated
   void addJMSSystemResource(JMSSystemResourceMBean var1);

   CustomResourceMBean[] getCustomResources();

   CustomResourceMBean createCustomResource(String var1, String var2, String var3);

   CustomResourceMBean createCustomResource(String var1, String var2, String var3, String var4);

   void destroyCustomResource(CustomResourceMBean var1);

   CustomResourceMBean lookupCustomResource(String var1);

   ForeignJNDIProviderMBean[] getForeignJNDIProviders();

   ForeignJNDIProviderMBean lookupForeignJNDIProvider(String var1);

   ForeignJNDIProviderMBean createForeignJNDIProvider(String var1);

   void addForeignJNDIProvider(ForeignJNDIProviderMBean var1);

   void destroyForeignJNDIProvider(ForeignJNDIProviderMBean var1);

   String getAdminServerName();

   void setAdminServerName(String var1);

   String getAdministrationProtocol();

   void setAdministrationProtocol(String var1);

   WLDFSystemResourceMBean[] getWLDFSystemResources();

   WLDFSystemResourceMBean createWLDFSystemResource(String var1);

   WLDFSystemResourceMBean createWLDFSystemResource(String var1, String var2);

   WLDFSystemResourceMBean createWLDFSystemResourceFromBuiltin(String var1, String var2);

   void destroyWLDFSystemResource(WLDFSystemResourceMBean var1);

   WLDFSystemResourceMBean lookupWLDFSystemResource(String var1);

   /** @deprecated */
   @Deprecated
   void addWLDFSystemResource(WLDFSystemResourceMBean var1);

   JDBCSystemResourceMBean[] getJDBCSystemResources();

   JDBCSystemResourceMBean createJDBCSystemResource(String var1);

   JDBCSystemResourceMBean createJDBCSystemResource(String var1, String var2);

   JDBCSystemResourceMBean lookupJDBCSystemResource(String var1);

   void destroyJDBCSystemResource(JDBCSystemResourceMBean var1);

   /** @deprecated */
   @Deprecated
   void addJDBCSystemResource(JDBCSystemResourceMBean var1);

   SystemResourceMBean[] getSystemResources();

   SystemResourceMBean lookupSystemResource(String var1);

   SAFAgentMBean[] getSAFAgents();

   SAFAgentMBean createSAFAgent(String var1);

   void destroySAFAgent(SAFAgentMBean var1);

   SAFAgentMBean lookupSAFAgent(String var1);

   /** @deprecated */
   @Deprecated
   void addSAFAgent(SAFAgentMBean var1);

   MigratableRMIServiceMBean[] getMigratableRMIServices();

   MigratableRMIServiceMBean createMigratableRMIService(String var1);

   void destroyMigratableRMIService(MigratableRMIServiceMBean var1);

   MigratableRMIServiceMBean lookupMigratableRMIService(String var1);

   AdminServerMBean getAdminServerMBean();

   AdminServerMBean createAdminServerMBean();

   void destroyAdminServerMBean();

   /** @deprecated */
   @Deprecated
   JMSDistributedQueueMemberMBean[] getJMSDistributedQueueMembers();

   /** @deprecated */
   @Deprecated
   JMSDistributedQueueMemberMBean createJMSDistributedQueueMember(String var1);

   /** @deprecated */
   @Deprecated
   void destroyJMSDistributedQueueMember(JMSDistributedQueueMemberMBean var1);

   /** @deprecated */
   @Deprecated
   JMSDistributedQueueMemberMBean lookupJMSDistributedQueueMember(String var1);

   /** @deprecated */
   @Deprecated
   JMSDistributedTopicMemberMBean[] getJMSDistributedTopicMembers();

   /** @deprecated */
   @Deprecated
   JMSDistributedTopicMemberMBean createJMSDistributedTopicMember(String var1);

   /** @deprecated */
   @Deprecated
   void destroyJMSDistributedTopicMember(JMSDistributedTopicMemberMBean var1);

   /** @deprecated */
   @Deprecated
   JMSDistributedTopicMemberMBean lookupJMSDistributedTopicMember(String var1);

   SNMPTrapDestinationMBean createSNMPTrapDestination(String var1);

   void destroySNMPTrapDestination(SNMPTrapDestinationMBean var1);

   SNMPTrapDestinationMBean[] getSNMPTrapDestinations();

   SNMPProxyMBean[] getSNMPProxies();

   SNMPProxyMBean createSNMPProxy(String var1);

   void destroySNMPProxy(SNMPProxyMBean var1);

   SNMPGaugeMonitorMBean[] getSNMPGaugeMonitors();

   SNMPGaugeMonitorMBean createSNMPGaugeMonitor(String var1);

   void destroySNMPGaugeMonitor(SNMPGaugeMonitorMBean var1);

   SNMPStringMonitorMBean[] getSNMPStringMonitors();

   SNMPStringMonitorMBean createSNMPStringMonitor(String var1);

   void destroySNMPStringMonitor(SNMPStringMonitorMBean var1);

   SNMPCounterMonitorMBean[] getSNMPCounterMonitors();

   SNMPCounterMonitorMBean createSNMPCounterMonitor(String var1);

   void destroySNMPCounterMonitor(SNMPCounterMonitorMBean var1);

   SNMPLogFilterMBean[] getSNMPLogFilters();

   SNMPLogFilterMBean createSNMPLogFilter(String var1);

   void destroySNMPLogFilter(SNMPLogFilterMBean var1);

   SNMPAttributeChangeMBean[] getSNMPAttributeChanges();

   SNMPAttributeChangeMBean createSNMPAttributeChange(String var1);

   void destroySNMPAttributeChange(SNMPAttributeChangeMBean var1);

   WebserviceSecurityMBean[] getWebserviceSecurities();

   WebserviceSecurityMBean lookupWebserviceSecurity(String var1);

   WebserviceSecurityMBean createWebserviceSecurity(String var1);

   void destroyWebserviceSecurity(WebserviceSecurityMBean var1);

   ForeignJMSConnectionFactoryMBean[] getForeignJMSConnectionFactories();

   ForeignJMSConnectionFactoryMBean lookupForeignJMSConnectionFactory(String var1);

   ForeignJMSConnectionFactoryMBean createForeignJMSConnectionFactory(String var1);

   void destroyForeignJMSConnectionFactory(ForeignJMSConnectionFactoryMBean var1);

   ForeignJMSDestinationMBean[] getForeignJMSDestinations();

   ForeignJMSDestinationMBean lookupForeignJMSDestination(String var1);

   ForeignJMSDestinationMBean createForeignJMSDestination(String var1);

   void destroyForeignJMSDestination(ForeignJMSDestinationMBean var1);

   /** @deprecated */
   @Deprecated
   JMSConnectionConsumerMBean[] getJMSConnectionConsumers();

   JMSConnectionConsumerMBean lookupJMSConnectionConsumer(String var1);

   JMSConnectionConsumerMBean createJMSConnectionConsumer(String var1);

   void destroyJMSConnectionConsumer(JMSConnectionConsumerMBean var1);

   ForeignJMSDestinationMBean createForeignJMSDestination(String var1, ForeignJMSDestinationMBean var2);

   ForeignJMSConnectionFactoryMBean createForeignJMSConnectionFactory(String var1, ForeignJMSConnectionFactoryMBean var2);

   JMSDistributedQueueMemberMBean createJMSDistributedQueueMember(String var1, JMSDistributedQueueMemberMBean var2);

   JMSDistributedTopicMemberMBean createJMSDistributedTopicMember(String var1, JMSDistributedTopicMemberMBean var2);

   JMSTopicMBean createJMSTopic(String var1, JMSTopicMBean var2);

   JMSQueueMBean createJMSQueue(String var1, JMSQueueMBean var2);

   boolean isAutoDeployForSubmodulesEnabled();

   void setAutoDeployForSubmodulesEnabled(boolean var1);

   AdminConsoleMBean getAdminConsole();

   boolean isInternalAppsDeployOnDemandEnabled();

   void setInternalAppsDeployOnDemandEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean isGuardianEnabled();

   /** @deprecated */
   @Deprecated
   void setGuardianEnabled(boolean var1);

   boolean isOCMEnabled();

   void setOCMEnabled(boolean var1);

   boolean isMsgIdPrefixCompatibilityEnabled();

   void setMsgIdPrefixCompatibilityEnabled(boolean var1);

   boolean isLogFormatCompatibilityEnabled();

   void setLogFormatCompatibilityEnabled(boolean var1);

   CoherenceClusterSystemResourceMBean[] getCoherenceClusterSystemResources();

   CoherenceClusterSystemResourceMBean createCoherenceClusterSystemResource(String var1);

   void destroyCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean var1);

   CoherenceClusterSystemResourceMBean lookupCoherenceClusterSystemResource(String var1);

   /** @deprecated */
   @Deprecated
   void addCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean var1);

   RestfulManagementServicesMBean getRestfulManagementServices();

   SystemComponentMBean[] getSystemComponents();

   SystemComponentMBean createSystemComponent(String var1, String var2);

   void destroySystemComponent(SystemComponentMBean var1);

   SystemComponentMBean lookupSystemComponent(String var1);

   SystemComponentConfigurationMBean[] getSystemComponentConfigurations();

   SystemComponentConfigurationMBean createSystemComponentConfiguration(String var1, String var2);

   void destroySystemComponentConfiguration(SystemComponentConfigurationMBean var1);

   SystemComponentConfigurationMBean lookupSystemComponentConfiguration(String var1);

   OsgiFrameworkMBean[] getOsgiFrameworks();

   OsgiFrameworkMBean lookupOsgiFramework(String var1);

   /** @deprecated */
   @Deprecated
   void addOsgiFramework(OsgiFrameworkMBean var1);

   OsgiFrameworkMBean createOsgiFramework(String var1);

   void destroyOsgiFramework(OsgiFrameworkMBean var1);

   WebserviceTestpageMBean getWebserviceTestpage();

   int getServerMigrationHistorySize();

   void setServerMigrationHistorySize(int var1);

   int getServiceMigrationHistorySize();

   void setServiceMigrationHistorySize(int var1);

   CoherenceManagementClusterMBean[] getCoherenceManagementClusters();

   CoherenceManagementClusterMBean createCoherenceManagementCluster(String var1);

   CoherenceManagementClusterMBean lookupCoherenceManagementCluster(String var1);

   void destroyCoherenceManagementCluster(CoherenceManagementClusterMBean var1);

   PartitionMBean[] getPartitions();

   PartitionMBean lookupPartition(String var1);

   PartitionMBean findPartitionByID(String var1);

   PartitionMBean createPartition(String var1);

   PartitionMBean createPartition(String var1, String var2);

   void destroyPartition(PartitionMBean var1);

   boolean arePartitionsPresent();

   String getPartitionUriSpace();

   void setPartitionUriSpace(String var1);

   ResourceGroupMBean[] getResourceGroups();

   ResourceGroupMBean createResourceGroup(String var1);

   ResourceGroupMBean lookupResourceGroup(String var1);

   void destroyResourceGroup(ResourceGroupMBean var1);

   ResourceGroupTemplateMBean[] getResourceGroupTemplates();

   ResourceGroupTemplateMBean lookupResourceGroupTemplate(String var1);

   ResourceGroupTemplateMBean createResourceGroupTemplate(String var1);

   void destroyResourceGroupTemplate(ResourceGroupTemplateMBean var1);

   ConfigurationMBean[] findConfigBeansWithTags(String var1, String... var2);

   ConfigurationMBean[] findConfigBeansWithTags(String var1, boolean var2, String... var3);

   String[] listTags(String var1);

   int getMaxConcurrentNewThreads();

   void setMaxConcurrentNewThreads(int var1);

   int getMaxConcurrentLongRunningRequests();

   void setMaxConcurrentLongRunningRequests(int var1);

   boolean isParallelDeployApplications();

   void setParallelDeployApplications(boolean var1);

   boolean isParallelDeployApplicationModules();

   void setParallelDeployApplicationModules(boolean var1);

   ManagedExecutorServiceTemplateMBean[] getManagedExecutorServiceTemplates();

   ManagedExecutorServiceTemplateMBean createManagedExecutorServiceTemplate(String var1);

   void destroyManagedExecutorServiceTemplate(ManagedExecutorServiceTemplateMBean var1);

   ManagedExecutorServiceTemplateMBean lookupManagedExecutorServiceTemplate(String var1);

   ManagedScheduledExecutorServiceTemplateMBean[] getManagedScheduledExecutorServiceTemplates();

   ManagedScheduledExecutorServiceTemplateMBean createManagedScheduledExecutorServiceTemplate(String var1);

   void destroyManagedScheduledExecutorServiceTemplate(ManagedScheduledExecutorServiceTemplateMBean var1);

   ManagedScheduledExecutorServiceTemplateMBean lookupManagedScheduledExecutorServiceTemplate(String var1);

   ManagedThreadFactoryTemplateMBean[] getManagedThreadFactoryTemplates();

   ManagedThreadFactoryTemplateMBean createManagedThreadFactoryTemplate(String var1);

   void destroyManagedThreadFactoryTemplate(ManagedThreadFactoryTemplateMBean var1);

   ManagedThreadFactoryTemplateMBean lookupManagedThreadFactoryTemplate(String var1);

   ManagedExecutorServiceMBean[] getManagedExecutorServices();

   ManagedExecutorServiceMBean createManagedExecutorService(String var1);

   void destroyManagedExecutorService(ManagedExecutorServiceMBean var1);

   ManagedExecutorServiceMBean lookupManagedExecutorService(String var1);

   /** @deprecated */
   @Deprecated
   void addManagedExecutorService(ManagedExecutorServiceMBean var1);

   ManagedScheduledExecutorServiceMBean[] getManagedScheduledExecutorServices();

   ManagedScheduledExecutorServiceMBean createManagedScheduledExecutorService(String var1);

   void destroyManagedScheduledExecutorService(ManagedScheduledExecutorServiceMBean var1);

   ManagedScheduledExecutorServiceMBean lookupManagedScheduledExecutorService(String var1);

   /** @deprecated */
   @Deprecated
   void addManagedScheduledExecutorService(ManagedScheduledExecutorServiceMBean var1);

   ManagedThreadFactoryMBean[] getManagedThreadFactories();

   ManagedThreadFactoryMBean createManagedThreadFactory(String var1);

   void destroyManagedThreadFactory(ManagedThreadFactoryMBean var1);

   ManagedThreadFactoryMBean lookupManagedThreadFactory(String var1);

   /** @deprecated */
   @Deprecated
   void addManagedThreadFactory(ManagedThreadFactoryMBean var1);

   PartitionTemplateMBean[] getPartitionTemplates();

   PartitionTemplateMBean lookupPartitionTemplate(String var1);

   PartitionTemplateMBean createPartitionTemplate(String var1);

   void destroyPartitionTemplate(PartitionTemplateMBean var1);

   PartitionMBean createPartitionFromTemplate(String var1, PartitionTemplateMBean var2);

   /** @deprecated */
   @Deprecated
   LifecycleManagerEndPointMBean[] getLifecycleManagerEndPoints();

   /** @deprecated */
   @Deprecated
   LifecycleManagerEndPointMBean lookupLifecycleManagerEndPoint(String var1);

   /** @deprecated */
   @Deprecated
   LifecycleManagerEndPointMBean createLifecycleManagerEndPoint(String var1);

   /** @deprecated */
   @Deprecated
   void destroyLifecycleManagerEndPoint(LifecycleManagerEndPointMBean var1);

   InterceptorsMBean getInterceptors();

   BatchConfigMBean getBatchConfig();

   DebugPatchesMBean getDebugPatches();

   PartitionWorkManagerMBean[] getPartitionWorkManagers();

   PartitionWorkManagerMBean createPartitionWorkManager(String var1);

   void destroyPartitionWorkManager(PartitionWorkManagerMBean var1);

   PartitionWorkManagerMBean lookupPartitionWorkManager(String var1);

   void setDiagnosticContextCompatibilityModeEnabled(boolean var1);

   boolean isDiagnosticContextCompatibilityModeEnabled();

   String getBatchJobsDataSourceJndiName();

   void setBatchJobsDataSourceJndiName(String var1);

   String getBatchJobsExecutorServiceName();

   void setBatchJobsExecutorServiceName(String var1);

   OptionalFeatureDeploymentMBean getOptionalFeatureDeployment();

   LifecycleManagerConfigMBean getLifecycleManagerConfig();

   /** @deprecated */
   @Deprecated
   String getSiteName();

   /** @deprecated */
   @Deprecated
   void setSiteName(String var1) throws InvalidAttributeValueException;

   String getName();

   boolean isEnableEECompliantClassloadingForEmbeddedAdapters();

   void setEnableEECompliantClassloadingForEmbeddedAdapters(boolean var1);

   VirtualTargetMBean[] findAllVirtualTargets();

   VirtualTargetMBean lookupInAllVirtualTargets(String var1);

   TargetMBean[] findAllTargets();

   TargetMBean lookupInAllTargets(String var1);

   boolean isDBPassiveMode();

   void setDBPassiveMode(boolean var1);

   int getDBPassiveModeGracePeriodSeconds();

   void setDBPassiveModeGracePeriodSeconds(int var1);

   String getInstalledSoftwareVersion();

   void setInstalledSoftwareVersion(String var1);

   CalloutMBean[] getCallouts();

   CalloutMBean createCallout(String var1);

   void destroyCallout(CalloutMBean var1);

   CalloutMBean lookupCallout(String var1);
}
