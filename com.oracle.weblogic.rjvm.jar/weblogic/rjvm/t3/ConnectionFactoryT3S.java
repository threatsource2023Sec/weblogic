package weblogic.rjvm.t3;

import java.io.IOException;
import java.net.InetAddress;
import javax.net.ssl.SSLSocket;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.JVMID;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.RJVMConnectionFactory;
import weblogic.security.utils.SSLIOContext;
import weblogic.security.utils.SSLIOContextTable;
import weblogic.socket.ClientSSLFilterImpl;
import weblogic.socket.JSSESocket;
import weblogic.socket.MuxableSocket;
import weblogic.socket.SSLFilter;
import weblogic.socket.SocketMuxer;
import weblogic.socket.utils.JSSEUtils;

public class ConnectionFactoryT3S implements RJVMConnectionFactory {
   public MsgAbbrevJVMConnection createConnection(String host, InetAddress address, int port, ServerChannel networkChannel, JVMID destinationJVMID, int connectionTimeout) throws IOException {
      return this.createConnection(host, address, port, networkChannel, destinationJVMID, connectionTimeout, "t3s://" + address.getHostName() + ':' + port);
   }

   public MsgAbbrevJVMConnection createConnection(String host, InetAddress address, int port, ServerChannel networkChannel, JVMID destinationJVMID, int connectionTimeout, String partitionUrl) throws IOException {
      MuxableSocketT3S muxableSocket = MuxableSocketT3S.createMuxableSocket(address, port, networkChannel, destinationJVMID, connectionTimeout, partitionUrl);
      this.setupSSLFilter(muxableSocket);
      return muxableSocket.getConnection();
   }

   private void setupSSLFilter(MuxableSocketT3S muxableSocket) throws IOException {
      SSLSocket sslSocket = (SSLSocket)muxableSocket.getSocket();
      JSSESocket jsseSock = JSSEUtils.getJSSESocket(sslSocket);
      if (jsseSock != null) {
         JSSEUtils.registerJSSEFilter(jsseSock, muxableSocket);
         if (muxableSocket.isMessageComplete()) {
            muxableSocket.dispatch();
         } else {
            SocketMuxer.getMuxer().read(jsseSock.getFilter());
         }
      } else {
         SSLIOContext sslIOCtx = SSLIOContextTable.findContext(sslSocket);
         SSLFilter sslf = null;
         if (sslIOCtx == null) {
            sslf = new ClientSSLFilterImpl(sslSocket.getInputStream(), sslSocket);
         } else {
            sslf = (SSLFilter)sslIOCtx.getFilter();
         }

         muxableSocket.setSocketFilter((MuxableSocket)sslf);
         ((SSLFilter)sslf).setDelegate(muxableSocket);
         ((SSLFilter)sslf).activate();
      }

   }
}
