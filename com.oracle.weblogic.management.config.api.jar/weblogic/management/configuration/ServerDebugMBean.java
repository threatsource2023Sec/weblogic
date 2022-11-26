package weblogic.management.configuration;

import java.io.OutputStream;
import javax.management.InvalidAttributeValueException;

public interface ServerDebugMBean extends KernelDebugMBean {
   String MAGIC_THREAD_DUMP_FILE_NAME = "debugMagicThreadDumpFile";
   String DIAG_CTX_DEBUG_MODE_OFF = "Off";
   String DIAG_CTX_DEBUG_MODE_AND = "And";
   String DIAG_CTX_DEBUG_MODE_OR = "Or";

   boolean isPartitionDebugLoggingEnabled();

   void setPartitionDebugLoggingEnabled(boolean var1);

   String getDiagnosticContextDebugMode();

   void setDiagnosticContextDebugMode(String var1);

   String[] getDebugMaskCriterias();

   void setDebugMaskCriterias(String[] var1);

   ServerMBean getServer();

   void setServer(ServerMBean var1) throws InvalidAttributeValueException;

   boolean getListenThreadDebug();

   void setListenThreadDebug(boolean var1) throws InvalidAttributeValueException;

   boolean isMagicThreadDumpEnabled();

   void setMagicThreadDumpEnabled(boolean var1);

   String getMagicThreadDumpHost();

   void setMagicThreadDumpHost(String var1) throws InvalidAttributeValueException;

   String getMagicThreadDumpFile();

   void setMagicThreadDumpFile(String var1) throws InvalidAttributeValueException;

   boolean getMagicThreadDumpBackToSocket();

   void setMagicThreadDumpBackToSocket(boolean var1) throws InvalidAttributeValueException;

   void setBugReportServiceWsdlUrl(String var1);

   String getBugReportServiceWsdlUrl();

   void setStartupTimeoutNumOfThreadDump(int var1);

   int getStartupTimeoutNumOfThreadDump();

   void setStartupTimeoutThreadDumpInterval(int var1);

   int getStartupTimeoutThreadDumpInterval();

   void setGracefulShutdownTimeoutNumOfThreadDump(int var1);

   int getGracefulShutdownTimeoutNumOfThreadDump();

   void setGracefulShutdownTimeoutThreadDumpInterval(int var1);

   int getGracefulShutdownTimeoutThreadDumpInterval();

   void setForceShutdownTimeoutNumOfThreadDump(int var1);

   int getForceShutdownTimeoutNumOfThreadDump();

   void setForceShutdownTimeoutThreadDumpInterval(int var1);

   int getForceShutdownTimeoutThreadDumpInterval();

   boolean getDebugRMIRequestPerf();

   void setDebugRMIRequestPerf(boolean var1);

   boolean getDebugAppContainer();

   void setDebugAppContainer(boolean var1);

   boolean getDebugLibraries();

   void setDebugLibraries(boolean var1);

   boolean getDebugAppAnnotations();

   void setDebugAppAnnotations(boolean var1);

   boolean getDebugClassRedef();

   void setDebugClassRedef(boolean var1);

   boolean getRedefiningClassLoader();

   void setRedefiningClassLoader(boolean var1);

   boolean getDebugClassSize();

   void setDebugClassSize(boolean var1);

   boolean getDefaultStore();

   void setDefaultStore(boolean var1);

   boolean getClassChangeNotifier();

   void setClassChangeNotifier(boolean var1);

   boolean getDebugHttp();

   void setDebugHttp(boolean var1);

   boolean getDebugHttpConcise();

   void setDebugHttpConcise(boolean var1);

   boolean getDebugURLResolution();

   void setDebugURLResolution(boolean var1);

   boolean getDebugHttpSessions();

   void setDebugHttpSessions(boolean var1);

   boolean getDebugHttpLogging();

   void setDebugHttpLogging(boolean var1);

   boolean getDebugWebAppIdentityAssertion();

   void setDebugWebAppIdentityAssertion(boolean var1);

   boolean getDebugWebAppSecurity();

   void setDebugWebAppSecurity(boolean var1);

   boolean getDebugWebAppModule();

   void setDebugWebAppModule(boolean var1);

   boolean getDebugEjbCompilation();

   void setDebugEjbCompilation(boolean var1);

   boolean getDebugEjbDeployment();

   void setDebugEjbDeployment(boolean var1);

   boolean getDebugEjbMdbConnection();

   void setDebugEjbMdbConnection(boolean var1);

   boolean getDebugEjbCaching();

   void setDebugEjbCaching(boolean var1);

   boolean getDebugEjbSwapping();

   void setDebugEjbSwapping(boolean var1);

   boolean getDebugEjbLocking();

   void setDebugEjbLocking(boolean var1);

   boolean getDebugEjbPooling();

   void setDebugEjbPooling(boolean var1);

   boolean getDebugEjbTimers();

   void setDebugEjbTimers(boolean var1);

   boolean getDebugEjbInvoke();

   void setDebugEjbInvoke(boolean var1);

   boolean getDebugEjbSecurity();

   void setDebugEjbSecurity(boolean var1);

   boolean getDebugEjbCmpDeployment();

   void setDebugEjbCmpDeployment(boolean var1);

   boolean getDebugEjbCmpRuntime();

   void setDebugEjbCmpRuntime(boolean var1);

   boolean getDebugEjbMetadata();

   void setDebugEjbMetadata(boolean var1);

   boolean getDebugEventManager();

   void setDebugEventManager(boolean var1);

   boolean getDebugServerMigration();

   void setDebugServerMigration(boolean var1);

   boolean getDebugClusterFragments();

   void setDebugClusterFragments(boolean var1);

   boolean getDebugCluster();

   void setDebugCluster(boolean var1);

   boolean getDebugClusterHeartbeats();

   void setDebugClusterHeartbeats(boolean var1);

   boolean getDebugClusterAnnouncements();

   void setDebugClusterAnnouncements(boolean var1);

   boolean getDebugReplication();

   void setDebugReplication(boolean var1);

   boolean getDebugReplicationDetails();

   void setDebugReplicationDetails(boolean var1);

