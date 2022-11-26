package weblogic.management.configuration;

import java.util.Map;
import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;

public interface ServerTemplateMBean extends KernelMBean, TargetMBean, ConfigurationPropertiesMBean {
   String STAGING_NAME = "stage";
   String STAGE = "stage";
   String NO_STAGE = "nostage";
   String EXTERNAL_STAGE = "external_stage";
   String DEFAULT_STAGE = null;
   String UPLOAD_DIR_NAME = "upload";
   String LOGTYPE_WL_STDOUT = "WL_output";
   String LOGTYPE_WL_STDERR = "WL_error";
   String SYNCWRITE_CACHEFLUSH = "Cache-Flush";
   String SYNCWRITE_DIRECTWRITE = "Direct-Write";
   String CONNECTION_FACTORY_UNMAPPED_RESREF_RETURNDEFAULT = "ReturnDefault";
   String CONNECTION_FACTORY_UNMAPPED_RESREF_FAILSAFE = "FailSafe";
   /** @deprecated */
   @Deprecated
   String DEFAULT_JDBC_FILE_NAME = "jdbc.log";

   String getRootDirectory();

   /** @deprecated */
   @Deprecated
   void setRootDirectory(String var1) throws InvalidAttributeValueException;

   MachineMBean getMachine();

   void setMachine(MachineMBean var1) throws InvalidAttributeValueException;

   int getListenPort();

   void setListenPort(int var1) throws InvalidAttributeValueException;

   boolean isListenPortEnabled();

   void setListenPortEnabled(boolean var1) throws InvalidAttributeValueException;

   int getLoginTimeout();

