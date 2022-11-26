package weblogic.cluster.replication;

import java.security.AccessController;
import javax.inject.Singleton;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterService;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.URLManager;
import weblogic.security.SSL.SSLClientInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.SSLClientInfoService;
import weblogic.security.acl.internal.Security;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.utils.SSLContextManager;

@Service
@Singleton
public class ReplicationServiceLocator {
   private boolean isReplicationSecured = false;
   private String replicationChannel;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String REPLICATION_SECURED_PROP = "weblogic.replication.secured";

   public ReplicationServiceLocator() {
      ClusterMBean cluster = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      if (cluster != null) {
         this.isReplicationSecured = cluster.isSecureReplicationEnabled();
         this.replicationChannel = cluster.getReplicationChannel();
      }

      if (System.getProperty("weblogic.replication.secured") != null) {
         this.isReplicationSecured = Boolean.getBoolean("weblogic.replication.secured");
      }

   }

   ReplicationServicesInternal replicationServicesLookup(ServerIdentity hostID, Class clazz) throws NamingException {
      return this.replicationServicesLookup(hostID, this.replicationChannel, clazz);
   }

   ReplicationServicesInternal replicationServicesLookup(ServerIdentity hostID, String replicationChannel, Class clazz) throws NamingException {
      int timeout = -1;
      if (ClusterService.getClusterServiceInternal().isReplicationTimeoutEnabled()) {
         timeout = ClusterService.getClusterServiceInternal().getHeartbeatTimeoutMillis();
      }

      return this.getReplicationServiceInternal(hostID, replicationChannel, clazz, timeout);
   }

   private ServerChannel getOutBoundChannel(String channelName, boolean isdefaultSecuredChannelPreferred) {
      ServerChannel channel = ServerChannelManager.findOutboundServerChannel(channelName);
      if (channel == null) {
         Protocol preferredProtocol = ProtocolManager.getDefaultProtocol();
         Protocol fallbackProtocol = ProtocolManager.getDefaultSecureProtocol();
         if (isdefaultSecuredChannelPreferred) {
            preferredProtocol = ProtocolManager.getDefaultSecureProtocol();
            fallbackProtocol = ProtocolManager.getDefaultProtocol();
         }

         channel = ServerChannelManager.findLocalServerChannel(preferredProtocol);
         if (channel == null) {
            channel = ServerChannelManager.findLocalServerChannel(fallbackProtocol);
         }
      }

      return channel;
   }

   private SSLClientInfo getOutChannelSSLClientInfo(ServerChannel outChannel) {
      SSLClientInfo outChannelClientInfo = null;
      if (outChannel != null) {
         try {
            outChannelClientInfo = SSLContextManager.getChannelSSLClientInfo(outChannel, kernelId);
         } catch (Exception var4) {
            if (ReplicationDebugLogger.isDebugEnabled()) {
               ReplicationDebugLogger.debug((String)("Failed to load Channel Certificates. Exception: " + var4), (Throwable)var4);
            }
         }
      }

      return outChannelClientInfo;
   }

   private ReplicationServicesInternal getReplicationServiceInternal(ServerIdentity hostID, String replicationChannel, Class replicationServiceClass, int timeout) throws NamingException {
      SSLClientInfoService initialSSLInfo = null;
      SSLClientInfo outChannelClientInfo = null;
      boolean securedChannel = false;

      ReplicationServicesInternal var10;
      try {
         String url = URLManager.findURL(hostID, replicationChannel, this.isReplicationSecured);
         ServerChannel outChannel = this.getOutBoundChannel(replicationChannel, this.isReplicationSecured);
         if (outChannel != null) {
            securedChannel = outChannel.getProtocol().isSecure();
            if (securedChannel) {
               initialSSLInfo = Security.getThreadSSLClientInfo();
               outChannelClientInfo = this.getOutChannelSSLClientInfo(outChannel);
               if (outChannelClientInfo != null) {
                  Security.setThreadSSLClientInfo(outChannelClientInfo);
               }
            }

            var10 = SecureReplicationInvocationHandler.lookupService(url, outChannel.getChannelName(), timeout, replicationServiceClass, securedChannel);
            return var10;
         }

         var10 = null;
      } finally {
         if (securedChannel && outChannelClientInfo != null) {
            Security.setThreadSSLClientInfo(initialSSLInfo);
         }

      }

      return var10;
   }
}
