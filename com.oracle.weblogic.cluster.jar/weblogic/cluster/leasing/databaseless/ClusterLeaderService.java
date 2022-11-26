package weblogic.cluster.leasing.databaseless;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.MemberManager;
import weblogic.cluster.messaging.internal.ConsensusLeasingDebugLogger;
import weblogic.cluster.messaging.internal.RMIClusterMessageEndPointImpl;
import weblogic.cluster.messaging.internal.ServerInformation;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.t3.srvr.EnableListenersIfAdminChannelAbsentService;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

@Service
@Named
@RunLevel(15)
public final class ClusterLeaderService extends AbstractServerService {
   @Inject
   @Named("InboundService")
   private ServerService dependencyOnInboundService;
   private static final DebugCategory debugClusterLeaderService = Debug.getCategory("weblogic.cluster.leasing.ClusterLeaderService");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ClusterLeaderService THE_ONE = null;
   private boolean started = false;

   public ClusterLeaderService() {
      Class var1 = ClusterLeaderService.class;
      synchronized(ClusterLeaderService.class) {
         assert THE_ONE == null;

         THE_ONE = this;
      }
   }

   public static ClusterLeaderService getInstance() {
      assert THE_ONE != null;

      return THE_ONE;
   }

   public String getLeaderName() {
      ServerInformation info = this.getLeaderInformation();
      return info != null ? info.getServerName() : null;
   }

   public void start() throws ServiceFailureException {
      if (!this.started) {
         PrimordialClusterLeaderService.getInstance().stop();
         ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
         if (clusterMBean != null) {
            if ("consensus".equalsIgnoreCase(clusterMBean.getMigrationBasis())) {
               if (debugEnabled()) {
                  debug("--- CONSENSUS LEASING IS TURNED ON ---");
               }

               ensureServersHaveMachines(clusterMBean);
               if (!Boolean.getBoolean("weblogic.nodemanager.ServiceEnabled")) {
                  DatabaseLessLeasingLogger.logServerNotStartedByNodeManager();
                  throw new ServiceFailureException("Server must be started by NodeManager when consensus leasing is enabled");
               } else {
                  try {
                     ServerHelper.exportObject(RMIClusterMessageEndPointImpl.getInstance());
                  } catch (RemoteException var3) {
                     throw new ServiceFailureException(var3);
                  }

                  try {
                     if (!EnableListenersIfAdminChannelAbsentService.startInRunningState()) {
                        MemberManager.theOne().sendMemberRuntimeState();
                     }
                  } catch (IOException var4) {
                     throw new ServiceFailureException("Failed to send runtime state message", var4);
                  }

                  int discoveryTimeout = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getDatabaseLessLeasingBasis().getMemberDiscoveryTimeout();
                  if (debugEnabled()) {
                     debug("Initialize EnvironmentFoctory and start the discovery timer");
                  }

                  EnvironmentFactory.initialize();
                  EnvironmentFactory.getDiscoveryService().start(discoveryTimeout);
                  this.started = true;
               }
            }
         }
      }
   }

   static void ensureServersHaveMachines(ClusterMBean cluster) throws ServiceFailureException {
      ServerMBean[] servers = cluster.getServers();

      for(int count = 0; count < servers.length; ++count) {
         if (servers[count].getMachine() == null) {
            throw new ServiceFailureException("server " + servers[count] + " is not associated with a machine");
         }
      }

   }

   private ServerInformation getLeaderInformation() {
      ServerInformation info = EnvironmentFactory.getClusterMember().getLeaderInformation();
      return info != null ? info : EnvironmentFactory.getClusterLeader().getLeaderInformation();
   }

   static ServerInformation getLeader() {
      return THE_ONE != null && THE_ONE.getLeaderInformation() != null ? THE_ONE.getLeaderInformation() : PrimordialClusterLeaderService.getInstance().getLeaderInformation();
   }

   ServerInformation getLocalServerInformation() {
      return ClusterMember.getInstance().getLocalServerInformation();
   }

   private static boolean debugEnabled() {
      return debugClusterLeaderService.isEnabled() || ConsensusLeasingDebugLogger.isDebugEnabled();
   }

   private static void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[ClusterLeaderService] " + s);
   }
}