   void setLoginTimeout(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   ClusterMBean getCluster();

   void setCluster(ClusterMBean var1) throws InvalidAttributeValueException;

   int getClusterWeight();

   void setClusterWeight(int var1) throws InvalidAttributeValueException;

   String getReplicationGroup();

   void setReplicationGroup(String var1) throws InvalidAttributeValueException;

   String getPreferredSecondaryGroup();

   void setPreferredSecondaryGroup(String var1) throws InvalidAttributeValueException;

   int getConsensusProcessIdentifier();

   void setConsensusProcessIdentifier(int var1);

   boolean isAutoMigrationEnabled();

   void setAutoMigrationEnabled(boolean var1);

   WebServerMBean getWebServer();

   boolean getExpectedToRun();

   void setExpectedToRun(boolean var1);

   /** @deprecated */
   @Deprecated
   String synchronousStart();

   /** @deprecated */
   @Deprecated
   String synchronousKill();

   /** @deprecated */
   @Deprecated
   boolean isJDBCLoggingEnabled();

   /** @deprecated */
   @Deprecated
   void setJDBCLoggingEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   String getJDBCLogFileName();

   /** @deprecated */
   @Deprecated
   void setJDBCLogFileName(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isJ2EE12OnlyModeEnabled();

   void setJ2EE12OnlyModeEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean isJ2EE13WarningEnabled();

   void setJ2EE13WarningEnabled(boolean var1);

   boolean isIIOPEnabled();

   void setIIOPEnabled(boolean var1);

   String getDefaultIIOPUser();

   void setDefaultIIOPUser(String var1) throws InvalidAttributeValueException;

   String getDefaultIIOPPassword();

   void setDefaultIIOPPassword(String var1) throws InvalidAttributeValueException;

   byte[] getDefaultIIOPPasswordEncrypted();

   void setDefaultIIOPPasswordEncrypted(byte[] var1);

   boolean isTGIOPEnabled();

   void setTGIOPEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   String getDefaultTGIOPUser();

   void setDefaultTGIOPUser(String var1) throws InvalidAttributeValueException;

   String getDefaultTGIOPPassword();

   void setDefaultTGIOPPassword(String var1) throws InvalidAttributeValueException;

   byte[] getDefaultTGIOPPasswordEncrypted();

   void setDefaultTGIOPPasswordEncrypted(byte[] var1);

   /** @deprecated */
   @Deprecated
   boolean isCOMEnabled();

   /** @deprecated */
   @Deprecated
   void setCOMEnabled(boolean var1);

   boolean isJRMPEnabled();

   void setJRMPEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   COMMBean getCOM();

   ServerDebugMBean getServerDebug();

   boolean isHttpdEnabled();

   void setHttpdEnabled(boolean var1);

   String getSystemPassword();

   void setSystemPassword(String var1) throws InvalidAttributeValueException;

   byte[] getSystemPasswordEncrypted();

   void setSystemPasswordEncrypted(byte[] var1);

   boolean isConsoleInputEnabled();

   void setConsoleInputEnabled(boolean var1);

   int getListenThreadStartDelaySecs();

   void setListenThreadStartDelaySecs(int var1) throws InvalidAttributeValueException;

   boolean getListenersBindEarly();

   void setListenersBindEarly(boolean var1) throws InvalidAttributeValueException;

   String getListenAddress();

   void setListenAddress(String var1) throws InvalidAttributeValueException;

   String getExternalDNSName();

   void setExternalDNSName(String var1) throws InvalidAttributeValueException;

   boolean getResolveDNSName();

   void setResolveDNSName(boolean var1);

   String getInterfaceAddress();

   void setInterfaceAddress(String var1) throws InvalidAttributeValueException;

   NetworkAccessPointMBean[] getNetworkAccessPoints();

   NetworkAccessPointMBean lookupNetworkAccessPoint(String var1);

   NetworkAccessPointMBean createNetworkAccessPoint(String var1);

   void destroyNetworkAccessPoint(NetworkAccessPointMBean var1);

   /** @deprecated */
   @Deprecated
   void setNetworkAccessPoints(NetworkAccessPointMBean[] var1) throws InvalidAttributeValueException, ConfigurationException;

   /** @deprecated */
   @Deprecated
   boolean addNetworkAccessPoint(NetworkAccessPointMBean var1) throws InvalidAttributeValueException, ConfigurationException;

   /** @deprecated */
   @Deprecated
   boolean removeNetworkAccessPoint(NetworkAccessPointMBean var1) throws InvalidAttributeValueException, ConfigurationException;

   int getAcceptBacklog();

   void setAcceptBacklog(int var1) throws InvalidAttributeValueException;

   int getMaxBackoffBetweenFailures();

   void setMaxBackoffBetweenFailures(int var1) throws InvalidAttributeValueException;

   int getLoginTimeoutMillis();

   void setLoginTimeoutMillis(int var1) throws InvalidAttributeValueException;

   boolean isAdministrationPortEnabled();

   void setAdministrationPortEnabled(boolean var1);

   int getAdministrationPort();

   void setAdministrationPort(int var1) throws InvalidAttributeValueException;

   String[] getJNDITransportableObjectFactoryList();

   void setJNDITransportableObjectFactoryList(String[] var1) throws InvalidAttributeValueException;

   Map getIIOPConnectionPools();

   void setIIOPConnectionPools(Map var1) throws InvalidAttributeValueException;

   XMLRegistryMBean getXMLRegistry();

   void setXMLEntityCache(XMLEntityCacheMBean var1) throws InvalidAttributeValueException;

   XMLEntityCacheMBean getXMLEntityCache();

   void setXMLRegistry(XMLRegistryMBean var1) throws InvalidAttributeValueException;

   String getJavaCompiler();

   void setJavaCompiler(String var1) throws InvalidAttributeValueException;

   String getJavaCompilerPreClassPath();

   void setJavaCompilerPreClassPath(String var1) throws InvalidAttributeValueException;

   String getJavaCompilerPostClassPath();

   void setJavaCompilerPostClassPath(String var1) throws InvalidAttributeValueException;

   String getExtraRmicOptions();

   void setExtraRmicOptions(String var1) throws InvalidAttributeValueException;

   String getExtraEjbcOptions();

   void setExtraEjbcOptions(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getVerboseEJBDeploymentEnabled();

   /** @deprecated */
   @Deprecated
   void setVerboseEJBDeploymentEnabled(String var1);

   String getTransactionLogFilePrefix();

   void setTransactionLogFilePrefix(String var1) throws InvalidAttributeValueException;

   String getTransactionLogFileWritePolicy();

   void setTransactionLogFileWritePolicy(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean isNetworkClassLoadingEnabled();

   void setNetworkClassLoadingEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isTunnelingEnabled();

   void setTunnelingEnabled(boolean var1) throws DistributedManagementException;

   int getTunnelingClientPingSecs();

   void setTunnelingClientPingSecs(int var1) throws InvalidAttributeValueException;

   int getTunnelingClientTimeoutSecs();

   void setTunnelingClientTimeoutSecs(int var1) throws InvalidAttributeValueException;

   int getAdminReconnectIntervalSeconds();

   void setAdminReconnectIntervalSeconds(int var1) throws InvalidAttributeValueException;

   boolean isJMSDefaultConnectionFactoriesEnabled();

   void setJMSDefaultConnectionFactoriesEnabled(boolean var1) throws DistributedManagementException;

   String getJMSConnectionFactoryUnmappedResRefMode();

   void setJMSConnectionFactoryUnmappedResRefMode(String var1) throws DistributedManagementException;

   void setName(String var1) throws InvalidAttributeValueException, ManagementException;

   String getName();

   ServerStartMBean getServerStart();

   /** @deprecated */
   @Deprecated
   int getListenDelaySecs();

   void setListenDelaySecs(int var1);

   JTAMigratableTargetMBean getJTAMigratableTarget();

   JTAMigratableTargetMBean createJTAMigratableTarget();

   void destroyJTAMigratableTarget();

   /** @deprecated */
   @Deprecated
   int getLowMemoryTimeInterval();

   /** @deprecated */
   @Deprecated
   void setLowMemoryTimeInterval(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   int getLowMemorySampleSize();

   /** @deprecated */
   @Deprecated
   void setLowMemorySampleSize(int var1);

   /** @deprecated */
   @Deprecated
   int getLowMemoryGranularityLevel();

   /** @deprecated */
   @Deprecated
   void setLowMemoryGranularityLevel(int var1);

   /** @deprecated */
   @Deprecated
   int getLowMemoryGCThreshold();

   /** @deprecated */
   @Deprecated
   void setLowMemoryGCThreshold(int var1);

   String getStagingDirectoryName();

   void setStagingDirectoryName(String var1);

   String getUploadDirectoryName();

   void setUploadDirectoryName(String var1);

   String getActiveDirectoryName();

   void setActiveDirectoryName(String var1);

   String getStagingMode();

   void setStagingMode(String var1);

   boolean getAutoRestart();

   void setAutoRestart(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean getAutoKillIfFailed();

   void setAutoKillIfFailed(boolean var1);

   int getRestartIntervalSeconds();

   void setRestartIntervalSeconds(int var1) throws InvalidAttributeValueException;

   int getRestartMax();

   void setRestartMax(int var1) throws InvalidAttributeValueException;

   int getHealthCheckIntervalSeconds();

   void setHealthCheckIntervalSeconds(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   int getHealthCheckTimeoutSeconds();

   /** @deprecated */
   @Deprecated
   void setHealthCheckTimeoutSeconds(int var1) throws InvalidAttributeValueException;

   int getHealthCheckStartDelaySeconds();

   void setHealthCheckStartDelaySeconds(int var1) throws InvalidAttributeValueException;

   int getRestartDelaySeconds();

   void setRestartDelaySeconds(int var1) throws InvalidAttributeValueException;

   void setClasspathServletDisabled(boolean var1);

   boolean isClasspathServletDisabled();

   void setDefaultInternalServletsDisabled(boolean var1);

   boolean isDefaultInternalServletsDisabled();

   /** @deprecated */
   @Deprecated
   String getServerVersion();

   /** @deprecated */
   @Deprecated
   void setServerVersion(String var1);

   void setStartupMode(String var1);

   String getStartupMode();

   void setServerLifeCycleTimeoutVal(int var1);

   int getServerLifeCycleTimeoutVal();

   void setGracefulShutdownTimeout(int var1);

   void setStartupTimeout(int var1);

   int getStartupTimeout();

   int getGracefulShutdownTimeout();

   boolean isIgnoreSessionsDuringShutdown();

   void setIgnoreSessionsDuringShutdown(boolean var1);

   boolean isManagedServerIndependenceEnabled();

   void setManagedServerIndependenceEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean isMSIFileReplicationEnabled();

   /** @deprecated */
   @Deprecated
   void setMSIFileReplicationEnabled(boolean var1);

   void setClientCertProxyEnabled(boolean var1);

   boolean isClientCertProxyEnabled();

   void setWeblogicPluginEnabled(boolean var1);

   boolean isWeblogicPluginEnabled();

   void setHostsMigratableServices(boolean var1);

   boolean getHostsMigratableServices();

   void setHttpTraceSupportEnabled(boolean var1);

   boolean isHttpTraceSupportEnabled();

   String getKeyStores();

   void setKeyStores(String var1);

   String getCustomIdentityKeyStoreFileName();

   void setCustomIdentityKeyStoreFileName(String var1);

   String getCustomIdentityKeyStoreType();

   void setCustomIdentityKeyStoreType(String var1);

   String getCustomIdentityKeyStorePassPhrase();

   void setCustomIdentityKeyStorePassPhrase(String var1);

   byte[] getCustomIdentityKeyStorePassPhraseEncrypted();

   void setCustomIdentityKeyStorePassPhraseEncrypted(byte[] var1);

   String getCustomTrustKeyStoreFileName();

   void setCustomTrustKeyStoreFileName(String var1);

   String getCustomTrustKeyStoreType();

   void setCustomTrustKeyStoreType(String var1);

   String getCustomTrustKeyStorePassPhrase();

   void setCustomTrustKeyStorePassPhrase(String var1);

   byte[] getCustomTrustKeyStorePassPhraseEncrypted();

   void setCustomTrustKeyStorePassPhraseEncrypted(byte[] var1);

   String getJavaStandardTrustKeyStorePassPhrase();

   void setJavaStandardTrustKeyStorePassPhrase(String var1);

   byte[] getJavaStandardTrustKeyStorePassPhraseEncrypted();

   void setJavaStandardTrustKeyStorePassPhraseEncrypted(byte[] var1);

   void setReliableDeliveryPolicy(WSReliableDeliveryPolicyMBean var1);

   WSReliableDeliveryPolicyMBean getReliableDeliveryPolicy();

   boolean isMessageIdPrefixEnabled();

   void setMessageIdPrefixEnabled(boolean var1);

   DefaultFileStoreMBean getDefaultFileStore();

   MachineMBean[] getCandidateMachines();

   void setCandidateMachines(MachineMBean[] var1);

   OverloadProtectionMBean getOverloadProtection();

   String getJDBCLLRTableName();

   void setJDBCLLRTableName(String var1);

   boolean isUseFusionForLLR();

   void setUseFusionForLLR(boolean var1);

   int getJDBCLLRTableXIDColumnSize();

   void setJDBCLLRTableXIDColumnSize(int var1);

   int getJDBCLLRTablePoolColumnSize();

   void setJDBCLLRTablePoolColumnSize(int var1);

   int getJDBCLLRTableRecordColumnSize();

   void setJDBCLLRTableRecordColumnSize(int var1);

   int getJDBCLoginTimeoutSeconds();

   void setJDBCLoginTimeoutSeconds(int var1);

   WLDFServerDiagnosticMBean getServerDiagnosticConfig();

   void setAutoJDBCConnectionClose(String var1);

   String getAutoJDBCConnectionClose();

   String[] getSupportedProtocols();

   String getDefaultStagingDirName();

   String get81StyleDefaultStagingDirName();

   FederationServicesMBean getFederationServices();

   SingleSignOnServicesMBean getSingleSignOnServices();

   WebServiceMBean getWebService();

   /** @deprecated */
   @Deprecated
   int getNMSocketCreateTimeoutInMillis();

   /** @deprecated */
   @Deprecated
   void setNMSocketCreateTimeoutInMillis(int var1) throws InvalidAttributeValueException, DistributedManagementException;

   void setCoherenceClusterSystemResource(CoherenceClusterSystemResourceMBean var1);

   CoherenceClusterSystemResourceMBean getCoherenceClusterSystemResource();

   void setVirtualMachineName(String var1);

   /** @deprecated */
   @Deprecated
   String getVirtualMachineName();

   String getReplicationPorts();

   void setReplicationPorts(String var1);

   TransactionLogJDBCStoreMBean getTransactionLogJDBCStore();

   DataSourceMBean getDataSource();

   CoherenceMemberConfigMBean getCoherenceMemberConfig();

   int getBuzzPort();

   void setBuzzPort(int var1) throws InvalidAttributeValueException;

   String getBuzzAddress();

   void setBuzzAddress(String var1) throws InvalidAttributeValueException;

   void setBuzzEnabled(boolean var1);

   boolean isBuzzEnabled();

   int getMaxConcurrentNewThreads();

   void setMaxConcurrentNewThreads(int var1);

   int getMaxConcurrentLongRunningRequests();

   void setMaxConcurrentLongRunningRequests(int var1);

   int getNumOfRetriesBeforeMSIMode();

   void setNumOfRetriesBeforeMSIMode(int var1);

   int getRetryIntervalBeforeMSIMode();

   void setRetryIntervalBeforeMSIMode(int var1);

   WebLogicMBean getParent();

   void setParent(WebLogicMBean var1) throws ConfigurationException;

   void setClasspathServletSecureModeEnabled(boolean var1);

   boolean isClasspathServletSecureModeEnabled();

   void setSitConfigPollingInterval(int var1);

   int getSitConfigPollingInterval();

   void setSessionReplicationOnShutdownEnabled(boolean var1);

   boolean isSessionReplicationOnShutdownEnabled();

   void setCleanupOrphanedSessionsEnabled(boolean var1);

   boolean isCleanupOrphanedSessionsEnabled();

   String getTransactionPrimaryChannelName();

   void setTransactionPrimaryChannelName(String var1) throws InvalidAttributeValueException;

   String getTransactionSecureChannelName();

   void setTransactionSecureChannelName(String var1) throws InvalidAttributeValueException;

   String getTransactionPublicChannelName();

   void setTransactionPublicChannelName(String var1) throws InvalidAttributeValueException;

   String getTransactionPublicSecureChannelName();

   void setTransactionPublicSecureChannelName(String var1) throws InvalidAttributeValueException;

   void setSitConfigRequired(boolean var1);

   boolean isSitConfigRequired();
}