   boolean getDebugAsyncQueue();

   void setDebugAsyncQueue(boolean var1);

   boolean getDebugLeaderElection();

   void setDebugLeaderElection(boolean var1);

   boolean getDebugDRSCalls();

   void setDebugDRSCalls(boolean var1);

   boolean getDebugDRSHeartbeats();

   void setDebugDRSHeartbeats(boolean var1);

   boolean getDebugDRSMessages();

   void setDebugDRSMessages(boolean var1);

   boolean getDebugDRSUpdateStatus();

   void setDebugDRSUpdateStatus(boolean var1);

   boolean getDebugDRSStateTransitions();

   void setDebugDRSStateTransitions(boolean var1);

   boolean getDebugDRSQueues();

   void setDebugDRSQueues(boolean var1);

   boolean getDebugJNDI();

   void setDebugJNDI(boolean var1);

   boolean getDebugJNDIResolution();

   void setDebugJNDIResolution(boolean var1);

   boolean getDebugJNDIFactories();

   void setDebugJNDIFactories(boolean var1);

   boolean getDebugTunnelingConnectionTimeout();

   void setDebugTunnelingConnectionTimeout(boolean var1);

   boolean getDebugTunnelingConnection();

   void setDebugTunnelingConnection(boolean var1);

   boolean getDebugJMSBackEnd();

   void setDebugJMSBackEnd(boolean var1);

   boolean getDebugJMSFrontEnd();

   void setDebugJMSFrontEnd(boolean var1);

   boolean getDebugJMSCommon();

   void setDebugJMSCommon(boolean var1);

   boolean getDebugJMSConfig();

   void setDebugJMSConfig(boolean var1);

   boolean getDebugJMSDistTopic();

