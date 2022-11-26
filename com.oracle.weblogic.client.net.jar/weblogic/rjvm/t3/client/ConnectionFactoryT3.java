package weblogic.rjvm.t3.client;

import java.io.IOException;
import java.net.InetAddress;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.JVMID;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.RJVMConnectionFactory;
import weblogic.socket.SocketMuxer;

public class ConnectionFactoryT3 implements RJVMConnectionFactory {
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
      MuxableSocketT3 connection = new MuxableSocketT3(networkChannel, partitionUrl);
      int timeout = connectionTimeout > 0 ? connectionTimeout : networkChannel.getConnectTimeout() * 1000;
      connection.connect(address, port, timeout);
      SocketMuxer.getMuxer().register(connection);
      SocketMuxer.getMuxer().read(connection);
      return connection.getConnection();
   }
}
