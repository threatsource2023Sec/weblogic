package weblogic.management.runtime;

import java.net.InetSocketAddress;
import java.util.HashMap;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.management.ManagementException;
import weblogic.management.PartitionLifeCycleException;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.protocol.ServerIdentity;
import weblogic.security.ServerRuntimeSecurityAccess;
import weblogic.server.ServerLifecycleException;

public interface ServerRuntimeMBean extends RuntimeMBean, HealthFeedback, ServerStates, ServerRuntimeSecurityAccess {
   void suspend() throws ServerLifecycleException;

   void suspend(int var1, boolean var2) throws ServerLifecycleException;

   void forceSuspend() throws ServerLifecycleException;

   void resume() throws ServerLifecycleException;

   void shutdown() throws ServerLifecycleException;

   void shutdown(int var1, boolean var2) throws ServerLifecycleException;

   void shutdown(int var1, boolean var2, boolean var3) throws ServerLifecycleException;

   void forceShutdown() throws ServerLifecycleException;

   void abortStartupAfterAdminState() throws ServerLifecycleException;

   boolean isStartupAbortedInAdminState();

   boolean isShuttingDownDueToFailure();

   /** @deprecated */
   @Deprecated
   void start();

   String getState();

   boolean isShuttingDown();

   int getStateVal();

   long getActivationTime();

   long getServerStartupTime();

   /** @deprecated */
   @Deprecated
   String getListenAddress();

   InetSocketAddress getServerChannel(String var1);

   String getDefaultURL();

   String getAdministrationURL();

   String getURL(String var1);

   String getIPv4URL(String var1);

   String getIPv6URL(String var1);

   /** @deprecated */
   @Deprecated
   int getListenPort();

   /** @deprecated */
   @Deprecated
   int getSSLListenPort();

   /** @deprecated */
   @Deprecated
   int getAdministrationPort();

   /** @deprecated */
   @Deprecated
   String getSSLListenAddress();

   String getJVMID();

   ServerIdentity getServerIdentity();

   int getOpenSocketsCurrentCount();

   /** @deprecated */
   @Deprecated
   int getRestartsTotalCount();

   /** @deprecated */
   @Deprecated
   SocketRuntime[] getSockets();

   ServerChannelRuntimeMBean[] getServerChannelRuntimes();

   /** @deprecated */
   @Deprecated
   long getSocketsOpenedTotalCount();

   String getMiddlewareHome();

   /** @deprecated */
   @Deprecated
   String getOracleHome();

   String getWeblogicHome();

   String getWeblogicVersion();

   JTARuntimeMBean getJTARuntime();

   void setJTARuntime(JTARuntimeMBean var1);

   JVMRuntimeMBean getJVMRuntime();

   void setJVMRuntime(JVMRuntimeMBean var1);

   JMSRuntimeMBean getJMSRuntime();

   void setJMSRuntime(JMSRuntimeMBean var1);

   /** @deprecated */
   @Deprecated
   MessagingBridgeRuntimeMBean getMessagingBridgeRuntime();

   void setMessagingBridgeRuntime(MessagingBridgeRuntimeMBean var1);

   MessagingBridgeRuntimeMBean[] getMessagingBridgeRuntimes();

   boolean addMessagingBridgeRuntime(MessagingBridgeRuntimeMBean var1);

   boolean removeMessagingBridgeRuntime(MessagingBridgeRuntimeMBean var1);

   MessagingBridgeRuntimeMBean lookupMessagingBridgeRuntime(String var1);

   JDBCServiceRuntimeMBean getJDBCServiceRuntime();

   void setJDBCServiceRuntime(JDBCServiceRuntimeMBean var1);

   WTCRuntimeMBean getWTCRuntime();

   void setWTCRuntime(WTCRuntimeMBean var1);

   JoltConnectionServiceRuntimeMBean getJoltRuntime();

   void setJoltRuntime(JoltConnectionServiceRuntimeMBean var1);

   ServerSecurityRuntimeMBean getServerSecurityRuntime();

   void setServerSecurityRuntime(ServerSecurityRuntimeMBean var1);

   ClusterRuntimeMBean getClusterRuntime();

   void setClusterRuntime(ClusterRuntimeMBean var1);

   EntityCacheCurrentStateRuntimeMBean getEntityCacheCurrentStateRuntime();

   void setEntityCacheCurrentStateRuntime(EntityCacheCurrentStateRuntimeMBean var1);

   EntityCacheCumulativeRuntimeMBean getEntityCacheCumulativeRuntime();

   void setEntityCacheCumulativeRuntime(EntityCacheCumulativeRuntimeMBean var1);

   EntityCacheCumulativeRuntimeMBean getEntityCacheHistoricalRuntime();

   void setEntityCacheHistoricalRuntime(EntityCacheCumulativeRuntimeMBean var1);

   ThreadPoolRuntimeMBean getThreadPoolRuntime();

