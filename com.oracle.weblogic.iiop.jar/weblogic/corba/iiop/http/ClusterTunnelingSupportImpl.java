package weblogic.corba.iiop.http;

import java.security.AccessController;
import java.util.Collection;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ClusterTunnelingSupportImpl extends ClusterTunnelingSupport {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ClusterServices clusterServices;
   private ClusterMBean clusterMBean;

   public boolean isClusterEnabled() {
      return this.getClusterServices() != null && this.getClusterMBean() != null;
   }

   private ClusterServices getClusterServices() {
      if (this.clusterServices == null) {
         this.clusterServices = Locator.locateClusterServices();
      }

      return this.clusterServices;
   }

   private ClusterMBean getClusterMBean() {
      if (this.clusterMBean == null) {
         this.clusterMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      }

      return this.clusterMBean;
   }

   public Collection getRemoteMemberInfos() {
      return this.getClusterServices().getRemoteMembers();
   }

   public ClusterMemberInfo getLocalMemberInfo() {
      return this.getClusterServices().getLocalMember();
   }
}
