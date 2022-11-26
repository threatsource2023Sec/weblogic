package weblogic.cluster;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.Context;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.replication.ReplicationServicesFactory.Locator;
import weblogic.cluster.replication.ReplicationServicesFactory.ServiceType;
import weblogic.cluster.singleton.DatabaseLeasingBasis;
import weblogic.cluster.singleton.LeaseManagerFactory;
import weblogic.cluster.singleton.Leasing;
import weblogic.cluster.singleton.LeasingBasis;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.VersionInfo;
import weblogic.common.internal.WLObjectInputStream;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceNotFoundException;
import weblogic.health.HealthMonitorService;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jndi.Environment;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SingletonServiceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.utils.ConnectionSigner;
import weblogic.nodemanager.mbean.NodeManagerLifecycleService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleServiceGenerator;
import weblogic.protocol.ClusterAddressCollaborator;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.URLManager;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.rjvm.JVMID;
import weblogic.rmi.cluster.ServerInfoManager;
import weblogic.security.HMAC;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ActivatedService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.AssertionError;
import weblogic.utils.ByteArrayDiffChecker;
import weblogic.utils.StringUtils;
import weblogic.utils.io.DataIO;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Service
@Singleton
public final class ClusterService extends ActivatedService implements ClusterServices, BeanUpdateListener {
   private static ClusterService singleton = null;
   static final String MULTICAST_QUEUE = "ClusterMessaging";
   static WorkManager MULTICAST_WORKMANAGER;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final SecurityHolder securityHolder = new SecurityHolder();
   private static final ClusterTextTextFormatter FORMATTER = new ClusterTextTextFormatter();
   private ImageSource clusterDiagnosticImageSource = new ClusterDiagnosticImageSource();
   private boolean isMemberDeathDetectorEnabled = false;
   private static String SECRET_STRING;
   private static String CLUSTERED_PARTITION_STATE_DUMP_QUERY;
   @Inject
   private ClusterAddressCollaborator clusterAddressCollaborator;
   private LeasingBasis defaultLeasingBasis;
   private ServerMBean serverMBean;
   private ClusterMBean clusterMBean;
   private static boolean isServerInCluster = false;
   private String clusterName;
   private boolean useOneWayRMI;
   private boolean isSessionLazyDeserializationEnabled = false;
   private List failoverServerGroups = null;
   private boolean sessionReplicationOnShutdownEnabled = false;
   private Set lazySessionDeserializationForPartition = new HashSet();
   private Map failoverServerGroupsForPartition = new HashMap();
   private Set sessionReplicationOnShutdownForPartition = new HashSet();
   private Set cleanupOrphanedSessionsEnabledForPartition = new HashSet();
   private Set sessionStateQueryProtocolEnabledForPartition = new HashSet();
   Map disableSessionStateQueryProtocolTimerForPartition = new HashMap();
   private boolean sessionStateQueryProtocolEnabled = false;
   private TimerManager disableSessionStateQueryProtocolTimer;
   private boolean cleanupOrphanedSessionsEnabled = false;

   private static void setSingleton(ClusterService oneOnly) {
      singleton = oneOnly;
   }

   public ClusterService() {
      setSingleton(this);
      RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
      if (rt != null) {
         rt.getServer().addBeanUpdateListener(this);
      }

      SECRET_STRING = "?PeerInfo=" + ClusterHelper.STRINGFIED_PEERINFO + "&ServerName=" + ManagementService.getRuntimeAccess(kernelId).getServer().getName();
      CLUSTERED_PARTITION_STATE_DUMP_QUERY = "/bea_wls_cluster_internal/a2e2gp2r2/p2s2" + SECRET_STRING;
   }

   public static ClusterService getClusterServiceInternal() {
      return singleton;
   }

