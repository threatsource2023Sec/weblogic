package weblogic.cluster;

import java.io.IOException;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(15)
public class InboundService extends AbstractServerService implements InboundListener {
   @Inject
   @Named("PartitionService")
   private ServerService dependencyOnPartitionService;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static volatile boolean started;

   public void start() throws ServiceFailureException {
      startListening();
      ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      if (clusterMBean != null) {
         MemberManager.theOne().waitToSyncWithCurrentMembers();
         ClusterMessagesManager.theOne().resume();
      }

   }

   public static synchronized void startListening() throws ServiceFailureException {
      if (!started) {
         ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
         if (clusterMBean != null) {
            try {
               ClusterMessagesManager.theOne().startListening();
            } catch (IOException var2) {
               ClusterLogger.logFailedToJoinClusterError(clusterMBean.getName(), clusterMBean.getMulticastAddress(), var2);
               throw new ServiceFailureException("Failed to listen on multicast address", var2);
            }

            if ("unicast".equals(clusterMBean.getClusterMessagingMode())) {
               ClusterLogger.logUnicastEnabled();
            } else {
               ClusterLogger.logListeningToCluster(clusterMBean.getName(), clusterMBean.getMulticastAddress(), clusterMBean.getMulticastPort() + "");
            }

            ClusterMessagesManager.theOne().startHeartbeat();
            started = true;
         }

      }
   }

   public void stop() {
      this.shutdownInternal(false);
   }

   public synchronized void halt() {
      this.shutdownInternal(true);
   }

   private synchronized void shutdownInternal(boolean force) {
      if (started) {
         if (force) {
            WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
               public void run() {
                  ClusterMessagesManager.theOne().stopHeartbeat();
               }
            });
         } else {
            ClusterMessagesManager.theOne().stopHeartbeat();
         }
      }

   }

   public boolean isStarted() {
      return started;
   }
}
