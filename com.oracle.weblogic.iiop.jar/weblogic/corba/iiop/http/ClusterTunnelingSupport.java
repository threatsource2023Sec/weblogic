package weblogic.corba.iiop.http;

import java.util.Collection;
import weblogic.cluster.ClusterMemberInfo;

public abstract class ClusterTunnelingSupport {
   private static ClusterTunnelingSupport clusterTunnelingSupport = new ClusterTunnelingSupportImpl();

   public static void setClusterTunnelingSupport(ClusterTunnelingSupport clusterTunnelingSupport) {
      ClusterTunnelingSupport.clusterTunnelingSupport = clusterTunnelingSupport;
   }

   public static ClusterTunnelingSupport getClusterTunnelingSupport() {
      return clusterTunnelingSupport;
   }

   public abstract boolean isClusterEnabled();

   public abstract Collection getRemoteMemberInfos();

   public abstract ClusterMemberInfo getLocalMemberInfo();
}