   public boolean startService() throws ServiceFailureException {
      boolean isStarted = false;
      securityHolder.init();
      this.serverMBean = ManagementService.getRuntimeAccess(kernelId).getServer();
      this.clusterMBean = this.serverMBean.getCluster();
      String machineName = this.serverMBean.getMachine() == null ? ClusterHelper.getMachineName() : this.serverMBean.getMachine().getName();
      if (this.clusterMBean != null) {
         this.useOneWayRMI = this.clusterMBean.isOneWayRmiForReplicationEnabled();
         this.isMemberDeathDetectorEnabled = this.clusterMBean.isMemberDeathDetectorEnabled() && this.verifyMemberDeathDetectorConfiguration();
         ServerInfoManager.theOne().addServer(this.serverMBean.getName(), LocalServerIdentity.getIdentity(), this.serverMBean.getClusterWeight());
         this.clusterName = this.clusterMBean.getName();
         String clusterAddress;
         if (this.isUnicastMessagingModeEnabled()) {
            ClusterConfigurationValidator validator = getClusterConfigurationValidator();
            if (validator != null) {
               validator.validateUnicastCluster(this.serverMBean, this.clusterMBean);
            }
         } else {
            clusterAddress = this.clusterMBean.getMulticastAddress();
            if (clusterAddress == null || clusterAddress.equals("")) {
               ClusterLogger.logMissingClusterMulticastAddressError(this.clusterName);
               throw new ServiceFailureException("configuration problem - missing multicast address for cluster: " + this.clusterName);
            }
         }

         clusterAddress = this.clusterMBean.getClusterAddress();
         int triggerIntervalMillis;
         if (clusterAddress != null) {
            int nodeNumber = 0;
            String[] nodeAddress = StringUtils.splitCompletely(clusterAddress, ",", false);
            triggerIntervalMillis = (new String(":")).charAt(0);

            try {
               if (nodeAddress.length > 1) {
                  while(nodeNumber < nodeAddress.length) {
                     String nodeNameOrAddress = StringUtils.upto(nodeAddress[nodeNumber], (char)triggerIntervalMillis);
                     InetAddress.getByName(nodeNameOrAddress);
                     ++nodeNumber;
                  }
               } else {
                  InetAddress.getByName(clusterAddress);
               }

               JVMID.localID().setClusterAddress(clusterAddress);
            } catch (UnknownHostException var10) {
               if (nodeAddress.length > 1) {
                  ClusterLogger.logCannotResolveClusterAddressWarning(clusterAddress + ": Unknown host: " + nodeAddress[nodeNumber]);
               } else {
                  ClusterLogger.logCannotResolveClusterAddressWarning(clusterAddress);
               }
            }
         }

         try {
            FORMATTER.startingClusterService();
            MemberAttributes localAttributes = new MemberAttributes(this.serverMBean.getListenAddress(), machineName, VersionInfo.theOne().getReleaseVersion(), System.currentTimeMillis(), this.serverMBean.getClusterWeight(), this.serverMBean.getReplicationGroup(), this.serverMBean.getPreferredSecondaryGroup(), this.clusterName, this.serverMBean.isAutoMigrationEnabled(), this.clusterMBean.getReplicationChannel(), PeerInfo.getPeerInfo());
            ClusterMessagesManager.initialize(this.clusterMBean.getMulticastAddress(), ManagementService.getRuntimeAccess(kernelId).getServer().getInterfaceAddress(), this.clusterMBean.getMulticastPort(), (byte)this.clusterMBean.getMulticastTTL(), (long)this.clusterMBean.getMulticastSendDelay());
            PartitionAwareSenderManager.theOne().setAgeThreshold((long)this.clusterMBean.getServiceAgeThresholdSeconds());
            MemberManager.initialize(localAttributes.joinTime(), this.clusterMBean.getIdlePeriodsUntilTimeout());
            UpgradeUtils.getInstance();
            this.initializeClusterAddressCollaborator(this.clusterMBean);
            AttributeManager.initialize(localAttributes);
            ClusterRuntime.initialize(this.clusterName);
            MULTICAST_WORKMANAGER = WorkManagerFactory.getInstance().findOrCreate("ClusterMessaging", 1, 1);
            isServerInCluster = true;
            ClusterRuntimeMBean clusterRTMBean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getClusterRuntime();
            HealthMonitorService.register("Cluster", clusterRTMBean, true);
            Locator.locate().getReplicationService(ServiceType.SYNC);
            triggerIntervalMillis = this.clusterMBean.getHealthCheckIntervalMillis();
            int idlePeriodsUntilTimeout = this.clusterMBean.getHealthCheckPeriodsUntilFencing();
            boolean isLeasingBasisNeeded = this.isLeasingBasisNeeded(this.clusterMBean);
            if (isLeasingBasisNeeded && this.clusterMBean.getMigrationBasis().equals("database") && this.clusterMBean.getDataSourceForAutomaticMigration() == null) {
               ClusterExtensionLogger.logDataSourceForDatabaseLeasingNotSet(this.clusterMBean.getName());
               throw new ServiceFailureException("Cluster " + this.clusterMBean.getName() + " uses database as the migration basis but no data source for  migration has been configured");
            }

            if (!this.clusterMBean.getMigrationBasis().equals("database") || !isLeasingBasisNeeded && this.clusterMBean.getDataSourceForAutomaticMigration() == null) {
               if (this.clusterMBean.getMigrationBasis().equals("consensus")) {
                  this.defaultLeasingBasis = weblogic.cluster.singleton.ConsensusLeasing.Locator.locate().createConsensusBasis(triggerIntervalMillis, idlePeriodsUntilTimeout * triggerIntervalMillis);
               } else if (this.clusterMBean.getDataSourceForJobScheduler() != null) {
                  this.defaultLeasingBasis = DatabaseLeasingBasis.createBasis(this.serverMBean, this.clusterMBean.getDataSourceForJobScheduler(), idlePeriodsUntilTimeout * triggerIntervalMillis / 1000, this.clusterMBean.getAutoMigrationTableName());
               }
            } else {
               this.defaultLeasingBasis = DatabaseLeasingBasis.createBasis(this.serverMBean, this.clusterMBean.getDataSourceForAutomaticMigration(), idlePeriodsUntilTimeout * triggerIntervalMillis / 1000, this.clusterMBean.getAutoMigrationTableName());
            }

            if (this.defaultLeasingBasis != null) {
               this.initializeLeasingFactoryDefaults(this.defaultLeasingBasis, triggerIntervalMillis, idlePeriodsUntilTimeout * triggerIntervalMillis, this.clusterMBean.getFencingGracePeriodMillis());
            }

            this.bindRemoteReplicaServiceInJNDI();
            this.bindRemoteOperationsInJNDI();
            isStarted = true;
         } catch (IOException var9) {
            ClusterLogger.logFailedToJoinClusterError(this.clusterName, this.clusterMBean.getMulticastAddress(), var9);
            throw new ServiceFailureException(var9);
         }
      }

      ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
      imageManager.registerImageSource("Cluster", this.clusterDiagnosticImageSource);
      return isStarted;
   }

