package weblogic.cluster.messaging.internal.server;

import java.io.IOException;
import java.security.AccessController;
import javax.net.ssl.SSLSocket;
import weblogic.cluster.messaging.internal.AbstractConnectionManager;
import weblogic.cluster.messaging.internal.Connection;
import weblogic.cluster.messaging.internal.ConnectionImpl;
import weblogic.cluster.messaging.internal.Environment;
import weblogic.cluster.messaging.internal.ServerConfigurationInformation;
import weblogic.cluster.messaging.protocol.MuxableSocketClusterBroadcast;
import weblogic.cluster.messaging.protocol.MuxableSocketClusterBroadcastS;
import weblogic.cluster.messaging.protocol.ProtocolHandlerClusterBroadcast;
import weblogic.cluster.messaging.protocol.ProtocolHandlerClusterBroadcastS;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.socket.WeblogicSocket;

public class ConnectionManagerImpl extends AbstractConnectionManager {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean DEBUG;
   private static int unicastReadTimeout;

   public static ConnectionManagerImpl getInstance() {
      return ConnectionManagerImpl.Factory.THE_ONE;
   }

   private ConnectionManagerImpl() {
      unicastReadTimeout = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getUnicastReadTimeout();
   }

   public Connection createConnection(ServerConfigurationInformation info) throws IOException {
      ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
      String broadcastChannel = server.getCluster().getClusterBroadcastChannel();
      if (broadcastChannel != null && broadcastChannel.trim().length() == 0) {
         broadcastChannel = null;
      }

      ServerChannel channel;
      if (broadcastChannel != null) {
         channel = ServerChannelManager.findOutboundServerChannel(broadcastChannel);
      } else if (!info.isUsingSSL()) {
         channel = ServerChannelManager.findOutboundServerChannel(ProtocolHandlerClusterBroadcast.PROTOCOL_CLUSTER);
      } else {
         channel = ServerChannelManager.findOutboundServerChannel(ProtocolHandlerClusterBroadcastS.PROTOCOL_CLUSTER_SECURE);
      }

      if (DEBUG) {
         this.debug("trying to create connection using outbound channel " + channel);
      }

      if (channel == null) {
         throw new IOException("Channel not yet bound!");
      } else {
         Object connection;
         if (channel.supportsTLS()) {
            connection = new SSLConnectionImpl(info, unicastReadTimeout);
            MuxableSocketClusterBroadcastS.createConnection(info.getAddress(), info.getPort(), (Connection)connection, channel);
         } else {
            connection = new ConnectionImpl(info, unicastReadTimeout);
            MuxableSocketClusterBroadcast.createConnection(info.getAddress(), info.getPort(), (Connection)connection, channel);
         }

         if (DEBUG) {
            this.debug("BootStrapped connection to " + info.getServerName());
         }

         return (Connection)connection;
      }
   }

   private void debug(String s) {
      Environment.getLogService().debug("[ConnectionManager] " + s);
   }

   public Connection createConnection(WeblogicSocket socket) throws IOException {
      return (Connection)(socket.getSocket() instanceof SSLSocket ? new SSLConnectionImpl(socket, unicastReadTimeout) : new ConnectionImpl(socket, unicastReadTimeout));
   }

   // $FF: synthetic method
   ConnectionManagerImpl(Object x0) {
      this();
   }

   static {
      DEBUG = Environment.DEBUG;
   }

   private static final class Factory {
      static final ConnectionManagerImpl THE_ONE = new ConnectionManagerImpl();
   }
}