   void setDebugJMSDistTopic(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugJMSLocking();

   /** @deprecated */
   @Deprecated
   void setDebugJMSLocking(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugJMSXA();

   /** @deprecated */
   @Deprecated
   void setDebugJMSXA(boolean var1);

   boolean getDebugJMSDispatcher();

   void setDebugJMSDispatcher(boolean var1);

   boolean getDebugJMSDispatcherProxy();

   void setDebugJMSDispatcherProxy(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugJMSStore();

   /** @deprecated */
   @Deprecated
   void setDebugJMSStore(boolean var1);

   boolean getDebugJMSBoot();

   void setDebugJMSBoot(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugJMSDurableSubscribers();

   /** @deprecated */
   @Deprecated
   void setDebugJMSDurableSubscribers(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugJMSJDBCScavengeOnFlush();

   /** @deprecated */
   @Deprecated
   void setDebugJMSJDBCScavengeOnFlush(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugJMSAME();

   /** @deprecated */
   @Deprecated
   void setDebugJMSAME(boolean var1);

   boolean getDebugJMSPauseResume();

   void setDebugJMSPauseResume(boolean var1);

   boolean getDebugJMSModule();

   void setDebugJMSModule(boolean var1);

   boolean getDebugJMSMessagePath();

   void setDebugJMSMessagePath(boolean var1);

   boolean getDebugJMSSAF();

   void setDebugJMSSAF(boolean var1);

   boolean getDebugJMSWrappers();

   void setDebugJMSWrappers(boolean var1);

   boolean getDebugJMSCDS();

   void setDebugJMSCDS(boolean var1);

   boolean getDebugJTAXA();

   void setDebugJTAXA(boolean var1);

   boolean getDebugJTANonXA();

   void setDebugJTANonXA(boolean var1);

   boolean getDebugJTAXAStackTrace();

   void setDebugJTAXAStackTrace(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugJTARMI();

   /** @deprecated */
   @Deprecated
   void setDebugJTARMI(boolean var1);

   boolean getDebugJTA2PC();

   void setDebugJTA2PC(boolean var1);

   boolean getDebugJTA2PCStackTrace();

   void setDebugJTA2PCStackTrace(boolean var1);

   boolean getDebugJTATLOG();

   void setDebugJTATLOG(boolean var1);

   boolean getDebugJTAJDBC();

   void setDebugJTAJDBC(boolean var1);

   boolean getDebugJTARecovery();

   void setDebugJTARecovery(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugJTARecoveryStackTrace();

   /** @deprecated */
   @Deprecated
   void setDebugJTARecoveryStackTrace(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugJTAAPI();

   /** @deprecated */
   @Deprecated
   void setDebugJTAAPI(boolean var1);

   boolean getDebugJTAPropagate();

   void setDebugJTAPropagate(boolean var1);

   boolean getDebugJTAGateway();

   void setDebugJTAGateway(boolean var1);

   boolean getDebugJTAGatewayStackTrace();

   void setDebugJTAGatewayStackTrace(boolean var1);

   boolean getDebugJTANaming();

   void setDebugJTANaming(boolean var1);

   boolean getDebugJTANamingStackTrace();

   void setDebugJTANamingStackTrace(boolean var1);

   boolean getDebugJTAResourceHealth();

   void setDebugJTAResourceHealth(boolean var1);

   boolean getDebugJTAMigration();

   void setDebugJTAMigration(boolean var1);

   boolean getDebugJTALifecycle();

   void setDebugJTALifecycle(boolean var1);

   boolean getDebugJTALLR();

   void setDebugJTALLR(boolean var1);

   boolean getDebugJTAHealth();

   void setDebugJTAHealth(boolean var1);

   /** @deprecated */
   @Deprecated
   String getDebugJTATransactionName();

   /** @deprecated */
   @Deprecated
   void setDebugJTATransactionName(String var1);

   String getDebugJTAResourceName();

   void setDebugJTAResourceName(String var1);

   boolean getDebugJTACDI();

   void setDebugJTACDI(boolean var1);

   boolean getDebugLifecycleManager();

   void setDebugLifecycleManager(boolean var1);

   void setDebugMessagingKernel(boolean var1);

   boolean getDebugMessagingKernel();

   void setDebugMessagingKernelBoot(boolean var1);

   boolean getDebugMessagingKernelBoot();

   void setDebugMessagingOwnableLock(boolean var1);

   boolean getDebugMessagingOwnableLock();

   /** @deprecated */
   @Deprecated
   boolean getDebugSAFLifeCycle();

   /** @deprecated */
   @Deprecated
   void setDebugSAFLifeCycle(boolean var1);

   boolean getDebugSAFAdmin();

   void setDebugSAFAdmin(boolean var1);

   boolean getDebugSAFManager();

   void setDebugSAFManager(boolean var1);

   boolean getDebugSAFSendingAgent();

   void setDebugSAFSendingAgent(boolean var1);

   boolean getDebugSAFReceivingAgent();

   void setDebugSAFReceivingAgent(boolean var1);

   boolean getDebugSAFTransport();

   void setDebugSAFTransport(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugSAFMessagePath();

   /** @deprecated */
   @Deprecated
   void setDebugSAFMessagePath(boolean var1);

   boolean getDebugSAFStore();

   void setDebugSAFStore(boolean var1);

   boolean getDebugSAFVerbose();

   void setDebugSAFVerbose(boolean var1);

   void setDebugPathSvc(boolean var1);

   boolean getDebugPathSvc();

   void setDebugPathSvcVerbose(boolean var1);

   boolean getDebugPathSvcVerbose();

   boolean getDebugScaContainer();

   void setDebugScaContainer(boolean var1);

   boolean getDebugSecurityRealm();

   void setDebugSecurityRealm(boolean var1);

   boolean getDebugSecurity();

   void setDebugSecurity(boolean var1);

   boolean getDebugSecurityPasswordPolicy();

   void setDebugSecurityPasswordPolicy(boolean var1);

   boolean getDebugSecurityUserLockout();

   void setDebugSecurityUserLockout(boolean var1);

   boolean getDebugSecurityService();

   void setDebugSecurityService(boolean var1);

   boolean getDebugSecurityPredicate();

   void setDebugSecurityPredicate(boolean var1);

   boolean getDebugSecuritySSL();

   void setDebugSecuritySSL(boolean var1);

   boolean getDebugSecuritySSLEaten();

   void setDebugSecuritySSLEaten(boolean var1);

   boolean getDebugCertRevocCheck();

   void setDebugCertRevocCheck(boolean var1);

   boolean getDebugEmbeddedLDAP();

   void setDebugEmbeddedLDAP(boolean var1);

   boolean getDebugEmbeddedLDAPLogToConsole();

   void setDebugEmbeddedLDAPLogToConsole(boolean var1);

   int getDebugEmbeddedLDAPLogLevel();

   void setDebugEmbeddedLDAPLogLevel(int var1);

   boolean getDebugEmbeddedLDAPWriteOverrideProps();

   void setDebugEmbeddedLDAPWriteOverrideProps(boolean var1);

   boolean getDebugSecurityAdjudicator();

   void setDebugSecurityAdjudicator(boolean var1);

   boolean getDebugSecurityAtn();

   void setDebugSecurityAtn(boolean var1);

   boolean getDebugSecurityAtz();

   void setDebugSecurityAtz(boolean var1);

   boolean getDebugSecurityAuditor();

   void setDebugSecurityAuditor(boolean var1);

   boolean getDebugSecurityCredMap();

   void setDebugSecurityCredMap(boolean var1);

   boolean getDebugSecurityEncryptionService();

   void setDebugSecurityEncryptionService(boolean var1);

   boolean getDebugSecurityKeyStore();

   void setDebugSecurityKeyStore(boolean var1);

   boolean getDebugSecurityCertPath();

   void setDebugSecurityCertPath(boolean var1);

   boolean getDebugSecurityProfiler();

   void setDebugSecurityProfiler(boolean var1);

   boolean getDebugSecurityRoleMap();

   void setDebugSecurityRoleMap(boolean var1);

   boolean getDebugSecurityEEngine();

   void setDebugSecurityEEngine(boolean var1);

   boolean getDebugSecurityJACC();

   void setDebugSecurityJACC(boolean var1);

   boolean getDebugSecurityJACCNonPolicy();

   void setDebugSecurityJACCNonPolicy(boolean var1);

   boolean getDebugSecurityJACCPolicy();

   void setDebugSecurityJACCPolicy(boolean var1);

   boolean getDebugSecuritySAMLLib();

   void setDebugSecuritySAMLLib(boolean var1);

   boolean getDebugSecuritySAMLAtn();

   void setDebugSecuritySAMLAtn(boolean var1);

   boolean getDebugSecuritySAMLCredMap();

   void setDebugSecuritySAMLCredMap(boolean var1);

   boolean getDebugSecuritySAMLService();

   void setDebugSecuritySAMLService(boolean var1);

   boolean getDebugSecuritySAML2Lib();

   void setDebugSecuritySAML2Lib(boolean var1);

   boolean getDebugSecuritySAML2Atn();

   void setDebugSecuritySAML2Atn(boolean var1);

   boolean getDebugSecuritySAML2CredMap();

   void setDebugSecuritySAML2CredMap(boolean var1);

   boolean getDebugSecuritySAML2Service();

   void setDebugSecuritySAML2Service(boolean var1);

   boolean getDebugJDBCConn();

   void setDebugJDBCConn(boolean var1);

   boolean getDebugJDBCSQL();

   void setDebugJDBCSQL(boolean var1);

   boolean getDebugJDBCRMI();

   void setDebugJDBCRMI(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugJDBCDriverLogging();

   /** @deprecated */
   @Deprecated
   void setDebugJDBCDriverLogging(boolean var1);

   boolean getDebugJDBCInternal();

   void setDebugJDBCInternal(boolean var1);

   boolean getDebugJDBCRAC();

   void setDebugJDBCRAC(boolean var1);

   boolean getDebugJDBCONS();

   void setDebugJDBCONS(boolean var1);

   boolean getDebugJDBCUCP();

   void setDebugJDBCUCP(boolean var1);

   boolean getDebugJDBCReplay();

   void setDebugJDBCReplay(boolean var1);

   boolean getDebugMessagingBridgeStartup();

   void setDebugMessagingBridgeStartup(boolean var1);

   boolean getDebugMessagingBridgeRuntime();

   void setDebugMessagingBridgeRuntime(boolean var1);

   boolean getDebugMessagingBridgeRuntimeVerbose();

   void setDebugMessagingBridgeRuntimeVerbose(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugMessagingBridgeDumpToLog();

   /** @deprecated */
   @Deprecated
   void setDebugMessagingBridgeDumpToLog(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugMessagingBridgeDumpToConsole();

   /** @deprecated */
   @Deprecated
   void setDebugMessagingBridgeDumpToConsole(boolean var1);

   void setDebugStoreIOLogical(boolean var1);

   boolean getDebugStoreIOLogical();

   void setDebugStoreIOLogicalBoot(boolean var1);

   boolean getDebugStoreIOLogicalBoot();

   void setDebugStoreIOPhysical(boolean var1);

   boolean getDebugStoreIOPhysical();

   void setDebugStoreIOPhysicalVerbose(boolean var1);

   boolean getDebugStoreIOPhysicalVerbose();

   /** @deprecated */
   @Deprecated
   void setDebugStoreXA(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugStoreXA();

   /** @deprecated */
   @Deprecated
   void setDebugStoreXAVerbose(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugStoreXAVerbose();

   void setDebugStoreAdmin(boolean var1);

   boolean getDebugStoreAdmin();

   int getDebugXMLRegistryDebugLevel();

   void setDebugXMLRegistryDebugLevel(int var1);

   String getDebugXMLRegistryDebugName();

   void setDebugXMLRegistryDebugName(String var1);

   OutputStream getDebugXMLRegistryOutputStream();

   void setDebugXMLRegistryOutputStream(OutputStream var1);

   boolean getDebugXMLRegistryIncludeTime();

   void setDebugXMLRegistryIncludeTime(boolean var1);

   boolean getDebugXMLRegistryIncludeName();

   void setDebugXMLRegistryIncludeName(boolean var1);

   boolean getDebugXMLRegistryIncludeClass();

   void setDebugXMLRegistryIncludeClass(boolean var1);

   boolean getDebugXMLRegistryIncludeLocation();

   void setDebugXMLRegistryIncludeLocation(boolean var1);

   boolean getDebugXMLRegistryUseShortClass();

   void setDebugXMLRegistryUseShortClass(boolean var1);

   int getDebugJAXPDebugLevel();

   void setDebugJAXPDebugLevel(int var1);

   String getDebugJAXPDebugName();

   void setDebugJAXPDebugName(String var1);

   OutputStream getDebugJAXPOutputStream();

   void setDebugJAXPOutputStream(OutputStream var1);

   boolean getDebugJAXPIncludeTime();

   void setDebugJAXPIncludeTime(boolean var1);

   boolean getDebugJAXPIncludeName();

   void setDebugJAXPIncludeName(boolean var1);

   boolean getDebugJAXPIncludeClass();

   void setDebugJAXPIncludeClass(boolean var1);

   boolean getDebugJAXPIncludeLocation();

   void setDebugJAXPIncludeLocation(boolean var1);

   boolean getDebugJAXPUseShortClass();

   void setDebugJAXPUseShortClass(boolean var1);

   int getDebugXMLEntityCacheDebugLevel();

   void setDebugXMLEntityCacheDebugLevel(int var1);

   String getDebugXMLEntityCacheDebugName();

   void setDebugXMLEntityCacheDebugName(String var1);

   OutputStream getDebugXMLEntityCacheOutputStream();

   void setDebugXMLEntityCacheOutputStream(OutputStream var1);

   boolean getDebugXMLEntityCacheIncludeTime();

   void setDebugXMLEntityCacheIncludeTime(boolean var1);

   boolean getDebugXMLEntityCacheIncludeName();

   void setDebugXMLEntityCacheIncludeName(boolean var1);

   boolean getDebugXMLEntityCacheIncludeClass();

   void setDebugXMLEntityCacheIncludeClass(boolean var1);

   boolean getDebugXMLEntityCacheIncludeLocation();

   void setDebugXMLEntityCacheIncludeLocation(boolean var1);

   boolean getDebugXMLEntityCacheUseShortClass();

   void setDebugXMLEntityCacheUseShortClass(boolean var1);

   boolean getDebugDeploy();

   void setDebugDeploy(boolean var1);

   boolean getDebugDeployment();

   void setDebugDeployment(boolean var1);

   boolean getDebugDeploymentConcise();

   void setDebugDeploymentConcise(boolean var1);

   boolean getDebugDeploymentService();

   void setDebugDeploymentService(boolean var1);

   boolean getDebugDeploymentServiceStatusUpdates();

   void setDebugDeploymentServiceStatusUpdates(boolean var1);

   boolean getDebugDeploymentServiceInternal();

   void setDebugDeploymentServiceInternal(boolean var1);

   boolean getDebugDeploymentServiceTransport();

   void setDebugDeploymentServiceTransport(boolean var1);

   boolean getDebugDeploymentServiceTransportHttp();

   void setDebugDeploymentServiceTransportHttp(boolean var1);

   boolean getMasterDeployer();

   void setMasterDeployer(boolean var1);

   boolean getSlaveDeployer();

   void setSlaveDeployer(boolean var1);

   boolean getApplicationContainer();

   void setApplicationContainer(boolean var1);

   boolean getClassFinder();

   void setClassFinder(boolean var1);

   boolean getClasspathServlet();

   void setClasspathServlet(boolean var1);

   boolean getWebModule();

   void setWebModule(boolean var1);

   boolean getDebugClassLoadingVerbose();

   void setDebugClassLoadingVerbose(boolean var1);

   boolean getDebugClassLoadingContextualTrace();

   void setDebugClassLoadingContextualTrace(boolean var1);

   boolean getClassLoader();

   void setClassLoader(boolean var1);

   boolean getClassLoaderVerbose();

   void setClassLoaderVerbose(boolean var1);

   boolean getClassloaderWebApp();

   void setClassloaderWebApp(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getDebugBootstrapServlet();

   /** @deprecated */
   @Deprecated
   void setDebugBootstrapServlet(boolean var1);

   boolean getDebugFileDistributionServlet();

   void setDebugFileDistributionServlet(boolean var1);

   boolean getDebugDiagnosticLifecycleHandlers();

   void setDebugDiagnosticLifecycleHandlers(boolean var1);

   boolean getDebugDiagnosticDataGathering();

   void setDebugDiagnosticDataGathering(boolean var1);

   boolean getDebugDiagnosticInstrumentation();

   void setDebugDiagnosticInstrumentation(boolean var1);

   boolean getDebugDiagnosticInstrumentationWeaving();

   void setDebugDiagnosticInstrumentationWeaving(boolean var1);

   boolean getDebugDiagnosticInstrumentationWeavingMatches();

   void setDebugDiagnosticInstrumentationWeavingMatches(boolean var1);

   boolean getDebugDiagnosticInstrumentationActions();

   void setDebugDiagnosticInstrumentationActions(boolean var1);

   boolean getDebugDiagnosticInstrumentationEvents();

   void setDebugDiagnosticInstrumentationEvents(boolean var1);

   boolean getDebugDiagnosticInstrumentationConfig();

   void setDebugDiagnosticInstrumentationConfig(boolean var1);

   boolean getDebugDiagnosticArchive();

   void setDebugDiagnosticArchive(boolean var1);

   boolean getDebugDiagnosticFileArchive();

   void setDebugDiagnosticFileArchive(boolean var1);

   boolean getDebugDiagnosticWlstoreArchive();

   void setDebugDiagnosticWlstoreArchive(boolean var1);

   boolean getDebugDiagnosticJdbcArchive();

   void setDebugDiagnosticJdbcArchive(boolean var1);

   boolean getDebugDiagnosticArchiveRetirement();

   void setDebugDiagnosticArchiveRetirement(boolean var1);

   boolean getDebugDiagnosticsModule();

   void setDebugDiagnosticsModule(boolean var1);

   boolean getDebugDiagnosticRuntimeControlDriver();

   void setDebugDiagnosticRuntimeControlDriver(boolean var1);

   boolean getDebugDiagnosticRuntimeControlService();

   void setDebugDiagnosticRuntimeControlService(boolean var1);

   boolean getDebugDiagnosticsHarvester();

   void setDebugDiagnosticsHarvester(boolean var1);

   boolean getDebugMBeanHarvesterControl();

   void setDebugMBeanHarvesterControl(boolean var1);

   boolean getDebugMBeanHarvesterResolution();

   void setDebugMBeanHarvesterResolution(boolean var1);

   boolean getDebugMBeanHarvesterDataCollection();

   void setDebugMBeanHarvesterDataCollection(boolean var1);

   boolean getDebugMBeanHarvesterThreading();

   void setDebugMBeanHarvesterThreading(boolean var1);

   boolean getDebugBeanTreeHarvesterControl();

   void setDebugBeanTreeHarvesterControl(boolean var1);

   boolean getDebugBeanTreeHarvesterResolution();

   void setDebugBeanTreeHarvesterResolution(boolean var1);

   boolean getDebugBeanTreeHarvesterDataCollection();

   void setDebugBeanTreeHarvesterDataCollection(boolean var1);

   boolean getDebugBeanTreeHarvesterThreading();

   void setDebugBeanTreeHarvesterThreading(boolean var1);

   boolean getDebugMBeanTypeUtilityListener();

   void setDebugMBeanTypeUtilityListener(boolean var1);

   boolean getDebugMBeanTypeUtilQueue();

   void setDebugMBeanTypeUtilQueue(boolean var1);

   boolean getDebugMBeanTypeUtilQueuePriority();

   void setDebugMBeanTypeUtilQueuePriority(boolean var1);

   boolean getDebugMBeanTypingUtility();

   void setDebugMBeanTypingUtility(boolean var1);

   boolean getDebugDiagnosticsHarvesterData();

   void setDebugDiagnosticsHarvesterData(boolean var1);

   boolean getDebugDiagnosticsHarvesterMBeanPlugin();

   void setDebugDiagnosticsHarvesterMBeanPlugin(boolean var1);

   boolean getDebugDiagnosticsHarvesterTreeBeanPlugin();

   void setDebugDiagnosticsHarvesterTreeBeanPlugin(boolean var1);

   boolean getDebugDiagnosticsExpressionEvaluators();

   void setDebugDiagnosticsExpressionEvaluators(boolean var1);

   boolean getDebugDiagnosticsExpressionMetrics();

   void setDebugDiagnosticsExpressionMetrics(boolean var1);

   boolean getDebugDiagnosticsBeanInfoProviders();

   void setDebugDiagnosticsBeanInfoProviders(boolean var1);

   boolean getDebugDiagnosticsELResolver();

   void setDebugDiagnosticsELResolver(boolean var1);

   boolean getDebugDiagnosticsBeanExtensionResolver();

   void setDebugDiagnosticsBeanExtensionResolver(boolean var1);

   boolean getDebugDiagnosticsValueTracingELResolver();

   void setDebugDiagnosticsValueTracingELResolver(boolean var1);

   boolean getDebugDiagnosticsMBeanELResolver();

   void setDebugDiagnosticsMBeanELResolver(boolean var1);

   boolean getDebugDiagnosticsELContext();

   void setDebugDiagnosticsELContext(boolean var1);

   boolean getDebugDiagnosticsExpressionPoller();

   void setDebugDiagnosticsExpressionPoller(boolean var1);

   boolean getDebugDiagnosticsExpressionFunctions();

   void setDebugDiagnosticsExpressionFunctions(boolean var1);

   boolean getDebugDiagnosticsExpressionFunctionMapper();

   void setDebugDiagnosticsExpressionFunctionMapper(boolean var1);

   boolean getDebugExpressionBeanLocalizer();

   void setDebugExpressionBeanLocalizer(boolean var1);

   boolean getDebugExpressionExtensionsManager();

   void setDebugExpressionExtensionsManager(boolean var1);

   boolean getDebugDiagnosticsUtils();

   void setDebugDiagnosticsUtils(boolean var1);

   boolean getDebugDiagnosticsExpressionPollerBuffer();

   void setDebugDiagnosticsExpressionPollerBuffer(boolean var1);

   boolean getDebugRESTNotifications();

   void setDebugRESTNotifications(boolean var1);

   boolean getDebugDiagnosticsScriptAction();

   void setDebugDiagnosticsScriptAction(boolean var1);

   boolean getDebugElasticActions();

   void setDebugElasticActions(boolean var1);

   boolean getDebugElasticServices();

   void setDebugElasticServices(boolean var1);

   boolean getDebugDiagnosticImage();

   void setDebugDiagnosticImage(boolean var1);

   boolean getDebugDiagnosticQuery();

   void setDebugDiagnosticQuery(boolean var1);

   boolean getDebugDiagnosticAccessor();

   void setDebugDiagnosticAccessor(boolean var1);

   boolean getDebugDiagnosticCollections();

   void setDebugDiagnosticCollections(boolean var1);

   boolean getDebugDiagnosticContext();

   void setDebugDiagnosticContext(boolean var1);

   boolean getDebugSNMPToolkit();

   void setDebugSNMPToolkit(boolean var1);

   boolean getDebugSNMPAgent();

   void setDebugSNMPAgent(boolean var1);

   boolean getDebugSNMPProtocolTCP();

   boolean getDebugSNMPExtensionProvider();

   void setDebugSNMPExtensionProvider(boolean var1);

   void setDebugSNMPProtocolTCP(boolean var1);

   boolean getDebugDomainLogHandler();

   void setDebugDomainLogHandler(boolean var1);

   boolean getDebugLoggingConfiguration();

   void setDebugLoggingConfiguration(boolean var1);

   boolean getDebugDiagnosticWatch();

   void setDebugDiagnosticWatch(boolean var1);

   boolean getDebugDiagnosticWatchEvents();

   void setDebugDiagnosticWatchEvents(boolean var1);

   boolean getDebugDiagnosticWatchEventsDetails();

   void setDebugDiagnosticWatchEventsDetails(boolean var1);

   boolean getDebugPartitionResourceMetricsRuntime();

   void setDebugPartitionResourceMetricsRuntime(boolean var1);

   boolean getDebugRAPoolVerbose();

   void setDebugRAPoolVerbose(boolean var1);

   boolean getDebugRA();

   void setDebugRA(boolean var1);

   boolean getDebugRAXAin();

   void setDebugRAXAin(boolean var1);

   boolean getDebugRAXAout();

   void setDebugRAXAout(boolean var1);

   boolean getDebugRAXAwork();

   void setDebugRAXAwork(boolean var1);

   boolean getDebugRALocalOut();

   void setDebugRALocalOut(boolean var1);

   boolean getDebugRALifecycle();

   void setDebugRALifecycle(boolean var1);

   boolean getDebugConnectorService();

   void setDebugConnectorService(boolean var1);

   boolean getDebugRADeployment();

   void setDebugRADeployment(boolean var1);

   boolean getDebugRAParsing();

   void setDebugRAParsing(boolean var1);

   boolean getDebugRASecurityCtx();

   void setDebugRASecurityCtx(boolean var1);

   boolean getDebugRAPooling();

   void setDebugRAPooling(boolean var1);

   boolean getDebugRAConnections();

   void setDebugRAConnections(boolean var1);

   boolean getDebugRAConnEvents();

   void setDebugRAConnEvents(boolean var1);

   boolean getDebugRAWork();

   void setDebugRAWork(boolean var1);

   boolean getDebugRAWorkEvents();

   void setDebugRAWorkEvents(boolean var1);

   boolean getDebugRAClassloader();

   void setDebugRAClassloader(boolean var1);

   boolean getDebugWANReplicationDetails();

   void setDebugWANReplicationDetails(boolean var1);

   boolean getDebugJMX();

   void setDebugJMX(boolean var1);

   boolean getDebugJMXCore();

   void setDebugJMXCore(boolean var1);

   boolean getDebugJMXCoreConcise();

   void setDebugJMXCoreConcise(boolean var1);

   boolean getDebugJMXRuntime();

   void setDebugJMXRuntime(boolean var1);

   boolean getDebugJMXDomain();

   void setDebugJMXDomain(boolean var1);

   boolean getDebugJMXEdit();

   void setDebugJMXEdit(boolean var1);

   boolean getDebugJMXCompatibility();

   void setDebugJMXCompatibility(boolean var1);

   boolean getDebugConfigurationEdit();

   void setDebugConfigurationEdit(boolean var1);

   boolean getDebugConfigurationRuntime();

   void setDebugConfigurationRuntime(boolean var1);

   boolean getDebugSituationalConfig();

   void setDebugSituationalConfig(boolean var1);

   boolean getDebugPatchingRuntime();

   void setDebugPatchingRuntime(boolean var1);

   boolean getDebugJ2EEManagement();

   void setDebugJ2EEManagement(boolean var1);

   boolean getDebugIIOPNaming();

   void setDebugIIOPNaming(boolean var1);

   boolean getDebugIIOPTunneling();

   void setDebugIIOPTunneling(boolean var1);

   boolean getDebugSelfTuning();

   void setDebugSelfTuning(boolean var1);

   boolean getDebugConsensusLeasing();

   void setDebugConsensusLeasing(boolean var1);

   boolean getDebugServerLifeCycle();

   void setDebugServerLifeCycle(boolean var1);

   boolean getDebugWTCConfig();

   void setDebugWTCConfig(boolean var1);

   boolean getDebugWTCTDomPdu();

   void setDebugWTCTDomPdu(boolean var1);

   boolean getDebugWTCUData();

   void setDebugWTCUData(boolean var1);

   boolean getDebugWTCGwtEx();

   void setDebugWTCGwtEx(boolean var1);

   boolean getDebugWTCJatmiEx();

   void setDebugWTCJatmiEx(boolean var1);

   boolean getDebugWTCCorbaEx();

   void setDebugWTCCorbaEx(boolean var1);

   boolean getDebugWTCtBridgeEx();

   void setDebugWTCtBridgeEx(boolean var1);

   boolean getDebugJpaMetaData();

   void setDebugJpaMetaData(boolean var1);

   boolean getDebugJpaEnhance();

   void setDebugJpaEnhance(boolean var1);

   boolean getDebugJpaRuntime();

   void setDebugJpaRuntime(boolean var1);

   boolean getDebugJpaQuery();

   void setDebugJpaQuery(boolean var1);

   boolean getDebugJpaDataCache();

   void setDebugJpaDataCache(boolean var1);

   boolean getDebugJpaTool();

   void setDebugJpaTool(boolean var1);

   boolean getDebugJpaManage();

   void setDebugJpaManage(boolean var1);

   boolean getDebugJpaProfile();

   void setDebugJpaProfile(boolean var1);

   boolean getDebugJpaJdbcSql();

   void setDebugJpaJdbcSql(boolean var1);

   boolean getDebugJpaJdbcJdbc();

   void setDebugJpaJdbcJdbc(boolean var1);

   boolean getDebugJpaJdbcSchema();

   void setDebugJpaJdbcSchema(boolean var1);

   void setDebugJMST3Server(boolean var1);

   boolean getDebugJMST3Server();

   boolean getDebugDescriptor();

   void setDebugDescriptor(boolean var1);

   boolean getDebugServerStartStatistics();

   void setDebugServerStartStatistics(boolean var1);

   boolean getDebugManagementServicesResource();

   void setDebugManagementServicesResource(boolean var1);

   boolean getDebugDeploymentPlan();

   void setDebugDeploymentPlan(boolean var1);

   boolean getDebugReplicationSize();

   void setDebugReplicationSize(boolean var1);

   boolean getDebugBuzzProtocol();

   void setDebugBuzzProtocol(boolean var1);

   boolean getDebugBuzzProtocolDetails();

   void setDebugBuzzProtocolDetails(boolean var1);

   boolean getDebugBuzzProtocolHttp();

   void setDebugBuzzProtocolHttp(boolean var1);

   boolean getDebugConcurrent();

   void setDebugConcurrent(boolean var1);

   boolean getDebugConcurrentContext();

   void setDebugConcurrentContext(boolean var1);

   boolean getDebugConcurrentTransaction();

   void setDebugConcurrentTransaction(boolean var1);

   boolean getDebugConcurrentMES();

   void setDebugConcurrentMES(boolean var1);

   boolean getDebugConcurrentMSES();

   void setDebugConcurrentMSES(boolean var1);

   boolean getDebugConcurrentMTF();

   void setDebugConcurrentMTF(boolean var1);

   boolean getDebugReadyApp();

   void setDebugReadyApp(boolean var1);

   boolean getDebugInterceptors();

   void setDebugInterceptors(boolean var1);

   boolean getDebugDynamicSingletonServices();

   void setDebugDynamicSingletonServices(boolean var1);

   boolean getDebugRCM();

   void setDebugRCM(boolean var1);

   boolean getDebugVerboseRCM();

   void setDebugVerboseRCM(boolean var1);

   boolean getDebugRestJersey1Integration();

   void setDebugRestJersey1Integration(boolean var1);

   boolean getDebugRestJersey2Integration();

   void setDebugRestJersey2Integration(boolean var1);

   boolean getDebugNodeManagerRuntime();

   void setDebugNodeManagerRuntime(boolean var1);

   void setDebugAbbrevs(boolean var1);

   boolean getDebugAbbrevs();

   void setDebugAppClient(boolean var1);

   boolean getDebugAppClient();

   void setDebugAppContainerTools(boolean var1);

   boolean getDebugAppContainerTools();

   void setDebugAppTiming(boolean var1);

   boolean getDebugAppTiming();

   void setDebugAttach(boolean var1);

   boolean getDebugAttach();

   void setDebugBackgroundDeployment(boolean var1);

   boolean getDebugBackgroundDeployment();

   void setDebugBatchConnector(boolean var1);

   boolean getDebugBatchConnector();

   void setDebugCAT(boolean var1);

   boolean getDebugCAT();

   void setDebugChannel(boolean var1);

   boolean getDebugChannel();

   void setDebugClassLoadingConsistencyChecker(boolean var1);

   boolean getDebugClassLoadingConsistencyChecker();

   void setDebugCoherence(boolean var1);

   boolean getDebugCoherence();

   void setDebugDataSourceInterceptor(boolean var1);

   boolean getDebugDataSourceInterceptor();

   void setDebugDebugPatches(boolean var1);

   boolean getDebugDebugPatches();

   void setDebugDefaultStoreVerbose(boolean var1);

   boolean getDebugDefaultStoreVerbose();

   void setDebugDiagnosticInstrumentationClassInfo(boolean var1);

   boolean getDebugDiagnosticInstrumentationClassInfo();

   void setDebugDiagnosticInstrumentationResult(boolean var1);

   boolean getDebugDiagnosticInstrumentationResult();

   void setDebugDiagnosticNotifications(boolean var1);

   boolean getDebugDiagnosticNotifications();

   void setDebugDiagnosticsNotifications(boolean var1);

   boolean getDebugDiagnosticsNotifications();

   void setDebugDiagnosticsTimer(boolean var1);

   boolean getDebugDiagnosticsTimer();

   void setDebugDomainUpgradeServerService(boolean var1);

   boolean getDebugDomainUpgradeServerService();

   void setDebugEjbTimerStore(boolean var1);

   boolean getDebugEjbTimerStore();

   void setDebugExpressionPoller(boolean var1);

   boolean getDebugExpressionPoller();

   void setDebugFileOwnerFixer(boolean var1);

   boolean getDebugFileOwnerFixer();

   void setDebugHarvesterTypeInfoCache(boolean var1);

   boolean getDebugHarvesterTypeInfoCache();

   void setDebugRestartInPlace(boolean var1);

   boolean getDebugRestartInPlace();

   void setDebugIIOPDetail(boolean var1);

   boolean getDebugIIOPDetail();

   void setDebugIIOPRepalcer(boolean var1);

   boolean getDebugIIOPRepalcer();

   void setDebugJMSCrossDomainSecurity(boolean var1);

   boolean getDebugJMSCrossDomainSecurity();

   void setDebugJMSDispatcherUtilsVerbose(boolean var1);

   boolean getDebugJMSDispatcherUtilsVerbose();

   void setDebugJMSDispatcherVerbose(boolean var1);

   boolean getDebugJMSDispatcherVerbose();

   void setDebugJMSDotNetProxy(boolean var1);

   boolean getDebugJMSDotNetProxy();

   void setDebugJMSDotNetT3Server(boolean var1);

   boolean getDebugJMSDotNetT3Server();

   void setDebugJMSDotNetTransport(boolean var1);

   boolean getDebugJMSDotNetTransport();

   void setDebugJMSDurSub(boolean var1);

   boolean getDebugJMSDurSub();

   void setDebugJMSJNDI(boolean var1);

   boolean getDebugJMSJNDI();

   void setDebugJMSOBS(boolean var1);

   boolean getDebugJMSOBS();

   void setDebugJMXContext(boolean var1);

   boolean getDebugJMXContext();

   void setDebugJPA(boolean var1);

   boolean getDebugJPA();

   void setDebugJTA2PCDetail(boolean var1);

   boolean getDebugJTA2PCDetail();

   void setDebugKodoWeblogic(boolean var1);

   boolean getDebugKodoWeblogic();

   void setDebugLegacy(boolean var1);

   boolean getDebugLegacy();

   void setDebugMBeanCIC(boolean var1);

   boolean getDebugMBeanCIC();

   void setDebugMBeanEventHandler(boolean var1);

   boolean getDebugMBeanEventHandler();

   void setDebugMBeanEventHandlerSummary(boolean var1);

   boolean getDebugMBeanEventHandlerSummary();

   void setDebugMBeanEventHandlerWork(boolean var1);

   boolean getDebugMBeanEventHandlerWork();

   void setDebugMBeanLocalization(boolean var1);

   boolean getDebugMBeanLocalization();

   void setDebugMailSessionDeployment(boolean var1);

   boolean getDebugMailSessionDeployment();

   void setDebugManagedBean(boolean var1);

   boolean getDebugManagedBean();

   void setDebugMigrationInfo(boolean var1);

   boolean getDebugMigrationInfo();

   void setDebugNIO(boolean var1);

   boolean getDebugNIO();

   void setDebugPageFlowMonitoring(boolean var1);

   boolean getDebugPageFlowMonitoring();

   void setDebugPartitionJMX(boolean var1);

   boolean getDebugPartitionJMX();

   void setDebugPartitionLifecycle(boolean var1);

   boolean getDebugPartitionLifecycle();

   void setDebugPartitionPortability(boolean var1);

   boolean getDebugPartitionPortability();

   void setDebugPersistentStoreManager(boolean var1);

   boolean getDebugPersistentStoreManager();

   void setDebugPubSubBayeux(boolean var1);

   boolean getDebugPubSubBayeux();

   void setDebugPubSubChannel(boolean var1);

   boolean getDebugPubSubChannel();

   void setDebugPubSubClient(boolean var1);

   boolean getDebugPubSubClient();

   void setDebugPubSubSecurity(boolean var1);

   boolean getDebugPubSubSecurity();

   void setDebugPubSubServer(boolean var1);

   boolean getDebugPubSubServer();

   void setDebugRMIDetailed(boolean var1);

   boolean getDebugRMIDetailed();

   void setDebugRedefAttach(boolean var1);

   boolean getDebugRedefAttach();

   void setDebugRequestManager(boolean var1);

   boolean getDebugRequestManager();

   void setDebugResourceGroupMigration(boolean var1);

   boolean getDebugResourceGroupMigration();

   void setDebugSNMPMib(boolean var1);

   boolean getDebugSNMPMib();

   void setDebugScrubberStartService(boolean var1);

   boolean getDebugScrubberStartService();

   void setDebugServerRuntime(boolean var1);

   boolean getDebugServerRuntime();

   void setDebugServerShutdownStatistics(boolean var1);

   boolean getDebugServerShutdownStatistics();

   void setDebugServerShutdownTimer(boolean var1);

   boolean getDebugServerShutdownTimer();

   void setDebugServerStartupTimer(boolean var1);

   boolean getDebugServerStartupTimer();

   void setDebugSingletonServices(boolean var1);

   boolean getDebugSingletonServices();

   void setDebugSpringStatistics(boolean var1);

   boolean getDebugSpringStatistics();

   void setDebugStore(boolean var1);

   boolean getDebugStore();

   void setDebugStoreCache(boolean var1);

   boolean getDebugStoreCache();

   void setDebugStoreRCM(boolean var1);

   boolean getDebugStoreRCM();

   void setDebugStoreRCMVerbose(boolean var1);

   boolean getDebugStoreRCMVerbose();

   void setDebugStubGeneration(boolean var1);

   boolean getDebugStubGeneration();

   void setDebugUnicastMessaging(boolean var1);

   boolean getDebugUnicastMessaging();

   void setDebugValidation(boolean var1);

   boolean getDebugValidation();

   void setDebugValidationClassLoading(boolean var1);

   boolean getDebugValidationClassLoading();

   void setDebugWTCTdomPdu(boolean var1);

   boolean getDebugWTCTdomPdu();

   void setDebugWatchScalingActions(boolean var1);

   boolean getDebugWatchScalingActions();

   void setDebugWebAppDI(boolean var1);

   boolean getDebugWebAppDI();

   void setDebugWebSocket(boolean var1);

   boolean getDebugWebSocket();

   void setGlassFishWebAppParser(boolean var1);

   boolean getGlassFishWebAppParser();

   void setOSGiForApps(boolean var1);

   boolean getOSGiForApps();

   void setPartitionEvenInterceptor(boolean var1);

   boolean getPartitionEvenInterceptor();

   void setPortablePartitionHelper(boolean var1);

   boolean getPortablePartitionHelper();

   void setScriptExecutorCommand(boolean var1);

   boolean getScriptExecutorCommand();

   void setSecurityEncryptionService(boolean var1);

   boolean getSecurityEncryptionService();

   void setT3HttpUpgradeHandler(boolean var1);

   boolean getT3HttpUpgradeHandler();

   void setWarExtraction(boolean var1);

   boolean getWarExtraction();
}