   void setThreadPoolRuntime(ThreadPoolRuntimeMBean var1);

   ClassLoaderRuntimeMBean getClassLoaderRuntime();

   void setClassLoaderRuntime(ClassLoaderRuntimeMBean var1);

   void setTimerRuntime(TimerRuntimeMBean var1);

   TimerRuntimeMBean getTimerRuntime();

   void setTimeServiceRuntime(TimeServiceRuntimeMBean var1);

   TimeServiceRuntimeMBean getTimeServiceRuntime();

   ExecuteQueueRuntimeMBean getDefaultExecuteQueueRuntime();

   void setDefaultExecuteQueueRuntime(ExecuteQueueRuntimeMBean var1);

   ExecuteQueueRuntimeMBean[] getExecuteQueueRuntimes();

   boolean addExecuteQueueRuntime(ExecuteQueueRuntimeMBean var1);

   boolean removeExecuteQueueRuntime(ExecuteQueueRuntimeMBean var1);

   WorkManagerRuntimeMBean[] getWorkManagerRuntimes();

   boolean addWorkManagerRuntime(WorkManagerRuntimeMBean var1);

   boolean removeWorkManagerRuntime(WorkManagerRuntimeMBean var1);

   MinThreadsConstraintRuntimeMBean lookupMinThreadsConstraintRuntime(String var1);

   RequestClassRuntimeMBean lookupRequestClassRuntime(String var1);

   MaxThreadsConstraintRuntimeMBean lookupMaxThreadsConstraintRuntime(String var1);

   boolean addMaxThreadsConstraintRuntime(MaxThreadsConstraintRuntimeMBean var1);

   boolean addMinThreadsConstraintRuntime(MinThreadsConstraintRuntimeMBean var1);

   boolean addRequestClassRuntime(RequestClassRuntimeMBean var1);

   MaxThreadsConstraintRuntimeMBean[] getMaxThreadsConstraintRuntimes();

   MinThreadsConstraintRuntimeMBean[] getMinThreadsConstraintRuntimes();

   RequestClassRuntimeMBean[] getRequestClassRuntimes();

   String getAdminServerHost();

   int getAdminServerListenPort();

   boolean isAdminServerListenPortSecure();

   boolean isListenPortEnabled();

   boolean isSSLListenPortEnabled();

   boolean isAdministrationPortEnabled();

   HealthState getHealthState();

   CompositeData getHealthStateJMX() throws OpenDataException;

   HealthState getOverallHealthState();

   CompositeData getOverallHealthStateJMX() throws OpenDataException;

   /** @deprecated */
   @Deprecated
   void setHealthState(int var1, String var2);

   void setHealthState(int var1, Symptom var2);

   boolean isAdminServer();

   String getCurrentDirectory();

   ApplicationRuntimeMBean[] getApplicationRuntimes();

   ApplicationRuntimeMBean lookupApplicationRuntime(String var1);

   LibraryRuntimeMBean[] getLibraryRuntimes();

   LibraryRuntimeMBean lookupLibraryRuntime(String var1);

   LogBroadcasterRuntimeMBean getLogBroadcasterRuntime() throws ManagementException;

   /** @deprecated */
   @Deprecated
   LogRuntimeMBean getLogRuntime();

   ServerLogRuntimeMBean getServerLogRuntime();

   void setServerLogRuntime(ServerLogRuntimeMBean var1);

   WLDFRuntimeMBean getWLDFRuntime();

   void setWLDFRuntime(WLDFRuntimeMBean var1);

   MANReplicationRuntimeMBean getMANReplicationRuntime();

   void setMANReplicationRuntime(MANReplicationRuntimeMBean var1);

   WANReplicationRuntimeMBean getWANReplicationRuntime();

   void setWANReplicationRuntime(WANReplicationRuntimeMBean var1);

   /** @deprecated */
   @Deprecated
   void setCurrentMachine(String var1);

   String getCurrentMachine();

   void restartSSLChannels();

   HealthState[] getSubsystemHealthStates();

   HashMap getServerServiceVersions();

   MailSessionRuntimeMBean[] getMailSessionRuntimes();

   boolean addMailSessionRuntime(MailSessionRuntimeMBean var1);

   boolean removeMailSessionRuntime(MailSessionRuntimeMBean var1);

   PersistentStoreRuntimeMBean[] getPersistentStoreRuntimes();

   PersistentStoreRuntimeMBean lookupPersistentStoreRuntime(String var1);

   void addPersistentStoreRuntime(PersistentStoreRuntimeMBean var1);

   void removePersistentStoreRuntime(PersistentStoreRuntimeMBean var1);

   ConnectorServiceRuntimeMBean getConnectorServiceRuntime();

   void setConnectorServiceRuntime(ConnectorServiceRuntimeMBean var1);

   WebServerRuntimeMBean[] getWebServerRuntimes();

   boolean addWebServerRuntime(WebServerRuntimeMBean var1);

