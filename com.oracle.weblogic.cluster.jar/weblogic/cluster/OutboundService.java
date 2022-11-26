package weblogic.cluster;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(20)
public final class OutboundService extends AbstractServerService {
   @Inject
   @Named("EnableListenersService")
   private ServerService dependencyOnEnableListenersService;
   @Inject
   private RuntimeAccess runtimeAccess;
   private ClusterMBean clusterMBean;
   private boolean serverVisibleToCluster;

   public void start() throws ServiceFailureException {
      this.clusterMBean = this.runtimeAccess.getServer().getCluster();
      if (this.clusterMBean != null) {
         try {
            ClusterMessagesManager.theOne().resumeNonAdminMulticastSessions();
            AttributeManager.theOne().sendAttributes();
         } catch (IOException var2) {
            throw new ServiceFailureException("Unexpected exception sending attributes", var2);
         }

         this.sendServerRuntimeState();
         PartitionAwareSenderManager.theOne().unblockAllAnnouncements();
         if ("multicast".equals(this.clusterMBean.getClusterMessagingMode())) {
            ClusterLogger.logJoinedCluster(this.clusterMBean.getName(), this.clusterMBean.getMulticastAddress(), this.clusterMBean.getMulticastPort() + "");
         }

         this.serverVisibleToCluster = true;
      }

   }

   public void stop() throws ServiceFailureException {
      this.shutdownInternal(false);
   }

   public synchronized void halt() throws ServiceFailureException {
      this.shutdownInternal(true);
   }

   public synchronized void shutdownInternal(boolean force) throws ServiceFailureException {
      if (this.clusterMBean != null && this.serverVisibleToCluster) {
         ClusterLogger.logOutboundClusterServiceStopped();
         if (force) {
            WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
               public void run() {
                  if (OutboundService.this.isShuttingDown()) {
                     ClusterMessagesManager.theOne().stopHeartbeat();
                  } else {
                     try {
                        ClusterMessagesManager.theOne().suspendNonAdminMulticastSessions();
                        OutboundService.this.sendServerRuntimeState();
                     } catch (ServiceFailureException var2) {
                     }
                  }

               }
            });
         } else if (this.isShuttingDown()) {
            ClusterMessagesManager.theOne().stopHeartbeat();
         } else {
            ClusterMessagesManager.theOne().suspendNonAdminMulticastSessions();
            this.sendServerRuntimeState();
         }

         PartitionAwareSenderManager.theOne().blockAllAnnouncements();
         this.serverVisibleToCluster = false;
      }

   }

   private void sendServerRuntimeState() throws ServiceFailureException {
      if (this.clusterMBean != null) {
         try {
            MemberManager.theOne().sendMemberRuntimeState();
         } catch (IOException var2) {
            throw new ServiceFailureException("Unexpected exception sending  runtime state: ", var2);
         }
      }

   }

   private boolean isShuttingDown() {
      int state = this.runtimeAccess.getServerRuntime().getStableState();
      return state == 9;
   }
}
