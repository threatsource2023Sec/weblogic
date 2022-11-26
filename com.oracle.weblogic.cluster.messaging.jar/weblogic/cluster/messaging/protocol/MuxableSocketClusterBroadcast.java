package weblogic.cluster.messaging.protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.AccessController;
import weblogic.cluster.messaging.internal.Connection;
import weblogic.cluster.messaging.internal.ConnectionImpl;
import weblogic.cluster.messaging.internal.Environment;
import weblogic.cluster.messaging.internal.server.ConnectionManagerImpl;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ServerChannel;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.socket.AbstractMuxableSocket;
import weblogic.socket.SocketMuxer;
import weblogic.socket.WeblogicSocket;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedInputStream;

public class MuxableSocketClusterBroadcast extends AbstractMuxableSocket {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final int DEFAULT_CONNECT_TIMEOUT = 10000;
   protected static final boolean DEBUG;
   private static final int PROTOCOL_LENGTH = 17;
   private static final int MESSAGE_LENGTH_SIZE = 4;
   private Connection connection;
   private static boolean enabled;

   MuxableSocketClusterBroadcast(Chunk headChunk, Socket socket, ServerChannel nc) throws IOException {
      super(headChunk, socket, nc);
      this.ensureEnabled(this.getSocket().getLocalAddress(), this.getSocket().getPort());
      UnicastSocket us = new UnicastSocket(this.getSocket(), this);
      this.connection = ConnectionManagerImpl.getInstance().createConnection((WeblogicSocket)us);
      if (DEBUG) {
         this.debug("created connection " + this.connection);
      }

   }

   MuxableSocketClusterBroadcast(InetAddress address, int port, ServerChannel channel, Connection connection) throws IOException {
      super(channel);
      if (channel != null && channel.getConnectTimeout() != 0) {
         this.connect(address, port);
      } else {
         this.connect(address, port, 10000);
      }

      this.ensureEnabled(this.getSocket().getLocalAddress(), this.getSocket().getPort());
      UnicastSocket us = new UnicastSocket(this.getSocket(), this);
      ((ConnectionImpl)connection).setSocket(us);
      this.connection = connection;
      ((ConnectionImpl)connection).bootStrapConnection();
   }

   public static MuxableSocketClusterBroadcast createConnection(InetAddress address, int port, Connection connection, ServerChannel channel) throws IOException {
      MuxableSocketClusterBroadcast muxableSocket = new MuxableSocketClusterBroadcast(address, port, channel, connection);
      SocketMuxer.getMuxer().register(muxableSocket);
      SocketMuxer.getMuxer().read(muxableSocket);
      return muxableSocket;
   }

   protected int getHeaderLength() {
      return this.getProtocolLength() + 4;
   }

   protected int getMessageLength() {
      int offset = this.getProtocolLength();
      int r0 = this.getHeaderByte(offset) & 255;
      if (DEBUG) {
         this.debug("r0=" + r0);
      }

      int r1 = this.getHeaderByte(offset + 1) & 255;
      if (DEBUG) {
         this.debug("r1=" + r1);
      }

      int r2 = this.getHeaderByte(offset + 2) & 255;
      if (DEBUG) {
         this.debug("r2=" + r2);
      }

      int r3 = this.getHeaderByte(offset + 3) & 255;
      if (DEBUG) {
         this.debug("r3=" + r3);
      }

      if (DEBUG) {
         this.debug("length=" + (r0 << 24 | r1 << 16 | r2 << 8 | r3));
      }

      return r0 << 24 | r1 << 16 | r2 << 8 | r3;
   }

   protected int getProtocolLength() {
      return 17;
   }

   public void dispatch(Chunk chunks) {
      try {
         if (DEBUG) {
            this.debug("dispatching with data [" + chunks + "]");
         }

         if (!this.connection.isBootStrapped()) {
            this.connection.handleBootStrapMessage(new ChunkedInputStream(chunks, this.getHeaderLength()));
         } else {
            this.connection.handleIncomingMessage(new ChunkedInputStream(chunks, this.getHeaderLength()));
         }
      } catch (IOException var3) {
         SocketMuxer.getMuxer().deliverHasException(this.getSocketFilter(), var3);
      }

   }

   protected void debug(String s) {
      Environment.getLogService().debug("[UnicastMuxableSocket] " + s);
   }

   public void hasException(Throwable t) {
      this.connection.close();
      this.close();
   }

   public void endOfStream() {
      this.connection.close();
      this.close();
   }

   public boolean timeout() {
      this.connection.close();
      this.close();
      return true;
   }

   public int getIdleTimeoutMillis() {
      return Environment.getPropertyService().getHeartbeatTimeoutMillis();
   }

   public static boolean isEnabled() {
      ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
      if (server != null && server.getCluster() != null) {
         return "unicast".equals(server.getCluster().getClusterMessagingMode()) && Environment.isInitialized();
      } else {
         return false;
      }
   }

   private void ensureEnabled(InetAddress address, int port) throws IOException {
      if (!enabled) {
         Socket s = this.getSocket();
         if (s != null) {
            s.close();
         }

         throw new IOException("An attempt to connect via CLUSTER-BROADCAST to: '" + address + "', on port: '" + port + "' was rejected because CLUSTER-BROADCAST is not enabled.");
      }
   }

   static {
      DEBUG = Environment.DEBUG;
      enabled = true;
   }

   public class UnicastSocket extends WeblogicSocket {
      private MuxableSocketClusterBroadcast ms;

      UnicastSocket(Socket s, MuxableSocketClusterBroadcast msl) {
         super(s);
         this.ms = msl;
      }

      public final void close() throws IOException {
         if (this.ms.getSocketInfo() == null && this.ms.getSocketFilter().getSocketInfo() == null) {
            super.close();
            this.ms.endOfStream();
         } else {
            SocketMuxer.getMuxer().deliverEndOfStream(this.ms);
         }

      }
   }
}
