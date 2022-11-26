package weblogic.cluster;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeoutException;
import weblogic.cluster.replication.MigrationInProgressException;
import weblogic.cluster.replication.ReplicationServices;
import weblogic.cluster.replication.ResourceGroupMigrationManagerImpl;
import weblogic.cluster.replication.SecondarySelector;
import weblogic.cluster.replication.ReplicationServicesFactory.Locator;
import weblogic.cluster.replication.ReplicationServicesFactory.ServiceType;
import weblogic.cluster.replication.SecondarySelector.Locator.SelectorPolicy;
import weblogic.cluster.singleton.MigratableServerService;
import weblogic.cluster.singleton.ServerMigrationRuntimeMBeanImpl;
import weblogic.cluster.singleton.SingletonServicesManagerService;
import weblogic.health.HealthState;
import weblogic.management.ManagementException;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.runtime.JobSchedulerRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerMigrationRuntimeMBean;
import weblogic.management.runtime.UnicastMessagingRuntimeMBean;
import weblogic.management.utils.RuntimeGeneratorService;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.AssertionError;
import weblogic.utils.LocatorUtilities;

public final class ClusterRuntime extends RuntimeMBeanDelegate implements ClusterRuntimeMBean, ClusterMembersChangeListener {
   private static final long serialVersionUID = 7321104020611342137L;
   private static int invocationCounter = 0;
   private String clusterType;
   private String[] currentServerNames;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static void initialize(String clusterName) {
      try {
         ClusterRuntime cr = new ClusterRuntime(clusterName);
         cr.clusterType = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getClusterType();
      } catch (ManagementException var2) {
         ClusterLogger.logErrorCreatingClusterRuntime(var2);
      }

   }

   public ClusterRuntime() throws ManagementException {
      throw new AssertionError("for JMX compliance only");
   }

   private ClusterRuntime(String name) throws ManagementException {
      super(name);
      ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setClusterRuntime(this);
      ClusterService.getClusterServiceInternal().addClusterMembersListener(this);
   }

   public int getAliveServerCount() {
      return ClusterService.getServices().getRemoteMembers().size() + 1;
   }

   public long getResendRequestsCount() {
      return ClusterMessagesManager.theOne().getResendRequestsCount();
   }

   public long getFragmentsSentCount() {
      return ClusterMessagesManager.theOne().getFragmentsSentCount();
   }

   public long getFragmentsReceivedCount() {
      return ClusterMessagesManager.theOne().getFragmentsReceivedCount();
   }

   public long getMulticastMessagesLostCount() {
      return ClusterMessagesManager.theOne().getMulticastMessagesLostCount();
   }

   public String[] getServerNames() {
      synchronized(this) {
         if (this.currentServerNames == null) {
            this.currentServerNames = this.calculateServerNames();
         }
      }

      return this.currentServerNames;
   }

   private String[] calculateServerNames() {
      Collection members = ClusterService.getServices().getRemoteMembers();
      String[] names = new String[members.size() + 1];
      Iterator memIter = members.iterator();

      int i;
      ClusterMemberInfo member;
      for(i = 0; memIter.hasNext(); names[i++] = member.serverName()) {
         member = (ClusterMemberInfo)memIter.next();
      }

      names[i] = ClusterService.getServices().getLocalMember().serverName();
      return names;
   }

   public String[] getSecondaryDistributionNames() {
      return this.getReplicationServicesByClusterType().getSecondaryDistributionNames();
   }

   public String[] getDetailedSecondariesDistribution() {
      return this.getSecondaryDistributionNames();
   }

   public long getPrimaryCount() {
      return this.getReplicationServicesByClusterType().getPrimaryCount();
   }

   public long getSecondaryCount() {
      ReplicationServices services = Locator.locate().getReplicationService(ServiceType.SYNC);
      return services.getSecondaryCount();
   }

   public long getForeignFragmentsDroppedCount() {
      return ClusterMessagesManager.theOne().getForeignFragmentsDroppedCount();
   }

   public String getCurrentSecondaryServer() {
      SecondarySelector.Locator.SelectorPolicy policy;
      if (this.clusterType.equals("man")) {
         policy = SelectorPolicy.MAN;
      } else {
         policy = SelectorPolicy.LOCAL;
      }

      HostID srvr = weblogic.cluster.replication.SecondarySelector.Locator.locate(policy).getSecondarySrvr();
      return srvr != null ? srvr.toString() : "";
   }

   public String getSecondaryServerDetails() {
      return this.getCurrentSecondaryServer();
   }

   public HashMap getUnreliableServers() {
      return ClusterDropoutListener.theOne().getDropoutCounts();
   }

   public HealthState getHealthState() {
      return new HealthState(0);
   }

   public MachineMBean getCurrentMachine() {
      return MigratableServerService.theOne().getCurrentMachine();
   }

   public ServerMigrationRuntimeMBean getServerMigrationRuntime() {
      return ServerMigrationRuntimeMBeanImpl.getInstance();
   }

   public JobSchedulerRuntimeMBean getJobSchedulerRuntime() {
      RuntimeGeneratorService generator = (RuntimeGeneratorService)LocatorUtilities.getService(RuntimeGeneratorService.class);
      return generator.createJobSchedulerRuntimeMBean();
   }

   public UnicastMessagingRuntimeMBean getUnicastMessaging() {
      try {
         Class c = Class.forName("weblogic.cluster.messaging.internal.server.UnicastMessagingRuntimeMBeanImpl");
         Method m = c.getMethod("getInstance");
         m.setAccessible(true);
         return (UnicastMessagingRuntimeMBean)m.invoke((Object)null);
      } catch (Exception var3) {
         return null;
      }
   }

   public String[] getActiveSingletonServices() {
      return SingletonServicesManagerService.getInstance().getActiveServiceNames();
   }

   public void clusterMembersChanged(ClusterMembersChangeEvent cece) {
      String[] oldServerNames;
      synchronized(this) {
         oldServerNames = this.currentServerNames;
         this.currentServerNames = this.calculateServerNames();
      }

      this._postSet("ServerNames", oldServerNames, this.currentServerNames);
   }

   public void initiateResourceGroupMigration(String partitionName, String resourceGroupName, String targetCluster, int timeout) throws TimeoutException, IllegalStateException {
      try {
         ResourceGroupMigrationManagerImpl.theOne().initiateResourceGroupMigration(partitionName, resourceGroupName, targetCluster, timeout);
      } catch (MigrationInProgressException var6) {
         throw new IllegalStateException(var6);
      }
   }

   private ReplicationServices getReplicationServicesByClusterType() {
      return this.clusterType.equals("man") ? Locator.locate().getReplicationService(ServiceType.MAN_SYNC) : Locator.locate().getReplicationService(ServiceType.SYNC);
   }
}