   private static ClusterConfigurationValidator getClusterConfigurationValidator() {
      return (ClusterConfigurationValidator)GlobalServiceLocator.getServiceLocator().getService(ClusterConfigurationValidator.class, new Annotation[0]);
   }

   private String makeQueryString(MulticastSessionId multicastSessionId) {
      StringBuilder sb = new StringBuilder(CLUSTERED_PARTITION_STATE_DUMP_QUERY);
      sb.append("&partitionId=").append(multicastSessionId.getPartitionID()).append("&resourceGroupName=").append(multicastSessionId.getResourceGroupName()).append("&sessionName=").append(multicastSessionId.getName());
      return sb.toString();
   }

   private boolean verifyMemberDeathDetectorConfiguration() {
      ClusterMBean cluster = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      ServerMBean[] servers = cluster.getServers();
      boolean result = true;

      for(int i = 0; i < servers.length; ++i) {
         ServerMBean server = servers[i];
         if (server.getMachine() == null) {
            result = false;
            ClusterExtensionLogger.logServerWithNoMachineConfigured(server.getName());
         }
      }

      return result;
   }

   public void stopService() throws ServiceFailureException {
      this.haltService();
   }

   public synchronized void haltService() throws ServiceFailureException {
      if (this.clusterMBean != null && isServerInCluster) {
         ClusterLogger.logLeavingCluster(this.clusterMBean.getName());
         ClusterMessagesManager.theOne().forceSuspend();
         ClusterMessagesManager.theOne().stopListening();
         MemberManager.theOne().shutdown();
         PartitionAwareSenderManager.theOne().shutdown();
         HealthMonitorService.unregister("Cluster");
         this.unBindRemoteReplicaServiceFromJNDI();
         this.unBindRemoteOperationsFromJNDI();
         ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);

         try {
            imageManager.unregisterImageSource("Cluster");
         } catch (ImageSourceNotFoundException var3) {
         }
      }

   }

   public static ClusterServices getServices() {
      return isServerInCluster ? getClusterServiceInternal() : null;
   }

   MulticastSession createMulticastSession(MulticastSessionId multicastSessionId, RecoverListener listener, int cacheSize, boolean useHTTPForStateDumps) {
      return ClusterMessagesManager.theOne().createSender(multicastSessionId, listener, cacheSize, useHTTPForStateDumps);
   }

   public MulticastSession createMulticastSession(RecoverListener listener, int cacheSize, boolean useHTTPForStateDumps) {
      return this.createMulticastSession(MulticastSessionIDConstants.CUSTOM_MULTICAST_SESSION_ID, listener, cacheSize, useHTTPForStateDumps);
   }

   public MulticastSession createMulticastSession(RecoverListener listener, int cacheSize) {
      return this.createMulticastSession(MulticastSessionIDConstants.CUSTOM_MULTICAST_SESSION_ID, listener, cacheSize, false);
   }

   public MulticastSession createMulticastSession(RecoverListener listener, int cacheSize, boolean useHTTPForStateDumps, boolean adminModeEnabled) {
      return ClusterMessagesManager.theOne().createSender(MulticastSessionIDConstants.CUSTOM_MULTICAST_SESSION_ID, listener, cacheSize, useHTTPForStateDumps, adminModeEnabled);
   }

   public ClusterMemberInfo getLocalMember() {
      return isServerInCluster ? AttributeManager.theOne().getLocalAttributes() : null;
   }

   public Collection getRemoteMembers() {
      return MemberManager.theOne().getRemoteMembers();
   }

   public Collection getRemoteMembersWithActivePartition(MulticastSessionId sessionId) {
      return MemberManager.theOne().getRemoteMembersWithActivePartition(sessionId);
   }

   public Collection getRemoteMembersWithActivePartition(String partitionId) {
      return MemberManager.theOne().getRemoteMembersWithActivePartition(partitionId);
   }

   public Collection getClusterMemberInfoWithActivePartition(String partitionId) {
      return MemberManager.theOne().getClusterMembersWithActivePartition(partitionId);
   }

   public String getMigratableServerStateOnMachine(String serverName, String machineName) {
      try {
         if (this.clusterMBean == null) {
            return null;
         } else {
            DomainMBean domain = (DomainMBean)this.clusterMBean.getParent();
            ServerMBean server = domain.lookupServer(serverName);
            if (server == null) {
               return null;
            } else {
               MachineMBean machine = domain.lookupMachine(machineName);
               if (machine == null) {
                  return null;
               } else {
                  NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
                  NodeManagerLifecycleService nodeManagerRuntime = generator.getInstance(machine);
                  String state = nodeManagerRuntime.getState(server);
                  return state;
               }
            }
         }
      } catch (Exception var9) {
         return null;
      }
   }

   public Collection getAllRemoteMembers() {
      return MemberManager.theOne().getRemoteMembers(true);
   }

   public Collection getClusterMasterMembers() {
      return Collections.EMPTY_SET;
   }

   public void addClusterMembersListener(ClusterMembersChangeListener listener) {
      MemberManager.theOne().addClusterMembersListener(listener);
   }

   public void removeClusterMembersListener(ClusterMembersChangeListener listener) {
      MemberManager.theOne().removeClusterMembersListener(listener);
   }

   public void addClusterMembersPartitionListener(ClusterMembersPartitionChangeListener partitionChangeListener) {
      MemberManager.theOne().addClusterMembersPartitionListener(partitionChangeListener);
   }

   public void removeClusterMembersPartitionListener(ClusterMembersPartitionChangeListener partitionChangeListener) {
      MemberManager.theOne().removeClusterMembersPartitionListener(partitionChangeListener);
   }

   public void addHeartbeatMessage(GroupMessage message) {
      ClusterMessagesManager.theOne().addItem(message);
   }

   public void removeHeartbeatMessage(GroupMessage message) {
      ClusterMessagesManager.theOne().removeItem(message);
   }

   public int getHeartbeatTimeoutMillis() {
      return this.clusterMBean.getIdlePeriodsUntilTimeout() * 10000;
   }

   public void resendLocalAttributes() {
      try {
         AttributeManager.theOne().sendAttributes();
      } catch (IOException var2) {
         ClusterLogger.logFailureUpdatingServerInTheCluster(ManagementService.getRuntimeAccess(kernelId).getServer().getName(), var2);
      }

   }

   public Leasing getLeasingService(String leaseType) {
      return weblogic.cluster.singleton.LeasingFactory.Locator.locateService().findOrCreateLeasingService(leaseType);
   }

   public Leasing getSingletonLeasingService() {
      return weblogic.cluster.singleton.LeasingFactory.Locator.locateService().findOrCreateLeasingService("service");
   }

   public Leasing getServerLeasingService() {
      return weblogic.cluster.singleton.LeasingFactory.Locator.locateService().findOrCreateLeasingService("wlsserver");
   }

   public LeasingBasis getDefaultLeasingBasis() {
      return this.defaultLeasingBasis;
   }

   private boolean isLeasingBasisNeeded(ClusterMBean clusterMBean) {
      return isMigratableCluster(clusterMBean) || isAutoServiceMigrationEnabled(clusterMBean);
   }

   private static boolean isMigratableCluster(ClusterMBean cluster) {
      if (cluster == null) {
         return false;
      } else {
         ServerMBean[] servers = cluster.getServers();

         for(int i = 0; i < servers.length; ++i) {
            if (servers[i].isAutoMigrationEnabled()) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean isAutoServiceMigrationEnabled(ClusterMBean cluster) {
      MigratableTargetMBean[] targets = cluster.getMigratableTargets();

      for(int i = 0; targets != null && i < targets.length; ++i) {
         if (!"manual".equals(targets[i].getMigrationPolicy())) {
            return true;
         }
      }

      DomainMBean domain = (DomainMBean)cluster.getParent();
      SingletonServiceMBean[] services = domain.getSingletonServices();

      for(int i = 0; services != null && i < services.length; ++i) {
         if (services[i].getCluster() != null && services[i].getCluster().getName().equals(cluster.getName())) {
            return true;
         }
      }

      ServerMBean[] servers = cluster.getServers();

      for(int j = 0; servers != null && j < servers.length; ++j) {
         JTAMigratableTargetMBean bean = servers[j].getJTAMigratableTarget();
         if (bean != null && bean.getMigrationPolicy().equals("failure-recovery")) {
            return true;
         }
      }

      return false;
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      boolean isAdmin = false;
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         isAdmin = true;
      }

      for(int i = 0; i < updates.length; ++i) {
         String propertyName = updates[i].getPropertyName();
         if (propertyName.equals("Cluster")) {
            throw new BeanUpdateRejectedException("Cannot update '" + propertyName + "' while the server is running");
         }

         if (propertyName.equals("Machine")) {
            if (!isAdmin) {
               throw new BeanUpdateRejectedException("Cannot update '" + propertyName + "' while the server is running");
            }

            ClusterExtensionLogger.logUpdatingNonDynamicPropertyOnAdminServer(propertyName);
         }
      }

   }

   public void activateUpdate(BeanUpdateEvent event) {
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   byte[] getSecureHash() {
      return securityHolder.getSecretHash();
   }

   boolean checkRequest(String serverName, byte[] originalHash) {
      byte[] encrypt = securityHolder.getEncryptionService().encryptString(serverName);
      byte[] md5 = HMAC.digest(encrypt, securityHolder.getSecret(), securityHolder.getSalt());
      return ByteArrayDiffChecker.diffByteArrays(originalHash, md5) == null;
   }

   boolean multicastDataEncryptionEnabled() {
      return ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getMulticastDataEncryption();
   }

   public boolean isReplicationTimeoutEnabled() {
      return this.clusterMBean.isReplicationTimeoutEnabled();
   }

   String getLocalServerDetails() {
      String localServerName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      String localDomainName = ManagementService.getRuntimeAccess(kernelId).getDomainName();
      return "Server '" + localServerName + "', cluster '" + this.clusterName + "', domain '" + localDomainName + "',  with attributes multicastDataEncryptionEnabled '" + this.multicastDataEncryptionEnabled() + "' and Admin channel available '" + ChannelHelper.isLocalAdminChannelEnabled() + "'";
   }

   boolean isUnicastMessagingModeEnabled() {
      return this.clusterMBean.getClusterMessagingMode().equals("unicast");
   }

   public boolean useOneWayRMI() {
      return this.useOneWayRMI;
   }

   public boolean isMemberDeathDetectorEnabled() {
      return this.isMemberDeathDetectorEnabled;
   }

   public String getClusterName() {
      return this.clusterName;
   }

   public boolean isSessionStateQueryProtocolEnabled() {
      return this.clusterMBean == null ? this.sessionStateQueryProtocolEnabled : this.clusterMBean.isSessionStateQueryProtocolEnabled() || this.sessionStateQueryProtocolEnabled;
   }

   public int getSessionStateQueryRequestTimeout() {
      return this.clusterMBean == null ? 30 : this.clusterMBean.getSessionStateQueryRequestTimeout();
   }

   private void initializeLeasingFactoryDefaults(LeasingBasis basis, int heartbeatPeriod, int healthCheckPeriod, int gracePeriod) {
      LeaseManagerFactory factory = (LeaseManagerFactory)GlobalServiceLocator.getServiceLocator().getService(LeaseManagerFactory.class, new Annotation[0]);
      factory.initialize(basis, heartbeatPeriod, healthCheckPeriod, gracePeriod);
   }

   public boolean isSessionLazyDeserializationEnabled() {
      return this.clusterMBean == null ? this.isSessionLazyDeserializationEnabled : this.clusterMBean.isSessionLazyDeserializationEnabled() || this.isSessionLazyDeserializationEnabled;
   }

   public void setSessionLazyDeserializationEnabled(boolean enabled) {
      if (this.clusterMBean == null || !this.clusterMBean.isSessionLazyDeserializationEnabled()) {
         this.isSessionLazyDeserializationEnabled = enabled;
      }
   }

   public List getFailoverServerGroups() {
      return this.failoverServerGroups;
   }

   public void setFailoverServerGroups(List serverGroups) {
      this.failoverServerGroups = serverGroups;
   }

   public boolean isZDTAppRolloutInProgress() {
      return this.failoverServerGroups != null && !this.failoverServerGroups.isEmpty();
   }

   public boolean isSessionReplicationOnShutdownEnabled() {
      return this.serverMBean != null && this.serverMBean.isSessionReplicationOnShutdownEnabled() || this.sessionReplicationOnShutdownEnabled;
   }

   public void setSessionReplicationOnShutdownEnabled(boolean enabled) {
      this.sessionReplicationOnShutdownEnabled = enabled;
   }

   public boolean isSessionLazyDeserializationEnabled(String partitionName) {
      return this.lazySessionDeserializationForPartition.contains(partitionName) || this.isSessionLazyDeserializationEnabled || this.clusterMBean != null && this.clusterMBean.isSessionLazyDeserializationEnabled();
   }

   public void setSessionLazyDeserializationEnabled(String partitionName, boolean enabled) {
      if (this.clusterMBean == null || !this.clusterMBean.isSessionLazyDeserializationEnabled()) {
         if (enabled) {
            this.lazySessionDeserializationForPartition.add(partitionName);
         } else {
            this.lazySessionDeserializationForPartition.remove(partitionName);
         }

      }
   }

   public List getFailoverServerGroups(String partitionName) {
      return this.failoverServerGroups != null && !this.failoverServerGroups.isEmpty() ? this.failoverServerGroups : (List)this.failoverServerGroupsForPartition.get(partitionName);
   }

   public void setFailoverServerGroups(String partitionName, List serverGroups) {
      if (serverGroups == null) {
         this.failoverServerGroupsForPartition.remove(partitionName);
      } else {
         this.failoverServerGroupsForPartition.put(partitionName, serverGroups);
      }

   }

   public boolean isSessionReplicationOnShutdownEnabled(String partitionName) {
      return this.sessionReplicationOnShutdownForPartition.contains(partitionName) || this.isSessionReplicationOnShutdownEnabled();
   }

   public void setSessionReplicationOnShutdownEnabled(String partitionName, boolean enabled) {
      if (enabled) {
         this.sessionReplicationOnShutdownForPartition.add(partitionName);
      } else {
         this.sessionReplicationOnShutdownForPartition.remove(partitionName);
      }

   }

   public void setCleanupOrphanedSessionsEnabled(String partitionName, boolean enabled) {
      if (enabled) {
         this.cleanupOrphanedSessionsEnabledForPartition.add(partitionName);
      } else {
         this.cleanupOrphanedSessionsEnabledForPartition.remove(partitionName);
      }

   }

   public boolean isCleanupOrphanedSessionEnabled(String partitionName) {
      return this.cleanupOrphanedSessionsEnabledForPartition.contains(partitionName) || this.isCleanupOrphanedSessionEnabled();
   }

   public boolean isSessionStateQueryProtocolEnabled(String partitionName) {
      boolean mbeanValue = this.clusterMBean != null ? this.clusterMBean.isSessionStateQueryProtocolEnabled() : false;
      return this.sessionStateQueryProtocolEnabledForPartition.contains(partitionName) || this.sessionStateQueryProtocolEnabled || mbeanValue;
   }

   public void disableSessionStateQueryProtocolAfter(final String partitionName, int secondsToWait) {
      if (this.clusterMBean == null || !this.clusterMBean.isSessionStateQueryProtocolEnabled()) {
         TimerManager partitionDisableSessionStateQueryProtocolTimer = TimerManagerFactory.getTimerManagerFactory().getTimerManager("DisableSessionStateQueryProtocol");
         this.disableSessionStateQueryProtocolTimerForPartition.put(partitionName, partitionDisableSessionStateQueryProtocolTimer);
         partitionDisableSessionStateQueryProtocolTimer.schedule(new TimerListener() {
            public void timerExpired(Timer timer) {
               ClusterService.this.innerSetSessionStateQueryProtocolEnabled(partitionName, false);
               ClusterService.this.disableSessionStateQueryProtocolTimerForPartition.remove(partitionName);
            }
         }, TimeUnit.MILLISECONDS.convert((long)secondsToWait, TimeUnit.SECONDS));
      }
   }

   public void setSessionStateQueryProtocolEnabled(String partitionName, boolean enabled) {
      if (this.clusterMBean == null || !this.clusterMBean.isSessionStateQueryProtocolEnabled()) {
         TimerManager partitionDisableSessionStateQueryProtocolTimer = (TimerManager)this.disableSessionStateQueryProtocolTimerForPartition.remove(partitionName);
         if (partitionDisableSessionStateQueryProtocolTimer != null) {
            partitionDisableSessionStateQueryProtocolTimer.stop();
         }

         this.innerSetSessionStateQueryProtocolEnabled(partitionName, enabled);
      }
   }

   private void innerSetSessionStateQueryProtocolEnabled(String partitionName, boolean enabled) {
      if (enabled) {
         this.sessionStateQueryProtocolEnabledForPartition.add(partitionName);
      } else {
         this.sessionStateQueryProtocolEnabledForPartition.remove(partitionName);
      }

   }

   public void disableSessionStateQueryProtocolAfter(int secondsToWait) {
      if (this.clusterMBean == null || !this.clusterMBean.isSessionStateQueryProtocolEnabled()) {
         this.disableSessionStateQueryProtocolTimer = TimerManagerFactory.getTimerManagerFactory().getTimerManager("DisableSessionStateQueryProtocol");
         this.disableSessionStateQueryProtocolTimer.schedule(new TimerListener() {
            public void timerExpired(Timer timer) {
               ClusterService.this.innerSetSessionStateQueryProtocolEnabled(false);
            }
         }, TimeUnit.MILLISECONDS.convert((long)secondsToWait, TimeUnit.SECONDS));
      }
   }

   public void setSessionStateQueryProtocolEnabled(boolean enabled) {
      if (this.clusterMBean == null || !this.clusterMBean.isSessionStateQueryProtocolEnabled()) {
         if (this.disableSessionStateQueryProtocolTimer != null) {
            this.disableSessionStateQueryProtocolTimer.stop();
         }

         this.innerSetSessionStateQueryProtocolEnabled(enabled);
      }
   }

   private void innerSetSessionStateQueryProtocolEnabled(boolean enabled) {
      this.sessionStateQueryProtocolEnabled = enabled;
   }

   public void setCleanupOrphanedSessionsEnabled(boolean enabled) {
      this.cleanupOrphanedSessionsEnabled = enabled;
   }

   public boolean isCleanupOrphanedSessionEnabled() {
      return this.serverMBean != null && this.serverMBean.isCleanupOrphanedSessionsEnabled() || this.cleanupOrphanedSessionsEnabled;
   }

   private void initializeClusterAddressCollaborator(ClusterMBean clusterMBean) {
      this.clusterAddressCollaborator.configure(clusterMBean.getClusterAddress(), clusterMBean.getNumberOfServersInClusterAddress());
      this.addClusterMembersListener(new ClusterMembersChangeListener() {
         public void clusterMembersChanged(ClusterMembersChangeEvent cece) {
            ServerIdentity identity = cece.getClusterMemberInfo().identity();
            if (cece.getAction() == 0) {
               ClusterService.this.clusterAddressCollaborator.addMember(identity);
            } else if (cece.getAction() == 1) {
               ClusterService.this.clusterAddressCollaborator.removeMember(identity);
            }

         }
      });
   }

   private void bindRemoteOperationsInJNDI() throws ServiceFailureException {
      try {
         Environment env = new Environment();
         env.setReplicateBindings(false);
         env.setCreateIntermediateContexts(true);
         Context ctx = env.getInitialContext();
         ctx.bind("weblogic.cluster.RemoteClusterServicesOperations", new RemoteClusterServiceOperationsImpl());
      } catch (NamingException var3) {
         throw new ServiceFailureException(var3.getMessage());
      }
   }

   private void unBindRemoteOperationsFromJNDI() throws ServiceFailureException {
      try {
         Environment env = new Environment();
         env.setReplicateBindings(false);
         env.setCreateIntermediateContexts(true);
         Context ctx = env.getInitialContext();
         ctx.unbind("weblogic.cluster.RemoteClusterServicesOperations");
      } catch (NamingException var3) {
         throw new ServiceFailureException(var3);
      }
   }

   private void bindRemoteReplicaServiceInJNDI() throws ServiceFailureException {
      try {
         Environment env = new Environment();
         env.setReplicateBindings(false);
         env.setCreateIntermediateContexts(true);
         Context ctx = env.getInitialContext();
         ctx.bind("weblogic.cluster.RemoteReplicaService", new RemoteReplicaServiceImpl());
      } catch (NamingException var3) {
         throw new ServiceFailureException(var3.getMessage());
      }
   }

   private void unBindRemoteReplicaServiceFromJNDI() throws ServiceFailureException {
      try {
         Environment env = new Environment();
         env.setReplicateBindings(false);
         env.setCreateIntermediateContexts(true);
         Context ctx = env.getInitialContext();
         ctx.unbind("weblogic.cluster.RemoteReplicaService");
      } catch (NamingException var3) {
         throw new ServiceFailureException(var3);
      }
   }

   public void getClusterJNDIStateDump(MemberAttributes clusterMember, MulticastSessionId sessionId) throws AssertionError {
      URL url = null;
      HttpURLConnection con = null;
      DataInputStream in = null;
      WLObjectInputStream ois = null;
      String queryString = this.makeQueryString(sessionId);
      ComponentInvocationContextManager mgr = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext cic = mgr.getCurrentComponentInvocationContext();
      if (cic.getApplicationName() == null) {
         cic = mgr.createComponentInvocationContext(cic.getPartitionName(), "_CLUSTER_SERVICE_", cic.getApplicationVersion(), cic.getModuleName(), cic.getComponentName());
      }

      try {
         ManagedInvocationContext mic = mgr.setCurrentComponentInvocationContext(cic);
         Throwable var11 = null;

         try {
            ClusterLogger.logFetchClusterStateDump(clusterMember.serverName());
            url = this.getServerURL(clusterMember, queryString);
            con = URLManager.createAdminHttpConnection(url, true);
            ConnectionSigner.signConnection(con, kernelId, clusterMember.serverName());
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            OutputStream out = con.getOutputStream();
            con.connect();
            out.write(this.getSecureHash());
            out.flush();
            out.close();
            in = new DataInputStream(con.getInputStream());
            if (con.getResponseCode() != 200) {
               throw new IOException("Unexpected error on the server didn''t receive response code 200");
            }

            if (ClusterAnnouncementsDebugLogger.isDebugEnabled()) {
               ClusterAnnouncementsDebugLogger.debug("ClusterService.getClusterJNDIStateDump() Request for Clustered JNDI StateDump for senderID: " + sessionId + " using " + url + " CONTENT LENGTH " + con.getContentLength());
            }

            byte[] b = this.readHttpResponse(in, con.getContentLength());
            ois = ClusterMessagesManager.getInputStream(b);
            int stateDumpCount = ois.readInt();

            for(int i = 0; i < stateDumpCount; ++i) {
               MemberAttributes attributes = (MemberAttributes)ois.readObject();
               StateDumpMessage sd = (StateDumpMessage)ois.readObject();
               if (sd != null) {
                  this.processAttributes(attributes);
                  PartitionAwareSenderManager.theOne().findOrCreateAnnouncementManager(sessionId).receiveStateDump(attributes.identity(), sd);
               }
            }

            MemberAttributes attributes = (MemberAttributes)ois.readObject();
            this.processAttributes(attributes);
            StateDumpMessage sd = (StateDumpMessage)ois.readObject();
            PartitionAwareSenderManager.theOne().findOrCreateAnnouncementManager(sessionId).receiveStateDump(attributes.identity(), sd);
            ClusterLogger.logFetchClusterStateDumpComplete(clusterMember.serverName());
         } catch (Throwable var51) {
            var11 = var51;
            throw var51;
         } finally {
            if (mic != null) {
               if (var11 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var50) {
                     var11.addSuppressed(var50);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } catch (MalformedURLException var53) {
         throw new AssertionError("Unexpected exception", var53);
      } catch (IOException var54) {
         ClusterHelper.logStateDumpRequestRejection(con, var54, clusterMember.serverName());
      } catch (ClassNotFoundException var55) {
         ClusterLogger.logFailedToDeserializeStateDump(clusterMember.serverName(), var55);
      } finally {
         try {
            if (in != null) {
               in.close();
            }
         } catch (IOException var49) {
         }

         try {
            if (ois != null) {
               ois.close();
            }
         } catch (IOException var48) {
         }

         if (con != null) {
            con.disconnect();
         }

      }

   }

   private URL getServerURL(ClusterMemberInfo clusterMember, String queryString) throws MalformedURLException {
      return ClusterHelper.fabricateHTTPURL(queryString, clusterMember.identity());
   }

   private byte[] readHttpResponse(DataInputStream is, int contentLength) throws IOException, ProtocolException {
      byte[] b = new byte[contentLength];
      DataIO.readFully(is, b);
      return b;
   }

   private void processAttributes(MemberAttributes attr) {
      RemoteMemberInfo memberInfo = MemberManager.theOne().findOrCreate(attr.identity());

      try {
         memberInfo.processAttributes(attr);
      } finally {
         MemberManager.theOne().done(memberInfo);
      }

   }

   private static final class SecurityHolder {
      private boolean initialized;
      private EncryptionService es;
      private byte[] SALT;
      private byte[] SECRET;
      private byte[] SECRET_HASH;

      private SecurityHolder() {
         this.initialized = false;
         this.es = null;
         this.SALT = null;
         this.SECRET = null;
         this.SECRET_HASH = null;
      }

      private EncryptionService getEncryptionService() {
         return this.es;
      }

      private byte[] getSecretHash() {
         return this.SECRET_HASH;
      }

      private byte[] getSecret() {
         return this.SECRET;
      }

      private byte[] getSalt() {
         return this.SALT;
      }

      private synchronized void init() {
         if (!this.initialized) {
            this.es = SerializedSystemIni.getEncryptionService();
            this.SALT = SerializedSystemIni.getSalt();
            this.SECRET = SerializedSystemIni.getEncryptedSecretKey();
            this.SECRET_HASH = HMAC.digest(this.es.encryptString(ManagementService.getRuntimeAccess(ClusterService.kernelId).getServer().getName()), this.SECRET, this.SALT);
            this.initialized = true;
         }
      }

      // $FF: synthetic method
      SecurityHolder(Object x0) {
         this();
      }
   }
}
