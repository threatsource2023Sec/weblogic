package weblogic.rjvm.t3.client;

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
import weblogic.socket.SSLFilter;

public class ConnectionFactoryT3S implements RJVMConnectionFactory {
   public MsgAbbrevJVMConnection createConnection(InetAddress address, int port, ServerChannel networkChannel, JVMID destinationJVMID, int connectionTimeout) throws IOException {
      return this.createConnection(address.getCanonicalHostName(), address, port, networkChannel, destinationJVMID, connectionTimeout, "t3://" + address.getHostName() + ':' + port);
   }

   public MsgAbbrevJVMConnection createConnection(String host, InetAddress address, int port, ServerChannel networkChannel, JVMID destinationJVMID, int connectionTimeout) throws IOException {
      return this.createConnection(host, address, port, networkChannel, destinationJVMID, connectionTimeout, "t3://" + address.getHostName() + ':' + port);
   }

   public MsgAbbrevJVMConnection createConnection(InetAddress address, int port, ServerChannel networkChannel, JVMID destinationJVMID, int connectionTimeout, String partitionUrl) throws IOException {
      return this.createConnection(address.getCanonicalHostName(), address, port, networkChannel, destinationJVMID, connectionTimeout, partitionUrl);
   }

   public MsgAbbrevJVMConnection createConnection(String host, InetAddress address, int port, ServerChannel networkChannel, JVMID destinationJVMID, int connectionTimeout, String partitionUrl) throws IOException {
      MuxableSocketT3S connection = new MuxableSocketT3S(networkChannel, partitionUrl);
      return this.getMsgAbbrevJVMConnection(address, port, networkChannel, connectionTimeout, connection);
   }

   private MsgAbbrevJVMConnection getMsgAbbrevJVMConnection(InetAddress address, int port, ServerChannel networkChannel, int connectionTimeout, MuxableSocketT3S connection) throws IOException {
      int timeout = connectionTimeout > 0 ? connectionTimeout : networkChannel.getConnectTimeout() * 1000;
      connection.connect(address, port, timeout);
      SSLSocket sslSocket = (SSLSocket)connection.getSocket();
      SSLFilter sslf = new ClientSSLFilterImpl(sslSocket.getInputStream(), sslSocket);
      SSLIOContext context = new SSLIOContext(sslSocket.getInputStream(), sslSocket.getOutputStream(), sslSocket, sslf);
      SSLIOContextTable.addContext(context);
      SSLIOContext sslIOCtx = SSLIOContextTable.findContext(sslSocket);
      connection.setSocketFilter(sslf);
      sslf.setDelegate(connection);
      sslf.activate();
      return connection.getConnection();
   }
}