   boolean removeWebServerRuntime(WebServerRuntimeMBean var1);

   String[] getPendingRestartSystemResources();

   boolean addPendingRestartSystemResource(String var1);

   boolean removePendingRestartSystemResource(String var1);

   boolean isRestartPendingForSystemResource(String var1);

   boolean isRestartRequired();

   void setRestartRequired(boolean var1);

   void setPartitionRestartRequired(String var1, boolean var2);

   boolean isPartitionRestartRequired(String var1);

   String[] getPendingRestartPartitions();

   String getServerClasspath();

   /** @deprecated */
   @Deprecated
   PathServiceRuntimeMBean getPathServiceRuntime();

   /** @deprecated */
   @Deprecated
   void setPathServiceRuntime(PathServiceRuntimeMBean var1);

   PathServiceRuntimeMBean[] getPathServiceRuntimes();

   boolean addPathServiceRuntime(PathServiceRuntimeMBean var1, boolean var2);

   boolean removePathServiceRuntime(PathServiceRuntimeMBean var1, boolean var2);

   boolean isClusterMaster();

   SAFRuntimeMBean getSAFRuntime();

   void setSAFRuntime(SAFRuntimeMBean var1);

   SNMPAgentRuntimeMBean getSNMPAgentRuntime();

   void setSNMPAgentRuntime(SNMPAgentRuntimeMBean var1);

   void setSingleSignOnServicesRuntime(SingleSignOnServicesRuntimeMBean var1);

   SingleSignOnServicesRuntimeMBean getSingleSignOnServicesRuntime();

   boolean isServiceAvailable(String var1);

   MANAsyncReplicationRuntimeMBean getMANAsyncReplicationRuntime();

   void setMANAsyncReplicationRuntime(MANAsyncReplicationRuntimeMBean var1);

   AsyncReplicationRuntimeMBean getAsyncReplicationRuntime();

   void setAsyncReplicationRuntime(AsyncReplicationRuntimeMBean var1);

   int getStableState();

   void setWseeWsrmRuntime(WseeWsrmRuntimeMBean var1);

   WseeWsrmRuntimeMBean getWseeWsrmRuntime();

   void setWseeClusterFrontEndRuntime(WseeClusterFrontEndRuntimeMBean var1);

   WseeClusterFrontEndRuntimeMBean getWseeClusterFrontEndRuntime();

   PartitionRuntimeMBean[] getPartitionRuntimes();

   PartitionRuntimeMBean lookupPartitionRuntime(String var1);

   void addPartitionRuntime(PartitionRuntimeMBean var1);

   void removePartitionRuntime(PartitionRuntimeMBean var1);

   ConcurrentManagedObjectsRuntimeMBean getConcurrentManagedObjectsRuntime();

   void setConcurrentManagedObjectsRuntime(ConcurrentManagedObjectsRuntimeMBean var1);

   BatchJobRepositoryRuntimeMBean getBatchJobRepositoryRuntime();

   void setBatchJobRepositoryRuntime(BatchJobRepositoryRuntimeMBean var1);

   void bootPartition(String var1) throws PartitionLifeCycleException;

   void startPartition(String var1) throws PartitionLifeCycleException;

   void startPartitionInAdmin(String var1) throws PartitionLifeCycleException;

   void startResourceGroup(String var1) throws ResourceGroupLifecycleException;

   void startResourceGroupInAdmin(String var1) throws ResourceGroupLifecycleException;

   void suspendResourceGroup(String var1, int var2, boolean var3) throws ResourceGroupLifecycleException;

   void suspendResourceGroup(String var1) throws ResourceGroupLifecycleException;

   void forceSuspendResourceGroup(String var1) throws ResourceGroupLifecycleException;

   void resumeResourceGroup(String var1) throws ResourceGroupLifecycleException;

   void forceShutdownResourceGroup(String var1) throws ResourceGroupLifecycleException;

   void shutdownResourceGroup(String var1, int var2, boolean var3, boolean var4) throws ResourceGroupLifecycleException;

   void shutdownResourceGroup(String var1, int var2, boolean var3) throws ResourceGroupLifecycleException;

   void shutdownResourceGroup(String var1) throws ResourceGroupLifecycleException;

   void setRgState(String var1, String var2) throws ResourceGroupLifecycleException;

   String getRgState(String var1) throws ResourceGroupLifecycleException;

   ResourceGroupLifecycleOperations.RGState getInternalRgState(String var1) throws ResourceGroupLifecycleException;

   String getName();

   boolean isResourceGroupPresent(String var1) throws ResourceGroupLifecycleException;

   void forceRestartPartition(String var1) throws PartitionLifeCycleException;

   void forceRestartPartition(String var1, long var2) throws PartitionLifeCycleException;

   AggregateProgressMBean getAggregateProgress();

   void setAggregateProgress(AggregateProgressMBean var1);

   boolean isInSitConfigState();

   void setInSitConfigState(boolean var1);

   String[] getPatchList();
}
