package weblogic.cluster.messaging.protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import weblogic.cluster.messaging.internal.Connection;
import weblogic.cluster.messaging.internal.Environment;
import weblogic.protocol.ServerChannel;
import weblogic.security.utils.SSLIOContext;
import weblogic.security.utils.SSLIOContextTable;
import weblogic.socket.JSSESocket;
import weblogic.socket.SSLFilter;
import weblogic.socket.utils.JSSEUtils;
import weblogic.utils.io.Chunk;

public final class MuxableSocketClusterBroadcastS extends MuxableSocketClusterBroadcast {
   private static final int PROTOCOL_LENGTH = "CLUSTER-BROADCAST-SECURE".length();

   public MuxableSocketClusterBroadcastS(Chunk headChunk, Socket socket, ServerChannel nc) throws IOException {
      super(headChunk, socket, nc);
   }

   public MuxableSocketClusterBroadcastS(InetAddress address, int port, ServerChannel channel, Connection connection) throws IOException {
      super(address, port, channel, connection);
   }

   public static MuxableSocketClusterBroadcast createConnection(InetAddress address, int port, Connection connection, ServerChannel channel) throws IOException {
      MuxableSocketClusterBroadcastS muxableSocket = new MuxableSocketClusterBroadcastS(address, port, channel, connection);
      SSLSocket sslSocket = (SSLSocket)muxableSocket.getSocket();
      JSSESocket jsseSock = JSSEUtils.getJSSESocket(sslSocket);
      if (jsseSock != null) {
         JSSEUtils.registerJSSEFilter(jsseSock, muxableSocket);
         JSSEUtils.activate(jsseSock, muxableSocket);
      } else {
         SSLIOContext sslIOCtx = SSLIOContextTable.findContext(sslSocket);
         SSLFilter sslf = (SSLFilter)sslIOCtx.getFilter();
         muxableSocket.setSocketFilter(sslf);

         try {
            sslSocket.startHandshake();
         } catch (SSLException var12) {
            if (!sslSocket.isClosed()) {
               try {
                  sslSocket.close();
               } catch (IOException var11) {
               }
            }

            throw var12;
         }

         sslf.setDelegate(muxableSocket);
         if (DEBUG) {
            muxableSocket.debug("SSL socket initialized!");
         }

         sslf.activate();
      }

      return muxableSocket;
   }

   protected int getProtocolLength() {
      return PROTOCOL_LENGTH;
   }

   protected void debug(String s) {
      Environment.getLogService().debug("[UnicastMuxableSocketSecure] " + s);
   }
}
