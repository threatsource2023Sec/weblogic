package weblogic.rjvm.http.client;

import java.io.IOException;
import java.net.InetAddress;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.JVMID;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.RJVMConnectionFactory;
import weblogic.rjvm.http.HTTPSThinClientJVMConnection;
import weblogic.work.WorkManagerFactory;

public class HTTPSClientConnectionFactory implements RJVMConnectionFactory {
   public MsgAbbrevJVMConnection createConnection(String host, InetAddress address, int port, ServerChannel networkChannel, JVMID destinationJVMID, int connectionTimeout) throws IOException {
      return this.createConnection(host, address, port, networkChannel, destinationJVMID, connectionTimeout, "https://" + address.getHostName() + ':' + port);
   }

   public MsgAbbrevJVMConnection createConnection(String host, InetAddress address, int port, ServerChannel networkChannel, JVMID destinationJVMID, int connectionTimeout, String partitionUrl) throws IOException {
      HTTPSThinClientJVMConnection connection = new HTTPSThinClientJVMConnection(networkChannel, partitionUrl);
      connection.connect(host, address, port, connectionTimeout);
      WorkManagerFactory.getInstance().getSystem().schedule(connection);
      return connection;
   }
}
