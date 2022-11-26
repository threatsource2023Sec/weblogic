package weblogic.rjvm.t3;

import java.io.IOException;
import java.net.InetAddress;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.JVMID;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.RJVMConnectionFactory;
import weblogic.socket.SocketMuxer;

public class ConnectionFactoryT3 implements RJVMConnectionFactory {
   public MsgAbbrevJVMConnection createConnection(String host, InetAddress address, int port, ServerChannel networkChannel, JVMID destinationJVMID, int connectionTimeout) throws IOException {
      MuxableSocketT3 muxableSocket = (MuxableSocketT3)MuxableSocketT3.createMuxableSocket(address, port, networkChannel, connectionTimeout, "t3://" + address.getHostName() + ':' + port);
      SocketMuxer.getMuxer().register(muxableSocket);
      SocketMuxer.getMuxer().read(muxableSocket);
      return muxableSocket.getConnection();
   }

   public MsgAbbrevJVMConnection createConnection(String host, InetAddress address, int port, ServerChannel networkChannel, JVMID destinationJVMID, int connectionTimeout, String partitionUrl) throws IOException {
      MuxableSocketT3 muxableSocket = (MuxableSocketT3)MuxableSocketT3.createMuxableSocket(address, port, networkChannel, connectionTimeout, partitionUrl);
      SocketMuxer.getMuxer().register(muxableSocket);
      SocketMuxer.getMuxer().read(muxableSocket);
      return muxableSocket.getConnection();
   }
}
